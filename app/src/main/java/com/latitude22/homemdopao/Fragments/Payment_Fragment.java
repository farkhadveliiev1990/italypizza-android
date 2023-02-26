package com.latitude22.homemdopao.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.latitude22.homemdopao.Bean.BreadShopData;
import com.latitude22.homemdopao.MainActivity;
import com.latitude22.homemdopao.R;
import com.norbsoft.typefacehelper.TypefaceHelper;

import java.util.ArrayList;

/**
 * Created by Anuved on 23-08-2016.
 */
public class Payment_Fragment extends Fragment {


    static Context Mcontext;
    ListView cart_list;
    static android.support.v4.app.FragmentManager fm;


    public static android.support.v4.app.Fragment getInstance(Context Mcntx, FragmentManager FM) {
        Mcontext = Mcntx;
        fm = FM;
        android.support.v4.app.Fragment frag = new Payment_Fragment();
        return frag;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
        View v = inflater.inflate(R.layout.payment_fragment, container, false);
        TypefaceHelper.typeface(v);
        cart_list = (ListView) v.findViewById(R.id. cart_list);
        // breadShopList = new ArrayList<BreadShopData>();
        MainActivity.main_icon.setVisibility(View.GONE);
        MainActivity.cart.setVisibility(View.GONE);
        MainActivity.header_text.setVisibility(View.VISIBLE);
        MainActivity.header_text.setText("Produtos no seu carrinho de transporte");
        MainActivity.main_cart_quantity.setVisibility(View.GONE);
        MainActivity.mDrawerToggle.setVisibility(View.VISIBLE);
        MainActivity.mDrawerToggle.setImageResource(R.drawable.ar_hdr);
        MainActivity.icon_filter.setVisibility(View.GONE);
        return v;
    }
}

