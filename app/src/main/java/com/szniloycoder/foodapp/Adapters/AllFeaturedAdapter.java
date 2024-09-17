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
import com.szniloycoder.foodapp.Models.FeaturedModel;
import com.szniloycoder.foodapp.R;
import com.szniloycoder.foodapp.databinding.AllFeaturedImgViewBinding;

import java.util.ArrayList;

public class AllFeaturedAdapter extends RecyclerView.Adapter<AllFeaturedAdapter.viewHolder> {

      Context context;
      ArrayList<FeaturedModel> list;

    public AllFeaturedAdapter(Context context, ArrayList<FeaturedModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_featured_img_view, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        FeaturedModel model = list.get(position);

        // Load the image into the ImageView using Glide with transformations
        Glide.with(context)
                .load(model.getImageUrl())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.binding.img);

    }

    @Override
    public int getItemCount() {
        return  list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        AllFeaturedImgViewBinding binding;

        public viewHolder(View itemView) {
            super(itemView);
            this.binding = AllFeaturedImgViewBinding.bind(itemView);
        }
    }
}
