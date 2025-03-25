package com.stockAPI.service.impl;

import com.stockAPI.dto.CompanyDto;
import com.stockAPI.entity.Company;
import com.stockAPI.exception.CompanyNotFoundException;
import com.stockAPI.repository.CompanyRepository;
import com.stockAPI.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Company createCompany(CompanyDto dto) {
        Company company = modelMapper.map(dto, Company.class);
        return companyRepository.save(company);
    }

    @Transactional(readOnly = true)
    @Override
    public Company getCompanyBySymbol(String symbol) {
        return companyRepository.findById(symbol)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with symbol: " + symbol));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Transactional
    @Override
    public Company updateCompany(String symbol, CompanyDto dto) {
        Company company = getCompanyBySymbol(symbol);
        modelMapper.map(dto, company);
        return companyRepository.save(company);
    }

    @Transactional
    @Override
    public void deleteCompany(String symbol) {
        companyRepository.deleteById(symbol);
    }
}
