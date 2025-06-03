package com.Aurora_Technologies.crm.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class LeadSourceResponse {
    @SerializedName("sourceCounts")
    private Map<String, Integer> sourceCounts;

    public Map<String, Integer> getSourceCounts() {
        return sourceCounts;
    }
}
