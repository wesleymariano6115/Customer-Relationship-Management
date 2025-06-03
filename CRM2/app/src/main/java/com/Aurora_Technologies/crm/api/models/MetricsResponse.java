package com.Aurora_Technologies.crm.api.models;

import com.google.gson.annotations.SerializedName;

public class MetricsResponse {
    @SerializedName("sales")
    private int sales;

    @SerializedName("topRep")
    private String topRep;

    @SerializedName("topRepLeadsConverted")
    private int topRepLeadsConverted;

    @SerializedName("newCustomers")
    private int newCustomers;

    @SerializedName("newLeads")
    private int newLeads;

    // Getters
    public int getSales() { return sales; }
    public String getTopRep() { return topRep; }
    public int getTopRepLeadsConverted() { return topRepLeadsConverted; }
    public int getNewCustomers() { return newCustomers; }
    public int getNewLeads() { return newLeads; }
}
