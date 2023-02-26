package com.latitude22.homemdopao;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Anuved on 23-08-2016.
 */
@Table(name = "AddCart" ,id = "cartId")
public class AddCart extends Model {

    @Column(name = "name")
    public String productname;

    @Column(name = "productquantity")
    public int productquantity;

    @Column(name = "price")
    public Double productprice;

    @Column(name = "unitprice")
    public Double unitprice;

    @Column(name = "productId")
    public int productId;

    @Column(name = "image")
    public String productImage;

    @Column(name = "Userid")
    public int userId;


    public AddCart()
    {
        super();
    }


    public AddCart (String productname,int productquantity,double productprice,int productId,int userId,String productImage,double unitprice)
    {
        this.productname = productname;
        this.productquantity = productquantity;
        this.productprice = productprice;
        this.productId = productId;
        this.userId = userId;
        this.productImage = productImage;
        this.unitprice=unitprice;
    }

    public Double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(Double unitprice) {
        this.unitprice = unitprice;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public int getProductquantity() {
        return productquantity;
    }

    public void setProductquantity(int productquantity) {
        this.productquantity = productquantity;
    }

    public Double getProductprice() {
        return productprice;
    }

    public void setProductprice(Double productprice) {
        this.productprice = productprice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static List AddCart() {

        return new Select().from(AddCart.class).execute();
    }
}


