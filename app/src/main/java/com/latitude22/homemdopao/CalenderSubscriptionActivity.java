package com.latitude22.homemdopao;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.latitude22.homemdopao.Adapter.CalenderSubscriptionAdapter;
import com.latitude22.homemdopao.Adapter.CalenderSubscriptionProductAdapter;
import com.latitude22.homemdopao.Adapter.MyOrderProductAdapter;
import com.latitude22.homemdopao.Bean.CalenderCustomProductBean;
import com.latitude22.homemdopao.Bean.CalenderDateOfDeliveryBean;
import com.latitude22.homemdopao.Bean.CalenderProductBean;
import com.latitude22.homemdopao.Bean.CalenderSubscriptionBean;
import com.latitude22.homemdopao.Bean.FixedPlanProductBean;
import com.latitude22.homemdopao.Bean.MysubscriptionProductBean;
import com.latitude22.homemdopao.Service.MapLocationService;
import com.norbsoft.typefacehelper.TypefaceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CalenderSubscriptionActivity extends AppCompatActivity {
    ListView subscription_list;
    CalenderSubscriptionBean bean;
    AlertDialog b;
    String deliveryBoyId;
    CalenderCustomProductBean productbean;
    ImageView header_img;
    TextView tv_days;
    String boyID;
    CalenderSubscriptionAdapter adapter;
    ArrayList<CalenderDateOfDeliveryBean> arrayList;
    ArrayList<CalenderProductBean> calendrProductArray;
    ArrayList<CalenderDateOfDeliveryBean>mycalelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_calender_subscription);
        TypefaceHelper.typeface(this);
        subscription_list = (ListView) findViewById(R.id.subscription_list);
        header_img = (ImageView) findViewById(R.id.header_img);
        tv_days = (TextView) findViewById(R.id.tv_days);
        mycalelist=new ArrayList<>();
      /*int days_no =   getIntent().getIntExtra("days",0);
        tv_days.setText(""+days_no);*/
        header_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CalenderSubscriptionActivity.this,CalenderActivity.class);
                startActivity(i);
                finish();
            }
        });
        arrayList = new ArrayList<>();


        String listSting = getIntent().getStringExtra("productlist");
        String DaysNo = getIntent().getStringExtra("dayofDelivery");
        mycalelist=(ArrayList<CalenderDateOfDeliveryBean>)getIntent().getSerializableExtra("dateofdelevery");


        tv_days.setText(DaysNo);


        JSONArray jpitem = null;
        try {
            jpitem = new JSONArray(listSting);


            for (int j = 0; j < jpitem.length(); j++) {

                CalenderDateOfDeliveryBean bean= new CalenderDateOfDeliveryBean();

                JSONObject obj=new JSONObject();
                JSONObject jData = jpitem.getJSONObject(j);
                bean.setDelievrySubscName(jData.optString("SubscriptionName"));
                bean.setOrderDeliveryId(jData.optString("OrderDeliveryID"));
                bean.setDeliveryboyid(jData.optString("OrderDeliveryBoyID"));
                bean.setDeliveryTime(jData.optString("OrderDeliveryTime"));
                bean.setStatus(jData.optString("OrderDeliveryStatus"));
                bean.setDated(jData.optString("OrderDeliveryDate"));
                deliveryBoyId = jData.optString("OrderDeliveryBoyID");
              //  bean.setItemno(j+1);

              //  String listSting = arrayList.get(position).getCalenderProduct();

            /*    JSONArray jprorray = jData.getJSONArray("product");
                calendrProductArray = new ArrayList<CalenderProductBean>();
                JSONArray marray = new JSONArray();

                for (int k = 0; k < jprorray.length(); k++)
                {
                    CalenderProductBean productBean = new CalenderProductBean();
                    JSONObject objects = new JSONObject();
                    JSONObject jobj = jprorray.getJSONObject(k);
                    objects.put("ProductName", jobj.optString("ProductName"));
                    objects.put("ProductQuantity", jobj.optString("ProductQuantity"));
                    marray.put(objects);
                    productBean.setProductName(jobj.optString("ProductName"));
                    productBean.setProductQty(jobj.optString("ProductQuantity"));
                    calendrProductArray.add(productBean);
                }

                bean.setProductlist(calendrProductArray);
                bean.setCalenderProduct(marray.toString());*/
                arrayList.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

       // Toast.makeText(this, "BoyId"+deliveryBoyId, Toast.LENGTH_SHORT).show();
        if(arrayList.size()!=0)
        {
            adapter = new CalenderSubscriptionAdapter(arrayList,this);
            subscription_list.setAdapter(adapter);
           /* adapter.notifyDataSetChanged();
            subscription_list.invalidateViews();*/

        }
        else
        {
            // Toast.makeText(this, "Disconnect Server", Toast.LENGTH_SHORT).show();
        }

        subscription_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                showDaialog(position);
            }
        });

      //  Toast.makeText(this, ""+boyID, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    public void showDaialog(int position)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.subscription_product_dialog, null);
        dialogBuilder.setView(dialogView);
        TypefaceHelper.typeface(dialogView);
        b = dialogBuilder.create();
        b.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        b.setCancelable(true);
        b.getWindow().setGravity (Gravity.CENTER);
        b.show();
        ListView  productList = (ListView) dialogView.findViewById(R.id.dilaog_list);
        ImageView heder_back = (ImageView) dialogView.findViewById(R.id.heder_back);
        //  dialogBuilder.setTitle("Set No. of Days");
        ArrayList<CalenderProductBean> productListArray = new ArrayList<>();

        JSONArray jpitem = null;

        String listSting = mycalelist.get(position).getCalenderProduct();
        Log.i("listSting  ",productListArray.size()+" listSting "+listSting);

        try {
            jpitem = new JSONArray(listSting);

            if(jpitem.length()>0) {
                for (int j = 0; j < jpitem.length(); j++) {


                    JSONObject jData = jpitem.getJSONObject(j);
                    Log.i("listSting  ",jData.optString("ProductName")+" listSting "+jData.optString("ProductQuantity"));

                   // if(jData.optString("ProductName") != null && jData.optString("ProductQuantity").equals("0"))
                    {    CalenderProductBean bean = new CalenderProductBean();

                        bean.setProductName(jData.optString("ProductName"));
                        bean.setProductQty(jData.optString("ProductQuantity"));
                        productListArray.add(bean);
                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(productListArray.size()>0)
        productList.setAdapter(new CalenderSubscriptionProductAdapter(productListArray,CalenderSubscriptionActivity.this));
        else
        {
            b.dismiss();
            Toast.makeText(CalenderSubscriptionActivity.this,"Nenhum produto",Toast.LENGTH_SHORT).show();
        }

        heder_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
