package com.latitude22.homemdopao.Bean;

import java.util.ArrayList;

/**
 * Created by vaio1 on 05-12-2017.
 */

public class CalenderSubscriptionBean  {

    String deliery_time;
    String delivery_Status;

    public CalenderSubscriptionBean(String deliery_time, String delivery_Status, String subscription_name) {
        this.deliery_time = deliery_time;
        this.delivery_Status = delivery_Status;
        this.subscription_name = subscription_name;
    }

    public String getDeliery_time() {
        return deliery_time;
    }

    public void setDeliery_time(String deliery_time) {
        this.deliery_time = deliery_time;
    }

    public String getDelivery_Status() {
        return delivery_Status;
    }

    public void setDelivery_Status(String delivery_Status) {
        this.delivery_Status = delivery_Status;
    }

    public String getSubscription_name() {
        return subscription_name;
    }

    public void setSubscription_name(String subscription_name) {
        this.subscription_name = subscription_name;
    }

    public ArrayList<SubscriptionProductBean> getProductlist() {
        return productlist;
    }

    public void setProductlist(ArrayList<SubscriptionProductBean> productlist) {
        this.productlist = productlist;
    }


    public String getSubcription_product() {
        return subcription_product;
    }

    public void setSubcription_product(String subcription_product) {
        this.subcription_product = subcription_product;
    }

    String subscription_name;
    ArrayList<SubscriptionProductBean> productlist;
    String subcription_product;
}
