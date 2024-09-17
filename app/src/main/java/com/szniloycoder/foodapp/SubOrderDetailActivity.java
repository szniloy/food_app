package com.szniloycoder.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.szniloycoder.foodapp.Adapters.SubOrderAdapter;
import com.szniloycoder.foodapp.Models.OrderDetails;
import com.szniloycoder.foodapp.databinding.ActivitySubOrderDetailBinding;

import java.util.ArrayList;
import java.util.List;

public class SubOrderDetailActivity extends AppCompatActivity {

    // Member variables
     String address;
     SubOrderAdapter adapter;
     List<String> allFoodImages;
     List<String> allFoodNames;
     List<Integer> allFoodPrices;
     List<Integer> allFoodQuantities;
     List<String> allFoodSizes;
     FirebaseAuth auth;
     ActivitySubOrderDetailBinding binding;
     FirebaseDatabase database;
     ArrayList<OrderDetails> list;
     String orderId;
     String phone;
     FirebaseStorage storage;
     double totalAmount;
     String userGName;
     String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize binding
        binding = ActivitySubOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set status bar color
        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_color));

        // Initialize Firebase instances
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        // Initialize the list
        list = new ArrayList<>();

        // Set up button click listener
        binding.btnBack.setOnClickListener(view -> finish());

        // Show progress bar
        binding.progressBarSubOrdrs.setVisibility(View.VISIBLE);

        // Handle intent data
        Intent intent = getIntent();
        if (intent != null) {
            allFoodNames = intent.getStringArrayListExtra("foodName");
            allFoodPrices = intent.getIntegerArrayListExtra("foodPrice");
            allFoodQuantities = intent.getIntegerArrayListExtra("foodQuantities");
            allFoodImages = intent.getStringArrayListExtra("foodImage");
            allFoodSizes = intent.getStringArrayListExtra("foodSize");
            userGName = intent.getStringExtra("userName");
            address = intent.getStringExtra("address");
            phone = intent.getStringExtra("phone");
            orderId = intent.getStringExtra("orderId");
            userId = intent.getStringExtra("userId");
            totalAmount = intent.getDoubleExtra("TotalAmount", 0.0d);

            // Log total amount
            Log.d("TotalAmount", "Total Amount: " + totalAmount);

            // Set text views
            binding.nameViewTxt.setText(userGName);
            binding.addrsViewTxt.setText(address);
            binding.phnViewTxt.setText(phone);
            binding.orderIdViewTxt.setText(orderId);
            binding.totalViewPay.setText("$" + totalAmount);
        }

        // Set up RecyclerView adapter
        setAdapter();
    }

    private void setAdapter() {
        binding.recySubOrdrsView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubOrderAdapter(this, allFoodImages, allFoodNames, allFoodPrices, allFoodQuantities, allFoodSizes, list);
        binding.recySubOrdrsView.setAdapter(adapter);
        binding.progressBarSubOrdrs.setVisibility(View.GONE);
    }
}