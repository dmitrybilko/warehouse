package com.ssishaefer.warehouse.exception;

import lombok.Getter;

@Getter
public class StockItemNotFoundException extends RuntimeException {

    private Long id;

    private StockItemNotFoundException(final Long id) {
        super();
        this.id = id;
    }

    public static StockItemNotFoundException forStockItem(final Long id) {
        return new StockItemNotFoundException(id);
    }
}
