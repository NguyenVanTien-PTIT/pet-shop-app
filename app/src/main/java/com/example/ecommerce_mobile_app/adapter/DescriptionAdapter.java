package com.example.ecommerce_mobile_app.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce_mobile_app.R;
import com.example.ecommerce_mobile_app.databinding.DescriptionItemBinding;
import com.example.ecommerce_mobile_app.model.Description;

import java.util.List;

public class DescriptionAdapter extends RecyclerView.Adapter<DescriptionAdapter.DescriptionViewHolder> {
    private List<Description> list;
    public void setList(List<Description> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DescriptionItemBinding descriptionItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.description_item,parent,false);
        return new DescriptionViewHolder(descriptionItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DescriptionViewHolder holder, int position) {
        Description description = list.get(position);
        holder.descriptionItemBinding.setDescription(description);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class DescriptionViewHolder extends RecyclerView.ViewHolder {
        DescriptionItemBinding descriptionItemBinding;

        public DescriptionViewHolder(@NonNull DescriptionItemBinding descriptionItemBinding) {
            super(descriptionItemBinding.getRoot());
            this.descriptionItemBinding = descriptionItemBinding;
        }
    }
}
