package com.ssishaefer.warehouse.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.ssishaefer.warehouse.assembler.StockItemResourceAssembler;
import com.ssishaefer.warehouse.dto.StockItemDto;
import com.ssishaefer.warehouse.service.StockItemService;

import static java.net.URI.create;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class StockItemController {

    private final StockItemService stockItemService;

    private final StockItemResourceAssembler stockItemAssembler;

    @PostMapping
    public ResponseEntity createStockItem(@Valid @RequestBody final StockItemDto item) {
        final Resource<StockItemDto> resource =
            stockItemAssembler.toResource(stockItemService.createStockItem(item));
        return created(create(resource
            .getId()
            .getHref())).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateStockItem(@Valid @RequestBody final StockItemDto update,
        @PathVariable final Long id) {
        return ok(stockItemAssembler.toResource(stockItemService.updateStockItem(update, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStockItem(@PathVariable final Long id) {
        stockItemService.deleteStockItem(id);
        return noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity getStockItem(@PathVariable final Long id) {
        final Optional<StockItemDto> item = stockItemService.findStockItem(id);
        return item.isPresent()
            ? ok(stockItemAssembler.toResource(item.get()))
            : noContent().build();
    }

    @GetMapping
    public ResponseEntity getArticles(final Pageable pageable,
        final PagedResourcesAssembler<StockItemDto> assembler) {
        final Page<StockItemDto> page = stockItemService.findStockItems(pageable);
        return page.isEmpty()
            ? noContent().build()
            : ok(assembler.toResource(page));
    }
}
