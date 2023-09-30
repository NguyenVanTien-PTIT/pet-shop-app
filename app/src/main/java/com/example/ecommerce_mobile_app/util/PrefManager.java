package com.example.ecommerce_mobile_app.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ecommerce_mobile_app.model.Customer;
import com.google.gson.Gson;

public class PrefManager {
    Context context;
    Gson gson = new Gson();
    public PrefManager(Context context) {
        this.context = context;
    }

    public void saveLoginUser(Customer customer) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String customerJson = gson.toJson(customer);
        editor.putString("LogedCustomer",customerJson);
        editor.apply();
    }

    public void saveToken(String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("jwt", token);
        editor.apply();
    }

    public String getToken() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Token", Context.MODE_PRIVATE);
        return "Bearer " + sharedPreferences.getString("jwt", "");
    }

    public Customer getCustomer() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        String customerJson = sharedPreferences.getString("LogedCustomer", "");
        return gson.fromJson(customerJson,Customer.class);
    }
    public void removeCustomer(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("LogedCustomer");
        editor.apply();
    }

    public void removeToken(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("jwt");
        editor.apply();
    }

    public boolean isUserLogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        return sharedPreferences.getString("LogedCustomer", "").isEmpty();
    }
    public void changeCustomer(Customer customer){
        removeCustomer();
        saveLoginUser(customer);
    }

}
