package com.stockAPI.service;

import com.stockAPI.dto.request.AnalysisRequestDto;
import com.stockAPI.dto.response.AnalysisResponseDto;

public interface StockAnalysisService {

    AnalysisResponseDto analyzeStocks(AnalysisRequestDto dto);
}
