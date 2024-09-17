package com.szniloycoder.foodapp;

import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.szniloycoder.foodapp.Adapters.CheckOutAdapter;
import com.szniloycoder.foodapp.Models.CartModel;
import com.szniloycoder.foodapp.databinding.ActivityCheckoutBinding;

import java.util.ArrayList;
import java.util.Objects;

public class CheckoutActivity extends AppCompatActivity {

    // UI Components
     CheckOutAdapter adapter;
     ActivityCheckoutBinding binding;

    // Data Variables
     String addressTitle;
     String addrsTxt;
     String catId;
     String catName;
     String deliveryTime;
     String foodCategory;
     String foodImages;
     String foodName;
     String foodPrice;
     String itemId;
     String phone;
     String size;
     String userName;
     String userUid;

     int foodQuantities;
     double totalAmount;
     double totalPrice;

    // Firebase Variables
     FirebaseAuth auth;
     FirebaseDatabase database;
     FirebaseStorage storage;

    // Data Lists
    private ArrayList<CartModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase components
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        // Initialize other components
        list = new ArrayList<>();
        adapter = new CheckOutAdapter(this, list);
        binding.recOrdrListChk.setLayoutManager(new LinearLayoutManager(this));
        binding.recOrdrListChk.setAdapter(adapter);

        // Set event listeners
        binding.btnBack.setOnClickListener(view -> finish());
        binding.btnChangeAdr.setOnClickListener(view -> startActivity(new Intent(this, AddressActivity.class)));
        binding.btnPayment.setOnClickListener(view -> Checkout());

        // Extract intent extras
        Intent intent = getIntent();
        totalAmount = intent.getDoubleExtra("totalAmount", 0.0);
        totalPrice = intent.getDoubleExtra("TotalPrice", 0.0);
        foodName = intent.getStringExtra("foodName");
        foodImages = intent.getStringExtra("foodImage");
        foodQuantities = intent.getIntExtra("foodQuantity", 0);
        foodPrice = intent.getStringExtra("foodPrice");
        foodCategory = intent.getStringExtra("foodCategory");
        catId = intent.getStringExtra("catId");
        catName = intent.getStringExtra("foodCategory");
        size = intent.getStringExtra("size");
        itemId = intent.getStringExtra("itemId");
        deliveryTime = intent.getStringExtra("deliveryTime");


        // Set status bar color
        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_color));

        // Check user authentication
        if (auth.getCurrentUser() != null) {
            userUid = auth.getCurrentUser().getUid();
            setUserData();
            getData();
        } else {
            Toast.makeText(this, "User is not authenticated. Please log in.", Toast.LENGTH_SHORT).show();
            Log.e("CheckoutActivity", "User is not authenticated.");
        }

    }


    private void setUserData() {
        if (userUid != null) {
            database.getReference().child("user")
                    .child(userUid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                addressTitle = snapshot.child("addressTitle").getValue(String.class);
                                addrsTxt = snapshot.child("address").getValue(String.class);
                                userName = snapshot.child("userName").getValue(String.class);
                                phone = snapshot.child("phone").getValue(String.class);

                                binding.txtaddrsName.setText(addressTitle);
                                binding.txtAddress.setText(addrsTxt);
                            } else {
                                Log.w("CheckoutActivity", "User data not found in database");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Log.e("CheckoutActivity", "Database error: " + error.getMessage());
                        }
                    });
        } else {
            Log.e("CheckoutActivity", "User UID is null, unable to retrieve user data.");
        }
    }


    private void getData() {
        binding.progressBarCheckOut.setVisibility(View.VISIBLE);

        if (userUid != null) {
            database.getReference().child("CartItems")
                    .child(userUid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                list.clear();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    CartModel model = dataSnapshot.getValue(CartModel.class);
                                    if (model != null) {
                                        model.setKey(dataSnapshot.getKey());
                                        catId = model.getCatId();
                                        foodCategory = model.getFoodCategory();
                                        list.add(model);
                                    }
                                }
                                binding.progressBarCheckOut.setVisibility(View.GONE);
                                adapter.notifyDataSetChanged();
                            } else {
                                list.clear();
                                adapter.notifyDataSetChanged();
                                Toast.makeText(CheckoutActivity.this, "No items available", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Toast.makeText(CheckoutActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            binding.progressBarCheckOut.setVisibility(View.GONE);
            Toast.makeText(this, "User is not authenticated. Please log in.", Toast.LENGTH_SHORT).show();
            Log.e("CheckoutActivity", "User UID is null, unable to retrieve cart items.");
        }
    }




    private void Checkout() {
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra("foodName", foodName);
        intent.putExtra("foodImage", foodImages);
        intent.putExtra("foodQuantity", foodQuantities);
        intent.putExtra("foodPrice", foodPrice);
        intent.putExtra("TotalPrice", totalPrice);
        intent.putExtra("foodCategory", catName);
        intent.putExtra("catId", catId);
        intent.putExtra("size", size);
        intent.putExtra("userName", userName);
        intent.putExtra("itemId", itemId);
        intent.putExtra("deliveryTime", deliveryTime);
        intent.putExtra("totalAmount", totalAmount);
        intent.putExtra("addressTitle", addressTitle);
        intent.putExtra("address", addrsTxt);
        intent.putExtra("phone", phone);
        startActivity(intent);
    }
}