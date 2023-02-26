package com.latitude22.homemdopao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.latitude22.homemdopao.Adapter.AddToCartActivityAdapter;
import com.latitude22.homemdopao.Adapter.AddToCartAdapter;
import com.norbsoft.typefacehelper.TypefaceHelper;

import java.util.ArrayList;

public class AddToCartActivity extends AppCompatActivity {

    ArrayList<AddCart> addCarts;
    AddCart cartTable;
    AddToCartAdapter addToCartAdapter;
    static Context Mcontext;
    ListView cart_list;
    Double totalAmt;
    String UserId;
    Button bt_checkout;
    ImageView header_img;
    LinearLayout totalll;
    static TextView cart_total_amount,detail_cart_quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_to_cart);
        TypefaceHelper.typeface(this);
        cart_list = (ListView) findViewById(R.id.cart_list);
        bt_checkout = (Button) findViewById(R.id.bt_checkout);
        detail_cart_quantity = (TextView) findViewById(R.id.detail_cart_quantity);
        header_img = (ImageView) findViewById(R.id.header_img);
        cart_total_amount = (TextView) findViewById(R.id.cart_total_amount);
        totalll=(LinearLayout)findViewById(R.id.total_ll);
        totalll.setVisibility(View.GONE);
        cart_total_amount.setVisibility(View.GONE);
        addCarts = (ArrayList<AddCart>) AddCart.AddCart();
        // breadShopList = new ArrayList<BreadShopData>();
        cart_list.setAdapter(new AddToCartActivityAdapter(addCarts,this));
        detail_cart_quantity.setText("Seu carrinho");
      /*  MainActivity.main_icon.setVisibility(View.GONE);
        MainActivity.cart.setVisibility(View.GONE);
        MainActivity.header_text.setVisibility(View.VISIBLE);
        MainActivity.header_text.setText("Your Cart");
        MainActivity.main_cart_quantity.setVisibility(View.GONE);
        MainActivity.icon_filter.setVisibility(View.GONE);
        */
        // MainActivity.mDrawerToggle.setVisibility(View.VISIBLE);
        //   MainActivity.mDrawerToggle.setImageResource(R.drawable.ar_hdr);

       // sm =new SessionManager(Mcontext);
      //  UserId = sm.getuserid();
        header_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddToCartActivity.this,MainActivity.class);
                startActivity(i);
              //  finish();
            }
        });
        bt_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalAmt = Double.valueOf(cart_total_amount.getText().toString());
                if(totalAmt == 0) {
                    //    Intent i = new Intent(getActivity(), LoginActivity.class);
                    //  startActivity(i);
                    // getActivity().finish();
                    Toast.makeText(Mcontext, "Your cart is empty Add Item", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(AddToCartActivity.this, MyCart.class);
                   startActivity(intent);
                   // finish();
                }
            }
        });
        cart_total_amount.setText("" + PriceTotal(addCarts));
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
