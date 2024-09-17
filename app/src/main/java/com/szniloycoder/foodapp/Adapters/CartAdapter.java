package com.szniloycoder.foodapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.szniloycoder.foodapp.Models.CartModel;
import com.szniloycoder.foodapp.R;
import com.szniloycoder.foodapp.databinding.ViewholderCartBinding;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.viewHolder> {

    double TotalPrice;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    ArrayList<CartModel> cartList;
    String catId;
    Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String foodCategory;
    String foodImages;
    String foodName;
    String foodPrice;
    String deliveryTime;
    int foodQuantities;
    int totalPrice;

    public CartAdapter(ArrayList<CartModel> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the active_item layout
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_cart, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        CartModel models = cartList.get(position);

        // Setting text and data for views
        holder.binding.titleTxtCart.setText(cartList.get(position).getFoodName());
        holder.binding.totalTxtCart.setText("$" + cartList.get(position).getTotalPrice());
        holder.binding.numItem.setText(String.valueOf(cartList.get(position).getFoodQuantity()));
        holder.binding.catName.setText(cartList.get(position).getFoodCategory());
        holder.binding.sizeViewTxt.setText(cartList.get(position).getSize());

        // Loading food image using Glide
        Glide.with(holder.itemView.getContext())
                .load(cartList.get(position).getFoodImage().get(0))
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.binding.imgCart);

        // Assigning values for local variables
        foodName = holder.binding.titleTxtCart.getText().toString().trim();
        foodCategory = holder.binding.catName.getText().toString().trim();
        foodQuantities = Integer.parseInt(holder.binding.numItem.getText().toString().trim());
        foodPrice = cartList.get(position).getFoodPrice();
        deliveryTime = cartList.get(position).getDeliveryTime();
        foodImages = String.valueOf(cartList.get(position).getFoodImage());
        totalPrice += (int) cartList.get(position).getTotalPrice();


        // Broadcasting intent with cart details
        Intent intent = new Intent("CartTotalAmount");
        intent.putExtra("TotalPrice", totalPrice);
        intent.putExtra("foodName", foodName);
        intent.putExtra("foodImage", foodImages);
        intent.putExtra("foodQuantity", foodQuantities);
        intent.putExtra("foodPrice", foodPrice);
        intent.putExtra("foodCategory", foodCategory);
        intent.putExtra("catId", models.getCatId());
        intent.putExtra("size", models.getSize());
        intent.putExtra("deliveryTime", deliveryTime);
        intent.putExtra("itemId", models.getItemId());

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        // Resetting total price for calculation
        TotalPrice = 0.0;
        for (CartModel model : cartList) {
            TotalPrice += model.getFoodQuantity() * Double.parseDouble(model.getFoodPrice());
        }

        // Setting click listeners for buttons
        holder.binding.plusCartBtn.setOnClickListener(view -> increaseQuantity(position, holder));
        holder.binding.minusCartBtn.setOnClickListener(view -> decreaseQuantity(position, holder));
        holder.binding.btnDelete.setOnClickListener(view -> removeItem(holder));

    }

    @SuppressLint("SetTextI18n")
    private void increaseQuantity(int position, viewHolder holder) {
        int currentQuantity = cartList.get(position).getFoodQuantity() + 1;
        cartList.get(position).setFoodQuantity(currentQuantity);
        holder.binding.numItem.setText(String.valueOf(currentQuantity));

        double newTotalPrice = currentQuantity * Double.parseDouble(cartList.get(position).getFoodPrice());
        cartList.get(position).setTotalPrice(newTotalPrice);
        holder.binding.totalTxtCart.setText("$" + newTotalPrice);

        updateCartInFirebase(cartList.get(position));
    }

    @SuppressLint("SetTextI18n")
    private void decreaseQuantity(int position, viewHolder holder) {
        int currentQuantity = cartList.get(position).getFoodQuantity();
        if (currentQuantity > 1) {
            int newQuantity = currentQuantity - 1;
            cartList.get(position).setFoodQuantity(newQuantity);
            holder.binding.numItem.setText(String.valueOf(newQuantity));

            double newTotalPrice = newQuantity * Double.parseDouble(cartList.get(position).getFoodPrice());
            cartList.get(position).setTotalPrice(newTotalPrice);
            holder.binding.totalTxtCart.setText("$" + newTotalPrice);

            updateCartInFirebase(cartList.get(position));
        } else {
            removeCartItemFromFirebase(cartList.get(position));
        }
    }

    private void removeItem(viewHolder holder) {
        int clickedPosition = holder.getAdapterPosition();
        if (clickedPosition != -1) {
            removeCartItemFromFirebase(cartList.get(clickedPosition));
        }
    }

    private void removeCartItemFromFirebase(CartModel cartModel) {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String cartItemID = cartModel.getKey();
            if (cartItemID != null) {
                database.getReference().child("CartItems")
                        .child(currentUser.getUid())
                        .child(cartItemID)
                        .removeValue()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Cart item removed successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error removing cart item: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("removeCartItemFromFirebase", "Error: " + task.getException().getMessage());
                            }
                        });
            } else {
                Toast.makeText(context, "Cart item ID is null", Toast.LENGTH_SHORT).show();
                Log.e("removeCartItemFromFirebase", "Cart item ID is null");
            }
        } else {
            Toast.makeText(context, "User is not authenticated", Toast.LENGTH_SHORT).show();
            Log.e("removeCartItemFromFirebase", "User is not authenticated");
        }
    }

    private void updateCartInFirebase(CartModel cartModel) {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String cartItemID = cartModel.getKey();
            if (cartItemID != null) {
                database.getReference().child("CartItems")
                        .child(currentUser.getUid())
                        .child(cartItemID)
                        .setValue(cartModel)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Cart item updated successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error updating cart item: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("updateCartInFirebase", "Error: " + task.getException().getMessage());
                            }
                        });
            } else {
                Toast.makeText(context, "Cart item ID is null", Toast.LENGTH_SHORT).show();
                Log.e("updateCartInFirebase", "Cart item ID is null");
            }
        } else {
            Toast.makeText(context, "User is not authenticated", Toast.LENGTH_SHORT).show();
            Log.e("updateCartInFirebase", "User is not authenticated");
        }
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ViewholderCartBinding binding;

        public viewHolder(View itemView) {
            super(itemView);
            this.binding = ViewholderCartBinding.bind(itemView);
        }
    }
}
