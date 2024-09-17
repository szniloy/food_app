package com.szniloycoder.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.szniloycoder.foodapp.Adapters.SubOrderAdapter;
import com.szniloycoder.foodapp.Models.OrderDetails;
import com.szniloycoder.foodapp.databinding.ActivityTrackOrderBinding;

import java.util.ArrayList;
import java.util.List;

public class TrackOrderActivity extends AppCompatActivity {

     SubOrderAdapter adapter;
     List<String> allFoodImages;
     List<String> allFoodNames;
     List<Integer> allFoodPrices;
     List<Integer> allFoodQuantities;
     List<String> allFoodSizes;
     FirebaseAuth auth;
     ActivityTrackOrderBinding binding;
     FirebaseDatabase database;
     ArrayList<OrderDetails> list;
     String orderId;
     FirebaseStorage storage;
     double totalAmount;
     String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTrackOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase and other components
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        list = new ArrayList<>();

        // Set status bar color
        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_color));

        // Set click listener for back button
        binding.btnBack.setOnClickListener(v -> finish());

        // Initialize UI components and data
        binding.progressBarOrdrs.setVisibility(View.VISIBLE);

        // Retrieve and process intent data
        Intent intent = getIntent();
        if (intent != null) {
            allFoodNames = intent.getStringArrayListExtra("foodName");
            allFoodPrices = intent.getIntegerArrayListExtra("foodPrice");
            allFoodQuantities = intent.getIntegerArrayListExtra("foodQuantities");
            allFoodImages = intent.getStringArrayListExtra("foodImage");
            allFoodSizes = intent.getStringArrayListExtra("foodSize");
            orderId = intent.getStringExtra("orderId");
            userId = intent.getStringExtra("userId");
            totalAmount = intent.getDoubleExtra("TotalAmount", 0.0);

            Log.d("TotalAmount", "Total Amount: " + totalAmount);
            binding.orderIdShowTxt.setText(orderId);

            listenForOrderStatusUpdates(userId, orderId);
        }

        setAdapter();
    }

    private void setAdapter() {
        binding.recyclerOrdrs.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubOrderAdapter(this, allFoodImages, allFoodNames, allFoodPrices, allFoodQuantities, allFoodSizes, list);
        binding.recyclerOrdrs.setAdapter(adapter);
        binding.progressBarOrdrs.setVisibility(View.GONE);
    }

    private void listenForOrderStatusUpdates(String userId, String orderId) {
        database.getReference("ActiveOrders")
                .child(userId)
                .child(orderId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        OrderDetails orderDetails = snapshot.getValue(OrderDetails.class);
                        if (orderDetails != null) {
                            updateOrderStatusUI(orderDetails);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.e("TrackOrderActivity", "Failed to read order status", error.toException());
                    }
                });
    }

    private void updateOrderStatusUI(OrderDetails orderDetails) {
        if (orderDetails.getProcessing()) {
            updateUI(binding.viewState1, binding.tvState1, R.drawable.cv_green_circle, binding.viewProgress1, R.color.mainColor);
        }
        if (orderDetails.getAccept()) {
            updateUI(binding.viewState2, binding.tvState2, R.drawable.cv_green_circle, binding.viewProgress2, R.color.mainColor);
        }
        if (orderDetails.getCooking()) {
            updateUI(binding.viewState3, binding.tvState3, R.drawable.cv_green_circle, binding.viewProgress3, R.color.mainColor);
        }
        if (orderDetails.getOutForDelivery()) {
            updateUI(binding.viewState4, binding.tvState4, R.drawable.cv_green_circle, binding.viewProgress4, R.color.mainColor);
        }
        if (orderDetails.getDelivered()) {
            updateUI(binding.viewState5, binding.tvState5, R.drawable.cv_green_circle, null, R.color.mainColor);
        }
    }

    private void updateUI(View circleView, TextView textView, int circleDrawableRes, View progressView, int progressColorRes) {
        circleView.setBackgroundResource(circleDrawableRes);
        textView.setTextColor(getResources().getColor(R.color.mainColor));
        if (progressView != null) {
            progressView.setBackgroundColor(getResources().getColor(progressColorRes));
        }
    }
}