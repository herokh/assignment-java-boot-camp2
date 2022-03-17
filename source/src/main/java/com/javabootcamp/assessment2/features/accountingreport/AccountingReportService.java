package com.javabootcamp.assessment2.features.accountingreport;

import com.javabootcamp.assessment2.entities.Asset;
import com.javabootcamp.assessment2.entities.AccountingReport;
import com.javabootcamp.assessment2.enums.BatchStatus;
import com.javabootcamp.assessment2.features.asset.AssetRepository;
import com.javabootcamp.assessment2.features.filegenerator.CsvFileGeneratorServiceImpl;
import com.javabootcamp.assessment2.features.filetransfer.SftpFileTransferServiceImpl;
import com.javabootcamp.assessment2.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class AccountingReportService {

    @Autowired
    private AccountingReportRepository accountingReportRepository;

    @Autowired
    private SftpFileTransferServiceImpl fileTransferService;

    @Autowired
    private CsvFileGeneratorServiceImpl fileGeneratorService;

    @Autowired
    private AssetRepository assetRepository;

    @Async
    public CompletableFuture<AccountingReport> processAccountingReport(Date adjustDate) throws ExecutionException, InterruptedException {

        var task = assetRepository.findAllByInsertedDate(adjustDate)
                .thenApply(assets -> {
                    AccountingReport result = new AccountingReport();
                    result.setAdjustDate(adjustDate);
                    var data = assets.stream()
                            .map(x -> mapToStringArray(x))
                            .toList();
                    InputStream csvFile = null;
                    csvFile = fileGeneratorService.createFile(data);
                    var success = false;
                    if (csvFile != null) {
                        var dateString = DateUtil.convertDateToString(adjustDate, "yyyymmdd");
                        success = fileTransferService.uploadFile(csvFile, "/departments/accounting/" + dateString + ".csv");
                    }
                    result.setStatus(success ? BatchStatus.Success : BatchStatus.Fail);
                    return result;
                });
        accountingReportRepository.save(task.get());
        return task;
    }

    private String[] mapToStringArray(Asset asset) {
        var list = new ArrayList<String>();
        list.add(String.valueOf(asset.getId()));
        list.add(String.valueOf(asset.getAmount()));
        list.add(String.valueOf(asset.getCurrency()));
        return list.toArray(new String[list.size()]);
    }
}
