package com.example.ecommerce_mobile_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.ecommerce_mobile_app.api.RetrofitClient;
import com.example.ecommerce_mobile_app.databinding.ActivitySignUpBinding;
import com.example.ecommerce_mobile_app.model.response.BaseResponse;
import com.example.ecommerce_mobile_app.model.Customer;
import com.example.ecommerce_mobile_app.model.request.SignUpRequest;
import com.example.ecommerce_mobile_app.util.CustomToast;

import org.apache.commons.lang3.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding activitySignUpBinding;
    private SignUpRequest signUpRequest = new SignUpRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignUpBinding = ActivitySignUpBinding.inflate(LayoutInflater.from(getApplicationContext()));
        setContentView(activitySignUpBinding.getRoot());

        activitySignUpBinding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpRequest.setUsername(activitySignUpBinding.etUserName.getText().toString());
                signUpRequest.setPassword(activitySignUpBinding.etPasswordSignUp.getText().toString());
                signUpRequest.setFullname(activitySignUpBinding.etFirstName.getText().toString());
                signUpRequest.setPhoneNumber(activitySignUpBinding.etPhoneNumber.getText().toString());
                signUpRequest.setEmail(activitySignUpBinding.etEmail.getText().toString());

                if (StringUtils.isBlank(signUpRequest.getUsername()) || StringUtils.isBlank(signUpRequest.getPassword())
                        || StringUtils.isBlank(signUpRequest.getFullname()) || StringUtils.isBlank(signUpRequest.getPhoneNumber())
                        || StringUtils.isBlank(signUpRequest.getEmail())) {
                    CustomToast.showSuccessMessage(getApplicationContext(), "Vui lòng nhập đầy đủ");
                    return;
                }

                if (!activitySignUpBinding.etConfirmPwSignUp.getText().toString().equals(signUpRequest.getPassword())) {
                    Toast.makeText(getApplicationContext(), "Password and confirm password doesn't match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                doSignUp();
            }
        });
        activitySignUpBinding.tvBelowWelcomeSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        activitySignUpBinding.btnBackSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpActivity.super.onBackPressed();
            }
        });
    }

    public void doSignUp() {
        RetrofitClient.getInstance().signUp(signUpRequest).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        CustomToast.showSuccessMessage(getApplicationContext(), "Success");
                        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        CustomToast.showSystemError(getApplicationContext());
                    }
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                CustomToast.showSystemError(getApplicationContext());
            }
        });
    }
}