package com.stockAPI.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PeriodAnalysis {

    private Trade singleTradeData;

    private Double maxPossibleProfit;

    private List<String> betterPerformingStocks;

}
