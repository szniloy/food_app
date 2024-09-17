package com.szniloycoder.foodapp;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.szniloycoder.foodapp.Fragments.ActiveOrderFragment;
import com.szniloycoder.foodapp.Fragments.CancelOrderFragment;
import com.szniloycoder.foodapp.Fragments.CompletedOrderFragment;
import com.szniloycoder.foodapp.databinding.ActivityMyOrdersBinding;

public class MyOrdersActivity extends AppCompatActivity {

    ActivityMyOrdersBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set the status bar color
        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_color));

        // Set back button listener
        binding.btnBack.setOnClickListener(view -> finish());

        // Load the initial fragment (Active Orders)
        loadFragment(new ActiveOrderFragment());
        highlightButton(binding.btnActive);

        // Set up button click listeners
        setupButtonListeners();
    }

    /**
     * Set up click listeners for the buttons (Active, Completed, Cancelled).
     */
    private void setupButtonListeners() {
        binding.btnActive.setOnClickListener(v -> {
            loadFragment(new ActiveOrderFragment());
            highlightButton(binding.btnActive);
        });

        binding.btnCompleted.setOnClickListener(v -> {
            loadFragment(new CompletedOrderFragment());
            highlightButton(binding.btnCompleted);
        });

        binding.btnCancelled.setOnClickListener(v -> {
            loadFragment(new CancelOrderFragment());
            highlightButton(binding.btnCancelled);
        });
    }

    /**
     * Loads the selected fragment into the fragment container.
     *
     * @param fragment the fragment to load
     */
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * Highlights the selected button by changing its background and text color.
     *
     * @param selectedButton the button to highlight
     */
    private void highlightButton(Button selectedButton) {
        resetButtonColors();
        selectedButton.setBackgroundColor(getResources().getColor(R.color.mainColor));
        selectedButton.setTextColor(getResources().getColor(R.color.white));
    }

    /**
     * Resets the background and text colors of all buttons to their default state.
     */
    private void resetButtonColors() {
        int defaultBgColor = getResources().getColor(R.color.activity_color);
        int defaultTextColor = getResources().getColor(R.color.txt_gray);

        binding.btnActive.setBackgroundColor(defaultBgColor);
        binding.btnActive.setTextColor(defaultTextColor);

        binding.btnCompleted.setBackgroundColor(defaultBgColor);
        binding.btnCompleted.setTextColor(defaultTextColor);

        binding.btnCancelled.setBackgroundColor(defaultBgColor);
        binding.btnCancelled.setTextColor(defaultTextColor);
    }
}