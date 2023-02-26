package com.latitude22.homemdopao;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.latitude22.homemdopao.Adapter.HistoryProductAdapter;
import com.latitude22.homemdopao.Adapter.MyOrderProductAdapter;
import com.latitude22.homemdopao.Bean.HistoryProductBean;
import com.latitude22.homemdopao.Bean.MysubscriptionProductBean;
import com.latitude22.homemdopao.Fragments.HistoryFragment;
import com.latitude22.homemdopao.Fragments.ProductFragment;
import com.norbsoft.typefacehelper.TypefaceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryDetail extends AppCompatActivity {
    ArrayList<HistoryProductBean> productList;
    ListView history_product__list;
    ImageView header_img;
    TextView sbsc_name,sbsc_cost,sbsc_delivery,sbsc_date,sbsc_status;
    Fragment fragment = null;
    static android.support.v4.app.FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_history_detail);
        TypefaceHelper.typeface(this);
        String listSting = getIntent().getStringExtra("productlist");

        productList = new ArrayList<HistoryProductBean>();
        header_img = (ImageView) findViewById(R.id.header_img);
        sbsc_name = (TextView) findViewById(R.id.sbsc_name);
        sbsc_cost = (TextView) findViewById(R.id.sbsc_cost);
        sbsc_status = (TextView) findViewById(R.id.sbsc_status);
        sbsc_delivery = (TextView) findViewById(R.id.sbsc_delivery);
        sbsc_date = (TextView) findViewById(R.id.sbsc_date);
        history_product__list = (ListView) findViewById(R.id.history_product__list);


        sbsc_name.setText(getIntent().getStringExtra("SubscriptionName"));
        sbsc_cost.setText(getIntent().getStringExtra("SubscriptionCost"));
        sbsc_delivery.setText(getIntent().getStringExtra("NoDelivery"));
        sbsc_date.setText(getIntent().getStringExtra("SubscriptionDate"));
        sbsc_status.setText(getIntent().getStringExtra("SubscriptionStatus"));

        if(getIntent().getStringExtra("SubscriptionStatus").equals("Continuar"))
        {
            sbsc_status.setTextColor(ContextCompat.getColor(this,R.color.green));
        }
        if(getIntent().getStringExtra("SubscriptionStatus").equals("Completa"))
        {
            sbsc_status.setTextColor(ContextCompat.getColor(this,R.color.orange));
        }
        if(getIntent().getStringExtra("SubscriptionStatus").equals("Inátivo"))
        {
            sbsc_status.setTextColor(ContextCompat.getColor(this,R.color.red));
        }
        header_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        JSONArray jpitem = null;
        try {
            jpitem = new JSONArray(listSting);

            for (int j = 0; j < jpitem.length(); j++) {

                HistoryProductBean bean= new HistoryProductBean();

                JSONObject obj=new JSONObject();
                JSONObject jData = jpitem.getJSONObject(j);
                bean.setProductName(jData.optString("ProductName"));
                bean.setProductQty(jData.optInt("Productqty"));
                bean.setProductPrice(jData.optString("ProductPrice"));
                bean.setProductImage(jData.optString("ProductImage"));
                productList.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        history_product__list.setAdapter(new HistoryProductAdapter(productList,this));
    }
}
