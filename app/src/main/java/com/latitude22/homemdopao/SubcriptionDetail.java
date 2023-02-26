package com.latitude22.homemdopao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.latitude22.homemdopao.Adapter.MyOrderProductAdapter;
import com.latitude22.homemdopao.Bean.FixedPlanProductBean;
import com.latitude22.homemdopao.Bean.FixedplanBean;
import com.latitude22.homemdopao.Bean.MysubscriptionProductBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubcriptionDetail extends AppCompatActivity {

    ListView subscription_detail_list;
    ArrayList<MysubscriptionProductBean> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcription_detail);
        subscription_detail_list = (ListView) findViewById(R.id.subscription_detail_list);

        String listSting = getIntent().getStringExtra("productlist");
        productList = new ArrayList<MysubscriptionProductBean>();
        JSONArray jpitem = null;
        try {
            jpitem = new JSONArray(listSting);

            for (int j = 0; j < jpitem.length(); j++) {

                MysubscriptionProductBean bean= new MysubscriptionProductBean();

                JSONObject obj=new JSONObject();
                JSONObject jData = jpitem.getJSONObject(j);
                bean.setName(jData.optString("ProductName"));
                bean.setQty(jData.optInt("Productqty"));
                productList.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        subscription_detail_list.setAdapter(new MyOrderProductAdapter(productList,this));
    }
}
