package com.example.ecommerce_mobile_app.model.response;

import java.io.Serializable;

public class OrdersDTO implements Serializable {
    private int id;
    private Double totalprice;
    private String address;
    private String phoneNumber;
    private String nameUser;
    private String orderDate;
    private int status;
    private Integer idUser;
    private String orderIdMobile;
    private String statusDisplay;
    private String orderDateMobile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Double totalprice) {
        this.totalprice = totalprice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getOrderIdMobile() {
        return orderIdMobile;
    }

    public void setOrderIdMobile(String orderIdMobile) {
        this.orderIdMobile = orderIdMobile;
    }

    public String getStatusDisplay() {
        return statusDisplay;
    }

    public void setStatusDisplay(String statusDisplay) {
        this.statusDisplay = statusDisplay;
    }

    public String getOrderDateMobile() {
        return orderDateMobile;
    }

    public void setOrderDateMobile(String orderDateMobile) {
        this.orderDateMobile = orderDateMobile;
    }
}
