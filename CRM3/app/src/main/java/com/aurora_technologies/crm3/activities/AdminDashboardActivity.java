package com.aurora_technologies.crm3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurora_technologies.crm3.R;
import com.aurora_technologies.crm3.adapters.AdminDashboardCardAdapter;
import com.aurora_technologies.crm3.models.AdminDashboardCard;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Admin Dashboard Activity
 */
public class AdminDashboardActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private RecyclerView recyclerViewDashboardCards;
    private AdminDashboardCardAdapter cardAdapter;
    private List<AdminDashboardCard> cardList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton fabAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Aurora CRM - Admin");
        toolbar.setNavigationIcon(R.drawable.ic_avatar);
        toolbar.setNavigationOnClickListener(v -> {
            // TODO: Open admin profile or settings
            Snackbar.make(coordinatorLayout, "Admin profile clicked", Snackbar.LENGTH_SHORT).show();
        });

        recyclerViewDashboardCards = findViewById(R.id.recyclerViewDashboardCards);
        recyclerViewDashboardCards.setLayoutManager(new GridLayoutManager(this, 2));
        cardAdapter = new AdminDashboardCardAdapter(cardList, this::onCardClicked);
        recyclerViewDashboardCards.setAdapter(cardAdapter);

        fabAddUser = findViewById(R.id.fabAddUser);
        fabAddUser.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        loadDashboardCards();
    }

    private void loadDashboardCards() {
        cardList.clear();
        cardList.add(new AdminDashboardCard("User Management", R.drawable.ic_user));
        cardList.add(new AdminDashboardCard("System Stats", R.drawable.ic_lead));
        cardList.add(new AdminDashboardCard("Reports", R.drawable.ic_report));
        cardList.add(new AdminDashboardCard("Notifications", R.drawable.ic_notifications));
        cardAdapter.notifyDataSetChanged();
    }

    private void onCardClicked(AdminDashboardCard card) {
        switch (card.getTitle()) {
            case "User Management":
                startActivity(new Intent(this, UserManagementActivity.class));
                break;
            case "System Stats":
                showSystemStats();
                break;
            case "Reports":
                startActivity(new Intent(this, ReportsActivity.class));
                break;
            case "Notifications":
                showNotifications();
                break;
            default:
                Snackbar.make(coordinatorLayout, "Unknown option", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void showSystemStats() {
        // TODO: Fetch and display system stats
        new AlertDialog.Builder(this)
                .setTitle("System Stats")
                .setMessage("Total Leads: 100\nTotal Sales: $50,000\nActive Users: 25")
                .setPositiveButton("OK", null)
                .show();
    }

    private void showNotifications() {
        // TODO: Show notifications list
        new AlertDialog.Builder(this)
                .setTitle("Notifications")
                .setMessage("No new notifications.")
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_logout){
            // Clear token and logout
            com.aurora_technologies.crm3.utils.SharedPrefManager.getInstance(this).clearJwtToken();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
