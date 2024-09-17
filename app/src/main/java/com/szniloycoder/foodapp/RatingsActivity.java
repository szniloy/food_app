package com.szniloycoder.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.szniloycoder.foodapp.Adapters.RatingAdapter;
import com.szniloycoder.foodapp.Models.FoodsModel;
import com.szniloycoder.foodapp.Models.RatingsModel;
import com.szniloycoder.foodapp.databinding.ActivityRatingsBinding;

import java.util.ArrayList;

public class RatingsActivity extends AppCompatActivity {

     RatingAdapter adapter;
     FirebaseAuth auth;
     FirebaseDatabase database;
     String foodItemId;
     ArrayList<RatingsModel> list;
     DatabaseReference ratingsRef;
     ActivityRatingsBinding binding;
     float averageRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRatingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase and variables
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        list = new ArrayList<>();
        foodItemId = getIntent().getStringExtra("itemId");

        // UI setup
        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_color));
        setupRecyclerView();
        setupListeners();

        // Fetch data and update UI
        fetchAndDisplayAverageRating();
        getReview();
        totalReviewCount();
        updateReview();
    }

    // Setup RecyclerView and adapter
    private void setupRecyclerView() {
        binding.recyReview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RatingAdapter(this, list);
        binding.recyReview.setAdapter(adapter);
    }

    // Setup listeners for buttons
    private void setupListeners() {
        binding.btnBack.setOnClickListener(view -> finish());
        binding.btnWriteReview.setOnClickListener(view -> {
            Intent intent = new Intent(this, Write_ReviewActivity.class);
            intent.putExtra("itemId", foodItemId);
            startActivity(intent);
        });
    }

    // Fetch total number of reviews and update UI
    private void totalReviewCount() {
        database.getReference().child("reviews").child(foodItemId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            binding.totalReviewCountTxt
                                    .setText("( " + Math.toIntExact(snapshot.getChildrenCount()) + " Reviews)");
                        } else {
                            binding.totalReviewCountTxt.setText("( 0 Reviews)");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Handle error
                    }
                });
    }

    // Fetch and display reviews in RecyclerView
    private void getReview() {
        binding.progressBarReview.setVisibility(View.VISIBLE);
        ratingsRef = database.getReference().child("reviews").child(foodItemId);
        ratingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot ratingSnapshot : snapshot.getChildren()) {
                        RatingsModel rating = ratingSnapshot.getValue(RatingsModel.class);
                        list.add(rating);
                    }
                    binding.progressBarReview.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(RatingsActivity.this, "No ratings available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Fetch and display the average rating
    private void fetchAndDisplayAverageRating() {
        database.getReference("reviews").child(foodItemId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        float totalRating = 0.0f;
                        int ratingCount = 0;

                        for (DataSnapshot ratingSnapshot : snapshot.getChildren()) {
                            RatingsModel rating = ratingSnapshot.getValue(RatingsModel.class);
                            if (rating != null) {
                                totalRating += rating.getRating();
                                ratingCount++;
                            }
                        }

                        if (ratingCount > 0) {
                            averageRating = totalRating / ratingCount;
                            binding.totalReviewTxt.setText(String.valueOf(averageRating));
                            binding.totalRatingView.setRating(averageRating);
                        } else {
                            binding.totalReviewTxt.setText("0.0");
                            binding.totalRatingView.setRating(0.0f);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(RatingsActivity.this, "Failed to load ratings", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Update the food item's rating based on reviews
    private void updateReview() {
        DatabaseReference userRef = database.getReference("menu").child("Foods").child(foodItemId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                FoodsModel model = snapshot.getValue(FoodsModel.class);
                if (model != null) {
                    model.setRatings(String.valueOf(averageRating));
                    userRef.setValue(model)
                            .addOnSuccessListener(unused -> {
                                // Success callback
                            })
                            .addOnFailureListener(e -> {
                                // Failure callback
                            });
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }
}