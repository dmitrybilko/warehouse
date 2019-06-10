package com.ssishaefer.warehouse.exception;

import lombok.Getter;

@Getter
public class LocationAlreadyExistsException extends RuntimeException {

    private Long id;

    private String name;

    private LocationAlreadyExistsException(final Long id, final String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public static LocationAlreadyExistsException forLocation(final Long id, final String name) {
        return new LocationAlreadyExistsException(id, name);
    }
}
