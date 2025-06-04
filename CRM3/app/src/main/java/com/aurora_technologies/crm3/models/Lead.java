package com.aurora_technologies.crm3.models;

import com.google.gson.annotations.SerializedName;

public class Lead {

    @SerializedName("_id")
    private String id;

    private String name;

    private String status;

    private String source;

    @SerializedName("lastContactDate")
    private String lastContactDate;

    public Lead() {}

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getSource() {
        return source;
    }

    public String getLastContactDate() {
        return lastContactDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setLastContactDate(String lastContactDate) {
        this.lastContactDate = lastContactDate;
    }
}
