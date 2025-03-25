package com.stockAPI.service.impl;

import com.stockAPI.dto.StockDto;
import com.stockAPI.entity.Company;
import com.stockAPI.entity.Stock;
import com.stockAPI.exception.CompanyNotFoundException;
import com.stockAPI.exception.StockNotFoundException;
import com.stockAPI.repository.CompanyRepository;
import com.stockAPI.repository.StockRepository;
import com.stockAPI.service.StockService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Stock createStock(StockDto dto) {
        Company company = companyRepository.findById(dto.getCompanySymbol())
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with symbol: " + dto.getCompanySymbol()));

        Stock stock = modelMapper.map(dto, Stock.class);
        stock.setCompany(company);
        return stockRepository.save(stock);
    }

    @Transactional(readOnly = true)
    @Override
    public Stock getStockById(UUID id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new StockNotFoundException("Stock not found with id: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Transactional
    @Override
    public Stock updateStock(UUID id, StockDto dto) {
        Stock stock = getStockById(id);

        if (dto.getCompanySymbol() != null &&
                !dto.getCompanySymbol().equals(stock.getCompany().getSymbol())) {
            Company company = companyRepository.findById(dto.getCompanySymbol())
                    .orElseThrow(() -> new CompanyNotFoundException("Company not found"));
            stock.setCompany(company);
        }

        modelMapper.typeMap(StockDto.class, Stock.class)
                .addMappings(mapper -> mapper.skip(Stock::setCompany));
        modelMapper.map(dto, stock);
        return stockRepository.save(stock);
    }

    @Transactional
    @Override
    public void deleteStock(UUID id) {
        stockRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Stock> getStocksByCompanySymbol(String symbol) {
        return stockRepository.findByCompanySymbol(symbol);
    }
}
