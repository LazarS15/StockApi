package com.stockAPI.dataLoader;

import com.stockAPI.entity.Company;
import com.stockAPI.repository.CompanyRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!test")
public class CompanyDataLoader {

    private final CompanyRepository companyRepository;

    @PostConstruct
    public void loadCompanies() {
        if (companyRepository.count() == 0) {

            companyRepository.saveAll(List.of(
                    Company.builder()
                            .symbol("AMZN")
                            .name("Amazon")
                            .creationDate(LocalDate.of(1995, 4, 22))
                            .address("Amazon Address")
                            .build(),

                    Company.builder()
                            .symbol("AAPL")
                            .name("Apple")
                            .creationDate(LocalDate.of(1979, 2, 9))
                            .address("Apple Address")
                            .build(),

                    Company.builder()
                            .symbol("FB")
                            .name("Facebook")
                            .creationDate(LocalDate.of(2005, 4, 25))
                            .address("Facebook Address")
                            .build(),

                    Company.builder()
                            .symbol("GOOGL")
                            .name("Google")
                            .creationDate(LocalDate.of(1998, 3, 12))
                            .address("Google Address")
                            .build(),

                    Company.builder()
                            .symbol("NFLX")
                            .name("Netflix")
                            .creationDate(LocalDate.of(2002, 3, 5))
                            .address("Netflix Address")
                            .build()
            ));
            log.info("Successfully loaded company data");
        }
    }
}
