package com.stockAPI.service.impl.helper;

import com.stockAPI.dto.response.Trade;
import com.stockAPI.entity.Stock;
import com.stockAPI.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StockProfitCalculator {

    private final StockRepository stockRepository;

    public Trade findBestSingleTrade(List<Stock> stocks) {
        if (stocks == null || stocks.size() < 2) {
            return null;
        }

        double minPrice = stocks.getFirst().getClose();
        Stock buyStock = stocks.getFirst();
        double maxProfit = 0;
        Stock bestBuyStock = null;
        Stock bestSellStock = null;

        for (int i = 1; i < stocks.size(); i++) {
            Stock currentStock = stocks.get(i);
            double currentPrice = currentStock.getClose();

            if (currentPrice < minPrice) {
                minPrice = currentPrice;
                buyStock = currentStock;
            }

            double currentProfit = currentPrice - minPrice;
            if (currentProfit > maxProfit) {
                maxProfit = currentProfit;
                bestBuyStock = buyStock;
                bestSellStock = currentStock;
            }
        }

        if (bestBuyStock == null) {
            return null;
        }

        return Trade.builder()
                .buyDate(bestBuyStock.getDate())
                .buyPrice(bestBuyStock.getClose())
                .sellDate(bestSellStock.getDate())
                .sellPrice(bestSellStock.getClose())
                .profit(maxProfit)
                .build();
    }

    public Double calculateTotalProfit(List<Stock> stocks) {
        if (stocks == null || stocks.size() < 2) {
            return null;
        }
        double totalProfit = 0;
        for (int i = 0; i < stocks.size() - 1; i++) {
            if (stocks.get(i + 1).getClose() > stocks.get(i).getClose()) {
                totalProfit += stocks.get(i + 1).getClose() - stocks.get(i).getClose();
            }
        }
        return totalProfit;
    }

    public List<String> findBetterPerformingStocks(String excludedSymbol, LocalDate startDate, LocalDate endDate) {
        List<Stock> allStocks = stockRepository.findByDateBetween(startDate, endDate);

        Map<String, Double> profitBySymbol = allStocks.stream()
                .filter(stock -> stock.getClose() != null && stock.getCompany() != null)
                .collect(Collectors.groupingBy(
                        stock -> stock.getCompany().getSymbol(),
                        Collectors.collectingAndThen(Collectors.toList(),this::calculateTotalProfit)));

        double requestedProfit = profitBySymbol.getOrDefault(excludedSymbol, 0.0);

        return profitBySymbol.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(excludedSymbol))
                .filter(entry -> entry.getValue() > requestedProfit)
                .map(Map.Entry::getKey)
                .toList();

    }
}
