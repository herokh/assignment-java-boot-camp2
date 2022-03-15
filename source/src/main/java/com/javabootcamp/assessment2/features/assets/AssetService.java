package com.javabootcamp.assessment2.features.assets;

import com.javabootcamp.assessment2.entities.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    public UUID createAsset(CreateAssetRequest createAssetRequest) {
        Asset entity = new Asset();
        entity.setAmount(createAssetRequest.getAmount());
        entity.setCurrency(createAssetRequest.getCurrency());
        entity.setInsertedDate(new Date());
        entity.setShipmentId(createAssetRequest.getShipmentId());
        assetRepository.save(entity);
        return entity.getId();
    }

    public AssetListResponse getAssetsByInsertedDate(Date insertedDate) {
        var result = assetRepository.findAllByInsertedDate(insertedDate);
        var response = mapToAssetListResponse(result);
        return response;
    }

    public AssetListResponse getAssetsByShipmentId(UUID shipmentId) {
        var result = assetRepository.findAllByShipmentId(shipmentId);
        var response = mapToAssetListResponse(result);
        return response;
    }

    public AssetFullResponse getAsset(UUID assetId) {
        var result = assetRepository.findById(assetId)
                .orElseThrow(() -> new AssetNotFoundException("Asset not found."));

        var response = new AssetFullResponse();
        response.setId(result.getId());
        response.setAmount(result.getAmount());
        response.setCurrency(result.getCurrency());
        return response;
    }

    public void updateAsset(UUID assetId, UpdateAssetRequest updateAssetRequest) {
        var assetToModify = assetRepository.findById(assetId)
                .orElseThrow(() -> new AssetNotFoundException("Asset not found."));

        assetToModify.setAmount(updateAssetRequest.getAmount());
        assetToModify.setCurrency(updateAssetRequest.getCurrency());
        assetToModify.setUpdatedDate(new Date());
        assetRepository.save(assetToModify);
    }

    private AssetListResponse mapToAssetListResponse(List<Asset> result) {
        var response = new AssetListResponse();
        var items = new ArrayList<AssetItemResponse>();
        for (var asset : result) {
            items.add(new AssetItemResponse(
                    asset.getId(), asset.getAmount(), asset.getCurrency()));
        }
        response.setResult(items);
        return response;
    }

}

