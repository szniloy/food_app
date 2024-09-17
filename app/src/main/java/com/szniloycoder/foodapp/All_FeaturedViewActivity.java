package com.szniloycoder.foodapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.szniloycoder.foodapp.Adapters.AllFeaturedAdapter;
import com.szniloycoder.foodapp.Models.FeaturedModel;
import com.szniloycoder.foodapp.databinding.ActivityAllFeaturedViewBinding;

import java.util.ArrayList;

public class All_FeaturedViewActivity extends AppCompatActivity {

     AllFeaturedAdapter adapter;
     ActivityAllFeaturedViewBinding binding;
     FirebaseDatabase database;
     ArrayList<FeaturedModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout and set the content view
        binding = ActivityAllFeaturedViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set status bar color
        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_color));

        // Initialize Firebase database and list
        database = FirebaseDatabase.getInstance();
        list = new ArrayList<>();

        // Set up RecyclerView
        binding.recAllFetView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AllFeaturedAdapter(this, list);
        binding.recAllFetView.setAdapter(adapter);

        // Load data into RecyclerView
        setRecyclerData();

        // Set up back button click listener
        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void setRecyclerData() {
        database.getReference().child("featured").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        FeaturedModel model = dataSnapshot.getValue(FeaturedModel.class);
                        if (model != null) {
                            model.setKey(dataSnapshot.getKey());
                            list.add(model);
                        } else {
                            throw new AssertionError();
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(All_FeaturedViewActivity.this, "No featured exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(All_FeaturedViewActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}