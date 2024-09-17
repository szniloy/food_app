package com.szniloycoder.foodapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.szniloycoder.foodapp.DetailActivity;
import com.szniloycoder.foodapp.Models.FoodsModel;
import com.szniloycoder.foodapp.Models.RatingsModel;
import com.szniloycoder.foodapp.R;
import com.szniloycoder.foodapp.databinding.CatFoodViewBinding;

import java.util.ArrayList;

public class CategoryFoodsAdapter extends RecyclerView.Adapter<CategoryFoodsAdapter.viewHolder> {

     Context context;
     ArrayList<FoodsModel> list;
     FirebaseDatabase database;
     FirebaseStorage storage;
     String averageRatings;

    // Constructor
    public CategoryFoodsAdapter(Context context, ArrayList<FoodsModel> list) {
        this.context = context;
        this.list = list;
        this.database = FirebaseDatabase.getInstance();
        this.storage = FirebaseStorage.getInstance();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cat_food_view, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        FoodsModel model = list.get(position);

        // Set food details to the view
        holder.binding.titleTxt.setText(model.getFoodName());
        holder.binding.priceTxt.setText("$" + model.getFoodPrice());
        holder.binding.catName.setText(model.getFoodCategory());
        holder.binding.timeTxt.setText(model.getFoodDeliveryTme() + " min");

        // Load food image with Glide and apply transformations
        Glide.with(context)
                .load(model.getImageUrls().get(0))
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.binding.img);

        // Set click listener to open DetailActivity with food details
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

        // Retrieve average ratings for the food item
        loadAverageRating(holder, model.getItemId());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    // Loads the average rating of the food item from the database
    private void loadAverageRating(final viewHolder holder, String itemId) {
        database.getReference("reviews").child(itemId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        float totalRating = 0.0f;
                        int ratingCount = 0;

                        for (DataSnapshot ratingSnapshot : snapshot.getChildren()) {
                            RatingsModel rating = ratingSnapshot.getValue(RatingsModel.class);
                            if (rating != null) {
                                totalRating += rating.getRating();
                                ratingCount++;
                            }
                        }

                        // Set rating text based on available data
                        if (ratingCount > 0) {
                            holder.binding.ratingTxt.setText(String.valueOf(totalRating / ratingCount));
                        } else {
                            holder.binding.ratingTxt.setText("0.0");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, "Failed to load ratings", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        CatFoodViewBinding binding;

        public viewHolder(View itemView) {
            super(itemView);
            this.binding = CatFoodViewBinding.bind(itemView);
        }
    }

}
