package com.example.ecommerce_mobile_app.view;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce_mobile_app.R;
import com.example.ecommerce_mobile_app.api.RetrofitClient;
import com.example.ecommerce_mobile_app.databinding.ActivityChangePasswordBinding;
import com.example.ecommerce_mobile_app.model.response.BaseResponse;
import com.example.ecommerce_mobile_app.model.Customer;
import com.example.ecommerce_mobile_app.model.request.UpdatePasswordRequest;
import com.example.ecommerce_mobile_app.util.CustomToast;
import com.example.ecommerce_mobile_app.util.PrefManager;
import com.example.ecommerce_mobile_app.util.SendOTP;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePasswordActivity extends AppCompatActivity {

    private ActivityChangePasswordBinding activityChangePasswordBinding;
    Customer customer;
    private String oldPassword, newPassword, confirmPassword;
    private final PrefManager prefManager = new PrefManager(this);

    private final UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();

    private CountDownTimer mCountDownTimer;
    private static final long START_TIME_IN_MILLIS = 30000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;


    private int seconds;
    private int codeOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChangePasswordBinding = ActivityChangePasswordBinding.inflate(LayoutInflater.from(getApplicationContext()));
        setContentView(activityChangePasswordBinding.getRoot());
        customer = prefManager.getCustomer();
        activityChangePasswordBinding.setCustomer(customer);
        activityChangePasswordBinding.btnSave.setOnClickListener(view -> {
            oldPassword = activityChangePasswordBinding.etPasswordChange.getText().toString().trim();
            newPassword = activityChangePasswordBinding.etNewPasswordChange.getText().toString();
            confirmPassword = activityChangePasswordBinding.etConfirmPasswordChange.getText().toString();
            updatePasswordRequest.setOldPassword(oldPassword);
            updatePasswordRequest.setNewPassword(newPassword);
            updatePasswordRequest.setConfirmPassword(confirmPassword);
            doChangePassword();
        });

        activityChangePasswordBinding.backChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdatePasswordActivity.super.onBackPressed();
            }
        });

        activityChangePasswordBinding.btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                codeOTP = random.nextInt(8999) + 1000;
                SendOTP.sendMailOtp(codeOTP, customer.getEmail());
                seconds = 30;
                activityChangePasswordBinding.btnSendOTP.setEnabled(false);
                activityChangePasswordBinding.btnSendOTP.setBackground(getDrawable(R.drawable.custom_btn_dialog_delete_item_cart_cancel));
                activityChangePasswordBinding.btnSendOTP.setTextColor(getApplication().getResources().getColor(R.color.black));
                setBtnSendWait();
            }
        });

        activityChangePasswordBinding.btnSubmitChangePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputCode = activityChangePasswordBinding.firstPinView.getText().toString();
                if (inputCode.equals(String.valueOf(codeOTP))) {
                    activityChangePasswordBinding.LayoutConfirmOTP.setVisibility(View.GONE);
                    activityChangePasswordBinding.LayoutEditProfileDetail.setVisibility(View.VISIBLE);
                    CustomToast.showSuccessMessage(getApplicationContext(),"Successful!");
                } else {
                    CustomToast.showFailMessage(getApplicationContext(),"OTP is invalid!");
                }
            }
        });
    }

    public void doChangePassword(){
        RetrofitClient.getInstance().updatePassword(prefManager.getCustomer().getId(), updatePasswordRequest).enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<String>> call, @NonNull Response<BaseResponse<String>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponse_message().equals("Success")) {
                        CustomToast.showSuccessMessage(getApplicationContext(), response.body().getResponse_description());
                        UpdatePasswordActivity.super.onBackPressed();
                    } else {
                        CustomToast.showFailMessage(getApplicationContext(), response.body().getResponse_description());
                    }
                } else {
                    CustomToast.showFailMessage(getApplicationContext(), "INTERNAL_SERVER_ERROR");
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<String>> call, @NonNull Throwable t) {

            }
        });
    }

    private void setBtnSendWait(){
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                seconds = seconds - 1;
                String textWait = "Resend " + seconds +"s";
                activityChangePasswordBinding.btnSendOTP.setText(textWait);
            }
            @Override
            public void onFinish() {
                codeOTP = 999999999;
                seconds = 30;

                activityChangePasswordBinding.btnSendOTP.setEnabled(true);
                activityChangePasswordBinding.btnSendOTP.setBackground(getDrawable(R.drawable.custom_btn_dialog_delete_item_cart_yes));
                activityChangePasswordBinding.btnSendOTP.setTextColor(getApplication().getResources().getColor(R.color.white));
                activityChangePasswordBinding.btnSendOTP.setText("Send Code");

                mTimeLeftInMillis = START_TIME_IN_MILLIS;
            }
        }.start();
    }
}
