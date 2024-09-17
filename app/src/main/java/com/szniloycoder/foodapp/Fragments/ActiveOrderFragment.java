package com.szniloycoder.foodapp.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.szniloycoder.foodapp.Adapters.ActiveOrderAdapter;
import com.szniloycoder.foodapp.Models.OrderDetails;
import com.szniloycoder.foodapp.R;
import com.szniloycoder.foodapp.databinding.FragmentActiveOrderBinding;

import java.util.ArrayList;
import java.util.Objects;

public class ActiveOrderFragment extends Fragment {

    // Member variables
     ActiveOrderAdapter adapter;
     FirebaseAuth auth;
     FragmentActiveOrderBinding binding;
     Activity context;
     FirebaseDatabase database;
     FirebaseUser firebaseUser;
     ArrayList<OrderDetails> list;
     DatabaseReference reference;

     String userId;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        binding = FragmentActiveOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Firebase and other components
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        list = new ArrayList<>();

        // Set up RecyclerView
       binding.recyActiveOrdr.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        adapter = new ActiveOrderAdapter(context, list);
        binding.recyActiveOrdr.setAdapter(adapter);

        userId = firebaseUser.getUid();

        // Fetch food items from Firebase
        fetchFoodItems();
    }

    private void fetchFoodItems() {
        this.binding.progressBarActiveOrdr.setVisibility(View.VISIBLE);
        this.database.getReference().child("ActiveOrders")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String userId = userSnapshot.getKey();
                        for (DataSnapshot orderSnapshot : userSnapshot.getChildren()) {
                            String orderId = orderSnapshot.getKey();
                            OrderDetails orderDetails = orderSnapshot.getValue(OrderDetails.class);
                            if (orderDetails != null && orderDetails.getFoodNames() != null) {
                                orderDetails.setUserId(userId);
                                orderDetails.setItemPushKey(orderId);
                                list.add(orderDetails);
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "No active orders found", Toast.LENGTH_SHORT).show();
                }
                binding.progressBarActiveOrdr.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load food items: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                binding.progressBarActiveOrdr.setVisibility(View.GONE);
            }
        });
    }
}