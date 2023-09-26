package com.example.ecommerce_mobile_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce_mobile_app.R;
import com.example.ecommerce_mobile_app.databinding.ListItemWishlistBinding;
import com.example.ecommerce_mobile_app.model.Product;
import com.example.ecommerce_mobile_app.model.WishlistItem;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder>{
    private List<WishlistItem> wishlistItems;
    private IOnClickWishItem iOnClickWishItem;

    public interface IOnClickWishItem {
        public void addToCart(WishlistItem wishlistItem);
        public void removeWishItem(WishlistItem wishlistItem);
    }

    public void setWishlistItems(List<WishlistItem> wishlistItems) {
        this.wishlistItems = wishlistItems;
        notifyDataSetChanged();
    }

    public void setiOnClickWishItem(IOnClickWishItem iOnClickWishItem) {
        this.iOnClickWishItem = iOnClickWishItem;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemWishlistBinding listItemWishlistBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_wishlist,parent,false);
        return new WishListViewHolder(listItemWishlistBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListViewHolder holder, int position) {
        WishlistItem wishlistItem = wishlistItems.get(position);
        holder.listItemWishlistBinding.setProduct(wishlistItem.getProduct());
        holder.listItemWishlistBinding.btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnClickWishItem.addToCart(wishlistItem);
            }
        });
        holder.listItemWishlistBinding.btnDeleteItemWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnClickWishItem.removeWishItem(wishlistItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wishlistItems != null ? wishlistItems.size() : 0;
    }

    public class WishListViewHolder extends RecyclerView.ViewHolder{
        private ListItemWishlistBinding listItemWishlistBinding;

        public WishListViewHolder(@NonNull ListItemWishlistBinding listItemWishlistBinding) {
            super(listItemWishlistBinding.getRoot());
            this.listItemWishlistBinding = listItemWishlistBinding;
        }
    }
}
