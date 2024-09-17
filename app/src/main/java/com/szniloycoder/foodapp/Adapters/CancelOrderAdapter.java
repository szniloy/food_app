package com.szniloycoder.foodapp.Adapters;

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
import com.szniloycoder.foodapp.databinding.CancelItemBinding;

import java.util.ArrayList;

public class CancelOrderAdapter extends RecyclerView.Adapter<CancelOrderAdapter.viewHolder> {

      Context context;
      ArrayList<OrderDetails> list;

    public CancelOrderAdapter(Context context, ArrayList<OrderDetails> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cancel_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        OrderDetails model = list.get(position);

        // Set text values in the holder
        holder.binding.titleItem.setText(model.getFoodNames().get(0));
        holder.binding.catName.setText(model.getFoodCategory().get(0));
        holder.binding.sizeShowTxt.setText(model.getFoodSize().get(0));
        holder.binding.prizeItem.setText("$" + model.getTotalAmount());

        // Log details for debugging
        Log.d("CancelOrderAdapter", "Food Name: " + model.getFoodNames());
        Log.d("CancelOrderAdapter", "Food Category: " + model.getFoodCategory());

        // Load image with Glide and apply transformations
        Glide.with(context)
                .load(model.getFoodImages().get(0))
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.binding.foodImageView);

        // Set click listener for the item view
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, SubOrderDetailActivity.class);
            intent.putExtra("userName", model.getUserName());
            intent.putExtra("foodName", model.getFoodNames());
            intent.putExtra("TotalAmount", model.getTotalAmount());
            intent.putExtra("foodQuantities", model.getFoodQuantity());
            intent.putExtra("foodPrice", model.getTotalPrices());
            intent.putExtra("foodImage", model.getFoodImages());
            intent.putExtra("foodSize", model.getFoodSize());
            intent.putExtra("address", model.getAddress());
            intent.putExtra("phone", model.getPhone());
            intent.putExtra("foodCategory", model.getFoodCategory());
            intent.putExtra("orderId", model.getItemPushKey());
            intent.putExtra("userId", model.getUserId());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        CancelItemBinding binding;

        public viewHolder(View itemView) {
            super(itemView);
            this.binding = CancelItemBinding.bind(itemView);
        }
    }
}
