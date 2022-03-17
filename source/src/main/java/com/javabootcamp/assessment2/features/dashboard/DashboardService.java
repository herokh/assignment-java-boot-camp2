package com.javabootcamp.assessment2.features.dashboard;

import com.javabootcamp.assessment2.entities.Address;
import com.javabootcamp.assessment2.entities.Asset;
import com.javabootcamp.assessment2.entities.Shipment;
import com.javabootcamp.assessment2.entities.TruckLocationPath;
import com.javabootcamp.assessment2.features.asset.AssetRepository;
import com.javabootcamp.assessment2.features.cashcenter.CashCenterRepository;
import com.javabootcamp.assessment2.features.shipment.ShipmentTypeInvalidException;
import com.javabootcamp.assessment2.features.shipment.ShipmentNotFoundException;
import com.javabootcamp.assessment2.features.shipment.ShipmentRepository;
import com.javabootcamp.assessment2.features.trucklocationpath.TruckLocationPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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
        var totalBalances = assets.stream()
                .map(x -> x.getAmount())
                .reduce(0d, (a, b) -> a + b);
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
