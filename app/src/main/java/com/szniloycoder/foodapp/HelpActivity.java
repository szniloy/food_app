package com.szniloycoder.foodapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.szniloycoder.foodapp.Fragments.ContactFragment;
import com.szniloycoder.foodapp.Fragments.FaqFragment;
import com.szniloycoder.foodapp.databinding.ActivityHelpBinding;

public class HelpActivity extends AppCompatActivity {
     ActivityHelpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout and set the content view
        binding = ActivityHelpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set the status bar color
        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_color));

        // Set up button click listeners
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new FaqFragment());
                highlightButton(binding.btnFaq);
            }
        });

        binding.btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ContactFragment());
                highlightButton(binding.btnContact);
            }
        });

        // Load the initial fragment and highlight the default button
        loadFragment(new FaqFragment());
        highlightButton(binding.btnFaq);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void highlightButton(Button selectedButton) {
        resetButtonColors();
        selectedButton.setBackgroundColor(getResources().getColor(R.color.mainColor));
        selectedButton.setTextColor(getResources().getColor(R.color.white));
    }

    private void resetButtonColors() {
        binding.btnFaq.setBackgroundColor(getResources().getColor(R.color.activity_color));
        binding.btnFaq.setTextColor(getResources().getColor(R.color.mainColor));
        binding.btnContact.setBackgroundColor(getResources().getColor(R.color.activity_color));
        binding.btnContact.setTextColor(getResources().getColor(R.color.mainColor));
    }
}