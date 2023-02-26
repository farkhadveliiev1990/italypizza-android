package com.latitude22.homemdopao.Bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vaio1 on 07-12-2017.
 */

public class CalenderDateOfDeliveryBean implements Serializable {

    String orderDeliveryId;
    String calenderProduct;
    String deliveryTime;
    String deliveryboyid;
    String dated;
    String delievrySubscName;
    ArrayList<CalenderProductBean> productlist;

    public String getDated() {
        return dated;
    }

    public void setDated(String dated) {
        this.dated = dated;
    }

    int itemno;



    public String getCalenderProduct() {
        return calenderProduct;
    }

    public void setCalenderProduct(String calenderProduct) {
        this.calenderProduct = calenderProduct;
    }
    public ArrayList<CalenderProductBean> getProductlist() {
        return productlist;
    }

    public void setProductlist(ArrayList<CalenderProductBean> productlist) {
        this.productlist = productlist;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getOrderDeliveryId() {
        return orderDeliveryId;
    }

    public void setOrderDeliveryId(String orderDeliveryId) {
        this.orderDeliveryId = orderDeliveryId;
    }

    public String getDeliveryboyid() {
        return deliveryboyid;
    }

    public void setDeliveryboyid(String deliveryboyid) {
        this.deliveryboyid = deliveryboyid;
    }

    public String getDelievrySubscName() {
        return delievrySubscName;
    }

    public void setDelievrySubscName(String delievrySubscName) {
        this.delievrySubscName = delievrySubscName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    String status;

    public int getItemno() {
        return itemno;
    }

    public void setItemno(int itemno) {
        this.itemno = itemno;
    }
}
