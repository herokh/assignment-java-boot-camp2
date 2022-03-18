package com.javabootcamp.assessment2.features.accountingreport;

import com.javabootcamp.assessment2.entities.AccountingReport;
import com.javabootcamp.assessment2.enums.BatchStatus;
import com.javabootcamp.assessment2.features.asset.AssetRepository;
import com.javabootcamp.assessment2.features.filegenerator.CsvFileGeneratorServiceImpl;
import com.javabootcamp.assessment2.features.filetransfer.SftpFileTransferServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountingReportServiceTests {

    private AccountingReportService testService;

    @Mock
    private AccountingReportRepository accountingReportRepositoryMock;

    @Mock
    private SftpFileTransferServiceImpl fileTransferServiceMock;

    @Mock
    private CsvFileGeneratorServiceImpl fileGeneratorServiceMock;

    @Mock
    private AssetRepository assetRepositoryMock;

    @BeforeEach
    void setup() {
        testService = new AccountingReportService(
                accountingReportRepositoryMock,
                fileTransferServiceMock,
                fileGeneratorServiceMock,
                assetRepositoryMock);
    }

    @Test
    @DisplayName("Verify processAccountingReport should be success")
    void test1() throws InterruptedException, ExecutionException {
        when(fileGeneratorServiceMock.createFile(Mockito.anyList())).thenReturn(new ByteArrayInputStream(new byte[0]));
        when(fileTransferServiceMock.uploadFile(Mockito.any(), Mockito.anyString())).thenReturn(true);

        Future<AccountingReport> accountingReportFuture = testService.processAccountingReport(new Date());
        while (!accountingReportFuture.isDone()) {
            TimeUnit.SECONDS.sleep(1000);
        }
        assertEquals(BatchStatus.Success, accountingReportFuture.get().getStatus());
    }

    @Test
    @DisplayName("Verify processAccountingReport should be failed")
    void test2() throws InterruptedException, ExecutionException {
        when(fileGeneratorServiceMock.createFile(Mockito.anyList())).thenReturn(new ByteArrayInputStream(new byte[0]));
        when(fileTransferServiceMock.uploadFile(Mockito.any(), Mockito.anyString())).thenReturn(false);

        Future<AccountingReport> accountingReportFuture = testService.processAccountingReport(new Date());
        while (!accountingReportFuture.isDone()) {
            TimeUnit.SECONDS.sleep(1000);
        }
        assertEquals(BatchStatus.Fail, accountingReportFuture.get().getStatus());
    }
}
