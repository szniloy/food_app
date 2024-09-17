package com.szniloycoder.foodapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.szniloycoder.foodapp.Adapters.CartAdapter;
import com.szniloycoder.foodapp.Models.CartModel;
import com.szniloycoder.foodapp.databinding.ActivityCartBinding;

import java.util.ArrayList;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {

    CartAdapter adapter;
    double allItemPrice;
    FirebaseAuth auth;
    ActivityCartBinding binding;
    FirebaseDatabase database;
    FirebaseStorage storage;
    ArrayList<CartModel> list;
    int totalBill;

    // Cart item details
    String catId, catName, foodImages, foodName, foodPrice, itemId, size, deliveryTime;
    int foodQuantities;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set status bar color
        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_color));

        // Initialize Firebase
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        // Setup cart list
        list = new ArrayList<>();
        binding.recyCart.setLayoutManager(new LinearLayoutManager
                (this, LinearLayoutManager.VERTICAL, false));
        adapter = new CartAdapter(list, this);
        binding.recyCart.setAdapter(adapter);

        // Register local broadcast receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("CartTotalAmount"));

        // Setup button click listeners
        binding.btnBack.setOnClickListener(view -> finish());
        binding.btnCheckout.setOnClickListener(view -> placeOrder());

        // Fetch data from Firebase
        getData();
    }

    // BroadcastReceiver to receive updates on total price and cart item details
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            totalBill = intent.getIntExtra("TotalPrice", 0);
            Log.d("CartFragment", "Received Intent extras: " + intent.getExtras());

            // Update cart item details from intent
            foodName = intent.getStringExtra("foodName");
            foodImages = intent.getStringExtra("foodImage");
            size = intent.getStringExtra("size");
            catId = intent.getStringExtra("catId");
            itemId = intent.getStringExtra("itemId");
            deliveryTime = intent.getStringExtra("deliveryTime");
            catName = intent.getStringExtra("foodCategory");
            foodPrice = intent.getStringExtra("foodPrice");
            foodQuantities = intent.getIntExtra("foodQuantity", 0);

            // Pass cart details back to the intent
            intent.putExtra("foodImage", foodImages);
            intent.putExtra("foodQuantity", foodQuantities);
            intent.putExtra("foodPrice", foodPrice);
            intent.putExtra("TotalPrice", totalBill);
            intent.putExtra("foodCategory", catName);
            intent.putExtra("catId", catId);
            intent.putExtra("itemId", itemId);
            intent.putExtra("deliveryTime", deliveryTime);
            intent.putExtra("size", size);
            intent.putExtra("totalAmount", allItemPrice);

            // Calculate tax and update UI
            double tax = totalBill * 0.02;
            binding.totalFeeTxt.setText("$" + totalBill);
            binding.totalTaxTxt.setText("$" + tax);
            binding.deliveryTxt.setText("$3.0");
            binding.totalTxt.setText("$" + (totalBill + tax + 3.0));

            // Update total amount
            String totalPriceString = binding.totalTxt.getText().toString().trim().replaceAll("[^\\d.]", "");
            allItemPrice = Double.parseDouble(totalPriceString);
        }
    };

    private void getData() {
        binding.progressBarCart.setVisibility(View.VISIBLE);
        String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        database.getReference().child("CartItems").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        CartModel model = dataSnapshot.getValue(CartModel.class);
                        if (model != null) {
                            model.setKey(dataSnapshot.getKey());
                            list.add(model);
                        }
                    }
                    binding.progressBarCart.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                } else {
                    list.clear();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(CartActivity.this, "No items in the cart", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CartActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void placeOrder() {
        Intent intent = new Intent(this, CheckoutActivity.class);
        intent.putExtra("foodName", foodName);
        intent.putExtra("foodImage", foodImages);
        intent.putExtra("foodQuantity", foodQuantities);
        intent.putExtra("foodPrice", foodPrice);
        intent.putExtra("TotalPrice", totalBill);
        intent.putExtra("foodCategory", catName);
        intent.putExtra("catId", catId);
        intent.putExtra("size", size);
        intent.putExtra("itemId", itemId);
        intent.putExtra("deliveryTime", deliveryTime);
        intent.putExtra("totalAmount", allItemPrice);
        startActivity(intent);


    }
}