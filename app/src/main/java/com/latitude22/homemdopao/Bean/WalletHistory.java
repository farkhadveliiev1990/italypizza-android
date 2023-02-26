package com.latitude22.homemdopao.Bean;

import java.io.Serializable;

/**
 * Created by  on 07/10/16.
 */
public class WalletHistory implements Serializable {

    public String wallet_amount;
    public String wallet_reason;
    public String added_amt;



    public String deducted_date;
    public String deducted_amt;
    public String added_date;
    public String id;

    public  WalletHistory()
    {

    }

    public WalletHistory(String wallet_amount, String wallet_reason, String added_amt, String deducted_amt, String added_date,String deducted_date, String id) {
        this.wallet_amount = wallet_amount;
        this.wallet_reason = wallet_reason;
        this.added_amt = added_amt;
        this.deducted_amt = deducted_amt;
        this.deducted_date=deducted_date;
        this.added_date = added_date;
        this.id = id;
    }
    public String getDeducted_date() {
        return deducted_date;
    }

    public void setDeducted_date(String deducted_date) {
        this.deducted_date = deducted_date;
    }
    public String getWallet_amount() {
        return wallet_amount;
    }

    public void setWallet_amount(String wallet_amount) {
        this.wallet_amount = wallet_amount;
    }

    public String getWallet_reason() {
        return wallet_reason;
    }

    public void setWallet_reason(String wallet_reason) {
        this.wallet_reason = wallet_reason;
    }

    public String getAdded_amt() {
        return added_amt;
    }

    public void setAdded_amt(String added_amt) {
        this.added_amt = added_amt;
    }

    public String getDeducted_amt() {
        return deducted_amt;
    }

    public void setDeducted_amt(String deducted_amt) {
        this.deducted_amt = deducted_amt;
    }

    public String getAdded_date() {
        return added_date;
    }

    public void setAdded_date(String added_date) {
        this.added_date = added_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }





  }