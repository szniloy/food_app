package com.szniloycoder.foodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.szniloycoder.foodapp.Models.RatingsModel;
import com.szniloycoder.foodapp.R;
import com.szniloycoder.foodapp.databinding.RatingviewBinding;

import java.util.ArrayList;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.viewHolder> {

     Context context;
     ArrayList<RatingsModel> list;

    public RatingAdapter(Context context, ArrayList<RatingsModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ratingview, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        RatingsModel model = list.get(position);
        holder.binding.userReviewTxt.setText(model.getReview());
        holder.binding.userRatngs.setText(String.valueOf(model.getRating()));
        holder.binding.userName.setText(model.getReviewerName());

        if ("default".equals(model.getReviewerImg())) {
            holder.binding.userImg.setImageResource(R.drawable.profile);
        } else {
            Glide.with(context).load(model.getReviewerImg()).into(holder.binding.userImg);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        RatingviewBinding binding;

        public viewHolder(View itemView) {
            super(itemView);
            this.binding = RatingviewBinding.bind(itemView);
        }
    }
}
