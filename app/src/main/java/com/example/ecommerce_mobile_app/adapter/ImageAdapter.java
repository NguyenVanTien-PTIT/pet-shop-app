package com.example.ecommerce_mobile_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.ecommerce_mobile_app.R;
import com.example.ecommerce_mobile_app.api.CONSTANT;
import com.example.ecommerce_mobile_app.model.Image;
import com.example.ecommerce_mobile_app.view.MainActivity;

import java.util.List;

public class ImageAdapter extends PagerAdapter {
    Context mContext;
    private List<Image> imagesList;
    public ImageAdapter(Context mContext, List<Image> imagesList){
        this.mContext = mContext;
        this.imagesList = imagesList;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.images_slide_itemdetails,container,false);
        ImageView imgItem = view.findViewById(R.id.imgSlideItem);

        Image image = imagesList.get(position);
        if(image != null)
        {
            MainActivity.setImage(imgItem,image.getImagePath());
        }

        container.addView(view);

        return view;
    }
    @Override
    public int getCount() {
        if(imagesList != null)
        {
            return imagesList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        container.removeView((View) object);
    }
}