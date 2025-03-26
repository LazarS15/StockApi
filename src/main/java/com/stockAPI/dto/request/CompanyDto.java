package com.stockAPI.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CompanyDto {

    private String name;

    @JsonFormat(pattern = "yyyy-M-d")
    private LocalDate creationDate;

    private String address;
}
