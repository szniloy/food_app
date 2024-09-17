package com.szniloycoder.foodapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.szniloycoder.foodapp.R;
import com.szniloycoder.foodapp.databinding.SizeViewBinding;

import java.util.ArrayList;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.viewHolder> {

      Context context;
      ArrayList<String> sizeOptions;
      OnItemClickListener listener;
      String selectedSize = "";

    public interface OnItemClickListener {
        void onItemClick(String size);
    }

    public SizeAdapter(Context context, ArrayList<String> sizeOptions, OnItemClickListener listener) {
        this.context = context;
        this.sizeOptions = sizeOptions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.size_view, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        String size = sizeOptions.get(position);
        holder.bind(size, listener);

        int backgroundColor = selectedSize.equals(size)
                ? context.getResources().getColor(R.color.mainColor)
                : context.getResources().getColor(R.color.gray);

        int textColor = selectedSize.equals(size)
                ? context.getResources().getColor(R.color.white)
                : context.getResources().getColor(R.color.black);

        holder.binding.sizeCardView.setCardBackgroundColor(backgroundColor);
        holder.binding.sizeTxt.setTextColor(textColor);

    }

    @Override
    public int getItemCount() {
        return sizeOptions.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelectedSize(String size) {
        this.selectedSize = size;
        notifyDataSetChanged();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        SizeViewBinding binding;

        public viewHolder(View itemView) {
            super(itemView);
            this.binding = SizeViewBinding.bind(itemView);
        }

        @SuppressLint("NotifyDataSetChanged")
        public void bind(String size, OnItemClickListener listener) {
            binding.sizeTxt.setText(size);
            binding.sizeCardView.setOnClickListener(v -> {
                listener.onItemClick(size);
                selectedSize = size;
                notifyDataSetChanged();
            });
        }
    }
}
