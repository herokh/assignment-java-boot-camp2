package com.javabootcamp.assessment2.features.balanceadjustmentbatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/secure/balanceadjustmentbatches")
public class BalanceAdjustmentBatchesController {

    @Autowired
    private BalanceAdjustmentBatchService balanceAdjustmentBatchService;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public void processBatchesToSftp(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date adjustDate) {
        balanceAdjustmentBatchService.processBatchesToSftp(adjustDate);
    }

}
