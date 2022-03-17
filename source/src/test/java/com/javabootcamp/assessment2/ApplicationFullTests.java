package com.javabootcamp.assessment2;

import com.javabootcamp.assessment2.enums.ShipmentStatus;
import com.javabootcamp.assessment2.enums.ShipmentType;
import com.javabootcamp.assessment2.features.asset.AssetListResponse;
import com.javabootcamp.assessment2.features.asset.CreateAssetRequest;
import com.javabootcamp.assessment2.features.auth.AuthRequest;
import com.javabootcamp.assessment2.features.auth.AuthResponse;
import com.javabootcamp.assessment2.features.branch.BranchRepository;
import com.javabootcamp.assessment2.features.branch.BranchSeeder;
import com.javabootcamp.assessment2.features.cashcenter.CashCenterRepository;
import com.javabootcamp.assessment2.features.cashcenter.CashCenterSeeder;
import com.javabootcamp.assessment2.features.currencyrate.CurrencyCodeConst;
import com.javabootcamp.assessment2.features.currencyrate.CurrencyRateItemRequest;
import com.javabootcamp.assessment2.features.currencyrate.CurrencyRatesRequest;
import com.javabootcamp.assessment2.features.dashboard.DashboardResponse;
import com.javabootcamp.assessment2.features.device.DeviceRepository;
import com.javabootcamp.assessment2.features.device.DeviceSeeder;
import com.javabootcamp.assessment2.features.driver.DriverRepository;
import com.javabootcamp.assessment2.features.driver.DriverSeeder;
import com.javabootcamp.assessment2.features.shipment.CreateShipmentRequest;
import com.javabootcamp.assessment2.features.shipment.ShipmentFullResponse;
import com.javabootcamp.assessment2.features.truck.TruckRepository;
import com.javabootcamp.assessment2.features.truck.TruckSeeder;
import com.javabootcamp.assessment2.features.trucklocationpath.CreateTruckLocationPathRequest;
import com.javabootcamp.assessment2.features.trucklocationpath.TruckLocationPathResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationFullTests {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private TruckRepository truckRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private CashCenterRepository cashCenterRepository;

    @Test
    @DisplayName("Full test flow with happy case")
    void fullTest_ShouldBeSuccess() {
        // update currency rate
        updateCurrencyRatesFromWebhook();

        // branch login
        var branchAuth = authenticateUser("branch", "1234");
        var branchToken = branchAuth.getToken();

        // create shipment with initial status
        var shipmentId = createShipment(branchToken);

        // put assets to shipment
        prepareAssetToShipment(branchToken, shipmentId, 1000, "THB");
        prepareAssetToShipment(branchToken, shipmentId, 10, "USD");

        // driver login
        var driverAuth = authenticateUser("driver", "1234");
        var driverToken = driverAuth.getToken();

        // update shipment status to heading to destination
        setShipmentStatus(shipmentId, driverToken, ShipmentStatus.HeadingToDestination);

        // iot login
        var deviceAuth = authenticateUser("iot", "1234");
        var deviceToken = deviceAuth.getToken();

        // send current location
        var totalSendLocationInterval = 5;
        for (var i = 0; i < totalSendLocationInterval; i++) {
            sendLocationPath(shipmentId, deviceToken);
        }

        // cash center login
        var cashCenterAuth = authenticateUser("cashcenter", "1234");
        var cashCenterToken = cashCenterAuth.getToken();

        // view dashboard to see the current location
        var dashboard = viewDashboard(shipmentId, cashCenterToken);
        assertEquals(1332.3d, dashboard.getTotalBalances());

        // view shipment detail with status
        var shipmentFullResponseWithHeadingToDestination = viewShipmentDetail(shipmentId, cashCenterToken);
        assertEquals(ShipmentStatus.HeadingToDestination, shipmentFullResponseWithHeadingToDestination.getStatus());

        // update shipment status to done
        setShipmentStatus(shipmentId, driverToken, ShipmentStatus.Done);

        // view shipment detail with status
        var shipmentFullResponseWithDone = viewShipmentDetail(shipmentId, cashCenterToken);
        assertEquals(ShipmentStatus.Done, shipmentFullResponseWithDone.getStatus());

        // view assets that belong to shipment
        var assets = viewAllAssetsByShipmentId(shipmentId, cashCenterToken);

        // Assert
        assertEquals(2, assets.getResult().size());
        var asset1 = assets.getResult().get(0);
        assertEquals(1000, asset1.getAmount());
        assertEquals(CurrencyCodeConst.THB, asset1.getCurrency());

        var asset2 = assets.getResult().get(1);
        assertEquals(10, asset2.getAmount());
        assertEquals(CurrencyCodeConst.USD, asset2.getCurrency());
    }

    private void updateCurrencyRatesFromWebhook() {
        CurrencyRatesRequest currencyRatesRequest = new CurrencyRatesRequest();
        var rates = new ArrayList<CurrencyRateItemRequest>();
        var rateItem = new CurrencyRateItemRequest();
        rateItem.setRate(33.23d);
        rateItem.setCode(CurrencyCodeConst.USD);
        rates.add(rateItem);
        currencyRatesRequest.setRates(rates);
        testRestTemplate.postForObject("/api/currencyrates", currencyRatesRequest, void.class);
    }

    private ShipmentFullResponse viewShipmentDetail(UUID shipmentId, String cashCenterToken) {
        var shipmentHttpEntity = createHttpEntityMock(cashCenterToken, void.class);
        var shipmentDetail = testRestTemplate.exchange("/api/secure/shipments/" + shipmentId, HttpMethod.GET, shipmentHttpEntity, ShipmentFullResponse.class)
                .getBody();
        return shipmentDetail;
    }

    private DashboardResponse viewDashboard(UUID shipmentId, String cashCenterToken) {
        var dashboardHttpEntity = createHttpEntityMock(cashCenterToken, Object.class);
        var dashboard = testRestTemplate.exchange("/api/secure/dashboard?shipmentId=" + shipmentId, HttpMethod.GET, dashboardHttpEntity, DashboardResponse.class)
                .getBody();
        return dashboard;
    }

    private AssetListResponse viewAllAssetsByShipmentId(UUID shipmentId, String cashCenterToken) {
        var assetHttpEntity = createHttpEntityMock(cashCenterToken, Object.class);
        var assets = testRestTemplate.exchange("/api/secure/assets?shipmentId=" + shipmentId, HttpMethod.GET, assetHttpEntity, AssetListResponse.class)
                .getBody();
        return assets;
    }

    private void prepareAssetToShipment(String branchToken, UUID shipmentId, double amount, String currency) {
        var createAssetRequest = new CreateAssetRequest();
        createAssetRequest.setShipmentId(shipmentId);
        createAssetRequest.setAmount(amount);
        createAssetRequest.setCurrency(currency);
        HttpEntity<CreateAssetRequest> createAssetHttpEntity = createHttpEntityMock(branchToken, createAssetRequest);
        var assetId = testRestTemplate.postForObject("/api/secure/assets", createAssetHttpEntity, UUID.class);
    }

    private void setShipmentStatus(UUID shipmentId, String driverToken, ShipmentStatus newStatus) {
        var httpEntity = createHttpEntityMock(driverToken, Object.class);
        testRestTemplate.postForObject("/api/secure/shipmentstatushistories?shipmentId=" + shipmentId + "&status=" + newStatus.toString(), httpEntity, void.class);
}

    private TruckLocationPathResponse sendLocationPath(UUID shipmentId, String deviceToken) {
        var createTruckLocationPathRequest = new CreateTruckLocationPathRequest();
        createTruckLocationPathRequest.setShipmentId(shipmentId);
        createTruckLocationPathRequest.setLatitude(String.valueOf(new Random().nextDouble()));
        createTruckLocationPathRequest.setLongitude(String.valueOf(new Random().nextDouble()));
        HttpEntity<CreateTruckLocationPathRequest> createTruckLocationPathHttpEntity = createHttpEntityMock(deviceToken, createTruckLocationPathRequest);
        var truckLocationPathResponse = testRestTemplate.postForObject("/api/secure/trucklocationpaths",
                createTruckLocationPathHttpEntity, TruckLocationPathResponse.class);
        return truckLocationPathResponse;
    }

    private UUID createShipment(String userToken) {
        var truck = truckRepository.findByName(TruckSeeder.NAME);
        var driver = driverRepository.findByName(DriverSeeder.NAME);
        var device = deviceRepository.findByName(DeviceSeeder.NAME);
        var branch = branchRepository.findByName(BranchSeeder.NAME);
        var cashCenter = cashCenterRepository.findByName(CashCenterSeeder.NAME);

        var createShipmentRequest = new CreateShipmentRequest();
        createShipmentRequest.setShipmentType(ShipmentType.BranchToCashCenter);
        createShipmentRequest.setDeviceId(device.getId());
        createShipmentRequest.setDriverId(driver.getId());
        createShipmentRequest.setTruckId(truck.getId());
        createShipmentRequest.setSenderId(branch.getId());
        createShipmentRequest.setReceiverId(cashCenter.getId());

        var createShipmentHttpEntity = createHttpEntityMock(userToken, createShipmentRequest);
        var shipmentId = testRestTemplate.postForObject("/api/secure/shipments", createShipmentHttpEntity, UUID.class);
        return shipmentId;
    }

    private AuthResponse authenticateUser(String username, String password) {
        var authRequest = new AuthRequest();
        authRequest.setUsername(username);
        authRequest.setPassword(password);
        var authResponse = testRestTemplate.postForObject("/api/auth/signin", authRequest, AuthResponse.class);
        return authResponse;
    }

    public <T extends Object> HttpEntity<T> createHttpEntityMock(String userToken, T body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", userToken));
        var entity = new HttpEntity<T>(body, headers);
        return entity;
    }

}
