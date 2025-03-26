package com.stockAPI.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PeriodAnalysis {

    private Trade singleTradeData;

    private Double maxPossibleProfit;
}
