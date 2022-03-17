package com.javabootcamp.assessment2.features.accountingreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/secure/balanceadjustmentbatches")
public class AccountingReportsController {

    @Autowired
    private AccountingReportService accountingReportService;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public void processBatchesToSftp(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date adjustDate) {
        accountingReportService.processBatchesToSftp(adjustDate);
    }

}
