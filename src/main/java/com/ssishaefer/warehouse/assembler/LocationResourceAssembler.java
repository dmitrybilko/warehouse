package com.ssishaefer.warehouse.assembler;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.ssishaefer.warehouse.controller.LocationController;
import com.ssishaefer.warehouse.dto.LocationDto;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class LocationResourceAssembler
    implements ResourceAssembler<LocationDto, Resource<LocationDto>> {

    @Override
    public Resource<LocationDto> toResource(final LocationDto location) {
        return new Resource<>(location,
            linkTo(methodOn(LocationController.class).getLocation(location.getId())).withSelfRel());
    }
}
