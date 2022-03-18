package com.javabootcamp.assessment2.features.asset;
import com.javabootcamp.assessment2.entities.Asset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssetServiceTest {

    private AssetService testService;

    @Mock
    private AssetRepository assetRepositoryMock;

    @BeforeEach
    void setup() {
        testService = new AssetService(assetRepositoryMock);
    }

    @Test
    @DisplayName("Verify getAsset should be success")
    void test1() {
        var assetMock = new Asset();
        assetMock.setAmount(1000);
        assetMock.setCurrency("THB");
        when(assetRepositoryMock.findById(Mockito.any())).thenReturn(Optional.of(assetMock));

        var result = testService.getAsset(UUID.randomUUID());

        assertEquals(1000, result.getAmount());
        assertEquals("THB", result.getCurrency());
    }

    @Test
    @DisplayName("Verify getAsset should be thrown AssetNotFoundException")
    void test2() {
        when(assetRepositoryMock.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));
        assertThrows(AssetNotFoundException.class, () -> testService.getAsset(UUID.randomUUID()));
    }

    @Test
    @DisplayName("Verify updateAsset should be success")
    void test3() {
        var assetMock = new Asset();
        assetMock.setAmount(1000);
        assetMock.setCurrency("THB");
        when(assetRepositoryMock.findById(Mockito.any())).thenReturn(Optional.of(assetMock));

        var updateAssetRequest = new UpdateAssetRequest();
        updateAssetRequest.setAmount(100);
        updateAssetRequest.setCurrency("THB");
        testService.updateAsset(UUID.randomUUID(), updateAssetRequest);
        verify(assetRepositoryMock, times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Verify updateAsset should be thrown AssetNotFoundException")
    void test4() {
        when(assetRepositoryMock.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));
        assertThrows(AssetNotFoundException.class, () -> testService.updateAsset(UUID.randomUUID(), new UpdateAssetRequest()));
    }
}
