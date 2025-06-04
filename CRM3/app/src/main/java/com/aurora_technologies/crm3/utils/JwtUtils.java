package com.aurora_technologies.crm3.utils;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Utility class to decode JWT token and extract claims
 */
public class JwtUtils {

    public static String getRoleFromJwt(String jwt) {
        if (jwt == null) return null;
        String[] parts = jwt.split("\\.");
        if (parts.length != 3) return null;

        try {
            String payload = parts[1];
            byte[] decodedBytes = Base64.decode(payload, Base64.URL_SAFE);
            String decodedPayload = new String(decodedBytes);
            JSONObject jsonObject = new JSONObject(decodedPayload);
            return jsonObject.optString("role", null);
        } catch (IllegalArgumentException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
