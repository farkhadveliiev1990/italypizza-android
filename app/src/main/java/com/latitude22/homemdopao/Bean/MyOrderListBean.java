package com.latitude22.homemdopao.Bean;

import java.util.ArrayList;

/**
 * Created by Anuved on 27-08-2016.
 */
public class MyOrderListBean {

    String subscriptionProduct;
    String deliveryBoyID;

    public String getDeliveryBoyID() {
        return deliveryBoyID;
    }

    public void setDeliveryBoyID(String deliveryBoyID) {
        this.deliveryBoyID = deliveryBoyID;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    String endDate;
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    String startDate;

    String orderId;
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getSubscriptionCount() {
        return subscriptionCount;
    }

    public void setSubscriptionCount(int subscriptionCount) {
        this.subscriptionCount = subscriptionCount;
    }

    int subscriptionCount;

    public String getSubscriptionProduct() {
        return subscriptionProduct;
    }

    public void setSubscriptionProduct(String subscriptionProduct) {
        this.subscriptionProduct = subscriptionProduct;
    }

    ArrayList<MysubscriptionProductBean> productlist;

    public ArrayList<MysubscriptionProductBean> getProductlist() {
        return productlist;
    }

    public void setProductlist(ArrayList<MysubscriptionProductBean> productlist) {
        this.productlist = productlist;
    }

    public String getSubscriptionName() {
        return SubscriptionName;
    }

    public void setSubscriptionName(String subscriptionName) {
        SubscriptionName = subscriptionName;
    }

    public String getNoOfPending() {
        return noOfPending;
    }

    public void setNoOfPending(String noOfPending) {
        this.noOfPending = noOfPending;
    }

    /* String Price,id,name,qty;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String price) {
            Price = price;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }*/
   String SubscriptionName;
    String noOfPending;
}
