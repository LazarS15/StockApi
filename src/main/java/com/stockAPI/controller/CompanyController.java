package com.stockAPI.controller;


import com.stockAPI.dto.CompanyDto;
import com.stockAPI.entity.Company;
import com.stockAPI.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody CompanyDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(companyService.createCompany(dto));
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<Company> getCompanyBySymbol(@PathVariable String symbol) {
        return ResponseEntity.ok(companyService.getCompanyBySymbol(symbol));
    }

    @PutMapping("/{symbol}")
    public ResponseEntity<Company> updateCompany(
            @PathVariable String symbol,
            @RequestBody CompanyDto dto) {
        return ResponseEntity.ok(companyService.updateCompany(symbol, dto));
    }

    @DeleteMapping("/{symbol}")
    public ResponseEntity<Void> deleteCompany(@PathVariable String symbol) {
        companyService.deleteCompany(symbol);
        return ResponseEntity.noContent().build();
    }
}
