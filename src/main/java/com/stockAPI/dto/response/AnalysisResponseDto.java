package com.stockAPI.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnalysisResponseDto {

    private PeriodAnalysis previousPeriod;

    private PeriodAnalysis requestedPeriod;

    private PeriodAnalysis nextPeriod;
}
