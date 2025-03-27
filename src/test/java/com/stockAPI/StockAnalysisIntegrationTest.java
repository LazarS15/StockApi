package com.stockAPI;

import com.stockAPI.dto.request.AnalysisRequestDto;
import com.stockAPI.dto.response.AnalysisResponseDto;
import com.stockAPI.dto.response.PeriodAnalysis;
import com.stockAPI.entity.Company;
import com.stockAPI.entity.Stock;
import com.stockAPI.repository.CompanyRepository;
import com.stockAPI.repository.StockRepository;
import com.stockAPI.service.StockAnalysisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class StockAnalysisIntegrationTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private StockAnalysisService stockAnalysisService;

    private List<Stock> testStocks;
    private LocalDate startDate;
    private LocalDate endDate;

    @BeforeEach
    void initData() {
        startDate = LocalDate.of(2025, 1, 7);
        endDate = LocalDate.of(2025, 1, 12);

        Company apple = new Company("AAPL", "Apple", null, "Address 1");
        Company amazon = new Company("AMZN", "Amazon", null, "Address 2");
        Company google = new Company("GOOGL", "Google", null, "Address 3");

        companyRepository.saveAllAndFlush(List.of(apple, amazon, google));

        testStocks = List.of(
                createStock(apple, LocalDate.of(2025, 1, 7), 100.0),
                createStock(apple, LocalDate.of(2025, 1, 8), 110.0),
                createStock(apple, LocalDate.of(2025, 1, 9), 90.0),
                createStock(apple, LocalDate.of(2025, 1, 10), 150.0),
                createStock(apple, LocalDate.of(2025, 1, 13), 160.0),

                createStock(amazon, LocalDate.of(2025, 1, 9), 100.0),
                createStock(amazon, LocalDate.of(2025, 1, 10), 120.0),

                createStock(google, LocalDate.of(2025, 1, 8), 100.0),
                createStock(google, LocalDate.of(2025, 1, 9), 95.0),
                createStock(google, LocalDate.of(2025, 1, 10), 190.0)
        );

        stockRepository.saveAll(testStocks);
    }

    @Test
    void testAnalyzeStocks_shouldReturnMaxPossibleProfitAndBestPossibleTrade() {
        AnalysisRequestDto request = AnalysisRequestDto.builder()
                .symbol("AAPL")
                .startDate(startDate)
                .endDate(endDate)
                .build();

        AnalysisResponseDto response = stockAnalysisService.analyzeStocks(request);

        PeriodAnalysis analysis = response.getRequestedPeriod();

        assertNull(response.getPreviousPeriod());
        assertNull(response.getNextPeriod());

        assertEquals(70.0, analysis.getMaxPossibleProfit());
        assertEquals(60.0, analysis.getSingleTradeData().getProfit());
    }

    @Test
    void testFindBetterPerformingStocks_shouldReturnGoogleForRequestedPeriod() {
        AnalysisRequestDto request = AnalysisRequestDto.builder()
                .symbol("AAPL")
                .startDate(startDate)
                .endDate(endDate)
                .build();

        AnalysisResponseDto response = stockAnalysisService.analyzeStocks(request);

        assertEquals(List.of("GOOGL"), response.getRequestedPeriod().getBetterPerformingStocks());
    }

    private Stock createStock(Company company, LocalDate date, double close) {
        return Stock.builder()
                .company(company)
                .date(date)
                .close(close)
                .build();
    }
}
