package com.latitude22.homemdopao.Bean;

/**
 * Created by Anuved on 08-08-2016.
 */
public class BreadShopData  {


    int product_id;
    String product_name;
    String category_name;
    String size_Name;
    String type_Name;
    String suplier_Name;
    String product_Description;
    Double product_Price;
    String isFeatured;
    String product_Image;

   /* public  BreadShopData( int product_id, String product_name, String category_name, String size_Name, String type_Name, String suplier_Name, String product_Description, Double product_Price,String isFeatured, String product_Image)

    {
        this.product_id = product_id;
        this.product_name = product_name;
        this.category_name = category_name;
        this.size_Name = size_Name;
        this.type_Name = type_Name;
        this.suplier_Name = suplier_Name;
        this.product_Description = product_Description;
        this. product_Price = product_Price;
        this.isFeatured =isFeatured;
        this.product_Image = product_Image;



    }
    */


    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getSize_Name() {
        return size_Name;
    }

    public void setSize_Name(String size_Name) {
        this.size_Name = size_Name;
    }

    public String getType_Name() {
        return type_Name;
    }

    public void setType_Name(String type_Name) {
        this.type_Name = type_Name;
    }

    public String getSuplier_Name() {
        return suplier_Name;
    }

    public void setSuplier_Name(String suplier_Name) {
        this.suplier_Name = suplier_Name;
    }

    public String getProduct_Description() {
        return product_Description;
    }

    public void setProduct_Description(String product_Description) {
        this.product_Description = product_Description;
    }

    public Double getProduct_Price() {
        return product_Price;
    }

    public void setProduct_Price(Double product_Price) {
        this.product_Price = product_Price;
    }

    public String getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(String isFeatured) {
        this.isFeatured = isFeatured;
    }

    public String getProduct_Image() {
        return product_Image;
    }

    public void setProduct_Image(String product_Image) {
        this.product_Image = product_Image;
    }
}
