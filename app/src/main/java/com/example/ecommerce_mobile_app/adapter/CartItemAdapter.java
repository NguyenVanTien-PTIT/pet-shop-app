package com.example.ecommerce_mobile_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce_mobile_app.R;
import com.example.ecommerce_mobile_app.databinding.ListItemCartBinding;
import com.example.ecommerce_mobile_app.model.CartItem;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {
    private List<CartItem> mListCartItems;
    private IClickOnCartItem iClickOnCartItem;
    public interface IClickOnCartItem {
        public void clickMinus(CartItem cartItem);
        public void clickPlus(CartItem cartItem);
        public void clickDelete(List<CartItem> mListCartItems, CartItem cartItem);
        public void clickProduct(CartItem cartItem);
    }

    public void setiClickOnCartItem(IClickOnCartItem iClickOnCartItem) {
        this.iClickOnCartItem = iClickOnCartItem;
        notifyDataSetChanged();
    }

    public void setmListCartItems(List<CartItem> mListCartItems){
        this.mListCartItems = mListCartItems;
        notifyDataSetChanged();
    }
    public List<CartItem> getmListCartItems(){
        return this.mListCartItems;
    }
    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemCartBinding listItemCartBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_cart,parent,false);
        return new CartItemViewHolder(listItemCartBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        CartItem cartItem = mListCartItems.get(position);
        holder.listItemCartBinding.setCartItem(cartItem);
        holder.listItemCartBinding.btnMinusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickOnCartItem.clickMinus(cartItem);
                notifyDataSetChanged();
            }
        });

        holder.listItemCartBinding.btnPlusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickOnCartItem.clickPlus(cartItem);
                notifyDataSetChanged();
            }
        });

        holder.listItemCartBinding.btnDeleteItemCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickOnCartItem.clickDelete(mListCartItems,cartItem);
                notifyDataSetChanged();
            }
        });

        holder.listItemCartBinding.shapeableImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickOnCartItem.clickProduct(cartItem);
            }
        });
        holder.listItemCartBinding.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickOnCartItem.clickProduct(cartItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListCartItems != null ? mListCartItems.size() : 0;
    }
    public Float calTotal(){
        Float sumPrice = 0f;
        for (CartItem cartItem : mListCartItems){
            sumPrice = sumPrice + cartItem.getSubtotal();
        }
        return sumPrice;
    }
    public class CartItemViewHolder extends RecyclerView.ViewHolder{
        ListItemCartBinding listItemCartBinding;
        public CartItemViewHolder(@NonNull ListItemCartBinding listItemCartBinding) {
            super(listItemCartBinding.getRoot());
            this.listItemCartBinding = listItemCartBinding;
        }
    }
}
