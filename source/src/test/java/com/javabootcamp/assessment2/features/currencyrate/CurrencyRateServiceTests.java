package com.javabootcamp.assessment2.features.currencyrate;
import com.javabootcamp.assessment2.entities.CurrencyRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CurrencyRateServiceTests {

    private CurrencyRateService testService;

    @Mock
    private CurrencyRateRepository currencyRateRepositoryMock;

    @BeforeEach
    void setup() {

        testService = new CurrencyRateService(currencyRateRepositoryMock);
    }

    @Test
    @DisplayName("Verify convertCurrencyAmountToTHB that should be succes")
    void test1() {
        var rates = new ArrayList<CurrencyRate>();
        var usdRate = new CurrencyRate();
        usdRate.setRate(30);
        usdRate.setCode("USD");
        rates.add(usdRate);
        var result = testService.convertCurrencyAmountToTHB(rates, "USD", 10);
        assertEquals(300, result);
    }

    @Test
    @DisplayName("Verify convertCurrencyAmountToTHB that should be thrown CurrencyRateNotFoundException")
    void test2() {
        var rates = new ArrayList<CurrencyRate>();
        var usdRate = new CurrencyRate();
        usdRate.setRate(30);
        usdRate.setCode("TEST");
        rates.add(usdRate);
        assertThrows(CurrencyRateNotFoundException.class,
                () -> testService.convertCurrencyAmountToTHB(rates, "USD", 10));
    }
}
