package com.szniloycoder.foodapp;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.szniloycoder.foodapp.databinding.ActivityPrivacyPolicyBinding;

public class Privacy_policyActivity extends AppCompatActivity {

    ActivityPrivacyPolicyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the binding and set the content view
        binding = ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set the status bar color
        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_color));

        // Set up the back button click listener
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Set the privacy policy text
        binding.privacyPolicyText.setText(getString(R.string.privacy_policy_content));
    }
}