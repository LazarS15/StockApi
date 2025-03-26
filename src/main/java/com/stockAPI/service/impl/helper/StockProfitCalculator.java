package com.stockAPI.service.impl.helper;

import com.stockAPI.dto.response.Trade;
import com.stockAPI.entity.Stock;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockProfitCalculator {

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

    public double calculateTotalProfit(List<Stock> stocks) {
        double totalProfit = 0;
        for (int i = 0; i < stocks.size() - 1; i++) {
            if (stocks.get(i + 1).getClose() > stocks.get(i).getClose()) {
                totalProfit += stocks.get(i + 1).getClose() - stocks.get(i).getClose();
            }
        }
        return totalProfit;
    }
}
