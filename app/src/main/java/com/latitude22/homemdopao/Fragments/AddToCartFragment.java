package com.latitude22.homemdopao.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.latitude22.homemdopao.Adapter.AddToCartAdapter;
import com.latitude22.homemdopao.AddCart;
import com.latitude22.homemdopao.MainActivity;
import com.latitude22.homemdopao.MyCart;
import com.latitude22.homemdopao.R;
import com.latitude22.homemdopao.SessionManager;
import com.norbsoft.typefacehelper.TypefaceHelper;

import java.util.ArrayList;

/**
 * Created by Anuved on 23-08-2016.
 */
public class AddToCartFragment extends android.support.v4.app.Fragment {

    ArrayList<AddCart> addCarts;
    AddCart cartTable;
    AddToCartAdapter addToCartAdapter;
    static Context Mcontext;
    ListView cart_list;
    Double totalAmt;
    String UserId;
    Button bt_checkout;
    SessionManager sm;
    static TextView cart_total_amount;
    static android.support.v4.app.FragmentManager fm;
    LinearLayout total_ll;

    public static android.support.v4.app.Fragment getInstance(Context Mcntx, FragmentManager FM) {
        Mcontext = Mcntx;
        fm = FM;
        android.support.v4.app.Fragment frag = new AddToCartFragment();
        return frag;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
        View v = inflater.inflate(R.layout.addtocart_fragment, container, false);
        TypefaceHelper.typeface(v);
        cart_list = (ListView) v.findViewById(R.id.cart_list);
        bt_checkout = (Button) v.findViewById(R.id.bt_checkout);
        cart_total_amount = (TextView) v.findViewById(R.id.cart_total_amount);
        addCarts = (ArrayList<AddCart>) AddCart.AddCart();
        // breadShopList = new ArrayList<BreadShopData>();

        cart_list.setAdapter(new AddToCartAdapter(addCarts, Mcontext));

        MainActivity.main_icon.setVisibility(View.GONE);
        MainActivity.cart.setVisibility(View.GONE);
        MainActivity.header_text.setVisibility(View.VISIBLE);
        MainActivity.header_text.setText("Your Cart");
        MainActivity.main_cart_quantity.setVisibility(View.GONE);
        MainActivity.icon_filter.setVisibility(View.GONE);
        total_ll = (LinearLayout) v.findViewById(R.id.total_ll);
        total_ll.setVisibility(View.GONE);
        // MainActivity.mDrawerToggle.setVisibility(View.VISIBLE);
        //   MainActivity.mDrawerToggle.setImageResource(R.drawable.ar_hdr);
        sm = new SessionManager(Mcontext);
        UserId = sm.getuserid();

        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        getActivity().finish();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                        return true;
                    }
                }
                return false;
            }
        });

        bt_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalAmt = Double.valueOf(cart_total_amount.getText().toString());
                if (totalAmt == 0) {
                    //    Intent i = new Intent(getActivity(), LoginActivity.class);
                    //  startActivity(i);
                    // getActivity().finish();
                    Toast.makeText(Mcontext, "Your cart is empty Add Item", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), MyCart.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }
            }
        });
        cart_total_amount.setText("" + PriceTotal(addCarts));
        return v;
    }

    public double PriceTotal(ArrayList<AddCart> Cartlist) {
        double total = 0.0;
        for (int i = 0; i < Cartlist.size(); i++) {
            total = total + (Cartlist.get(i).getProductprice() * Cartlist.get(i).getProductquantity());
        }
        cart_total_amount.setText("" + total);
        return total;
    }
}
