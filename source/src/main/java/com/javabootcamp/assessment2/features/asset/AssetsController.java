package com.javabootcamp.assessment2.features.asset;

import com.javabootcamp.assessment2.controllers.SecuredRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/secure/assets")
public class AssetsController extends SecuredRestController {

    @Autowired
    private AssetService assetService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createAsset(@RequestBody CreateAssetRequest createAssetRequest) {
        return assetService.createAsset(createAssetRequest);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public AssetListResponse getAssets(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date insertedDate) throws ExecutionException, InterruptedException {
        return assetService.getAssetsByInsertedDate(insertedDate);
    }

    @GetMapping(value = "/{assetId}")
    @ResponseStatus(HttpStatus.OK)
    public AssetFullResponse getAsset(@PathVariable UUID assetId) {
        return assetService.getAsset(assetId);
    }

    @PutMapping(value = "/{assetId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAsset(@PathVariable UUID assetId, @RequestBody UpdateAssetRequest updateAssetRequest) {
        assetService.updateAsset(assetId, updateAssetRequest);
    }

}
