package com.stockAPI.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ExceptionDto {
    private LocalDateTime timeStamp;
    private int code;
    private String message;
}
