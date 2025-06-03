package com.Aurora_Technologies.crm.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class LeadStatusResponse {
    @SerializedName("statusCounts")
    private Map<String, Integer> statusCounts;

    public Map<String, Integer> getStatusCounts() {
        return statusCounts;
    }
}
