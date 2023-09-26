package com.example.ecommerce_mobile_app.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.ecommerce_mobile_app.BR;

import java.io.Serializable;
import java.util.Date;

public class WishlistItem  implements Serializable {
    private Product product;
    private Date addedDate;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }
}
