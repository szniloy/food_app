package com.szniloycoder.foodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.szniloycoder.foodapp.Models.CartModel;
import com.szniloycoder.foodapp.R;
import com.szniloycoder.foodapp.databinding.CheckoutItemViewBinding;

import java.util.ArrayList;

public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutAdapter.viewHolder> {
      Context context;
      ArrayList<CartModel> list;

    public CheckOutAdapter(Context context, ArrayList<CartModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.checkout_item_view, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        CartModel model = list.get(position);

        // Set data to UI elements
        holder.binding.titleItem.setText(model.getFoodName());
        holder.binding.prizeItem.setText(String.format("$%s", model.getTotalPrice()));
        holder.binding.catName.setText(model.getFoodCategory());
        holder.binding.sizeShowTxt.setText(model.getSize());

        // Safeguard to prevent potential null pointer exception
        if (model.getFoodImage() != null && !model.getFoodImage().isEmpty()) {
            // Load image using Glide with transformations
            Glide.with(holder.itemView.getContext())
                    .load(model.getFoodImage().get(0))  // Assuming first image is used
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(holder.binding.foodImageView);
        } else {
            // You can set a placeholder image if no image is available
            holder.binding.foodImageView.setImageResource(R.drawable.profile);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        CheckoutItemViewBinding binding;

        public viewHolder(View itemView) {
            super(itemView);
            this.binding = CheckoutItemViewBinding.bind(itemView);
        }
    }
}
