package com.example.ecommerce_mobile_app.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.ecommerce_mobile_app.adapter.LineProductAdapter;
import com.example.ecommerce_mobile_app.adapter.OrderAdapter;
import com.example.ecommerce_mobile_app.api.CONSTANT;
import com.example.ecommerce_mobile_app.api.RetrofitClient;
import com.example.ecommerce_mobile_app.databinding.ActivityMyOrderDetailsBinding;
import com.example.ecommerce_mobile_app.model.CartItem;
import com.example.ecommerce_mobile_app.model.Order;
import com.example.ecommerce_mobile_app.model.OrderDetail;
import com.example.ecommerce_mobile_app.model.response.OrdersDTO;
import com.example.ecommerce_mobile_app.util.CustomToast;
import com.example.ecommerce_mobile_app.util.PrefManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {
    private ActivityMyOrderDetailsBinding activityMyOrderDetailsBinding;
    private OrdersDTO order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyOrderDetailsBinding = ActivityMyOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityMyOrderDetailsBinding.getRoot());
        order = new Gson().fromJson(getIntent().getExtras().getString("order"),OrdersDTO.class);
        activityMyOrderDetailsBinding.setOrder(order);
        setListProduct();
        activityMyOrderDetailsBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDetailActivity.super.onBackPressed();
            }
        });
    }
    public void setListProduct(){
        // get list product
        RetrofitClient.getInstance().getOrderDetail(order.getId(), new PrefManager(this).getToken()).enqueue(new Callback<List<CartItem>>() {
            @Override
            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    LineProductAdapter lineProductAdapter = new LineProductAdapter();
                    List<CartItem> cartItems = response.body();
                    cartItems.forEach(cartItem -> cartItem.setImage(CONSTANT.PATH_IMAGE_PREFIX + cartItem.getImage()));

                    lineProductAdapter.setmListCartItems(cartItems);
                    activityMyOrderDetailsBinding.rvMyOrderListProduct.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    activityMyOrderDetailsBinding.rvMyOrderListProduct.setAdapter(lineProductAdapter);
                } else {
                    CustomToast.showSystemError(getApplicationContext());
                }
            }

            @Override
            public void onFailure(Call<List<CartItem>> call, Throwable t) {

            }
        });
    }
}