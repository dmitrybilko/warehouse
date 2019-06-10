package com.ssishaefer.warehouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.modelmapper.ModelMapper;

import com.ssishaefer.warehouse.dto.StockItemDto;
import com.ssishaefer.warehouse.model.StockItem;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper mapper = new ModelMapper();
        mapper
            .createTypeMap(StockItem.class, StockItemDto.class)
            .addMapping(item -> item
                .getArticle()
                .getId(), StockItemDto::setArticle)
            .addMapping(item -> item
                .getLocation()
                .getId(), StockItemDto::setLocation);
        return mapper;
    }
}
