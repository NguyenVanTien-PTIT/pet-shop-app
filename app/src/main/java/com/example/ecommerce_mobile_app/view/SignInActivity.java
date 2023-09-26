package com.example.ecommerce_mobile_app.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce_mobile_app.api.CONSTANT;
import com.example.ecommerce_mobile_app.api.RetrofitClient;
import com.example.ecommerce_mobile_app.databinding.ActivitySignInBinding;
import com.example.ecommerce_mobile_app.model.Customer;
import com.example.ecommerce_mobile_app.model.request.SignInRequest;
import com.example.ecommerce_mobile_app.model.response.LoginResponse;
import com.example.ecommerce_mobile_app.util.Constant;
import com.example.ecommerce_mobile_app.util.CustomToast;
import com.example.ecommerce_mobile_app.util.PrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding activitySignInBinding;
    private String username, password;
    private PrefManager prefManager = new PrefManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignInBinding = ActivitySignInBinding.inflate(LayoutInflater.from(getApplicationContext()));
        setContentView(activitySignInBinding.getRoot());
        if (!prefManager.isUserLogedOut()){
            startMainActivity();
        }

        activitySignInBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = activitySignInBinding.etEmailSignIn.getText().toString().trim();
                password = activitySignInBinding.etPasswordSignIn.getText().toString();
                doSignIn();
            }
        });

        activitySignInBinding.tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        activitySignInBinding.tvManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this,ManagerActivity.class));
            }
        });
    }

    public void doSignIn(){
        RetrofitClient.getInstance().signIn(new SignInRequest(username,password)).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null && Constant.STATUS_SUCCESS.equals(response.body().getHttpStatus())) {
                        Customer customer = response.body().getUserDTO();
                        customer.setPhotoImagePath(CONSTANT.PATH_IMAGE_PREFIX + customer.getImage());

                        prefManager.saveLoginUser(customer);
                        prefManager.saveToken(response.body().getToken());
                        startMainActivity();
                    } else {
                        CustomToast.showFailMessage(getApplicationContext(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                CustomToast.showSystemError(getApplicationContext());
            }
        });
    }
    public void startMainActivity(){
        Intent intent = new Intent(SignInActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}