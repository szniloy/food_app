package com.szniloycoder.foodapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.szniloycoder.foodapp.Adapters.FullscreenImageAdapter;
import com.szniloycoder.foodapp.databinding.ActivityFullscreenImageBinding;

import java.util.ArrayList;

public class FullscreenImageActivity extends AppCompatActivity {

     ActivityFullscreenImageBinding binding;
     ArrayList<String> imageUrls;
     int position;
     ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the binding and set the content view
        binding = ActivityFullscreenImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set the window flags for full-screen mode
        getWindow().setFlags(512, 512);

        // Initialize views and variables
        viewPager = findViewById(R.id.fullscreen_viewpager);
        imageUrls = getIntent().getStringArrayListExtra("imageUrls");
        position = getIntent().getIntExtra("position", 0);

        // Setup ViewPager with the adapter
        setupViewPager(viewPager);

        // Handle back button click event
        binding.btnBack.setOnClickListener(view -> finish());
    }

    // Setup the ViewPager with the FullscreenImageAdapter
    private void setupViewPager(ViewPager viewPager) {
        viewPager.setAdapter(new FullscreenImageAdapter(this, imageUrls));
        viewPager.setCurrentItem(position);
    }
}