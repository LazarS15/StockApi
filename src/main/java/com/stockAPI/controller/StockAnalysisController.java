package com.stockAPI.controller;

import com.stockAPI.dto.AnalisysRequestDto;
import com.stockAPI.dto.AnalisysResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class StockAnalysisController {

    @GetMapping
    public ResponseEntity<AnalisysResponseDto> analysePrices(AnalisysRequestDto dto) {

    }
}
