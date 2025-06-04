package com.aurora_technologies.crm3.models;

public class AdminDashboardCard {
    private String title;
    private int iconResId;

    public AdminDashboardCard(String title, int iconResId) {
        this.title = title;
        this.iconResId = iconResId;
    }

    public String getTitle() {
        return title;
    }

    public int getIconResId() {
        return iconResId;
    }
}
