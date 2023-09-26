package com.example.ecommerce_mobile_app.model.response;

import com.example.ecommerce_mobile_app.model.Product;

import java.io.Serializable;
import java.util.List;

public class LoadHomePageResponse implements Serializable {
    private List<Product> listNewProduct;
    private List<Product> listBestSeller;

    public List<Product> getListNewProduct() {
        return listNewProduct;
    }

    public void setListNewProduct(List<Product> listNewProduct) {
        this.listNewProduct = listNewProduct;
    }

    public List<Product> getListBestSeller() {
        return listBestSeller;
    }

    public void setListBestSeller(List<Product> listBestSeller) {
        this.listBestSeller = listBestSeller;
    }
}
