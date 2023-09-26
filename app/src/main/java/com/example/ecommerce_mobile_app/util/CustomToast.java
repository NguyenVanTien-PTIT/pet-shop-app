package com.example.ecommerce_mobile_app.util;

import android.content.Context;

import com.example.ecommerce_mobile_app.R;

import io.github.muddz.styleabletoast.StyleableToast;

public class CustomToast {
    public static void showSuccessMessage(Context context, String message){
        StyleableToast.makeText(context,message,R.style.toastSuccess).show();
    }
    public static void showFailMessage(Context context, String message){
        StyleableToast.makeText(context,message,R.style.toastFail).show();
    }

    public static void warning(Context context, String message){
        StyleableToast.makeText(context,message,R.style.toastWarning).show();
    }

    public static void showSystemError(Context context){
        StyleableToast.makeText(context, Constant.ERROR_MSG_FAIL, R.style.toastFail).show();
    }
}
