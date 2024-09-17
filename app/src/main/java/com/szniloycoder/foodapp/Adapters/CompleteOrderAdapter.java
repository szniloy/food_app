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
import com.szniloycoder.foodapp.Write_ReviewActivity;
import com.szniloycoder.foodapp.databinding.CompleteItemBinding;

import java.util.ArrayList;

public class CompleteOrderAdapter extends RecyclerView.Adapter<CompleteOrderAdapter.viewHolder> {

      Context context;
      ArrayList<OrderDetails> list;

    public CompleteOrderAdapter(Context context, ArrayList<OrderDetails> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.complete_item, parent, false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        OrderDetails model = list.get(position);

        holder.binding.titleItem.setText(model.getFoodNames().get(0));
        holder.binding.catName.setText(model.getFoodCategory().get(0));
        holder.binding.sizeShowTxt.setText(model.getFoodSize().get(0));
        holder.binding.prizeItem.setText("$" + model.getTotalAmount());

        Log.d("Adapter", "Food Name: " + model.getFoodNames());
        Log.d("Adapter", "Food Category: " + model.getFoodCategory());

        Glide.with(context)
                .load(model.getFoodImages().get(0))
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.binding.foodImageView);

        holder.binding.btnWriteReview.setOnClickListener(v -> {
            Intent intent = new Intent(context, Write_ReviewActivity.class);
            intent.putExtra("ItemId", model.getFoodItemId());
            context.startActivity(intent);
        });

        holder.itemView.setOnClickListener(v -> {
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
        CompleteItemBinding binding;

        public viewHolder(View itemView) {
            super(itemView);
            this.binding = CompleteItemBinding.bind(itemView);
        }
    }
}
