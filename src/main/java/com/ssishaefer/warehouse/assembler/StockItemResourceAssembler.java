package com.ssishaefer.warehouse.assembler;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.ssishaefer.warehouse.controller.StockItemController;
import com.ssishaefer.warehouse.dto.StockItemDto;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class StockItemResourceAssembler
    implements ResourceAssembler<StockItemDto, Resource<StockItemDto>> {

    @Override
    public Resource<StockItemDto> toResource(final StockItemDto item) {
        return new Resource<>(item,
            linkTo(methodOn(StockItemController.class).getStockItem(item.getId())).withSelfRel());
    }
}
