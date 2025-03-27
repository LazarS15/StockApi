package com.stockAPI.dataLoader;

import com.stockAPI.entity.Company;
import com.stockAPI.entity.Stock;
import com.stockAPI.repository.CompanyRepository;
import com.stockAPI.repository.StockRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!test")
@DependsOn("companyDataLoader")
public class StockDataLoader {

    private final StockRepository stockRepository;
    private final CompanyRepository companyRepository;

    private final List<String> STOCK_FILES = List.of(
            "data/Amazon.csv", "data/Apple.csv",
            "data/Facebook.csv", "data/Google.csv", "data/Netflix.csv"
    );

    @PostConstruct
    public void loadStocks() {
        if (stockRepository.count() == 0) {
            STOCK_FILES.forEach(this::loadStockDataFromFile);
            log.info("Successfully loaded stock data");
        }
    }

    private void loadStockDataFromFile(String filePath) {
        String name = filePath.substring(5, filePath.indexOf('.'));
        Company company = companyRepository.findByName(name);

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new ClassPathResource(filePath).getInputStream()))) {

            br.readLine();

            List<Stock> stocks = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length >= 7) {
                    Stock stock = Stock.builder()
                            .company(company)
                            .date(parseDate(values[0]))
                            .open(parseDouble(values[1]))
                            .high(parseDouble(values[2]))
                            .low(parseDouble(values[3]))
                            .close(parseDouble(values[4]))
                            .adjClose(parseDouble(values[5]))
                            .volume(parseLong(values[6]))
                            .build();
                    stocks.add(stock);
                }
            }
            stockRepository.saveAll(stocks);

        } catch (Exception e) {
            log.error("Error loading stock data from " + filePath + ": " + e.getMessage());
        }
    }

    private LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr.trim());
    }

    private double parseDouble(String value) {
        return Double.parseDouble(value.trim());
    }

    private long parseLong(String value) {
        return Long.parseLong(value.trim());
    }
}
