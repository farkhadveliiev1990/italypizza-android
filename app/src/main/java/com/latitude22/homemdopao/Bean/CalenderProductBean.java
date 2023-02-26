package com.latitude22.homemdopao.Bean;

import java.io.Serializable;

/**
 * Created by Anuved on 02-09-2016.
 */
public class CalenderProductBean  implements Serializable {

    String productId;



    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    String productQty;

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    String ProductName;
}
