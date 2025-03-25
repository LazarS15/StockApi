package com.stockAPI.controller;

import com.stockAPI.dto.StockDto;
import com.stockAPI.entity.Stock;
import com.stockAPI.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody StockDto stockDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(stockService.createStock(stockDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable UUID id) {
        return ResponseEntity.ok(stockService.getStockById(id));
    }

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        return ResponseEntity.ok(stockService.getAllStocks());
    }

    @GetMapping("/company/{symbol}")
    public ResponseEntity<List<Stock>> getStocksByCompany(@PathVariable String symbol) {
        return ResponseEntity.ok(stockService.getStocksByCompanySymbol(symbol));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(
            @PathVariable UUID id,
            @RequestBody StockDto stockDto) {
        return ResponseEntity.ok(stockService.updateStock(id, stockDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable UUID id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }
}