package com.latitude22.homemdopao.Bean;

import java.util.ArrayList;

/**
 * Created by Anuved on 27-08-2016.
 */
public class MyOrderBean  {
    String orderId;
    String deleveryNo;
    String orderCost;
    String orderStartDate;
    String orderEndDate;

    public String getDeliveryBoyId() {
        return deliveryBoyId;
    }

    public void setDeliveryBoyId(String deliveryBoyId) {
        this.deliveryBoyId = deliveryBoyId;
    }

    String deliveryBoyId;

    public String getSubscriptionName() {
        return SubscriptionName;
    }

    public String setSubscriptionName(String subscriptionName) {
        SubscriptionName = subscriptionName;
        return subscriptionName;
    }

    String SubscriptionName;
    int productId;

    public String getOrderDetail() {
        return OrderDetail;
    }

    public void setOrderDetail(String orderDetail) {
        OrderDetail = orderDetail;
    }

    String OrderDetail;

    ArrayList<MyOrderListBean> myOrderList;

    String MyOrderListBean;
    public ArrayList<MyOrderListBean> getmyOrderList() {
        return myOrderList;
    }

    public void setmyOrderList(ArrayList<MyOrderListBean> myOrderList) {
        this.myOrderList = myOrderList;
    }

    public String getOrderId() {
        return orderId;
    }

    public String setOrderId(String orderId) {
        this.orderId = orderId;
        return orderId;
    }

    public String getDeleveryNo() {
        return deleveryNo;
    }

    public String setDeleveryNo(String deleveryNo) {
        this.deleveryNo = deleveryNo;
        return deleveryNo;
    }

    public String getOrderCost() {
        return orderCost;
    }

    public String setOrderCost(String orderCost) {
        this.orderCost = orderCost;
        return orderCost;
    }

    public String getOrderStartDate() {
        return orderStartDate;
    }

    public String setOrderStartDate(String orderStartDate) {
        this.orderStartDate = orderStartDate;
        return orderStartDate;
    }

    public String getOrderEndDate() {
        return orderEndDate;
    }

    public void setOrderEndDate(String orderEndDate) {
        this.orderEndDate = orderEndDate;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    String ProductName,price,qty;
}
