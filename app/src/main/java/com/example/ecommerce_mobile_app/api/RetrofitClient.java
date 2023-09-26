package com.example.ecommerce_mobile_app.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient extends BaseClient{
    private static APIService apiService;

    public static APIService getInstance() {
        if (apiService == null) {
            return createService(APIService.class,CONSTANT.BASE_URL);
        }

        return apiService;
    }
}
