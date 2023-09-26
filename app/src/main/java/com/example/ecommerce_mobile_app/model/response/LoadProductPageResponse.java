package com.example.ecommerce_mobile_app.model.response;

import com.example.ecommerce_mobile_app.model.Category;
import com.example.ecommerce_mobile_app.model.Product;

import java.io.Serializable;
import java.util.List;

public class LoadProductPageResponse implements Serializable {
    private int page;
    private int totalPage;
    private int totalProduct;
    private List<Product> productDTOList;
    private List<Category> categoryDTOList;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    public List<Category> getCategoryDTOList() {
        return categoryDTOList;
    }

    public void setCategoryDTOList(List<Category> categoryDTOList) {
        this.categoryDTOList = categoryDTOList;
    }

    public List<Product> getProductDTOList() {
        return productDTOList;
    }

    public void setProductDTOList(List<Product> productDTOList) {
        this.productDTOList = productDTOList;
    }
}
