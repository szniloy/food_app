package com.szniloycoder.foodapp.Fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.szniloycoder.foodapp.Adapters.CategoryFoodsAdapter;
import com.szniloycoder.foodapp.Models.FoodsModel;
import com.szniloycoder.foodapp.R;
import com.szniloycoder.foodapp.databinding.FragmentExploreBinding;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {

     CategoryFoodsAdapter adapter;
     FragmentExploreBinding binding;
     Activity context;
     FirebaseDatabase database;
     ArrayList<FoodsModel> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        binding = FragmentExploreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance();
        list = new ArrayList<>();
        binding.recAllFoods.setLayoutManager(new GridLayoutManager(context, 2));
        adapter = new CategoryFoodsAdapter(context, list);
        binding.recAllFoods.setAdapter(adapter);
        setData();
    }

    private void setData() {
        binding.allFoodsProgressBar.setVisibility(View.VISIBLE);
        database.getReference().child("Foods").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        list.add(dataSnapshot.getValue(FoodsModel.class));
                    }
                    binding.allFoodsProgressBar.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "No Food Available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}