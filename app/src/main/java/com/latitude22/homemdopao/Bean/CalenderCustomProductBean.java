package com.latitude22.homemdopao.Bean;

/**
 * Created by vaio1 on 05-12-2017.
 */

public class CalenderCustomProductBean {

    String productId;

    public CalenderCustomProductBean(String productId, String productQty, String productName) {
        this.productId = productId;
        this.productQty = productQty;
        ProductName = productName;
    }

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
