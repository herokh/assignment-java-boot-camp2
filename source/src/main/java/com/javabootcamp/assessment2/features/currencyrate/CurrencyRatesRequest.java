package com.javabootcamp.assessment2.features.currencyrate;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CurrencyRatesRequest {
    private List<CurrencyRateItemRequest> rates;
}
