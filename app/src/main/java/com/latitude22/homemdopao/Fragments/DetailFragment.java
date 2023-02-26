package com.latitude22.homemdopao.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.latitude22.homemdopao.Bean.BreadShopData;
import com.latitude22.homemdopao.MainActivity;
import com.latitude22.homemdopao.R;
import com.melnykov.fab.FloatingActionButton;
import com.norbsoft.typefacehelper.TypefaceHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Anuved on 22-08-2016.
 */
public class DetailFragment extends Fragment {

    ArrayList<BreadShopData> breadShopList;
    Dialog category_dialog;
    private FloatingActionButton filterButton;
    static Context Mcontext;
    static android.support.v4.app.FragmentManager fm;
    ActionBar actionBar;
    TextView detail_breadName,detail_price,detail_breaddescrip;
    ImageView detail_breadImg,header_img;

    public static android.support.v4.app.Fragment getInstance(Context Mcntx, FragmentManager FM) {
        Mcontext = Mcntx;
        fm = FM;
        android.support.v4.app.Fragment frag = new DetailFragment();
        return frag;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
        View v = inflater.inflate(R.layout.detail_fragment, container, false);
        TypefaceHelper.typeface(v);
        final TextView tv_detail_count = (TextView) v.findViewById(R.id.tv_detail_count);

        MainActivity.main_icon.setVisibility(View.GONE);
        MainActivity.cart.setVisibility(View.VISIBLE);
        MainActivity.mDrawerToggle.setVisibility(View.VISIBLE);
        MainActivity.mDrawerToggle.setImageResource(R.drawable.ar_hdr);
        MainActivity.header_text.setVisibility(View.GONE);
        MainActivity.main_cart_quantity.setVisibility(View.VISIBLE);

        Button detail_minus_bt = (Button) v.findViewById(R.id.detail_minus_bt);

        Button detail_plus_bt = (Button) v.findViewById(R.id.detail_plus_bt);


        detail_breadName = (TextView) v.findViewById(R.id.detail_breadName);
        detail_price = (TextView) v.findViewById(R.id.detail_price);
        detail_breaddescrip = (TextView) v.findViewById(R.id.detail_breaddescrip);
        detail_breadImg = (ImageView) v.findViewById(R.id.detail_breadImg);
        LinearLayout toolbar_detail = (LinearLayout) v.findViewById(R.id.detail_header);

        MainActivity.mDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        setdata();

        detail_plus_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int values = Integer.parseInt(String.valueOf(tv_detail_count.getText()));
                int quantity = values + 1;
                tv_detail_count.setText("" + quantity);
            }
        });

        detail_minus_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int values = Integer.parseInt(String.valueOf(tv_detail_count.getText()));
                int quantity = values - 1;
                if (quantity >= 0) {
                    tv_detail_count.setText("" + quantity);
                }
            }
        });
        return v;
    }





    public void setdata(){
        Intent i= getActivity().getIntent();
        if(i!=null){


            String productName=i.getStringExtra("Productname");
            Double productPrice= i.getDoubleExtra("Productprice",0);
            String productImage = i.getStringExtra("ProductImage");
            String productDescription = i.getStringExtra("ProductDescription");
            detail_breadName.setText(productName);
            detail_price.setText(""+productPrice);
            detail_breaddescrip.setText(productDescription);


            try{
                if (productImage!=null&!productImage.equals("")){
                    Picasso.with(getActivity()).load(productImage).error(R.drawable.bread_images).placeholder(R.drawable.bread_images).into(detail_breadImg);
                }

            }catch (Exception ae){

            }


        }


    }
}

