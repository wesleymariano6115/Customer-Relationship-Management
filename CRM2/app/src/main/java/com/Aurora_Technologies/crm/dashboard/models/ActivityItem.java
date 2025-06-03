package com.Aurora_Technologies.crm.dashboard.models;

public class ActivityItem {
    private String icon;
    private String text;

    public ActivityItem(String icon, String text) {
        this.icon = icon;
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }
}
