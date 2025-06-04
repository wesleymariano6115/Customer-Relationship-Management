package com.aurora_technologies.crm3.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aurora_technologies.crm3.R;
import com.aurora_technologies.crm3.models.Lead;
import com.aurora_technologies.crm3.network.ApiClient;
import com.aurora_technologies.crm3.network.ApiService;
import com.aurora_technologies.crm3.utils.SharedPrefManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeadCreationActivity extends AppCompatActivity {

    private TextInputLayout tilName, tilSource, tilStatus, tilLastContact;
    private TextInputEditText etName, etSource, etStatus, etLastContact;
    private MaterialButton btnCreateLead;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_creation);

        tilName = findViewById(R.id.tilName);
        tilSource = findViewById(R.id.tilSource);
        tilStatus = findViewById(R.id.tilStatus);
        tilLastContact = findViewById(R.id.tilLastContact);

        etName = findViewById(R.id.etName);
        etSource = findViewById(R.id.etSource);
        etStatus = findViewById(R.id.etStatus);
        etLastContact = findViewById(R.id.etLastContact);

        btnCreateLead = findViewById(R.id.btnCreateLead);
        progressBar = findViewById(R.id.progressBar);

        btnCreateLead.setOnClickListener(v -> createLead());
    }

    private void createLead() {
        tilName.setError(null);
        tilSource.setError(null);
        tilStatus.setError(null);
        tilLastContact.setError(null);

        String name = etName.getText() != null ? etName.getText().toString().trim() : "";
        String source = etSource.getText() != null ? etSource.getText().toString().trim() : "";
        String status = etStatus.getText() != null ? etStatus.getText().toString().trim() : "";
        String lastContact = etLastContact.getText() != null ? etLastContact.getText().toString().trim() : "";

        boolean cancel = false;

        if (TextUtils.isEmpty(name)) {
            tilName.setError("Name is required");
            cancel = true;
        }
        if (TextUtils.isEmpty(source)) {
            tilSource.setError("Source is required");
            cancel = true;
        }
        if (TextUtils.isEmpty(status)) {
            tilStatus.setError("Status is required");
            cancel = true;
        }
        if (TextUtils.isEmpty(lastContact)) {
            tilLastContact.setError("Last contact date is required");
            cancel = true;
        }

        if (cancel) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        btnCreateLead.setEnabled(false);

        Lead lead = new Lead();
        lead.setName(name);
        lead.setSource(source);
        lead.setStatus(status);
        lead.setLastContactDate(lastContact);

        ApiService apiService = ApiClient.getClientWithAuth(SharedPrefManager.getInstance(this).getJwtToken()).create(ApiService.class);
        Call<Lead> call = apiService.createLead(lead);
        call.enqueue(new Callback<Lead>() {
            @Override
            public void onResponse(Call<Lead> call, Response<Lead> response) {
                progressBar.setVisibility(View.GONE);
                btnCreateLead.setEnabled(true);
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(LeadCreationActivity.this, "Lead created successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(LeadCreationActivity.this, "Failed to create lead", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Lead> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                btnCreateLead.setEnabled(true);
                Toast.makeText(LeadCreationActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
