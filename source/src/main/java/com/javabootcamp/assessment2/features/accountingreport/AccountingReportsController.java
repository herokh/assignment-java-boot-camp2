package com.javabootcamp.assessment2.features.accountingreport;

import com.javabootcamp.assessment2.controllers.SecuredRestController;
import com.javabootcamp.assessment2.entities.AccountingReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/secure/accountingreports")
public class AccountingReportsController extends SecuredRestController {

    @Autowired
    private AccountingReportService accountingReportService;

    @Async
    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<AccountingReport> processAccountingReport(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date adjustDate) throws ExecutionException, InterruptedException {
        return accountingReportService.processAccountingReport(adjustDate);
    }

}
