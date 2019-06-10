package com.ssishaefer.warehouse.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties("warehouse")
@Data
public class WarehouseProperties {

    private String articleDefaultName;

    private String locationDefaultName;

    private Long articlesAmount;

    private Long locationsAmount;
}
