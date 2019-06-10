package com.ssishaefer.warehouse.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {

    private Long id;

    @NotBlank
    private String name;

    private Set<StockItemDto> items;

    @Override
    public String toString() {
        return name;
    }
}
