package com.ssishaefer.warehouse.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssishaefer.warehouse.model.Location;

public interface LocationRepo extends JpaRepository<Location, Long> {

    Optional<Location> findByName(final String name);
}
