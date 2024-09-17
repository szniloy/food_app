package com.szniloycoder.foodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.szniloycoder.foodapp.Models.OrderDetails;
import com.szniloycoder.foodapp.R;
import com.szniloycoder.foodapp.databinding.SubOrderViewBinding;

import java.util.ArrayList;
import java.util.List;

public class SubOrderAdapter extends RecyclerView.Adapter<SubOrderAdapter.viewHolder>{

      Context context;
      List<String> foodImageList;
      List<String> foodNamesList;
      List<Integer> foodPricesList;
      List<Integer> foodQuantityList;
      List<String> foodSizeList;
      ArrayList<OrderDetails> list;

    public SubOrderAdapter(
            Context context,
            List<String> foodImageList,
            List<String> foodNamesList,
            List<Integer> foodPricesList,
            List<Integer> foodQuantityList,
            List<String> foodSizeList,
            ArrayList<OrderDetails> list )
    {
        this.context = context;
        this.foodImageList = foodImageList;
        this.foodNamesList = foodNamesList;
        this.foodPricesList = foodPricesList;
        this.foodQuantityList = foodQuantityList;
        this.foodSizeList = foodSizeList;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sub_order_view, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return Math.min(
                Math.min(foodNamesList.size(), foodSizeList.size()),
                Math.min(foodPricesList.size(), Math.min(foodQuantityList.size(), foodImageList.size()))
        );
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        SubOrderViewBinding binding;

        public viewHolder(View itemView) {
            super(itemView);
            this.binding = SubOrderViewBinding.bind(itemView);
        }

        public void bind(int position) {
            if (position >= 0 && position < getItemCount()) {
                binding.recentFoodNames.setText(foodNamesList.get(position));
                binding.recenFoodPrices.setText("$" + foodPricesList.get(position));
                binding.recenFoodQnty.setText(String.valueOf(foodQuantityList.get(position)));
                binding.sizeSubTxt.setText(foodSizeList.get(position));
                Glide.with(context).load(foodImageList.get(position)).into(binding.recenFoodImgs);
            }
        }
    }

}
