package com.example.ecommerce_mobile_app.model.response;

import com.example.ecommerce_mobile_app.model.Customer;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    private String httpStatus;
    private String msg;
    private Customer userDTO;
    private String token;

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Customer getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(Customer userDTO) {
        this.userDTO = userDTO;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
