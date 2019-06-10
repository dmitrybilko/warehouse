package com.ssishaefer.warehouse.exception;

import lombok.Getter;

@Getter
public class ArticleAlreadyExistsException extends RuntimeException {

    private Long id;

    private String name;

    private ArticleAlreadyExistsException(final Long id, final String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public static ArticleAlreadyExistsException forArticle(final Long id, final String name) {
        return new ArticleAlreadyExistsException(id, name);
    }
}
