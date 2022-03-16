package com.javabootcamp.assessment2.features.asset;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AssetListResponse {

    public AssetListResponse() {
        result = new ArrayList<>();
    }

    private List<AssetItemResponse> result;
}
