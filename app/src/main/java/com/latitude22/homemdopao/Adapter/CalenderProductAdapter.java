package com.latitude22.homemdopao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.latitude22.homemdopao.Bean.CalenderBean;
import com.latitude22.homemdopao.R;
import com.norbsoft.typefacehelper.TypefaceHelper;

import java.util.ArrayList;

/**
 * Created by Anuved on 02-09-2016.
 */
public class CalenderProductAdapter extends BaseAdapter {
    Context Mcontext;
    TextView item_no,tap_txt;
    String deliveryboyid;
    double deslat,deslng,sourcelat,sourcelng;
    int pos=1991;
  //  ArrayList<CalenderProductBean> productList;
  ArrayList<CalenderBean> deliveryList;

  //  public CalenderProductAdapter(ArrayList<CalenderProductBean> productList, Context mcontext) {
  //      this.productList  = productList;
   //     Mcontext = mcontext;
  //  }

    public CalenderProductAdapter(ArrayList<CalenderBean> deliveryList, Context mcontext) {
        this.deliveryList  = deliveryList;
        Mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return deliveryList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(Mcontext);


        convertView = layoutInflater.inflate(R.layout.calender_product_list, null);

        TypefaceHelper.typeface(convertView);
        item_no =(TextView)convertView .findViewById(R.id.calendr_product_no);
        tap_txt =(TextView)convertView .findViewById(R.id.tap_tv);

        int count =0;
       /* for(int j=0; j== deliveryList.length();j++)
        {
            //int cnt = j+1;
           // int cnt  = deliveryList.indexOf(deliveryList.get(position).getDeliveryDate());
            item_no.setText(""+j);

        }
        */

        TextView calendr_date =(TextView)convertView .findViewById(R.id.calendr_date);
        calendr_date.setText(deliveryList.get(position).getOrderdeliveryDate());

     /*   TextView calendr_order_id =(TextView)convertView .findViewById(R.id.calendr_order_id);
        calendr_order_id.setText(deliveryList.get(position).getOrderDeliveryId());*/


       // ImageView img_delivery = (ImageView)convertView.findViewById(R.id.img_delivery);

     //   TextView calendr_delvry_status =(TextView)convertView .findViewById(R.id.calendr_delvry_status);

/*
        if((deliveryList.get(position).getStatus().equals("0")))
        {
            calendr_delvry_status.setText("Pending");
            tap_txt.setText("Tap to Change Delivery");

        }
        else if(deliveryList.get(position).getStatus().equals("1")){
            calendr_delvry_status.setText("Process");
            tap_txt.setText("Tap to Trash Order");
        }
        else {
            calendr_delvry_status.setText("Delivered");
        }
        if((calendr_delvry_status.getText().equals("Pending")));
        {
            calendr_delvry_status.setTextColor(Mcontext.getResources().getColor(R.color.orange));
            img_delivery.setImageResource(R.drawable.icon_pend);
        }


        if (calendr_delvry_status.getText().equals("Delivered")) {
            calendr_delvry_status.setTextColor(Mcontext.getResources().getColor(R.color.green));
            img_delivery.setImageResource(R.drawable.icon_deliver);
        }
        if (calendr_delvry_status.getText().equals("Process")) {
            pos=1;
            calendr_delvry_status.setTextColor(Mcontext.getResources().getColor(R.color.caldroid_light_red));
            img_delivery.setImageResource(R.drawable.icon_location);

        }*/
        /*if(pos==1){
            img_delivery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deliveryboyid=deliveryList.get(position).getDeliveryboyid();

                    GetLocation();
                }
            });
        }*/
/*if(img_delivery.setImageResource(R.drawable.icon_location)=){

}*/
       item_no.setText(""+deliveryList.get(position).getDaysNo());
        return convertView;
    }
   /* public  void GetLocation() {

        //  Toast.makeText(getBaseContext(), "inffg", Toast.LENGTH_SHORT).show();
        Ion.with(Mcontext).load("http://l22.co.in/homemdopao/webservice/usersdeliveryboylocation")
                .setBodyParameter("id", deliveryboyid)
                .asString().setCallback(new FutureCallback<String>() {

            @Override
            public void onCompleted(Exception e, String result) {
                //  Log.d("result", result);


                try {

                    JSONObject jsonObject = new JSONObject(result);

                    String status = jsonObject.getString("status");

                    if (status.equals("1")) {
                        JSONObject jsonObject1=jsonObject.getJSONObject("result");
                        String lat=jsonObject1.getString("latitude");
                        String lng=jsonObject1.getString("longitude");

                        Toast.makeText(Mcontext, "lat"+lat, Toast.LENGTH_SHORT).show();
                        Toast.makeText(Mcontext, "lng"+lng, Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(Mcontext,TrackDeliveryActivity.class);

                        sourcelat= Double.parseDouble(lat);
                        sourcelng= Double.parseDouble(lng);
                        i.putExtra("deliveryboy",deliveryboyid);
                        i.putExtra("sourcelat",sourcelat);
                        i.putExtra("sourcelng",sourcelng);
                        i.putExtra("lat", CalenderActivity.deslat);
                        i.putExtra("lng",CalenderActivity.deslng);
                        Mcontext.startActivity(i);
                        *//* if(i==1){
                             status_txt.setText("Progress");
                         }ma
                        // lm.setOrderDeliveryStatus("Completed");
                         status_txt.setText("Completed");*//*
                        //  todaysdeliveryBeen.notifyAll();
                        //     Toast.makeText(getBaseContext(), ""+msg, Toast.LENGTH_SHORT).show();
                    } else if (status.equals("0")) {

                    }
                } catch (JSONException e1) {
                    //Toast.makeText(getBaseContext(), "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }*/
}
