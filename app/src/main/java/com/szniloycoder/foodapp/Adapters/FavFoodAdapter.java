package com.szniloycoder.foodapp.Adapters;

import android.annotation.SuppressLint;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.szniloycoder.foodapp.DetailActivity;
import com.szniloycoder.foodapp.Models.FavModel;
import com.szniloycoder.foodapp.R;
import com.szniloycoder.foodapp.databinding.CatFoodViewBinding;

import java.util.ArrayList;

public class FavFoodAdapter extends RecyclerView.Adapter<FavFoodAdapter.viewHolder> {

     Context context;
     ArrayList<FavModel> list;
     FirebaseDatabase database = FirebaseDatabase.getInstance();
     FirebaseStorage storage = FirebaseStorage.getInstance();

    public FavFoodAdapter(Context context, ArrayList<FavModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cat_food_view, parent, false);
        return new viewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        FavModel model = list.get(position);
        holder.binding.titleTxt.setText(model.getFoodName());
        holder.binding.priceTxt.setText("$" + model.getFoodPrice());
        holder.binding.catName.setText(model.getFoodCategory());
        holder.binding.ratingTxt.setText(model.getRatings());
        holder.binding.timeTxt.setText(model.getFoodDeliveryTme() + " min");

        Glide.with(context)
                .load(model.getImageUrls().get(0))
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.binding.img);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("foodName", model.getFoodName());
            intent.putExtra("foodRatings", model.getRatings());
            intent.putExtra("foodSize", model.getSizes());
            intent.putExtra("foodDeliveryTme", model.getFoodDeliveryTme());
            intent.putExtra("foodDescription", model.getFoodDescription());
            intent.putExtra("foodPrice", model.getFoodPrice());
            intent.putExtra("foodImage", model.getImageUrls());
            intent.putExtra("foodIngredient", model.getFoodIngredients());
            intent.putExtra("ItemId", model.getItemId());
            intent.putExtra("catId", model.getCatId());
            intent.putExtra("foodCatName", model.getFoodCategory());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CatFoodViewBinding binding;

        public viewHolder(View itemView) {
            super(itemView);
            this.binding = CatFoodViewBinding.bind(itemView);
        }
    }
}
