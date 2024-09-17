package com.szniloycoder.foodapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.szniloycoder.foodapp.Models.OrderDetails;
import com.szniloycoder.foodapp.R;
import com.szniloycoder.foodapp.SubOrderDetailActivity;
import com.szniloycoder.foodapp.TrackOrderActivity;
import com.szniloycoder.foodapp.databinding.ActiveItemBinding;

import java.util.ArrayList;

public class ActiveOrderAdapter extends RecyclerView.Adapter<ActiveOrderAdapter.ViewHolder>{

      Context context;
      ArrayList<OrderDetails> orderList;

    // Constructor
    public ActiveOrderAdapter(Context context, ArrayList<OrderDetails> orderList) {
        this.context = context;
        this.orderList = orderList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the active_item layout
        View view = LayoutInflater.from(context).inflate(R.layout.active_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Get the order details at the current position
        OrderDetails orderDetails = orderList.get(position);

        // Bind data to the views
        holder.binding.titleItem.setText(orderDetails.getFoodNames().get(0));
        holder.binding.catName.setText(orderDetails.getFoodCategory().get(0));
        holder.binding.sizeShowTxt.setText(orderDetails.getFoodSize().get(0));
        holder.binding.prizeItem.setText("$" + orderDetails.getTotalAmount());

        // Log the food name and category for debugging
        Log.d("Adapter", "Food Name: " + orderDetails.getFoodNames());
        Log.d("Adapter", "Food Category: " + orderDetails.getFoodCategory());

        // Load the image using Glide with transformations
        Glide.with(context)
                .load(orderDetails.getFoodImages().get(0))
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.binding.foodImageView);

        // Track Order button click
        holder.binding.btnTrackOrder.setOnClickListener(view -> {
            Intent intent = new Intent(context, TrackOrderActivity.class);
            intent.putExtra("userName", orderDetails.getUserName());
            intent.putExtra("foodName", orderDetails.getFoodNames());
            intent.putExtra("TotalAmount", orderDetails.getTotalAmount());
            intent.putExtra("foodQuantities", orderDetails.getFoodQuantity());
            intent.putExtra("foodPrice", orderDetails.getTotalPrices());
            intent.putExtra("foodImage", orderDetails.getFoodImages());
            intent.putExtra("foodSize", orderDetails.getFoodSize());
            intent.putExtra("address", orderDetails.getAddress());
            intent.putExtra("phone", orderDetails.getPhone());
            intent.putExtra("foodCategory", orderDetails.getFoodCategory());
            intent.putExtra("orderId", orderDetails.getItemPushKey());
            intent.putExtra("userId", orderDetails.getUserId());
            context.startActivity(intent);
        });

        // Item click listener
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, SubOrderDetailActivity.class);
            intent.putExtra("userName", orderDetails.getUserName());
            intent.putExtra("foodName", orderDetails.getFoodNames());
            intent.putExtra("TotalAmount", orderDetails.getTotalAmount());
            intent.putExtra("foodQuantities", orderDetails.getFoodQuantity());
            intent.putExtra("foodPrice", orderDetails.getTotalPrices());
            intent.putExtra("foodImage", orderDetails.getFoodImages());
            intent.putExtra("foodSize", orderDetails.getFoodSize());
            intent.putExtra("address", orderDetails.getAddress());
            intent.putExtra("phone", orderDetails.getPhone());
            intent.putExtra("foodCategory", orderDetails.getFoodCategory());
            intent.putExtra("orderId", orderDetails.getItemPushKey());
            intent.putExtra("userId", orderDetails.getUserId());

            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    // ViewHolder class for the adapter
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ActiveItemBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = ActiveItemBinding.bind(itemView);
        }
    }
}
