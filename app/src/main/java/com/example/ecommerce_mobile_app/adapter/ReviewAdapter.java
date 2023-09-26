package com.example.ecommerce_mobile_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce_mobile_app.R;
import com.example.ecommerce_mobile_app.databinding.ListItemReviewBinding;
import com.example.ecommerce_mobile_app.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private List<Review> reviews;

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemReviewBinding listItemReviewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_review,parent,false);
        return new ReviewViewHolder(listItemReviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.listItemReviewBinding.setReview(review);
    }

    @Override
    public int getItemCount() {
        return reviews != null ? reviews.size() : 0;
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        private ListItemReviewBinding listItemReviewBinding;
        public ReviewViewHolder(@NonNull ListItemReviewBinding listItemReviewBinding) {
            super(listItemReviewBinding.getRoot());
            this.listItemReviewBinding = listItemReviewBinding;
        }
    }
}
