package com.szniloycoder.foodapp;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.szniloycoder.foodapp.Models.UserModel;
import com.szniloycoder.foodapp.databinding.ActivityWriteReviewBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class Write_ReviewActivity extends AppCompatActivity {

    private static final int PICK_IMAGES_REQUEST = 1;
     FirebaseAuth auth;
     FirebaseDatabase database;
     FirebaseUser fUser;
     ActivityWriteReviewBinding binding;
     String foodItemId;
     ArrayList<Uri> imageUris = new ArrayList<>();
     DatabaseReference ratingsRef;
     String reviewTxt;
     String userImg;
     String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWriteReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase components
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        fUser = this.auth.getCurrentUser();

        // Set status bar color
        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_color));

        // Retrieve food item ID from intent
        foodItemId = getIntent().getStringExtra("itemId");

        // Set up button listeners
        binding.btnBack.setOnClickListener(view -> finish());
        binding.btnSubmitReview.setOnClickListener(view -> submitReview());
        binding.btnAddImg.setOnClickListener(view -> openImagePicker());

        // Fetch user data and submit review
        fetchUserDataAndSubmitReview();
    }

    private void fetchUserDataAndSubmitReview() {
        this.database.getReference().child("users").child(fUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            UserModel user = snapshot.getValue(UserModel.class);
                            if (user != null) {
                                userName = user.getUserName();
                                userImg = user.getImageUrl();
                                submitReview();
                            } else {
                                Toast.makeText(Write_ReviewActivity.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Write_ReviewActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(Write_ReviewActivity.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void submitReview() {
        float ratingValue =binding.SetRatings.getRating();
        reviewTxt = binding.reviewTxt.getText().toString().trim();

        if (ratingValue == 0.0f) {
            Toast.makeText(this, "Please provide a rating", Toast.LENGTH_SHORT).show();
        } else if (reviewTxt.isEmpty()) {
            Toast.makeText(this, "Please provide a review", Toast.LENGTH_SHORT).show();
        } else {
            ratingsRef = database.getReference("reviews");
            String ratingId = ratingsRef.push().getKey();
            HashMap<String, Object> userReview = new HashMap<>();
            userReview.put("reviewerName", userName);
            userReview.put("reviewerImg", userImg);
            userReview.put("rating", ratingValue);
            userReview.put("ratingImg", "default");
            userReview.put("review", reviewTxt);

            if (ratingId != null) {
                ratingsRef.child(foodItemId).child(ratingId).setValue(userReview)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(Write_ReviewActivity.this, "Review submitted successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(Write_ReviewActivity.this, "Failed to submit review", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                throw new AssertionError("Rating ID is null");
            }
        }
    }

    private void openImagePicker() {
        // Implementation for image picker
    }
}