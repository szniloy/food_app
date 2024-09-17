package com.szniloycoder.foodapp;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.szniloycoder.foodapp.Adapters.CategoryAdapter;
import com.szniloycoder.foodapp.Models.CategoryModel;
import com.szniloycoder.foodapp.databinding.ActivityCategoryBinding;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

     CategoryAdapter adapter;
     ActivityCategoryBinding binding;
     FirebaseDatabase database;
     ArrayList<CategoryModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_color));

        database = FirebaseDatabase.getInstance();
        list = new ArrayList<>();

        binding.btnBack.setOnClickListener(view -> finish());

        initCategory();
    }

    private void initCategory() {
        DatabaseReference myRef = database.getReference().child("Menu").child("FoodCategory");
        binding.progressBarCat.setVisibility(View.VISIBLE);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        CategoryModel model = dataSnapshot.getValue(CategoryModel.class);
                        if (model != null) {
                            model.setKey(dataSnapshot.getKey());
                            list.add(model);
                        } else {
                            throw new AssertionError();
                        }
                    }
                    if (!list.isEmpty()) {
                        binding.recAllCatView.setLayoutManager(new GridLayoutManager(CategoryActivity.this, 4));
                        adapter = new CategoryAdapter(CategoryActivity.this, list);
                        binding.recAllCatView.setAdapter(adapter);
                    }
                    binding.progressBarCat.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle possible errors.
            }
        });
    }
}