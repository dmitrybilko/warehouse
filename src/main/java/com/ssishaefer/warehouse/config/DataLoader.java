package com.ssishaefer.warehouse.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.ssishaefer.warehouse.dto.ArticleDto;
import com.ssishaefer.warehouse.dto.LocationDto;
import com.ssishaefer.warehouse.service.ArticleService;
import com.ssishaefer.warehouse.service.LocationService;

import static java.util.stream.LongStream.rangeClosed;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final WarehouseProperties warehouseProperties;

    private final ArticleService articleService;

    private final LocationService locationService;

    @Override
    public void run(final ApplicationArguments args) {
        checkArticles();
        checkLocations();
    }

    private void checkArticles() {
        log.info("Checking loaded articles");
        final String name = warehouseProperties.getArticleDefaultName();
        rangeClosed(1, warehouseProperties.getArticlesAmount())
            .parallel()
            .forEach(i -> articleService
                .findArticle(name + i)
                .orElseGet(() -> articleService.createArticle(ArticleDto
                    .builder()
                    .name(name + i)
                    .build())));
    }

    private void checkLocations() {
        log.info("Checking loaded locations");
        final String name = warehouseProperties.getLocationDefaultName();
        rangeClosed(1, warehouseProperties.getLocationsAmount())
            .parallel()
            .forEach(i -> locationService
                .findLocation(name + i)
                .orElseGet(() -> locationService.createLocation(LocationDto
                    .builder()
                    .name(name + i)
                    .build())));
    }
}
