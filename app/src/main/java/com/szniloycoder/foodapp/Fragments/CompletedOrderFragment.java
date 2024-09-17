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
import com.szniloycoder.foodapp.Adapters.CompleteOrderAdapter;
import com.szniloycoder.foodapp.Models.OrderDetails;
import com.szniloycoder.foodapp.R;
import com.szniloycoder.foodapp.databinding.FragmentCompletedOrderBinding;

import java.util.ArrayList;

public class CompletedOrderFragment extends Fragment {

     CompleteOrderAdapter adapter;
     FirebaseAuth auth;
     FragmentCompletedOrderBinding binding;
     Activity context;
     FirebaseDatabase database;
     FirebaseUser firebaseUser;
     ArrayList<OrderDetails> list;
     DatabaseReference reference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        binding = FragmentCompletedOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        list = new ArrayList<>();
        binding.recyDeliveredOrdr.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        adapter = new CompleteOrderAdapter(context, list);
        binding.recyDeliveredOrdr.setAdapter(adapter);
        fetchFoodItems();
    }

    private void fetchFoodItems() {
        binding.progressBarDeliverdOrdr.setVisibility(View.VISIBLE);
        database.getReference().child("DeliveredOrders").addListenerForSingleValueEvent(new ValueEventListener() {
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
                    binding.progressBarDeliverdOrdr.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                } else {
                    binding.progressBarDeliverdOrdr.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "No delivered orders found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                binding.progressBarDeliverdOrdr.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Failed to load food items: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}