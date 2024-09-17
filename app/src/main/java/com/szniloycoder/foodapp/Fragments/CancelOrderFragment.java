package com.szniloycoder.foodapp.Fragments;

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
import com.szniloycoder.foodapp.Adapters.CancelOrderAdapter;
import com.szniloycoder.foodapp.Models.OrderDetails;
import com.szniloycoder.foodapp.R;
import com.szniloycoder.foodapp.databinding.FragmentCancelOrderBinding;

import java.util.ArrayList;

public class CancelOrderFragment extends Fragment {

     CancelOrderAdapter adapter;
     FirebaseAuth auth;
     FragmentCancelOrderBinding binding;
     Activity context;
     FirebaseDatabase database;
     FirebaseUser firebaseUser;
     ArrayList<OrderDetails> list;
     DatabaseReference reference;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        binding = FragmentCancelOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeComponents();
        setupRecyclerView();
        fetchFoodItems();
    }

    private void initializeComponents() {
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        list = new ArrayList<>();
    }

    private void setupRecyclerView() {
        binding.recyCanceledOrdr.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        adapter = new CancelOrderAdapter(context, list);
        binding.recyCanceledOrdr.setAdapter(adapter);
    }

    private void fetchFoodItems() {
        binding.progressBarCanceledOrdr.setVisibility(View.VISIBLE);
        database.getReference().child("CanceledOrders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
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
                    Toast.makeText(getContext(), "No canceled orders found", Toast.LENGTH_SHORT).show();
                }
                binding.progressBarCanceledOrdr.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load food items: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                binding.progressBarCanceledOrdr.setVisibility(View.GONE);
            }
        });
    }
}