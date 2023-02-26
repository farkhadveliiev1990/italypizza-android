package com.latitude22.homemdopao.Bean;

import java.io.Serializable;

/**
 * Created by Anuved on 26-08-2016.
 */
public class FixedPlanProductBean  {

    int fixedProductId;
    String fixedProductName;
    String fixedProductPrice;


    public int getFixedProductId() {
        return fixedProductId;
    }

    public void setFixedProductId(int fixedProductId) {
        this.fixedProductId = fixedProductId;
    }

    public String getFixedProductPrice() {
        return fixedProductPrice;
    }

    public void setFixedProductPrice(String fixedProductPrice) {
        this.fixedProductPrice = fixedProductPrice;
    }

    public String getFixedProductName() {
        return fixedProductName;
    }

    public void setFixedProductName(String fixedProductName) {
        this.fixedProductName = fixedProductName;
    }
}
