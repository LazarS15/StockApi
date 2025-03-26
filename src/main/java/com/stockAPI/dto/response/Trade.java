package com.stockAPI.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Trade {
    private LocalDate buyDate;
    private Double buyPrice;

    private LocalDate sellDate;
    private Double sellPrice;

    private Double profit;
}
