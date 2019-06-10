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

import com.ssishaefer.warehouse.assembler.ArticleResourceAssembler;
import com.ssishaefer.warehouse.dto.ArticleDto;
import com.ssishaefer.warehouse.service.ArticleService;

import static java.net.URI.create;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    private final ArticleResourceAssembler articleAssembler;

    @PostMapping
    public ResponseEntity createArticle(@Valid @RequestBody final ArticleDto article) {
        final Resource<ArticleDto> resource =
            articleAssembler.toResource(articleService.createArticle(article));
        return created(create(resource
            .getId()
            .getHref())).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateArticle(@Valid @RequestBody final ArticleDto update,
        @PathVariable final Long id) {
        return ok(articleAssembler.toResource(articleService.updateArticle(update, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteArticle(@PathVariable final Long id) {
        articleService.deleteArticle(id);
        return noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity getArticle(@PathVariable final Long id) {
        final Optional<ArticleDto> article = articleService.findArticle(id);
        return article.isPresent()
            ? ok(articleAssembler.toResource(article.get()))
            : noContent().build();
    }

    @GetMapping
    public ResponseEntity getArticles(final Pageable pageable,
        final PagedResourcesAssembler<ArticleDto> assembler) {
        final Page<ArticleDto> page = articleService.findArticles(pageable);
        return page.isEmpty()
            ? noContent().build()
            : ok(assembler.toResource(page));
    }
}
