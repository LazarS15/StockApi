package com.stockAPI.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AnalisysRequestDto {
    private String symbol;

    @JsonFormat(pattern = "yyyy-M-d")
    private LocalDate beginDate;

    @JsonFormat(pattern = "yyyy-M-d")
    private LocalDate endDate;
}
