package com.stockAPI.service;

import com.stockAPI.dto.StockDto;
import com.stockAPI.entity.Stock;
import java.util.List;
import java.util.UUID;

public interface StockService {
    Stock createStock(StockDto dto);
    Stock getStockById(UUID id);
    List<Stock> getAllStocks();
    Stock updateStock(UUID id, StockDto dto);
    void deleteStock(UUID id);
    List<Stock> getStocksByCompanySymbol(String symbol);
}