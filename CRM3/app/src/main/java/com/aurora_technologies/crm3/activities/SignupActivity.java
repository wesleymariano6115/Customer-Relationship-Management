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

public class SignupActivity extends AppCompatActivity {

    private TextInputLayout emailLayout, passwordLayout, confirmPasswordLayout;
    private TextInputEditText emailEditText, passwordEditText, confirmPasswordEditText;
    private MaterialButton signupButton;
    private TextView toggleLoginText;
    private ProgressBar progressBar;
    private RadioGroup roleRadioGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailLayout = findViewById(R.id.tilEmail);
        passwordLayout = findViewById(R.id.tilPassword);
        confirmPasswordLayout = findViewById(R.id.tilConfirmPassword);
        emailEditText = findViewById(R.id.etEmail);
        passwordEditText = findViewById(R.id.etPassword);
        confirmPasswordEditText = findViewById(R.id.etConfirmPassword);
        signupButton = findViewById(R.id.btnSignup);
        toggleLoginText = findViewById(R.id.tvToggleLogin);
        progressBar = findViewById(R.id.progressBar);
        roleRadioGroup = findViewById(R.id.rgRole);

        toggleLoginText.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        signupButton.setOnClickListener(v -> attemptSignup());
    }

    private void attemptSignup() {
        emailLayout.setError(null);
        passwordLayout.setError(null);
        confirmPasswordLayout.setError(null);

        String email = emailEditText.getText() != null ? emailEditText.getText().toString().trim() : "";
        String password = passwordEditText.getText() != null ? passwordEditText.getText().toString() : "";
        String confirmPassword = confirmPasswordEditText.getText() != null ? confirmPasswordEditText.getText().toString() : "";

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
        } else if (password.length() < 6) {
            passwordLayout.setError("Password must be at least 6 characters");
            cancel = true;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordLayout.setError("Confirm password is required");
            cancel = true;
        } else if (!password.equals(confirmPassword)) {
            confirmPasswordLayout.setError("Passwords do not match");
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
        signupButton.setEnabled(false);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<AuthResponse> call = apiService.register(email, password, role);
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                progressBar.setVisibility(View.GONE);
                signupButton.setEnabled(true);
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse authResponse = response.body();
                    if (authResponse.getToken() != null) {
                        SharedPrefManager.getInstance(SignupActivity.this).saveJwtToken(authResponse.getToken());
                        String userRole = JwtUtils.getRoleFromJwt(authResponse.getToken());
                        navigateToDashboard(userRole);
                    } else {
                        Toast.makeText(SignupActivity.this, "Invalid response from server", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (response.errorBody() != null) {
                        Toast.makeText(SignupActivity.this, "Signup failed: " + response.message(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignupActivity.this, "Signup failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                signupButton.setEnabled(true);
                Toast.makeText(SignupActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToDashboard(String role) {
        if ("admin".equalsIgnoreCase(role)) {
            Intent intent = new Intent(SignupActivity.this, AdminDashboardActivity.class);
            startActivity(intent);
            finish();
        } else if ("salesRep".equalsIgnoreCase(role)) {
            Intent intent = new Intent(SignupActivity.this, SalesRepDashboardActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Unknown user role", Toast.LENGTH_SHORT).show();
        }
    }
}
