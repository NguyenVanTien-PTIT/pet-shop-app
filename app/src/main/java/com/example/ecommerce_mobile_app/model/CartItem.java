package com.example.ecommerce_mobile_app.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.ecommerce_mobile_app.BR;

import java.io.Serializable;
import java.util.Observable;

public class CartItem extends BaseObservable implements Serializable {
    private int productId;
    private String shortName;
    private String mobileName;
    private String image;
    private int quantity;
    private float subtotal;
    private int orderProductId;
    private Product product;

    @Bindable
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    @Bindable
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
        notifyPropertyChanged(BR.productId);
    }

    @Bindable
    public int getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(int orderProductId) {
        this.orderProductId = orderProductId;
        notifyPropertyChanged(BR.orderProductId);
    }

    @Bindable
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
        notifyPropertyChanged(BR.shortName);
    }
    @Bindable
    public String getMobileName() {
        return mobileName;
    }

    public void setMobileName(String mobileName) {
        this.mobileName = mobileName;
        notifyPropertyChanged(BR.mobileName);
    }
    @Bindable
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
        notifyPropertyChanged(BR.image);
    }
    @Bindable
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        notifyPropertyChanged(BR.quantity);
    }
    @Bindable
    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
        notifyPropertyChanged(BR.subtotal);
    }
}
