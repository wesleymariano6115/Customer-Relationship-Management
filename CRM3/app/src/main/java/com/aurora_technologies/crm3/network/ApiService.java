package com.aurora_technologies.crm3.network;

import com.aurora_technologies.crm3.models.AuthResponse;
import com.aurora_technologies.crm3.models.Lead;
import com.aurora_technologies.crm3.models.Report;
import com.aurora_technologies.crm3.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit API service interface
 */
public interface ApiService {

    @POST("/api/auth/login")
    Call<AuthResponse> login(@Query("email") String email,
                             @Query("password") String password,
                             @Query("role") String role);

    @POST("/api/auth/register")
    Call<AuthResponse> register(@Query("email") String email,
                                @Query("password") String password,
                                @Query("role") String role);

    @GET("/api/leads")
    Call<List<Lead>> getLeads();

    @POST("/api/leads")
    Call<Lead> createLead(@Body Lead lead);

    @POST("/api/leads/{id}/status")
    Call<Lead> updateLeadStatus(@Path("id") String leadId, @Query("status") String status);

    @DELETE("/api/leads/{id}")
    Call<Void> deleteLead(@Path("id") String leadId);

    @GET("/api/admin/users")
    Call<List<User>> getUsers();

    @DELETE("/api/admin/users/{id}")
    Call<Void> deleteUser(@Path("id") String userId);

    @GET("/api/reports")
    Call<List<Report>> getReports();

}
