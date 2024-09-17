package com.szniloycoder.foodapp;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.szniloycoder.foodapp.Models.CartModel;
import com.szniloycoder.foodapp.Models.OrderDetails;
import com.szniloycoder.foodapp.databinding.ActivityPaymentBinding;

import java.util.ArrayList;
import java.util.Objects;

public class PaymentActivity extends AppCompatActivity {

    // Variables for status tracking
    Boolean Accept = false;
    Boolean Cancel = false;
    Boolean Cooking = false;
    Boolean Delivered = false;
    Boolean OutForDelivery = false;
    Boolean Processing = true;

    // User and order information
    String addressTitle;
    String addrsTxt;
    String deliveryTime;
    String foodCategory;
    String foodImages;
    String foodName;
    String foodPrice;
    int foodQuantities;
    String itemId;
    String phone;
    String selectedPaymentMethod;
    String size;
    String catId;
    String catName;
    String key;
    String userName;
    String userUid;

    // Amount and cart details
    double totalAmount;
    double totalPrice;
    ArrayList<CartModel> list;

    // Firebase services
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;

    // View binding
    ActivityPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize binding and layout
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set status bar color
        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_color));

        // Firebase services initialization
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        list = new ArrayList<>();

        // Set up UI listeners
        setupUIListeners();

        // Get intent data
        getIntentData();

        // Fetch cart items
        fetchCartItems();
    }

    // Sets up listeners for UI interactions
    private void setupUIListeners() {
        binding.btnBack.setOnClickListener(view -> finish());
        binding.btnPaymentMethodCash.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                selectedPaymentMethod = "Cash On Delivery";
                binding.btnPaymentMethodCard.setChecked(false);
            }
        });

        binding.btnPaymentMethodCard.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                selectedPaymentMethod = "Card";
                binding.btnPaymentMethodCash.setChecked(false);
            }
        });

        binding.btnConfirmPayment.setOnClickListener(view -> placeOrder());
    }

    // Retrieves data from the intent
    private void getIntentData() {
        totalAmount = getIntent().getDoubleExtra("totalAmount", 0.0d);
        totalPrice = getIntent().getDoubleExtra("TotalPrice", 0.0d);
        foodName = getIntent().getStringExtra("foodName");
        foodImages = getIntent().getStringExtra("foodImage");
        foodQuantities = getIntent().getIntExtra("foodQuantity", 0);
        foodPrice = getIntent().getStringExtra("foodPrice");
        foodCategory = getIntent().getStringExtra("foodCategory");
        catId = getIntent().getStringExtra("catId");
        catName = getIntent().getStringExtra("foodCategory");
        size = getIntent().getStringExtra("size");
        itemId = getIntent().getStringExtra("itemId");
        deliveryTime = getIntent().getStringExtra("deliveryTime");
        addressTitle = getIntent().getStringExtra("addressTitle");
        addrsTxt = getIntent().getStringExtra("address");
        userName = getIntent().getStringExtra("userName");
        phone = getIntent().getStringExtra("phone");
    }

    // Fetches cart items from the database
    private void fetchCartItems() {
        userUid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        database.getReference().child("CartItems")
                .child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    list.add(dataSnapshot.getValue(CartModel.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(PaymentActivity.this, "Failed to load cart items", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Places the order in the database
    private void placeOrder() {
        if (selectedPaymentMethod == null || selectedPaymentMethod.isEmpty()) {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            return;
        }

        userUid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        String time = String.valueOf(System.currentTimeMillis());
        String itemPushKey = database.getReference().child("ActiveOrders").child(userUid).push().getKey();

        // Create lists for order details
        ArrayList<String> foodNamesList = new ArrayList<>();
        ArrayList<String> catIdList = new ArrayList<>();
        ArrayList<String> foodCategoryList = new ArrayList<>();
        ArrayList<String> foodImagesList = new ArrayList<>();
        ArrayList<Integer> totalPricesList = new ArrayList<>();
        ArrayList<Integer> foodQuantityList = new ArrayList<>();
        ArrayList<String> foodSizeList = new ArrayList<>();
        ArrayList<String> deliveryTimeList = new ArrayList<>();
        ArrayList<String> foodItemIdList = new ArrayList<>();

        // Loop through cart items to prepare order details
        for (CartModel cartItem : list) {
            catIdList.add(cartItem.getCatId());
            foodCategoryList.add(cartItem.getFoodCategory());
            foodImagesList.add(String.valueOf(cartItem.getFoodImage().get(0)));
            foodNamesList.add(cartItem.getFoodName());
            totalPricesList.add((int) cartItem.getTotalPrice());
            foodSizeList.add(cartItem.getSize());
            foodQuantityList.add(cartItem.getFoodQuantity());
            deliveryTimeList.add(cartItem.getDeliveryTime());
            foodItemIdList.add(cartItem.getItemId());
        }

        // Create order details object
        OrderDetails orderDetails = new OrderDetails(
                false, addrsTxt, addressTitle, false, catIdList, false, false,
                deliveryTimeList, foodCategoryList, foodImagesList, foodItemIdList,
                foodNamesList, foodQuantityList, foodSizeList, itemPushKey, false,
                selectedPaymentMethod, phone, true, time, totalAmount, totalPricesList, userUid, userName);

        // Push order to database
        if (itemPushKey != null) {
            database.getReference().child("ActiveOrders").child(userUid).child(itemPushKey)
                    .setValue(orderDetails)
                    .addOnSuccessListener(unused -> {
                        removeItemsFromCart();
                        Toast.makeText(this, "Order placed", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Order failed", Toast.LENGTH_SHORT).show();
                    });
        } else {
            throw new AssertionError();
        }
    }

    // Removes items from the cart after placing order
    private void removeItemsFromCart() {
        database.getReference().child("CartItems").child(userUid).removeValue();
    }
}