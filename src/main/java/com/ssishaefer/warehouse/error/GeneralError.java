package com.ssishaefer.warehouse.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("WeakerAccess")
public class GeneralError {

    private int status;

    private String title;

    private String details;
}
