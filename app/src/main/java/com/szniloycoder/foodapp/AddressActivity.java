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
import com.szniloycoder.foodapp.Adapters.AddressAdapter;
import com.szniloycoder.foodapp.Models.AddressModel;
import com.szniloycoder.foodapp.Models.UserModel;
import com.szniloycoder.foodapp.databinding.ActivityAddressBinding;

import java.util.ArrayList;
import java.util.Objects;

public class AddressActivity extends AppCompatActivity {

    // Member variables
    private String address;
    private String addressTitle;
    private AddressAdapter addressAdapter;
    private FirebaseAuth auth;
    private ActivityAddressBinding binding;
    private FirebaseDatabase database;
    private ArrayList<AddressModel> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase and other variables
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        list = new ArrayList<>();
        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_color));

        // Set up click listeners
        binding.btnAddAddress.setOnClickListener(this::onAddAddressClicked);
        binding.btnBack.setOnClickListener(this::onBackClicked);
        binding.btnSaveSelectedAddress.setOnClickListener(this::onSaveSelectedAddressClicked);

        // Initialize address list
        initAddress();
    }

    // Click handler for "Add Address" button
    private void onAddAddressClicked(View view) {
        startActivity(new Intent(this, Add_AddressActivity.class));
    }

    // Click handler for "Back" button
    private void onBackClicked(View view) {
        finish();
    }

    // Click handler for "Save Selected Address" button
    private void onSaveSelectedAddressClicked(View view) {
        AddressModel selectedAddress = addressAdapter.getSelectedAddress();
        if (selectedAddress != null) {
            addressTitle = selectedAddress.getAddrTitle();
            address = selectedAddress.getAddNewAddrs();
            updateAddressDatabase(addressTitle, address);
        } else {
            Toast.makeText(this, "Please select an address", Toast.LENGTH_SHORT).show();
        }
    }

    // Update the address in the database
    private void updateAddressDatabase(final String addressTitle, final String address) {
        final DatabaseReference userRef = database.getReference("user")
                .child(Objects.requireNonNull(auth.getCurrentUser()).getUid());

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserModel model = snapshot.getValue(UserModel.class);
                    if (model != null) {
                        model.setAddressTitle(addressTitle);
                        model.setAddress(address);
                        userRef.setValue(model)
                                .addOnSuccessListener(aVoid ->
                                        Toast.makeText(AddressActivity.this, "Address updated successfully", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e ->
                                        Toast.makeText(AddressActivity.this, "Failed to update address", Toast.LENGTH_SHORT).show());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(AddressActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Initialize address list
    private void initAddress() {
        DatabaseReference myRef = database.getReference().child("address")
                .child(Objects.requireNonNull(auth.getCurrentUser()).getUid());

        binding.progressBarAddressAll.setVisibility(View.VISIBLE);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        AddressModel model = dataSnapshot.getValue(AddressModel.class);
                        if (model != null) {
                            model.setKey(dataSnapshot.getKey());
                            list.add(model);
                        }
                    }

                    if (!list.isEmpty()) {
                        binding.recyclerViewAllAddrs.setLayoutManager(new LinearLayoutManager(AddressActivity.this));
                        addressAdapter = new AddressAdapter(AddressActivity.this, list);
                        binding.recyclerViewAllAddrs.setAdapter(addressAdapter);
                    }

                    binding.progressBarAddressAll.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(AddressActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}