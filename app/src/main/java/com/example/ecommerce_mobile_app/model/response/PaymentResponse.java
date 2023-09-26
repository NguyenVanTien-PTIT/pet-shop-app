package com.example.ecommerce_mobile_app.model.response;

import java.io.Serializable;

public class PaymentResponse implements Serializable {
    private OrdersDTO ordersDTO;
    private String msg;

    public OrdersDTO getOrdersDTO() {
        return ordersDTO;
    }

    public void setOrdersDTO(OrdersDTO ordersDTO) {
        this.ordersDTO = ordersDTO;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
