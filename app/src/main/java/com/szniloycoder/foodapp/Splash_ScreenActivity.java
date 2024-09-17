package com.szniloycoder.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.szniloycoder.foodapp.databinding.ActivitySplashScreenBinding;

public class Splash_ScreenActivity extends AppCompatActivity {

    FirebaseAuth auth;
    ActivitySplashScreenBinding binding;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize view binding
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Make the splash screen full-screen
        //hide statusBar:
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        // Show splash screen for 2 seconds, then redirect
        new Handler().postDelayed(() -> {
            if (currentUser != null) {
                // User is logged in, navigate to MainActivity
                startActivity(new Intent(Splash_ScreenActivity.this, MainActivity.class));
            } else {
                // User not logged in, navigate to LoginActivity
                startActivity(new Intent(Splash_ScreenActivity.this, LoginActivity.class));
            }
            finish();
        }, 2000);

    }
}