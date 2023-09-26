package com.example.ecommerce_mobile_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce_mobile_app.R;
import com.example.ecommerce_mobile_app.databinding.ListItemMyOrderBinding;
import com.example.ecommerce_mobile_app.model.Order;
import com.example.ecommerce_mobile_app.model.response.OrdersDTO;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{


    private List<OrdersDTO> mListOrder;
    private IOnClickItem iOnClickItem;
    public interface IOnClickItem {
        public void clickItem(OrdersDTO order);
    }

    public void setiOnClickItem(IOnClickItem iOnClickItem) {
        this.iOnClickItem = iOnClickItem;
        notifyDataSetChanged();
    }

    public void setmListOrder(List<OrdersDTO> mListOrder) {
        this.mListOrder = mListOrder;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemMyOrderBinding listItemMyOrderBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_my_order,parent,false);
        return new OrderViewHolder(listItemMyOrderBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrdersDTO order = mListOrder.get(position);
        holder.listItemMyOrderBinding.setOrder(order);
        holder.listItemMyOrderBinding.Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnClickItem.clickItem(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListOrder != null ? mListOrder.size() : 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{
        private ListItemMyOrderBinding listItemMyOrderBinding;
        public OrderViewHolder(@NonNull ListItemMyOrderBinding listItemMyOrderBinding) {
            super(listItemMyOrderBinding.getRoot());
            this.listItemMyOrderBinding = listItemMyOrderBinding;
        }
    }
}
