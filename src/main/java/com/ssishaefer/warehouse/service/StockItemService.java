package com.ssishaefer.warehouse.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;

import com.ssishaefer.warehouse.dto.StockItemDto;
import com.ssishaefer.warehouse.model.StockItem;
import com.ssishaefer.warehouse.repo.ArticleRepo;
import com.ssishaefer.warehouse.repo.LocationRepo;
import com.ssishaefer.warehouse.repo.StockItemRepo;

import static com.ssishaefer.warehouse.exception.ArticleNotFoundException.forArticle;
import static com.ssishaefer.warehouse.exception.LocationNotFoundException.forLocation;
import static com.ssishaefer.warehouse.exception.StockItemAlreadyExistsException.forStockItem;
import static com.ssishaefer.warehouse.exception.StockItemNotFoundException.forStockItem;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockItemService {

    private final StockItemRepo stockItemRepo;

    private final ArticleRepo articleRepo;

    private final LocationRepo locationRepo;

    private final ModelMapper modelMapper;

    @Transactional
    public StockItemDto createStockItem(final StockItemDto create) {
        final Long article = create.getArticle();
        final Long location = create.getLocation();
        log.info("Creating stock item '{}' in '{}'", article, location);
        stockItemRepo
            .findByArticleIdAndLocationId(article, location)
            .ifPresent(item -> { throw forStockItem(item.getId(), article, location); });

        final StockItem item = new StockItem();
        item.setArticle(articleRepo
            .findById(article)
            .orElseThrow(() -> forArticle(article)));
        item.setLocation(locationRepo
            .findById(location)
            .orElseThrow(() -> forLocation(location)));
        return modelMapper.map(stockItemRepo.save(item), StockItemDto.class);
    }

    @Transactional
    public StockItemDto updateStockItem(final StockItemDto update, final Long id) {
        final Long article = update.getArticle();
        final Long location = update.getLocation();
        log.info("Updating stock item '{}' in '{}'", article, location);

        final StockItem item = stockItemRepo
            .findById(id)
            .orElseThrow(() -> forStockItem(id));
        item.setArticle(articleRepo
            .findById(article)
            .orElseThrow(() -> forArticle(article)));
        item.setLocation(locationRepo
            .findById(location)
            .orElseThrow(() -> forLocation(location)));
        return modelMapper.map(stockItemRepo.save(item), StockItemDto.class);
    }

    @Transactional
    public void deleteStockItem(final Long id) {
        log.info("Deleting stock item '{}'", id);
        final StockItem item = stockItemRepo
            .findById(id)
            .orElseThrow(() -> forStockItem(id));
        item
            .getArticle()
            .getStockItems()
            .remove(item);
        item.setLocation(null);
        stockItemRepo.delete(item);
    }

    public Optional<StockItemDto> findStockItem(final Long id) {
        log.info("Finding stock item '{}'", id);
        return stockItemRepo
            .findById(id)
            .map(item -> modelMapper.map(item, StockItemDto.class));
    }

    public Page<StockItemDto> findStockItems(final Pageable pageable) {
        log.info("Finding all stock items");
        return stockItemRepo
            .findAll(pageable)
            .map(item -> modelMapper.map(item, StockItemDto.class));
    }
}
