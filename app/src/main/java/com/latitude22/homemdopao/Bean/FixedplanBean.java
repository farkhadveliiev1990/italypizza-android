package com.latitude22.homemdopao.Bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Anuved on 19-08-2016.
 */
public class FixedplanBean   {

    String image;
    int id,productId;
    Double productPrice;
    String Productname;
    String BreadName;
    String price;

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    String subscriptionId;
    String description;
    String SubscriptionType;

    public String getFixedpalnproduct() {
        return Fixedpalnproduct;
    }

    public void setFixedpalnproduct(String fixedpalnproduct) {
        Fixedpalnproduct = fixedpalnproduct;
    }

    String Fixedpalnproduct;
    public ArrayList<FixedPlanProductBean> getProductlist() {
        return productlist;
    }

    public void setProductlist(ArrayList<FixedPlanProductBean> productlist) {
        this.productlist = productlist;
    }

    ArrayList<FixedPlanProductBean> productlist;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductname() {
        return Productname;
    }

    public void setProductname(String productname) {
        Productname = productname;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBreadName() {
        return BreadName;
    }

    public void setBreadName(String breadText) {
        BreadName = breadText;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSubscriptionType() {
        return SubscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        SubscriptionType = subscriptionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
