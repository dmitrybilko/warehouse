package com.ssishaefer.warehouse.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;

import com.ssishaefer.warehouse.dto.ArticleDto;
import com.ssishaefer.warehouse.model.Article;
import com.ssishaefer.warehouse.repo.ArticleRepo;

import static com.ssishaefer.warehouse.exception.ArticleAlreadyExistsException.forArticle;
import static com.ssishaefer.warehouse.exception.ArticleNotFoundException.forArticle;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepo articleRepo;

    private final ModelMapper modelMapper;

    @Transactional
    public ArticleDto createArticle(final ArticleDto create) {
        log.info("Creating article '{}'", create);
        articleRepo
            .findByName(create.getName())
            .ifPresent(article -> { throw forArticle(article.getId(), article.getName()); });
        return modelMapper.map(articleRepo.save(modelMapper.map(create, Article.class)),
            ArticleDto.class);
    }

    @Transactional
    public ArticleDto updateArticle(final ArticleDto update, final Long id) {
        log.info("Updating article '{}' with '{}'", id, update);
        final Article article = articleRepo
            .findById(id)
            .orElseThrow(() -> forArticle(id));
        article.setName(update.getName());
        return modelMapper.map(articleRepo.save(article), ArticleDto.class);
    }

    @Transactional
    public void deleteArticle(final Long id) {
        log.info("Deleting article '{}'", id);
        final Article article = articleRepo
            .findById(id)
            .orElseThrow(() -> forArticle(id));
        article
            .getStockItems()
            .forEach(item -> item
                .getLocation()
                .setStockItem(null));
        articleRepo.delete(article);
    }

    public Optional<ArticleDto> findArticle(final Long id) {
        log.info("Finding article '{}'", id);
        return articleRepo
            .findById(id)
            .map(article -> modelMapper.map(article, ArticleDto.class));
    }

    public Optional<ArticleDto> findArticle(final String name) {
        log.info("Finding article '{}'", name);
        return articleRepo
            .findByName(name)
            .map(article -> modelMapper.map(article, ArticleDto.class));
    }

    public Page<ArticleDto> findArticles(final Pageable pageable) {
        log.info("Finding all articles");
        return articleRepo
            .findAll(pageable)
            .map(article -> modelMapper.map(article, ArticleDto.class));
    }
}
