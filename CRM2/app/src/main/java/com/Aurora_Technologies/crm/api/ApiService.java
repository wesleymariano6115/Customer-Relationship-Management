package com.Aurora_Technologies.crm.api;

import com.Aurora_Technologies.crm.api.models.AuthResponse;
import com.Aurora_Technologies.crm.api.models.LeadSourceResponse;
import com.Aurora_Technologies.crm.api.models.LeadStatusResponse;
import com.Aurora_Technologies.crm.api.models.LoginRequest;
import com.Aurora_Technologies.crm.api.models.MetricsResponse;
import com.Aurora_Technologies.crm.api.models.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    // Auth
    @POST("api/auth/login")
    Call<AuthResponse> login(@Body LoginRequest loginRequest);

    @POST("api/auth/register")
    Call<AuthResponse> register(@Body RegisterRequest registerRequest);

    // Admin endpoints
    @GET("api/admin/metrics")
    Call<MetricsResponse> getMetrics();

    @GET("api/leads/status")
    Call<LeadStatusResponse> getLeadStatus();

    @GET("api/reports/lead-sources")
    Call<LeadSourceResponse> getLeadSources();
}
