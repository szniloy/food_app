package com.szniloycoder.foodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.szniloycoder.foodapp.R;

import java.util.ArrayList;

public class FullscreenImageAdapter extends PagerAdapter {

     Context context;
     ArrayList<String> imageUrls;

    public FullscreenImageAdapter(Context context, ArrayList<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.item_image_pager, container, false);
        Glide.with(context).load(imageUrls.get(position)).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
