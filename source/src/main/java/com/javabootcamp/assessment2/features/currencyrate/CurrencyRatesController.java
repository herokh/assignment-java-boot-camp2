package com.javabootcamp.assessment2.features.currencyrate;

import com.javabootcamp.assessment2.controllers.SecuredRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/secure/currencyrates")
public class CurrencyRatesController extends SecuredRestController {

    @Autowired
    private CurrencyRateService currencyRateService;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public void saveRates(@RequestBody CurrencyRatesRequest currencyRatesRequest) {
        currencyRateService.saveRates(currencyRatesRequest);
    }
}
