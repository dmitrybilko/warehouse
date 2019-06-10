package com.ssishaefer.warehouse.dto;

import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockItemDto {

    private Long id;

    @Positive
    private Long article;

    @Positive
    private Long location;
}
