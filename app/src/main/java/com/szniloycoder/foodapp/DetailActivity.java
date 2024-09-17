package com.szniloycoder.foodapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.WindowManager;
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
import com.google.firebase.storage.FirebaseStorage;
import com.szniloycoder.foodapp.Adapters.DetailImgViewAdapter;
import com.szniloycoder.foodapp.Adapters.SizeAdapter;
import com.szniloycoder.foodapp.Models.FavModel;
import com.szniloycoder.foodapp.Models.FoodsModel;
import com.szniloycoder.foodapp.Models.RatingsModel;
import com.szniloycoder.foodapp.databinding.ActivityDetailBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    // Firebase and Database
     FirebaseAuth auth;
     FirebaseDatabase database;
     FirebaseStorage storage;

    // UI Components
     ActivityDetailBinding binding;
     SizeAdapter sizeAdapter;

    // Data Variables
     String catId;
     String foodCategory;
     String foodDeliveryTime;
     String foodDescription;
     String foodIngredients;
     String foodName;
     String foodPrice;
     ArrayList<String> imageUrls;
     String itemId;
     ArrayList<FoodsModel> list;
     String ratings;
     String selectedSize = "";
     ArrayList<String> sizeOptions = new ArrayList<>();
     ArrayList<String> sizes;
     double totalPrice = 0.0d;
     int totalQuantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Initialize Firebase and UI components
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        list = new ArrayList<>();
        imageUrls = new ArrayList<>();

        // Get data from Intent
        retrieveIntentData();

        // Set UI Components
        setupUI();

        // Setup ViewPager and RecyclerView
        setupViewPager();
        setupSizeOptionsRecyclerView();

        // Setup Event Listeners
        setupEventListeners();

        // Fetch and Display Average Rating
        fetchAndDisplayAverageRating();
    }


    private void retrieveIntentData() {
        Intent intent = getIntent();
        foodName = intent.getStringExtra("foodName");
        ratings = intent.getStringExtra("foodRatings");
        sizes = intent.getStringArrayListExtra("foodSize");
        foodDeliveryTime = intent.getStringExtra("foodDeliveryTime");
        foodDescription = intent.getStringExtra("foodDescription");
        foodPrice = intent.getStringExtra("foodPrice");
        imageUrls = intent.getStringArrayListExtra("foodImage");
        foodIngredients = intent.getStringExtra("foodIngredient");
        itemId = intent.getStringExtra("ItemId");
        catId = intent.getStringExtra("catId");
        foodCategory = intent.getStringExtra("foodCatName");
    }

    private void setupUI() {
        binding.foodIngredients.setText(Html.fromHtml(formatIngredientsAsHtml(foodIngredients), Html.FROM_HTML_MODE_COMPACT));
        binding.foodTitleTxt.setText(foodName);
        binding.descriptionTxt.setText(foodDescription);
        binding.foodPriceTxt.setText("$" + foodPrice);
        binding.ratingTxt.setText(ratings);
        binding.foodTimeTxt.setText(foodDeliveryTime + " min");
        binding.catNameTxt.setText(foodCategory);
    }

    private void setupEventListeners() {
        binding.btnBack.setOnClickListener(v -> finish());
        binding.btnFvtFood.setOnClickListener(v -> {
            favItmAdd();
            binding.btnFvtFood.setImageResource(R.drawable.favourite);
        });
        binding.ratingTxt.setOnClickListener(v -> {
            Intent intent = new Intent(this, RatingsActivity.class);
            intent.putExtra("itemId", itemId);
            startActivity(intent);
        });
        binding.addCartBtn.setOnClickListener(v -> setCartData());

        binding.plusBtn.setOnClickListener(v -> updateQuantity(true));
        binding.minusBtn.setOnClickListener(v -> updateQuantity(false));
    }

    private void setupViewPager() {
        binding.detailImgView.setAdapter(new DetailImgViewAdapter(this, imageUrls));
        binding.dotIndicator.setViewPager(binding.detailImgView);
    }

    private void setupSizeOptionsRecyclerView() {
        sizeOptions.addAll(sizes);
        sizeAdapter = new SizeAdapter(this, sizeOptions, size -> {
            selectedSize = size;
            sizeAdapter.setSelectedSize(size);
        });
        binding.recySizes.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        binding.recySizes.setAdapter(sizeAdapter);
    }

    @SuppressLint("StringFormatMatches")
    private void totalPriceAndQuantitySet() {
        double pricePerItem = Double.parseDouble(foodPrice.replaceAll("[^\\d.]", ""));
        totalPrice = pricePerItem;
        binding.totalFoodPriceTxt.setText(getString(R.string.total_price_format, totalPrice));
    }

    @SuppressLint("StringFormatMatches")
    private void updateQuantity(boolean increase) {
        double pricePerItem = Double.parseDouble(foodPrice.replaceAll("[^\\d.]", ""));
        if (increase) {
            totalQuantity++;
        } else if (totalQuantity > 1) {
            totalQuantity--;
        }
        binding.numTxt.setText(String.valueOf(totalQuantity));
        totalPrice = totalQuantity * pricePerItem;
        binding.totalFoodPriceTxt.setText(getString(R.string.total_price_format, totalPrice));
    }

    private String formatIngredientsAsHtml(String ingredients) {
        if (ingredients == null || ingredients.isEmpty()) {
            return "";
        }
        String[] items = ingredients.split(",");
        StringBuilder htmlString = new StringBuilder("<ol>");
        for (String item : items) {
            htmlString.append("<li>").append(item.trim()).append("</li>");
        }
        htmlString.append("</ol>");
        return htmlString.toString();
    }

    private void favItmAdd() {
        FavModel fModel = new FavModel();
        String foodName2 = binding.foodTitleTxt.getText().toString();
        DatabaseReference userFavFoodRef = database.getReference().child("favouriteFood")
                .child(auth.getCurrentUser().getUid());
        String key = userFavFoodRef.push().getKey();
        fModel.setKey(key);

        HashMap<String, Object> favFoodMap = new HashMap<>();
        favFoodMap.put("foodName", foodName2);
        favFoodMap.put("imageUrls", imageUrls);
        favFoodMap.put("foodPrice", foodPrice);
        favFoodMap.put("key", key);
        favFoodMap.put("foodDescription", foodDescription);
        favFoodMap.put("foodIngredients", foodIngredients);
        favFoodMap.put("ratings", ratings);
        favFoodMap.put("sizes", sizes);
        favFoodMap.put("catId", catId);
        favFoodMap.put("itemId", itemId);
        favFoodMap.put("foodDeliveryTime", foodDeliveryTime);
        favFoodMap.put("foodCategory", foodCategory);

        userFavFoodRef.push().setValue(favFoodMap);
        Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show();
    }

    private void fetchAndDisplayAverageRating() {
        database.getReference("reviews").child(itemId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                float totalRating = 0.0f;
                int ratingCount = 0;
                for (DataSnapshot ratingSnapshot : snapshot.getChildren()) {
                    RatingsModel rating = ratingSnapshot.getValue(RatingsModel.class);
                    if (rating != null) {
                        totalRating += rating.getRating();
                        ratingCount++;
                    }
                }
                binding.ratingTxt.setText(ratingCount > 0 ? String.valueOf(totalRating / ratingCount) : "0.0");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(DetailActivity.this, "Failed to load ratings", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCartData() {
        HashMap<String, Object> cartItemMap = new HashMap<>();
        cartItemMap.put("foodName", foodName);
        cartItemMap.put("foodPrice", foodPrice);
        cartItemMap.put("foodDescription", foodDescription);
        cartItemMap.put("foodIngredients", foodIngredients);
        cartItemMap.put("foodCategory", foodCategory);
        cartItemMap.put("catId", catId);
        cartItemMap.put("foodImage", imageUrls);
        cartItemMap.put("foodQuantity", totalQuantity);
        cartItemMap.put("ratings", ratings);
        cartItemMap.put("size", selectedSize);
        cartItemMap.put("deliveryTime", foodDeliveryTime);
        cartItemMap.put("totalPrice", totalPrice);
        cartItemMap.put("itemId", itemId);

        String key = database.getReference().child("CartItems")
                .child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).push().getKey();
        cartItemMap.put("key", key);

        if (key != null) {
            database.getReference().child("CartItems").child(auth.getCurrentUser().getUid()).child(key)
                    .setValue(cartItemMap).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(DetailActivity.this, "Food added to cart", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DetailActivity.this, "Failed to add item to cart", Toast.LENGTH_SHORT).show();
                            Log.e("addItemCart", "Error adding item to cart: " + task.getException().getMessage());
                        }
                    });
        } else {
            Toast.makeText(this, "Failed to generate cart item key", Toast.LENGTH_SHORT).show();
        }
    }

}