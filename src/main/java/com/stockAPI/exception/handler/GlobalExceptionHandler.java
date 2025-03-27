package com.stockAPI.exception.handler;

import com.stockAPI.dto.response.ExceptionDto;
import com.stockAPI.exception.model.CompanyNotFoundException;
import com.stockAPI.exception.model.StockNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.DateTimeException;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleNotFoundException(Exception ex) {
        log.error("Internal Server Error: {}", ex.getMessage());
        return buildException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler({CompanyNotFoundException.class, StockNotFoundException.class})
    public ResponseEntity<ExceptionDto> handleNotFoundException(RuntimeException ex) {
        log.error("Not Found: {}", ex.getMessage());
        return buildException(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<ExceptionDto> handleBadRequest(DateTimeException ex) {
        log.error("Bad Request: {}", ex.getMessage());
        return buildException(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private ResponseEntity<ExceptionDto> buildException(HttpStatus status, String message) {
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .code(status.value())
                .timeStamp(LocalDateTime.now())
                .message(message)
                .build();
        return new ResponseEntity<>(exceptionDto, status);
    }
}
