package com.example.ecommerce_mobile_app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce_mobile_app.R;
import com.example.ecommerce_mobile_app.databinding.ListCategoryBinding;
import com.example.ecommerce_mobile_app.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private final List<Category> mListCategories;
    private Context mContext;
    private int lastPosition = 0;
    int row_index = 0;
    private IOnItemClickListener onItemClickListener;
    public interface IOnItemClickListener {
        public void onItemClickListener(Category category);
    }

    public CategoryAdapter(List<Category> mListCategories, Context context, IOnItemClickListener onItemClickListener) {
        this.mListCategories = mListCategories;
        this.mContext = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListCategoryBinding listCategoryBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_category,parent,false);

        return new CategoryViewHolder(listCategoryBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Category category = mListCategories.get(position);
        holder.listCategoryBinding.setCategory(category);

        holder.listCategoryBinding.btnCategoryStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(category);
                row_index = position;
                notifyDataSetChanged();
            }
        });

        if (row_index == position) {
            holder.listCategoryBinding.btnCategoryStore.setBackground(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.custom_btn_category_store_click, null));
            holder.listCategoryBinding.btnCategoryStore.setTextColor(Color.WHITE);
        }
        else {
            holder.listCategoryBinding.btnCategoryStore.setBackground(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.custom_btn_category_store_unclick, null));
            holder.listCategoryBinding.btnCategoryStore.setTextColor(Color.BLACK);
        }
        setAnimation(holder.listCategoryBinding.getRoot(),position);
    }

    @Override
    public int getItemCount() {
        if (mListCategories != null)
            return mListCategories.size();
        return 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final ListCategoryBinding listCategoryBinding;
        public CategoryViewHolder(@NonNull ListCategoryBinding listCategoryBinding) {
            super(listCategoryBinding.getRoot());
            this.listCategoryBinding = listCategoryBinding;
        }
    }
    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            //TranslateAnimation anim = new TranslateAnimation(0,-1000,0,-1000);
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            //anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
            anim.setDuration(550);//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;

        }
    }
}
