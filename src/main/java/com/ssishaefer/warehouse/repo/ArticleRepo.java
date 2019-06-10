package com.ssishaefer.warehouse.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssishaefer.warehouse.model.Article;

public interface ArticleRepo extends JpaRepository<Article, Long> {

    Optional<Article> findByName(final String name);
}
