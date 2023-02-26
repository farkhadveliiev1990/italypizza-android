package com.latitude22.homemdopao;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.latitude22.homemdopao.Adapter.MyCartAdapter;
import com.latitude22.homemdopao.Adapter.PaymentAdapter;
import com.norbsoft.typefacehelper.TypefaceHelper;

import java.util.ArrayList;

import static com.latitude22.homemdopao.AddToCartActivity.Mcontext;

public class MyCartActivity extends AppCompatActivity {

    ListView cart_item_list;
    ImageView header_img;
    ArrayList<AddCart> addCarts;
    SessionManager sm;
    String UserId;
    Context Mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        TypefaceHelper.typeface(this);
        cart_item_list = (ListView) findViewById(R.id.cart_item_list);
        header_img = (ImageView) findViewById(R.id.header_img);
        addCarts = (ArrayList<AddCart>) AddCart.AddCart();
        cart_item_list.setAdapter(new MyCartAdapter(addCarts, this));
        sm =new SessionManager(Mcontext);
        UserId = sm.getuserid();
        header_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyCartActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        Intent i = new Intent(MyCartActivity.this,MainActivity.class);
        startActivity(i);
        finish();
        // finish();
    }
}
