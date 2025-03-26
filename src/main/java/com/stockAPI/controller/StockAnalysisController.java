package com.stockAPI.controller;

import com.stockAPI.dto.request.AnalysisRequestDto;
import com.stockAPI.dto.response.AnalysisResponseDto;
import com.stockAPI.service.StockAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class StockAnalysisController {

    private final StockAnalysisService stockAnalysisService;

    @GetMapping
    public ResponseEntity<AnalysisResponseDto> analysePrices(@RequestBody AnalysisRequestDto dto) {
        return ResponseEntity.ok(stockAnalysisService.analyzeStocks(dto));
    }
}
