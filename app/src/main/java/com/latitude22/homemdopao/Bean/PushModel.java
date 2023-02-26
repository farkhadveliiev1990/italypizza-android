package com.latitude22.homemdopao.Bean;

public class PushModel {

    public String orderID;
    public String deliveryName;
    public String customerID;

    public String comment;
    public String status;


    public PushModel(String orderID, String deliveryName, String customerID, String comment) {

       this.orderID = orderID;
       this.deliveryName = deliveryName;
       this.customerID = customerID;
       this.comment = comment;
       this.status = "1";

    }
}
