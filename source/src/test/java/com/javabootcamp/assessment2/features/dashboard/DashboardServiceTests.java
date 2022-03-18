package com.javabootcamp.assessment2.features.dashboard;
import com.javabootcamp.assessment2.entities.Asset;
import com.javabootcamp.assessment2.entities.Shipment;
import com.javabootcamp.assessment2.entities.TruckLocationPath;
import com.javabootcamp.assessment2.enums.ShipmentType;
import com.javabootcamp.assessment2.features.asset.AssetRepository;
import com.javabootcamp.assessment2.features.cashcenter.CashCenterRepository;
import com.javabootcamp.assessment2.features.currencyrate.CurrencyRateRepository;
import com.javabootcamp.assessment2.features.currencyrate.CurrencyRateService;
import com.javabootcamp.assessment2.features.shipment.ShipmentRepository;
import com.javabootcamp.assessment2.features.trucklocationpath.TruckLocationPathRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DashboardServiceTests {

    private DashboardService testService;

    @Mock
    private ShipmentRepository shipmentRepositoryMock;
    @Mock
    private TruckLocationPathRepository truckLocationPathRepositoryMock;
    @Mock
    private CashCenterRepository cashCenterRepositoryMock;
    @Mock
    private AssetRepository assetRepositoryMock;
    @Mock
    private CurrencyRateRepository currencyRateRepositoryMock;
    @Mock
    private CurrencyRateService currencyRateServiceMock;

    @BeforeEach
    void setup() {

        testService = new DashboardService(shipmentRepositoryMock,
                truckLocationPathRepositoryMock,
                cashCenterRepositoryMock,
                assetRepositoryMock,
                currencyRateRepositoryMock,
                currencyRateServiceMock);
    }

    @Test
    @DisplayName("Verify getDashboardOverview should be success")
    void test1() {
        var shipment = new Shipment();
        shipment.setShipmentType(ShipmentType.BranchToCashCenter);
        shipment.setDestinationLat("1234");
        shipment.setDestinationLng("5678");
        when(shipmentRepositoryMock.findById(Mockito.any())).thenReturn(Optional.of(shipment));
        var truckLocationPath = new TruckLocationPath();
        truckLocationPath.setLatitude("1234");
        truckLocationPath.setLongitude("5678");
        when(truckLocationPathRepositoryMock.findFirst1ByShipmentOrderByInsertedDateDesc(Mockito.any())).thenReturn(truckLocationPath);

        var assets = new ArrayList<Asset>();
        var asset = new Asset();
        asset.setAmount(1000);
        asset.setCurrency("THB");
        assets.add(asset);
        assets.add(asset);
        when(assetRepositoryMock.findAllByShipmentId(Mockito.any())).thenReturn(assets);

        when(currencyRateServiceMock.convertCurrencyAmountToTHB(Mockito.any(), Mockito.anyString(), Mockito.anyDouble()))
                .thenReturn(1000d);

        var result = testService.getDashboardOverview(UUID.randomUUID());

        assertEquals("1234", result.getTruckLatitude());
        assertEquals("5678", result.getTruckLongitude());
        assertEquals("1234", result.getCashCenterLatitude());
        assertEquals("5678", result.getCashCenterLongitude());
        assertEquals(2000d, result.getTotalBalances());
    }
}
