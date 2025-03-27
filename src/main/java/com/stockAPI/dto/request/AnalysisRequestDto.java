package com.stockAPI.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AnalysisRequestDto {
    private String symbol;

    @JsonFormat(pattern = "yyyy-M-d")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-M-d")
    private LocalDate endDate;
}
