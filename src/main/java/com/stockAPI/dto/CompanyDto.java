package com.stockAPI.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CompanyDto {

    private String name;

    @JsonFormat(pattern = "yyyy-M-d")
    private LocalDate creationDate;

    private String address;
}
