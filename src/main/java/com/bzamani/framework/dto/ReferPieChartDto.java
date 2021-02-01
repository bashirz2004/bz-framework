package com.bzamani.framework.dto;

import com.bzamani.framework.model.refer.ReferStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReferPieChartDto {
    private ReferStatus status;
    private Long percent;

    public ReferPieChartDto(ReferStatus status, Long percent) {
        this.status = status;
        this.percent = percent;
    }
}
