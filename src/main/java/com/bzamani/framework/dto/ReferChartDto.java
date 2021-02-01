package com.bzamani.framework.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReferChartDto {
    private String key;
    private Long value;

    public ReferChartDto(String key, Long value) {
        this.key = key;
        this.value = value;
    }
}
