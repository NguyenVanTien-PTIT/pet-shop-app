package com.example.ecommerce_mobile_app.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.ecommerce_mobile_app.BR;

import java.io.Serializable;

public class InfoCart extends BaseObservable implements Serializable {
    private int totalItem;
    private Float totalPrice;
    @Bindable
    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
        notifyPropertyChanged(BR.totalItem);
    }
    @Bindable
    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
        notifyPropertyChanged(BR.totalPrice);
    }
}
