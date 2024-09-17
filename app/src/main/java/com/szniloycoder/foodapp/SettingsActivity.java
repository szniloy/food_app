package com.szniloycoder.foodapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.szniloycoder.foodapp.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_color));

        setupListeners();
    }

    private void setupListeners() {
        binding.btnNotificationSettings.setOnClickListener(view -> openNotificationSettings());
        binding.btnDeleteAcct.setOnClickListener(view -> showDeleteAccountDialog());
        binding.btnBack.setOnClickListener(view -> finish());
    }

    private void openNotificationSettings() {
        Intent intent = new Intent(this, Notification_SettingsActivity.class);
        startActivity(intent);
    }

    private void showDeleteAccountDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account? This action is permanent.")
                .setPositiveButton("Delete", (dialog, which) -> deleteAccount())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteAccount() {
        // Handle account deletion logic here
    }
}