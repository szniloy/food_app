package com.szniloycoder.foodapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.szniloycoder.foodapp.Models.AddressModel;
import com.szniloycoder.foodapp.databinding.ActivityAddAddressBinding;

import java.util.HashMap;
import java.util.Objects;

public class Add_AddressActivity extends AppCompatActivity {

     String addNewAddrs;
     String addrTitle;
     String latitude;
     String longitude;
     FirebaseAuth auth;
     ActivityAddAddressBinding binding;
     FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize binding and set content view
        binding = ActivityAddAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set status bar color
        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_color));

        // Initialize Firebase components
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        // Set up button listeners
        binding.btnBack.setOnClickListener(view -> finish());
        binding.btnSaveNewAddrs.setOnClickListener(view -> {
            addrTitle = binding.addressTitleTxt.getText().toString();
            addNewAddrs = binding.addressWriteTxt.getText().toString();
            longitude = binding.longitudeTxt.getText().toString();
            latitude = binding.latitudeTxt.getText().toString();
            updateUserProfile(addrTitle, addNewAddrs, longitude, latitude);
        });
    }

    private void updateUserProfile(String addrTitle, String addNewAddrs, String longitude, String latitude) {
        String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        AddressModel adr = new AddressModel();
        DatabaseReference userAddressRef = database.getReference().child("address").child(userId);
        String key = userAddressRef.push().getKey();

        // Prepare address data
        HashMap<String, Object> addressData = new HashMap<>();
        addressData.put("addrTitle", addrTitle);
        addressData.put("addNewAddrs", addNewAddrs);
        addressData.put("key", key);
        addressData.put("longitude", longitude);
        addressData.put("latitude", latitude);

        // Save address data to Firebase
        if (key != null) {
            userAddressRef.child(key).setValue(addressData);
            Toast.makeText(this, "Added new address", Toast.LENGTH_SHORT).show();
        }
    }
}