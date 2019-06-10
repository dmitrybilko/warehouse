package com.ssishaefer.warehouse.exception;

import lombok.Getter;

@Getter
public class StockItemAlreadyExistsException extends RuntimeException {

    private Long id;

    private Long article;

    private Long location;

    private StockItemAlreadyExistsException(final Long id, final Long article,
        final Long location) {
        super();
        this.id = id;
        this.article = article;
        this.location = location;
    }

    public static StockItemAlreadyExistsException forStockItem(final Long id, final Long article,
        final Long location) {
        return new StockItemAlreadyExistsException(id, article, location);
    }
}
