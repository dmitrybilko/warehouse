package com.ssishaefer.warehouse.exception;

import lombok.Getter;

@Getter
public class ArticleNotFoundException extends RuntimeException {

    private Long id;

    private String name;

    private ArticleNotFoundException(final Long id) {
        super();
        this.id = id;
    }

    private ArticleNotFoundException(final String name) {
        super();
        this.name = name;
    }

    public static ArticleNotFoundException forArticle(final Long id) {
        return new ArticleNotFoundException(id);
    }

    public static ArticleNotFoundException forArticle(final String name) {
        return new ArticleNotFoundException(name);
    }
}
