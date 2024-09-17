package com.szniloycoder.foodapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.szniloycoder.foodapp.CatergoryFoodsActivity;
import com.szniloycoder.foodapp.Models.CategoryModel;
import com.szniloycoder.foodapp.R;
import com.szniloycoder.foodapp.databinding.CategoriesItemViewBinding;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder> {

    Context context;
    ArrayList<CategoryModel> list;

    // Constructor to initialize context and data list
    public CategoryAdapter(Context context, ArrayList<CategoryModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the category item view
        View view = LayoutInflater.from(context).inflate(R.layout.categories_item_view, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        // Get the current category item
        CategoryModel model = list.get(position);

    //     Bind data to views
        holder.binding.catTitle.setText(model.getTitle());

      //   Load category image using Glide with transformations
        Glide.with(context)
                .load(model.getImageUrl())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.binding.CategoryImg);

        // Set click listener for each category item
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, CatergoryFoodsActivity.class);
            intent.putExtra("Title", model.getTitle());
            intent.putExtra("id", model.getKey());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CategoriesItemViewBinding binding;

        public viewHolder(View itemView) {
            super(itemView);
            binding = CategoriesItemViewBinding.bind(itemView);
        }
    }
}
