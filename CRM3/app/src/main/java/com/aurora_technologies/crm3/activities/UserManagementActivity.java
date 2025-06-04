package com.aurora_technologies.crm3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurora_technologies.crm3.R;
import com.aurora_technologies.crm3.adapters.UserAdapter;
import com.aurora_technologies.crm3.models.User;
import com.aurora_technologies.crm3.network.ApiClient;
import com.aurora_technologies.crm3.network.ApiService;
import com.aurora_technologies.crm3.utils.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * User Management screen for Admin
 */
public class UserManagementActivity extends AppCompatActivity implements UserAdapter.UserActionListener {

    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;
    private List<User> userList = new ArrayList<>();
    private FloatingActionButton fabAddUser;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(userList, this);
        recyclerViewUsers.setAdapter(userAdapter);

        fabAddUser = findViewById(R.id.fabAddUser);
        progressBar = findViewById(R.id.progressBar);

        fabAddUser.setOnClickListener(v -> {
            Intent intent = new Intent(UserManagementActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        fetchUsers();
    }

    private void fetchUsers() {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClientWithAuth(SharedPrefManager.getInstance(this).getJwtToken()).create(ApiService.class);
        Call<List<User>> call = apiService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    userList.clear();
                    userList.addAll(response.body());
                    userAdapter.notifyDataSetChanged();
                } else {
                    Snackbar.make(coordinatorLayout, "Failed to load users", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(coordinatorLayout, "Network error: " + t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onEditUser(User user) {
        // TODO: Open edit user screen or dialog
        Snackbar.make(coordinatorLayout, "Edit user: " + user.getEmail(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteUser(User user) {
        new AlertDialog.Builder(this)
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete user " + user.getEmail() + "?")
                .setPositiveButton("Delete", (dialog, which) -> deleteUser(user))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteUser(User user) {
        ApiService apiService = ApiClient.getClientWithAuth(SharedPrefManager.getInstance(this).getJwtToken()).create(ApiService.class);
        Call<Void> call = apiService.deleteUser(user.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    userList.remove(user);
                    userAdapter.notifyDataSetChanged();
                    Snackbar.make(coordinatorLayout, "User deleted", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(coordinatorLayout, "Failed to delete user", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Snackbar.make(coordinatorLayout, "Network error: " + t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
