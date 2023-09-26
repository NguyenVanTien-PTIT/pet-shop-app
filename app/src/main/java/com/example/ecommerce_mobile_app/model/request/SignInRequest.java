package com.example.ecommerce_mobile_app.model.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class SignInRequest implements Serializable {
    private String username;

    private String password;

    public SignInRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
