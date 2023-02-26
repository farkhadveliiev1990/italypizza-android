package com.latitude22.homemdopao.Bean;

import java.util.ArrayList;

/**
 * Created by Anuved on 30-08-2016.
 */
public class CalenderBean {

    String OrderdeliveryDate;
    String daysNo;
    ArrayList<CalenderDateOfDeliveryBean> dateOfDeliveries;
    String dateDeliveryData;


    public String getDaysNo() {
        return daysNo;
    }

    public void setDaysNo(String daysNo) {
        this.daysNo = daysNo;
    }



    public String getOrderdeliveryDate() {
        return OrderdeliveryDate;
    }

    public void setOrderdeliveryDate(String OrderdeliveryDate) {
        this.OrderdeliveryDate = OrderdeliveryDate;
    }



    public ArrayList<CalenderDateOfDeliveryBean> getDateOfDeliveries() {
        return dateOfDeliveries;
    }

    public void setDateOfDeliveries(ArrayList<CalenderDateOfDeliveryBean> dateOfDeliveries) {
        this.dateOfDeliveries = dateOfDeliveries;
    }

    public String getDateDeliveryData() {
        return dateDeliveryData;
    }

    public void setDateDeliveryData(String dateDeliveryData) {
        this.dateDeliveryData = dateDeliveryData;
    }
}
