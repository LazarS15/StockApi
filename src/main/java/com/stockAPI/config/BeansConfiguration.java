package com.stockAPI.config;

import com.stockAPI.dto.StockDto;
import com.stockAPI.entity.Stock;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setAmbiguityIgnored(true);

        modelMapper.typeMap(StockDto.class, Stock.class)
                .addMappings(mapper -> {
                    mapper.skip(Stock::setId);
                    mapper.skip(Stock::setCompany);
                });

        return modelMapper;
    }
}
