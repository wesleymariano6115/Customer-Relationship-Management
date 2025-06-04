package com.aurora_technologies.crm3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurora_technologies.crm3.R;
import com.aurora_technologies.crm3.adapters.LeadAdapter;
import com.aurora_technologies.crm3.models.Lead;
import com.aurora_technologies.crm3.network.ApiClient;
import com.aurora_technologies.crm3.network.ApiService;
import com.aurora_technologies.crm3.utils.SharedPrefManager;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Sales Representative Dashboard Activity
 */
public class SalesRepDashboardActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private RecyclerView leadsRecyclerView;
    private LeadAdapter leadAdapter;
    private FloatingActionButton fabAddLead;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    private MaterialCardView cardTotalLeads, cardActiveCustomers, cardSalesMonth, cardPendingActivities;
    private TextView tvTotalLeads, tvActiveCustomers, tvSalesMonth, tvPendingActivities;

    private List<Lead> leadList = new ArrayList<>();

    private CoordinatorLayout coordinatorLayout;

    private int notificationCount = 3; // Example notification count, replace with real data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_rep_dashboard);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Aurora CRM");

        // User avatar icon click listener
        toolbar.setNavigationIcon(R.drawable.ic_avatar);
        toolbar.setNavigationOnClickListener(v -> {
            // TODO: Open user profile or settings
            Snackbar.make(coordinatorLayout, "User profile clicked", Snackbar.LENGTH_SHORT).show();
        });

        cardTotalLeads = findViewById(R.id.cardTotalLeads);
        cardActiveCustomers = findViewById(R.id.cardActiveCustomers);
        cardSalesMonth = findViewById(R.id.cardSalesMonth);
        cardPendingActivities = findViewById(R.id.cardPendingActivities);

        tvTotalLeads = findViewById(R.id.tvTotalLeads);
        tvActiveCustomers = findViewById(R.id.tvActiveCustomers);
        tvSalesMonth = findViewById(R.id.tvSalesMonth);
        tvPendingActivities = findViewById(R.id.tvPendingActivities);

        progressBar = findViewById(R.id.progressBar);

        leadsRecyclerView = findViewById(R.id.recyclerViewLeads);
        leadsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        leadAdapter = new LeadAdapter(leadList);
        leadsRecyclerView.setAdapter(leadAdapter);

        fabAddLead = findViewById(R.id.fabAddLead);
        fabAddLead.setOnClickListener(v -> {
            Intent intent = new Intent(SalesRepDashboardActivity.this, LeadCreationActivity.class);
            startActivity(intent);
        });

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this::fetchLeads);

        setupSwipeGestures();

        fetchDashboardSummary();
        fetchLeads();
        setupNotificationBadge();
    }

    private void setupNotificationBadge() {
        // Add badge to avatar icon in toolbar
        // This requires API 28+, or use third-party libs for lower versions
        // For simplicity, we skip actual badge implementation on toolbar icon here.
    }

    private void fetchDashboardSummary() {
        // TODO: Fetch dashboard summary from backend
        // For demo, set dummy values
        tvTotalLeads.setText("42");
        tvActiveCustomers.setText("15");
        tvSalesMonth.setText("$12,300");
        tvPendingActivities.setText("5");
    }

    private void fetchLeads() {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClientWithAuth(SharedPrefManager.getInstance(this).getJwtToken()).create(ApiService.class);
        Call<List<Lead>> call = apiService.getLeads();
        call.enqueue(new Callback<List<Lead>>() {
            @Override
            public void onResponse(Call<List<Lead>> call, Response<List<Lead>> response) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    leadList.clear();
                    leadList.addAll(response.body());
                    leadAdapter.notifyDataSetChanged();
                    fetchDashboardSummary(); // update summary based on real data
                } else {
                    Snackbar.make(coordinatorLayout, "Failed to load leads", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Lead>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                Snackbar.make(coordinatorLayout, "Network error: " + t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setupSwipeGestures() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false; // no move support
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Lead lead = leadList.get(position);

                if (direction == ItemTouchHelper.LEFT) {
                    // Delete lead
                    confirmDeleteLead(lead, position);
                } else if (direction == ItemTouchHelper.RIGHT) {
                    // Update status (cycle through statuses)
                    updateLeadStatus(lead, position);
                }
            }
        };

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(leadsRecyclerView);
    }

    private void confirmDeleteLead(Lead lead, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Lead")
                .setMessage("Are you sure you want to delete this lead?")
                .setPositiveButton("Delete", (dialog, which) -> deleteLead(lead, position))
                .setNegativeButton("Cancel", (dialog, which) -> {
                    leadAdapter.notifyItemChanged(position);
                    dialog.dismiss();
                })
                .setCancelable(false)
                .show();
    }

    private void deleteLead(Lead lead, int position) {
        ApiService apiService = ApiClient.getClientWithAuth(SharedPrefManager.getInstance(this).getJwtToken()).create(ApiService.class);
        Call<Void> call = apiService.deleteLead(lead.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    leadList.remove(position);
                    leadAdapter.notifyItemRemoved(position);
                    Snackbar.make(coordinatorLayout, "Lead deleted", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(coordinatorLayout, "Failed to delete lead", Snackbar.LENGTH_SHORT).show();
                    leadAdapter.notifyItemChanged(position);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Snackbar.make(coordinatorLayout, "Network error: " + t.getMessage(), Snackbar.LENGTH_SHORT).show();
                leadAdapter.notifyItemChanged(position);
            }
        });
    }

    private void updateLeadStatus(Lead lead, int position) {
        // Cycle statuses: New -> Contacted -> Qualified -> Lost -> New
        String currentStatus = lead.getStatus();
        String newStatus;
        switch (currentStatus.toLowerCase()) {
            case "new":
                newStatus = "Contacted";
                break;
            case "contacted":
                newStatus = "Qualified";
                break;
            case "qualified":
                newStatus = "Lost";
                break;
            default:
                newStatus = "New";
                break;
        }
        lead.setStatus(newStatus);

        ApiService apiService = ApiClient.getClientWithAuth(SharedPrefManager.getInstance(this).getJwtToken()).create(ApiService.class);
        Call<Lead> call = apiService.updateLeadStatus(lead.getId(), newStatus);
        call.enqueue(new Callback<Lead>() {
            @Override
            public void onResponse(Call<Lead> call, Response<Lead> response) {
                if (response.isSuccessful() && response.body() != null) {
                    leadList.set(position, response.body());
                    leadAdapter.notifyItemChanged(position);
                    Snackbar.make(coordinatorLayout, "Lead status updated to " + newStatus, Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(coordinatorLayout, "Failed to update status", Snackbar.LENGTH_SHORT).show();
                    // revert status
                    lead.setStatus(currentStatus);
                    leadAdapter.notifyItemChanged(position);
                }
            }

            @Override
            public void onFailure(Call<Lead> call, Throwable t) {
                Snackbar.make(coordinatorLayout, "Network error: " + t.getMessage(), Snackbar.LENGTH_SHORT).show();
                // revert status
                lead.setStatus(currentStatus);
                leadAdapter.notifyItemChanged(position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sales_rep_dashboard, menu);
        MenuItem notificationsItem = menu.findItem(R.id.action_notifications);
        // TODO: add badge to notifications icon if needed
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_notifications){
            // TODO: Show notifications
            Snackbar.make(coordinatorLayout, "Notifications clicked", Snackbar.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
