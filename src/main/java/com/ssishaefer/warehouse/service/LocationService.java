package com.ssishaefer.warehouse.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;

import com.ssishaefer.warehouse.dto.LocationDto;
import com.ssishaefer.warehouse.model.Location;
import com.ssishaefer.warehouse.model.StockItem;
import com.ssishaefer.warehouse.repo.LocationRepo;

import static com.ssishaefer.warehouse.exception.LocationAlreadyExistsException.forLocation;
import static com.ssishaefer.warehouse.exception.LocationNotFoundException.forLocation;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepo locationRepo;

    private final ModelMapper modelMapper;

    @Transactional
    public LocationDto createLocation(final LocationDto create) {
        log.info("Creating location '{}'", create);
        locationRepo
            .findByName(create.getName())
            .ifPresent(location -> { throw forLocation(location.getId(), location.getName()); });
        return modelMapper.map(locationRepo.save(modelMapper.map(create, Location.class)),
            LocationDto.class);
    }

    @Transactional
    public LocationDto updateLocation(final LocationDto update, final Long id) {
        log.info("Updating location '{}' with '{}'", id, update);
        final Location location = locationRepo
            .findById(id)
            .orElseThrow(() -> forLocation(id));
        location.setName(update.getName());
        return modelMapper.map(locationRepo.save(location), LocationDto.class);
    }

    @Transactional
    public void deleteLocation(final Long id) {
        log.info("Deleting location '{}'", id);
        final Location location = locationRepo
            .findById(id)
            .orElseThrow(() -> forLocation(id));
        final StockItem item = location.getStockItem();
        if (item != null) item
            .getArticle()
            .getStockItems()
            .remove(item);
        locationRepo.delete(location);
    }

    public Optional<LocationDto> findLocation(final Long id) {
        log.info("Finding location '{}'", id);
        return locationRepo
            .findById(id)
            .map(location -> modelMapper.map(location, LocationDto.class));
    }

    public Optional<LocationDto> findLocation(final String name) {
        log.info("Finding location '{}'", name);
        return locationRepo
            .findByName(name)
            .map(location -> modelMapper.map(location, LocationDto.class));
    }

    public Page<LocationDto> findLocations(final Pageable pageable) {
        log.info("Finding all locations");
        return locationRepo
            .findAll(pageable)
            .map(location -> modelMapper.map(location, LocationDto.class));
    }
}
