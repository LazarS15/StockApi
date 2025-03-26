package com.stockAPI.service.impl;

import com.stockAPI.dto.request.AnalysisRequestDto;
import com.stockAPI.dto.response.AnalysisResponseDto;
import com.stockAPI.dto.response.PeriodAnalysis;
import com.stockAPI.entity.Stock;
import com.stockAPI.repository.StockRepository;
import com.stockAPI.service.StockAnalysisService;
import com.stockAPI.service.impl.helper.StockProfitCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockAnalysisServiceImpl implements StockAnalysisService {

    private final StockRepository stockRepository;
    private final StockProfitCalculator calculator;

    @Override
    public AnalysisResponseDto analyzeStocks(AnalysisRequestDto dto) {
        validateRequest(dto);

        long daysBetween = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate()) + 1;

        LocalDate previousStartDate = dto.getStartDate().minusDays(daysBetween);
        LocalDate previousEndDate = dto.getStartDate().minusDays(1);

        LocalDate nextStartDate = dto.getEndDate().plusDays(1);
        LocalDate nextEndDate = dto.getEndDate().plusDays(daysBetween);

        PeriodAnalysis previousAnalysis = analyzePeriod(dto.getSymbol(), previousStartDate, previousEndDate);
        PeriodAnalysis requestedAnalysis = analyzePeriod(dto.getSymbol(), dto.getStartDate(), dto.getEndDate());
        PeriodAnalysis nextAnalysis = analyzePeriod(dto.getSymbol(), nextStartDate, nextEndDate);

        return AnalysisResponseDto.builder()
                .previousPeriod(previousAnalysis)
                .requestedPeriod(requestedAnalysis)
                .nextPeriod(nextAnalysis)
                .build();
    }

    private PeriodAnalysis analyzePeriod(String symbol, LocalDate startDate, LocalDate endDate) {
        List<Stock> stocks = stockRepository.findByCompanySymbolAndDateBetween(symbol, startDate, endDate);

        if (stocks == null || stocks.isEmpty()) {
            return null;
        }

        return PeriodAnalysis.builder()
                .singleTradeData(calculator.findBestSingleTrade(stocks))
                .maxPossibleProfit(calculator.calculateTotalProfit(stocks))
                .build();
    }

    private void validateRequest(AnalysisRequestDto dto) {
        LocalDate today = LocalDate.now();

        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new DateTimeException("End date can't be before start date.");
        }

        if (dto.getStartDate().isAfter(today) || dto.getEndDate().isAfter(today)) {
            throw new DateTimeException("Dates can't be in the future.");
        }
    }
}
