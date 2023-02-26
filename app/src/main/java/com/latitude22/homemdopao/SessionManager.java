package com.latitude22.homemdopao;


import android.content.Context;
import android.content.SharedPreferences;

import com.latitude22.homemdopao.Bean.Userdetail;

public class SessionManager {

    SharedPreferences sharedPreferences;

    public SessionManager(Context Mcontex){
        sharedPreferences = Mcontex.getSharedPreferences("UserProfile", Context.MODE_PRIVATE);


    }
    public void save(Userdetail bean) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", String.valueOf(bean.getId()));
        editor.putString("contactno", bean.getUserMobileno());
        editor.putString("email",bean.getEmail());
        editor.putString("userfullname",bean.getUserName());
        editor.putString("profileImage",bean.getProfileImage());
        editor.putString("UserAddress",bean.getUserAddress());


        editor.commit();
    }
    public  String getuserid( ){

        return sharedPreferences.getString("id","");

    }
    public  String getuseraddress( ){

        return sharedPreferences.getString("UserAddress","");
    }
   public String getUsername(){

        return sharedPreferences.getString("username","");

    }
   public String getUserFullName(){

        return sharedPreferences.getString("userfullname","");

    }

    public  String getEmail(){
        return  sharedPreferences.getString("email","");
    }
    public String getUsercontact(){return  sharedPreferences.getString("contactno","");}

    public String getprofileImage()
    {
        return  sharedPreferences.getString("profileImage","");
    }
    public  void logout(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void saveLastText(String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lastPushMessage", value);
        editor.commit();
    }

    public String getLastText(){
        return  sharedPreferences.getString("lastPushMessage","");
    }
}