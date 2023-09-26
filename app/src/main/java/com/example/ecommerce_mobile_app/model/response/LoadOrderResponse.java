package com.example.ecommerce_mobile_app.model.response;

import com.example.ecommerce_mobile_app.model.CartItem;

import java.io.Serializable;
import java.util.List;

public class LoadOrderResponse implements Serializable {
    private OrdersDTO ordersDTO;
    private List<CartItem> orderItems;

    public OrdersDTO getOrdersDTO() {
        return ordersDTO;
    }

    public void setOrdersDTO(OrdersDTO ordersDTO) {
        this.ordersDTO = ordersDTO;
    }

    public List<CartItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<CartItem> orderItems) {
        this.orderItems = orderItems;
    }
}
