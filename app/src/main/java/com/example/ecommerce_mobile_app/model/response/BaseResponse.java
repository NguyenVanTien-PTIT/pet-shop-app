package com.example.ecommerce_mobile_app.model.response;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {
    private String response_message;
    private String response_description;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getResponse_message() {
        return response_message;
    }

    public void setResponse_message(String response_message) {
        this.response_message = response_message;
    }

    public String getResponse_description() {
        return response_description;
    }

    public void setResponse_description(String response_description) {
        this.response_description = response_description;
    }


}
