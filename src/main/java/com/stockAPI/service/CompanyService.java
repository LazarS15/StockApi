package com.stockAPI.service;

import com.stockAPI.dto.CompanyDto;
import com.stockAPI.entity.Company;
import jakarta.validation.Valid;

import java.util.List;

public interface CompanyService {
    Company createCompany(@Valid CompanyDto dto);
    Company getCompanyBySymbol(String symbol);
    List<Company> getAllCompanies();
    Company updateCompany(String symbol, @Valid CompanyDto dto);
    void deleteCompany(String symbol);
}