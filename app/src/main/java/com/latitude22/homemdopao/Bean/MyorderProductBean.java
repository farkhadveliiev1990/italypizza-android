package com.latitude22.homemdopao.Bean;

/**
 * Created by vaio1 on 16-11-2017.
 */

public class MyorderProductBean {

    String subscriptionName;
    String ProductName;
    int OrderNumDelivery;

    public String getSubscriptionName() {
        return subscriptionName;
    }

    public void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getOrderNumDelivery() {
        return OrderNumDelivery;
    }

    public void setOrderNumDelivery(int orderNumDelivery) {
        OrderNumDelivery = orderNumDelivery;
    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    String productQty;
}
