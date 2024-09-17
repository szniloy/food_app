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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.szniloycoder.foodapp.Adapters.FavFoodAdapter;
import com.szniloycoder.foodapp.Models.FavModel;
import com.szniloycoder.foodapp.R;
import com.szniloycoder.foodapp.databinding.FragmentFavouriteBinding;

import java.util.ArrayList;
import java.util.Objects;

public class FavouriteFragment extends Fragment {

     FavFoodAdapter adapter;
     FirebaseAuth auth;
     FragmentFavouriteBinding binding;
     Activity context;
     FirebaseDatabase database;
     ArrayList<FavModel> list;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        binding = FragmentFavouriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        list = new ArrayList<>();

        binding.recAllFvFoods.setLayoutManager(new GridLayoutManager(context, 2));
        adapter = new FavFoodAdapter(context, list);
        binding.recAllFvFoods.setAdapter(adapter);

        setData();
    }

    private void setData() {
        binding.allFavFoodsProgress.setVisibility(View.VISIBLE);
        String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        database.getReference()
                .child("favouriteFood")
                .child(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            list.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                FavModel favModel = dataSnapshot.getValue(FavModel.class);
                                if (favModel != null) {
                                    list.add(favModel);
                                }
                            }
                            binding.allFavFoodsProgress.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                        } else {
                            binding.allFavFoodsProgress.setVisibility(View.GONE);
                            Toast.makeText(context, "No Favourite Food Available", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        binding.allFavFoodsProgress.setVisibility(View.GONE);
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}