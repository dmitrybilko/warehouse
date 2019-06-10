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

import com.ssishaefer.warehouse.assembler.LocationResourceAssembler;
import com.ssishaefer.warehouse.dto.LocationDto;
import com.ssishaefer.warehouse.service.LocationService;

import static java.net.URI.create;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    private final LocationResourceAssembler locationAssembler;

    @PostMapping
    public ResponseEntity createLocation(@Valid @RequestBody final LocationDto location) {
        final Resource<LocationDto> resource =
            locationAssembler.toResource(locationService.createLocation(location));
        return created(create(resource
            .getId()
            .getHref())).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateLocation(@Valid @RequestBody final LocationDto update,
        @PathVariable final Long id) {
        return ok(locationAssembler.toResource(locationService.updateLocation(update, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteLocation(@PathVariable final Long id) {
        locationService.deleteLocation(id);
        return noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity getLocation(@PathVariable final Long id) {
        final Optional<LocationDto> location = locationService.findLocation(id);
        return location.isPresent()
            ? ok(locationAssembler.toResource(location.get()))
            : noContent().build();
    }

    @GetMapping
    public ResponseEntity getLocations(final Pageable pageable,
        final PagedResourcesAssembler<LocationDto> assembler) {
        final Page<LocationDto> page = locationService.findLocations(pageable);
        return page.isEmpty()
            ? noContent().build()
            : ok(assembler.toResource(page));
    }
}
