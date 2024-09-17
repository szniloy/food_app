package com.szniloycoder.foodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.szniloycoder.foodapp.Models.FeaturedModel;
import com.szniloycoder.foodapp.R;
import com.szniloycoder.foodapp.databinding.FeaturedViewholderBinding;

import java.util.ArrayList;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.ViewHolder> {

     Context context;
     ArrayList<FeaturedModel> featuredItems;
     ViewPager2 viewPager2;


    // Constructor to initialize adapter with items and ViewPager2
    public FeaturedAdapter(ArrayList<FeaturedModel> featuredItems, ViewPager2 viewPager2) {
        this.featuredItems = featuredItems;
        this.viewPager2 = viewPager2;
    }

    // Runnable to load more items when nearing the end of the list
    private Runnable loadMoreItems = new Runnable() {
        @Override
        public void run() {
            featuredItems.addAll(featuredItems); // Adds duplicate items (optional: logic can be improved)
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.featured_viewholder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Set image for the current position
        holder.setImage(featuredItems.get(position));

        // If near the end of the list, load more items
        if (position == featuredItems.size() - 2) {
            viewPager2.post(loadMoreItems);
        }

    }

    @Override
    public int getItemCount() {
        return featuredItems.size();
    }

    // ViewHolder class to handle view binding
    public class ViewHolder extends RecyclerView.ViewHolder {
        private FeaturedViewholderBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = FeaturedViewholderBinding.bind(itemView);
        }

        // Method to set the image with Glide and transformations
        public void setImage(FeaturedModel featuredItem) {
            RequestOptions requestOptions = new RequestOptions()
                    .transform(new CenterCrop(), new RoundedCorners(60)); // Apply transformations

            Glide.with(context)
                    .load(featuredItem.getImageUrl())
                    .apply(requestOptions)
                    .into(binding.imageSlide); // Load image into ImageView
        }
    }

}
