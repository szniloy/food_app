package com.szniloycoder.foodapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.szniloycoder.foodapp.databinding.ActivityPasswordManagerBinding;

public class Password_ManagerActivity extends AppCompatActivity {

    FirebaseAuth auth;
    ActivityPasswordManagerBinding binding;
    ProgressDialog dialog;
    String currentPass;
    String newPass;
    String confNewPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPasswordManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_color));

        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Loading...");

        setupListeners();
    }

    private void setupListeners() {
        binding.btnBack.setOnClickListener(view -> finish());
        binding.btnForgetPassManager.setOnClickListener(view -> startActivity(new
                Intent(this, ForgotPasswordActivity.class)));
        binding.btnSavePass.setOnClickListener(view -> handlePasswordChange());
    }

    private void handlePasswordChange() {
        currentPass = binding.currentPass.getText().toString();
        newPass = binding.newPass.getText().toString();
        confNewPass = binding.confirmNewPass.getText().toString();

        if (currentPass.isEmpty()) {
            binding.currentPass.setError("Please enter your current password.");
        } else if (newPass.isEmpty()) {
            binding.newPass.setError("Please enter your new password.");
        } else if (confNewPass.isEmpty()) {
            binding.confirmNewPass.setError("Please confirm your new password.");
        } else {
            if (newPass.equals(confNewPass)) {
                changePassword(currentPass, newPass);
            } else {
                binding.confirmNewPass.setError("Passwords do not match.");
            }
        }
    }

    private void changePassword(String currentPass, String newPass) {
        dialog.show();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            user.reauthenticate(EmailAuthProvider.getCredential(user.getEmail(), currentPass))
                    .addOnSuccessListener(aVoid -> updatePassword(user, newPass))
                    .addOnFailureListener(e -> handleError(e));
        } else {
            throw new AssertionError("User is null.");
        }
    }

    private void updatePassword(FirebaseUser user, String newPass) {
        user.updatePassword(newPass)
                .addOnCompleteListener(task -> {
                    dialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Password update failed.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(this::handleError);
    }

    private void handleError(Exception e) {
        dialog.dismiss();
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}