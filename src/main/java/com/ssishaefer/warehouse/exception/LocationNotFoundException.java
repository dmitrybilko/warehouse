package com.ssishaefer.warehouse.exception;

import lombok.Getter;

@Getter
public class LocationNotFoundException extends RuntimeException {

    private Long id;

    private String name;

    private LocationNotFoundException(final Long id) {
        super();
        this.id = id;
    }

    private LocationNotFoundException(final String name) {
        super();
        this.name = name;
    }

    public static LocationNotFoundException forLocation(final Long id) {
        return new LocationNotFoundException(id);
    }

    public static LocationNotFoundException forLocation(final String name) {
        return new LocationNotFoundException(name);
    }
}
