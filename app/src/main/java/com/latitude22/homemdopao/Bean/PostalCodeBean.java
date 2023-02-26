package com.latitude22.homemdopao.Bean;

/**
 * Created by Anuved on 30-12-2016.
 */

public class PostalCodeBean {

    String postalCode;

    public int getPostalZoneid() {
        return postalZoneid;
    }

    public void setPostalZoneid(int postalZoneid) {
        this.postalZoneid = postalZoneid;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getPostalId() {
        return postalId;
    }

    public void setPostalId(int postalId) {
        this.postalId = postalId;
    }

    public int getPostalStatus() {
        return postalStatus;
    }

    public void setPostalStatus(int postalStatus) {
        this.postalStatus = postalStatus;
    }

    public int getPostalCreatedDate() {
        return postalCreatedDate;
    }

    public void setPostalCreatedDate(int postalCreatedDate) {
        this.postalCreatedDate = postalCreatedDate;
    }

    int postalZoneid;
    int postalId;
    int postalStatus;
    int postalCreatedDate;
}
