package com.ssishaefer.warehouse.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {

    private Long id;

    @NotBlank
    private String name;

    private StockItemDto item;

    @Override
    public String toString() {
        return name;
    }
}
