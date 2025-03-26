package com.stockAPI.repository;

import com.stockAPI.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {
    List<Stock> findByCompanySymbol(String symbol);
    List<Stock> findByCompanySymbolAndDateBetween(String symbol, LocalDate startDate, LocalDate endDate);
}
