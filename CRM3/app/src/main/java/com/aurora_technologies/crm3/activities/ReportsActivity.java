package com.aurora_technologies.crm3.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurora_technologies.crm3.R;
import com.aurora_technologies.crm3.adapters.ReportAdapter;
import com.aurora_technologies.crm3.models.Report;
import com.aurora_technologies.crm3.network.ApiClient;
import com.aurora_technologies.crm3.network.ApiService;
import com.aurora_technologies.crm3.utils.SharedPrefManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Reports screen for Admin
 */
public class ReportsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewReports;
    private ReportAdapter reportAdapter;
    private List<Report> reportList = new ArrayList<>();
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        recyclerViewReports = findViewById(R.id.recyclerViewReports);
        recyclerViewReports.setLayoutManager(new LinearLayoutManager(this));
        reportAdapter = new ReportAdapter(reportList);
        recyclerViewReports.setAdapter(reportAdapter);

        progressBar = findViewById(R.id.progressBar);

        fetchReports();
    }

    private void fetchReports() {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClientWithAuth(SharedPrefManager.getInstance(this).getJwtToken()).create(ApiService.class);
        Call<List<Report>> call = apiService.getReports();
        call.enqueue(new Callback<List<Report>>() {
            @Override
            public void onResponse(Call<List<Report>> call, Response<List<Report>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    reportList.clear();
                    reportList.addAll(response.body());
                    reportAdapter.notifyDataSetChanged();
                } else {
                    Snackbar.make(coordinatorLayout, "Failed to load reports", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Report>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(coordinatorLayout, "Network error: " + t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
