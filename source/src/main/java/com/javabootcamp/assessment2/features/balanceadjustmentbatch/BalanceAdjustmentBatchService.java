package com.javabootcamp.assessment2.features.balanceadjustmentbatch;

import com.javabootcamp.assessment2.entities.Asset;
import com.javabootcamp.assessment2.entities.BalanceAdjustmentBatch;
import com.javabootcamp.assessment2.enums.BatchStatus;
import com.javabootcamp.assessment2.features.asset.AssetRepository;
import com.javabootcamp.assessment2.features.filegenerator.CsvFileGeneratorServiceImpl;
import com.javabootcamp.assessment2.features.filetransfer.SftpFileTransferServiceImpl;
import com.javabootcamp.assessment2.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class BalanceAdjustmentBatchService {

    @Autowired
    private BalanceAdjustmentBatchRepository balanceAdjustmentBatchRepository;

    @Autowired
    private SftpFileTransferServiceImpl fileTransferService;

    @Autowired
    private CsvFileGeneratorServiceImpl fileGeneratorService;

    @Autowired
    private AssetRepository assetRepository;

    public void processBatchesToSftp(Date adjustDate) {
        BalanceAdjustmentBatch result = new BalanceAdjustmentBatch();
        result.setAdjustDate(adjustDate);

        try {
            var resource = assetRepository.findAllByInsertedDate(adjustDate)
                    .stream()
                    .map(x -> mapToStringArray(x))
                    .toList();
            var csvFile = fileGeneratorService.createFile(resource);
            var dateString = DateUtil.convertDateToString(adjustDate, "yyyymmdd");
            var success = fileTransferService.uploadFile(csvFile, "/departments/accounting/" + dateString + ".csv");
            if (success) {
                result.setStatus(BatchStatus.Success);
            }
            throw new Exception("upload to sftp fail.");
        }
        catch (Exception e) {
            result.setStatus(BatchStatus.Fail);
            result.setErrorReason(e.getMessage());
        }

        balanceAdjustmentBatchRepository.save(result);
    }

    private String[] mapToStringArray(Asset asset) {
        var list = new ArrayList<String>();
        list.add(String.valueOf(asset.getId()));
        list.add(String.valueOf(asset.getAmount()));
        list.add(String.valueOf(asset.getCurrency()));
        return list.toArray(new String[list.size()]);
    }
}
