package com.latitude22.homemdopao.Bean;

/**
 * Created by Anuved on 24-08-2016.
 */
public class SubscriptionBean {
    String SubscriptionID;


    public String getSubscriptionName() {
        return SubscriptionName;
    }

    public void setSubscriptionName(String subscriptionName) {
        SubscriptionName = subscriptionName;
    }

    public String getSubscriptionID() {
        return SubscriptionID;
    }

    public void setSubscriptionID(String subscriptionID) {
        SubscriptionID = subscriptionID;
    }

    String SubscriptionName,noofdays;

    public String getNoofdays() {
        return noofdays;
    }

    public void setNoofdays(String noofdays) {
        this.noofdays = noofdays;
    }
}

