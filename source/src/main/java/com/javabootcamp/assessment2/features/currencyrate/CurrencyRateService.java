package com.javabootcamp.assessment2.features.currencyrate;

import com.javabootcamp.assessment2.entities.CurrencyRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CurrencyRateService {

    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    public CurrencyRateService(CurrencyRateRepository currencyRateRepository) {
        this.currencyRateRepository = currencyRateRepository;
    }

    public CurrencyRateService(){}

    public double convertCurrencyAmountToTHB(List<CurrencyRate> rates, String fromCurrency, double amount) {
        if (fromCurrency.equals(CurrencyCodeConst.THB)) {
            return amount;
        }
        else {
            var expectedRate = rates.stream()
                    .filter(x -> x.getCode().equals(fromCurrency))
                    .findFirst()
                    .orElseThrow(() -> new CurrencyRateNotFoundException("currency rate not found."));
            return expectedRate.getRate() * amount;
        }
    }

    @Transactional
    public void saveRates(CurrencyRatesRequest currencyRatesRequest) {
        var rates = currencyRatesRequest.getRates();
        var currencyRates = rates.stream().map(x -> mapToCurrencyRate(x)).toList();
        currencyRateRepository.saveAll(currencyRates);
    }

    private CurrencyRate mapToCurrencyRate(CurrencyRateItemRequest currencyRateItemRequest) {
        var entity = new CurrencyRate();
        entity.setCode(currencyRateItemRequest.getCode());
        entity.setRate(currencyRateItemRequest.getRate());
        return entity;
    }
}
