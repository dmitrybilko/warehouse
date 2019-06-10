package com.ssishaefer.warehouse.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssishaefer.warehouse.model.StockItem;

public interface StockItemRepo extends JpaRepository<StockItem, Long> {

    Optional<StockItem> findByArticleIdAndLocationId(final Long article, final Long location);
}
