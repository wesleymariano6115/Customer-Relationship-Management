package com.aurora_technologies.crm3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.aurora_technologies.crm3.R;
import com.aurora_technologies.crm3.models.AuthResponse;
import com.aurora_technologies.crm3.network.ApiClient;
import com.aurora_technologies.crm3.network.ApiService;
import com.aurora_technologies.crm3.utils.JwtUtils;
import com.aurora_technologies.crm3.utils.SharedPrefManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout emailLayout, passwordLayout;
    private TextInputEditText emailEditText, passwordEditText;
    private MaterialButton loginButton;
    private TextView toggleSignupText;
    private ProgressBar progressBar;
    private RadioGroup roleRadioGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLayout = findViewById(R.id.tilEmail);
        passwordLayout = findViewById(R.id.tilPassword);
        emailEditText = findViewById(R.id.etEmail);
        passwordEditText = findViewById(R.id.etPassword);
        loginButton = findViewById(R.id.btnLogin);
        toggleSignupText = findViewById(R.id.tvToggleSignup);
        progressBar = findViewById(R.id.progressBar);
        roleRadioGroup = findViewById(R.id.rgRole);

        toggleSignupText.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
            finish();
        });

        loginButton.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        emailLayout.setError(null);
        passwordLayout.setError(null);

        String email = emailEditText.getText() != null ? emailEditText.getText().toString().trim() : "";
        String password = passwordEditText.getText() != null ? passwordEditText.getText().toString() : "";

        int selectedRoleId = roleRadioGroup.getCheckedRadioButtonId();
        String role = null;
        if (selectedRoleId == R.id.rbAdmin) {
            role = "admin";
        } else if (selectedRoleId == R.id.rbSalesRep) {
            role = "salesRep";
        }

        boolean cancel = false;

        if (TextUtils.isEmpty(email)) {
            emailLayout.setError("Email is required");
            cancel = true;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.setError("Invalid email address");
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError("Password is required");
            cancel = true;
        }

        if (role == null) {
            Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show();
            cancel = true;
        }

        if (cancel) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        loginButton.setEnabled(false);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<AuthResponse> call = apiService.login(email, password, role);
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                progressBar.setVisibility(View.GONE);
                loginButton.setEnabled(true);
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse authResponse = response.body();
                    if (authResponse.getToken() != null) {
                        SharedPrefManager.getInstance(LoginActivity.this).saveJwtToken(authResponse.getToken());
                        String userRole = JwtUtils.getRoleFromJwt(authResponse.getToken());
                        navigateToDashboard(userRole);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid response from server", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (response.errorBody() != null) {
                        // Try to parse error message
                        Toast.makeText(LoginActivity.this, "Login failed: " + response.message(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                loginButton.setEnabled(true);
                Toast.makeText(LoginActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToDashboard(String role) {
        if ("admin".equalsIgnoreCase(role)) {
            Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
            startActivity(intent);
            finish();
        } else if ("salesRep".equalsIgnoreCase(role)) {
            Intent intent = new Intent(LoginActivity.this, SalesRepDashboardActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Unknown user role", Toast.LENGTH_SHORT).show();
        }
    }
}
