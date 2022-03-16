package com.javabootcamp.assessment2.features.currencyrate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyRateItemRequest {
    private String code;
    private double rate;
}
