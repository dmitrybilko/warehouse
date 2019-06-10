package com.ssishaefer.warehouse;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.ssishaefer.warehouse.config.WarehouseProperties;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableConfigurationProperties(WarehouseProperties.class)
public class WarehouseApplication {

    public static void main(final String[] args) {
        run(WarehouseApplication.class, args);
    }
}
