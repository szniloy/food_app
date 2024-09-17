package com.szniloycoder.foodapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.szniloycoder.foodapp.FullscreenImageActivity;
import com.szniloycoder.foodapp.R;

import java.util.ArrayList;

public class DetailImgViewAdapter extends PagerAdapter {


     Context context;
     ArrayList<String> imgUrls;

    public DetailImgViewAdapter(Context context, ArrayList<String> imgUrls) {
        this.context = context;
        this.imgUrls = imgUrls;
    }

    @Override
    public int getCount() {
        return imgUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_pager, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        Glide.with(context)
                .load(imgUrls.get(position))
                .into(imageView);

        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullscreenImageActivity.class);
            intent.putExtra("imageUrls", imgUrls);
            intent.putExtra("position", position);
            context.startActivity(intent);
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
