package com.javabootcamp.assessment2.features.dashboard;

import com.javabootcamp.assessment2.entities.Shipment;
import com.javabootcamp.assessment2.features.asset.AssetRepository;
import com.javabootcamp.assessment2.features.cashcenter.CashCenterRepository;
import com.javabootcamp.assessment2.features.currencyrate.CurrencyRateRepository;
import com.javabootcamp.assessment2.features.currencyrate.CurrencyRateService;
import com.javabootcamp.assessment2.features.shipment.ShipmentNotFoundException;
import com.javabootcamp.assessment2.features.shipment.ShipmentRepository;
import com.javabootcamp.assessment2.features.shipment.ShipmentTypeInvalidException;
import com.javabootcamp.assessment2.features.trucklocationpath.TruckLocationPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class DashboardService {

    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private TruckLocationPathRepository truckLocationPathRepository;
    @Autowired
    private CashCenterRepository cashCenterRepository;
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private CurrencyRateRepository currencyRateRepository;
    @Autowired
    private CurrencyRateService currencyRateService;

    public DashboardResponse getDashboardOverview(UUID shipmentId) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new ShipmentNotFoundException("Shipment not found."));

        var dashboardResponse = new DashboardResponse();
        var currentLocation = truckLocationPathRepository.findFirst1ByShipmentOrderByInsertedDateDesc(shipment);
        dashboardResponse.setTruckLatitude(currentLocation.getLatitude());
        dashboardResponse.setTruckLongitude(currentLocation.getLongitude());
        var cashCenterAddress = getCashCenterAddress(shipment);
        dashboardResponse.setCashCenterLatitude(cashCenterAddress.get("lat"));
        dashboardResponse.setCashCenterLongitude(cashCenterAddress.get("lng"));
        var assets = assetRepository.findAllByShipmentId(shipmentId);
        var rates = currencyRateRepository.findAll();
        var totalBalances = 0d;
        for (var asset : assets) {
            double balanceTHB = currencyRateService.convertCurrencyAmountToTHB(
                    rates, asset.getCurrency(), asset.getAmount());
            totalBalances += balanceTHB;
        }
        dashboardResponse.setTotalBalances(totalBalances);

        return dashboardResponse;
    }

    private Map<String, String> getCashCenterAddress(Shipment shipment) {
        Map<String, String> location = new HashMap<>();
        switch (shipment.getShipmentType()){
            case BranchToCashCenter:
                location.put("lat",
                        shipment.getDestinationLat());
                location.put("lng",
                        shipment.getDestinationLng());
                break;
            case CashCenterToBranch:
                location.put("lat",
                        shipment.getOriginLat());
                location.put("lng",
                        shipment.getOriginLng());
                break;
            default:
                throw new ShipmentTypeInvalidException("Shipment type is not support.");
        }
        return location;
    }

}
