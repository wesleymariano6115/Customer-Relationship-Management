package com.Aurora_Technologies.crm.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.Aurora_Technologies.crm.api.ApiClient;
import com.Aurora_Technologies.crm.api.ApiService;
import com.Aurora_Technologies.crm.api.models.LeadSourceResponse;
import com.Aurora_Technologies.crm.api.models.LeadStatusResponse;
import com.Aurora_Technologies.crm.api.models.MetricsResponse;
import com.Aurora_Technologies.crm.dashboard.models.ActivityItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDashboardViewModel extends ViewModel {

    private final MutableLiveData<MetricsResponse> metricsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Map<String, Integer>> leadStatusLiveData = new MutableLiveData<>();
    private final MutableLiveData<Map<String, Integer>> leadSourceLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<ActivityItem>> activitiesLiveData = new MutableLiveData<>();

    private final ApiService apiService;

    public AdminDashboardViewModel() {
        apiService = ApiClient.getClient().create(ApiService.class);
        loadDashboardData();
        loadActivities();
    }

    public LiveData<MetricsResponse> getMetrics() {
        return metricsLiveData;
    }

    public LiveData<Map<String, Integer>> getLeadStatus() {
        return leadStatusLiveData;
    }

    public LiveData<Map<String, Integer>> getLeadSource() {
        return leadSourceLiveData;
    }

    public LiveData<List<ActivityItem>> getActivities() {
        return activitiesLiveData;
    }

    public void loadDashboardData() {
        // Load metrics
        apiService.getMetrics().enqueue(new Callback<MetricsResponse>() {
            @Override
            public void onResponse(Call<MetricsResponse> call, Response<MetricsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    metricsLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MetricsResponse> call, Throwable t) {
                // Handle failure (log or notify)
            }
        });

        // Load lead status
        apiService.getLeadStatus().enqueue(new Callback<LeadStatusResponse>() {
            @Override
            public void onResponse(Call<LeadStatusResponse> call, Response<LeadStatusResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    leadStatusLiveData.postValue(response.body().getStatusCounts());
                }
            }

            @Override
            public void onFailure(Call<LeadStatusResponse> call, Throwable t) {
                // Handle failure
            }
        });

        // Load lead sources
        apiService.getLeadSources().enqueue(new Callback<LeadSourceResponse>() {
            @Override
            public void onResponse(Call<LeadSourceResponse> call, Response<LeadSourceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    leadSourceLiveData.postValue(response.body().getSourceCounts());
                }
            }

            @Override
            public void onFailure(Call<LeadSourceResponse> call, Throwable t) {
                // Handle failure
            }
        });
    }

    private void loadActivities() {
        // Placeholder: Load today's activities/reminders
        List<ActivityItem> activities = new ArrayList<>();
        activities.add(new ActivityItem("🔔", "Follow up with Juan Dela Cruz – 10:00 AM"));
        activities.add(new ActivityItem("🔔", "Lead status update for Tarlac Enterprise – 2:00 PM"));
        activitiesLiveData.postValue(activities);
    }
}