package com.latitude22.homemdopao.Bean;

import java.util.ArrayList;

/**
 * Created by vaio1 on 04-12-2017.
 */

public class CalenderChildBean {

    String delivery_date;

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public ArrayList<CalenderBean> getCalendersubscriptionList() {
        return calendersubscriptionList;
    }

    public void setCalendersubscriptionList(ArrayList<CalenderBean> calendersubscriptionList) {
        this.calendersubscriptionList = calendersubscriptionList;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    ArrayList<CalenderBean> calendersubscriptionList;
    String subscription;
}
