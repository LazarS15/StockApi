package com.stockAPI.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StockDto {
    private String companySymbol;

    @JsonFormat(pattern = "yyyy-M-d")
    private LocalDate date;

    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double adjClose;
    private Long volume;
}
