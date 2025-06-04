package com.aurora_technologies.crm3.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static final String PREF_NAME = "aurora_crm_prefs";
    private static final String KEY_JWT_TOKEN = "jwt_token";

    private static SharedPrefManager instance;
    private SharedPreferences sharedPreferences;

    private SharedPrefManager(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public void saveJwtToken(String token) {
        sharedPreferences.edit().putString(KEY_JWT_TOKEN, token).apply();
    }

    public String getJwtToken() {
        return sharedPreferences.getString(KEY_JWT_TOKEN, null);
    }

    public void clearJwtToken() {
        sharedPreferences.edit().remove(KEY_JWT_TOKEN).apply();
    }
}
