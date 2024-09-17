package com.szniloycoder.foodapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.szniloycoder.foodapp.Adapters.CategoryFoodsAdapter;
import com.szniloycoder.foodapp.Models.FoodsModel;
import com.szniloycoder.foodapp.databinding.ActivityCatergoryFoodsBinding;

import java.util.ArrayList;

public class CatergoryFoodsActivity extends AppCompatActivity {

     CategoryFoodsAdapter adapter;
     ActivityCatergoryFoodsBinding binding;
     String catId;
     String catTitle;
     FirebaseDatabase database;
     ArrayList<FoodsModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCatergoryFoodsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        list = new ArrayList<>();

        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_color));

        // Setup RecyclerView
        binding.recCatFoods.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new CategoryFoodsAdapter(this, list);
        binding.recCatFoods.setAdapter(adapter);

        // Retrieve data from Intent
        catId = getIntent().getStringExtra("id");
        catTitle = getIntent().getStringExtra("Title");
        binding.nameCat.setText(catTitle);

        // Set up Back button listener
        binding.btnBack.setOnClickListener(v -> finish());

        // Load data
        setData();
    }

    private void setData() {
        binding.progressBarCatFoods.setVisibility(View.VISIBLE);

        database.getReference().child("Foods").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    boolean hasFood = false;

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        FoodsModel model = dataSnapshot.getValue(FoodsModel.class);

                        if (model != null && model.getCatId().equals(catId)) {
                            model.setItemId(dataSnapshot.getKey());
                            list.add(model);
                            hasFood = true;
                        }
                    }

                    if (hasFood) {
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(CatergoryFoodsActivity.this, "No food available", Toast.LENGTH_SHORT).show();
                    }

                    binding.progressBarCatFoods.setVisibility(View.GONE);
                } else {
                    Toast.makeText(CatergoryFoodsActivity.this, "No Food Available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(CatergoryFoodsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}