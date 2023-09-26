package com.example.ecommerce_mobile_app.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce_mobile_app.adapter.LineProductAdapter;
import com.example.ecommerce_mobile_app.api.RetrofitClient;
import com.example.ecommerce_mobile_app.databinding.ActivityPlaceOrderBinding;
import com.example.ecommerce_mobile_app.model.CartItem;
import com.example.ecommerce_mobile_app.model.Customer;
import com.example.ecommerce_mobile_app.model.InfoCart;
import com.example.ecommerce_mobile_app.model.response.OrdersDTO;
import com.example.ecommerce_mobile_app.model.response.PaymentResponse;
import com.example.ecommerce_mobile_app.util.CustomToast;
import com.example.ecommerce_mobile_app.util.OrderStatus;
import com.example.ecommerce_mobile_app.util.OrderSuccessDialog;
import com.example.ecommerce_mobile_app.util.PrefManager;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceOrderActivity extends AppCompatActivity {
    ActivityPlaceOrderBinding activityPlaceOrderBinding;
    PrefManager prefManager = new PrefManager(this);
    Customer customer;
    List<CartItem> cartItemList;
    LineProductAdapter lineProductAdapter = new LineProductAdapter();
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    private OrdersDTO orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPlaceOrderBinding = ActivityPlaceOrderBinding.inflate(getLayoutInflater());
        setContentView(activityPlaceOrderBinding.getRoot());

        customer = prefManager.getCustomer();
        orders = (OrdersDTO) getIntent().getSerializableExtra("order");

        activityPlaceOrderBinding.setOrder(orders);

        activityPlaceOrderBinding.setInfoCart((InfoCart) getIntent().getSerializableExtra("infoCart"));

        recyclerView = activityPlaceOrderBinding.rvProductOrder;
        cartItemList = (List<CartItem>) getIntent().getSerializableExtra("listCartItems");
        lineProductAdapter.setmListCartItems(cartItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(lineProductAdapter);


        activityPlaceOrderBinding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlaceOrderActivity.super.onBackPressed();
            }
        });

        activityPlaceOrderBinding.btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check order information
                String address = activityPlaceOrderBinding.tvAddressLine1.getText().toString();
                String phone = activityPlaceOrderBinding.tvPhoneOrder.getText().toString();
                String receiver = activityPlaceOrderBinding.tvReceiver.getText().toString();

                if (StringUtils.isBlank(address) || StringUtils.isBlank(phone) || StringUtils.isBlank(receiver)) {
                    CustomToast.warning(getApplicationContext(), "Please fill in complete order information!");
                    return;
                }

                progressDialog = new ProgressDialog(PlaceOrderActivity.this);
                progressDialog.setMessage("Wait a second...");

                orders.setAddress(address);
                orders.setPhoneNumber(phone);
                orders.setNameUser(receiver);
                orders.setStatus(OrderStatus.WAIT_CONFIRM.getValue());

                placeOrder();
            }
        });
    }

    public void placeOrder() {
        progressDialog.show();

        PrefManager prefManager = new PrefManager(getApplicationContext());

        RetrofitClient.getInstance().payment(prefManager.getCustomer().getId(), orders, prefManager.getToken()).enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    CustomToast.showSuccessMessage(getApplicationContext(), response.body().getMsg());
                    OrderSuccessDialog orderSuccessDialog = new OrderSuccessDialog();
                    orderSuccessDialog.show(getSupportFragmentManager(), "Order success");
                } else {
                    progressDialog.dismiss();
                    CustomToast.showFailMessage(getApplicationContext(), "Order is unsuccessful!");
                }
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {

            }
        });
    }
}