package com.example.ecommerce_mobile_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce_mobile_app.databinding.ListItemPlaceOrderBinding;
import com.example.ecommerce_mobile_app.model.CartItem;

import java.util.List;

public class LineProductAdapter extends RecyclerView.Adapter<LineProductAdapter.LineProductViewHolder> {
    private List<CartItem> mListCartItems;
    public void setmListCartItems(List<CartItem> mListCartItems){
        this.mListCartItems = mListCartItems;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public LineProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemPlaceOrderBinding listItemPlaceOrderBinding = ListItemPlaceOrderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new LineProductViewHolder(listItemPlaceOrderBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull LineProductViewHolder holder, int position) {
        CartItem cartItem = mListCartItems.get(position);
        holder.listItemOrderBinding.setCartItem(cartItem);
    }

    @Override
    public int getItemCount() {
        return mListCartItems != null ? mListCartItems.size() : 0;
    }

    public class LineProductViewHolder extends RecyclerView.ViewHolder{
        ListItemPlaceOrderBinding listItemOrderBinding;
        public LineProductViewHolder(@NonNull ListItemPlaceOrderBinding listItemOrderBinding) {
            super(listItemOrderBinding.getRoot());
            this.listItemOrderBinding = listItemOrderBinding;
        }
    }
}
