package com.latitude22.homemdopao.Bean;

import java.util.ArrayList;

/**
 * Created by vaio1 on 24-11-2017.
 */

public class HistoryBean {
    String subscriptionProduct;
    int orderId;
    String orderPymentStatus,orderCurrentStatus;
    String subscriptionStatus;

    public String getOrderPymentStatus() {
        return orderPymentStatus;
    }

    public void setOrderPymentStatus(String orderPymentStatus) {
        this.orderPymentStatus = orderPymentStatus;
    }

    public String getOrderCurrentStatus() {
        return orderCurrentStatus;
    }

    public void setOrderCurrentStatus(String orderCurrentStatus) {
        this.orderCurrentStatus = orderCurrentStatus;
    }

    int deliveryboyId;
    String orderCost;
    String endDate;

    public int getDeliveryboyId() {
        return deliveryboyId;
    }

    public void setDeliveryboyId(int deliveryboyId) {
        this.deliveryboyId = deliveryboyId;
    }

    public String getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(String orderCost) {
        this.orderCost = orderCost;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    int subscriptionCount;
    String subscriptionCost;
    ArrayList<HistoryProductBean> productlist;
    String SubscriptionName;
    String startDate;
    String noOfDelivery;

    public String getSubscriptionProduct() {
        return subscriptionProduct;
    }

    public void setSubscriptionProduct(String subscriptionProduct) {
        this.subscriptionProduct = subscriptionProduct;
    }

    public int getSubscriptionCount() {
        return subscriptionCount;
    }

    public void setSubscriptionCount(int subscriptionCount) {
        this.subscriptionCount = subscriptionCount;
    }

    public String getSubscriptionCost() {
        return subscriptionCost;
    }

    public void setSubscriptionCost(String subscriptionCost) {
        this.subscriptionCost = subscriptionCost;
    }

    public ArrayList<HistoryProductBean> getProductlist() {
        return productlist;
    }

    public void setProductlist(ArrayList<HistoryProductBean> productlist) {
        this.productlist = productlist;
    }

    public String getSubscriptionName() {
        return SubscriptionName;
    }

    public void setSubscriptionName(String subscriptionName) {
        SubscriptionName = subscriptionName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getNoOfDelivery() {
        return noOfDelivery;
    }

    public void setNoOfDelivery(String noOfDelivery) {
        this.noOfDelivery = noOfDelivery;
    }
}
