package com.ssishaefer.warehouse.assembler;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.ssishaefer.warehouse.controller.ArticleController;
import com.ssishaefer.warehouse.dto.ArticleDto;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ArticleResourceAssembler
    implements ResourceAssembler<ArticleDto, Resource<ArticleDto>> {

    @Override
    public Resource<ArticleDto> toResource(final ArticleDto article) {
        return new Resource<>(article,
            linkTo(methodOn(ArticleController.class).getArticle(article.getId())).withSelfRel());
    }
}
