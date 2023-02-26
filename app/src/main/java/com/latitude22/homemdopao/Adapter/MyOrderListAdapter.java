package com.latitude22.homemdopao.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.latitude22.homemdopao.Bean.FixedPlanProductBean;
import com.latitude22.homemdopao.Bean.MyOrderListBean;
import com.latitude22.homemdopao.Bean.MysubscriptionProductBean;
import com.latitude22.homemdopao.R;
import com.latitude22.homemdopao.TrackDeliveryActivity;
import com.norbsoft.typefacehelper.TypefaceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Anuved on 29-08-2016.
 */
public class MyOrderListAdapter extends BaseAdapter {

    Context Mcontext;
    public  static  String MySubscrdeliveryBoyId;
    ArrayList<MyOrderListBean> myOrderList;
    ArrayList<MysubscriptionProductBean> productListArray ;

    public MyOrderListAdapter( ArrayList<MyOrderListBean> myOrderList , Context mcontext) {
        this.myOrderList  = myOrderList;
        Mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return myOrderList.size();
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


        convertView = layoutInflater.inflate(R.layout.myorder_list, null);

        TypefaceHelper.typeface(convertView);

        TextView nameSubscription=(TextView)convertView.findViewById(R.id.subscriptionName);
        nameSubscription.setText(myOrderList.get(position).getSubscriptionName());

        TextView text_count=(TextView)convertView.findViewById(R.id.text_count);
        text_count.setText(""+myOrderList.get(position).getSubscriptionCount());

        TextView NoofPending=(TextView)convertView.findViewById(R.id.txt_pendingNo);
        NoofPending.setText(myOrderList.get(position).getNoOfPending().toString());

        TextView ordr_id=(TextView)convertView.findViewById(R.id.ordr_id);
        ordr_id.setText(myOrderList.get(position).getOrderId());

/*
        TextView layout_track = (TextView) convertView.findViewById(R.id.track_delivery);


            layout_track.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(myOrderList.get(position).getDeliveryBoyID().equals("0")||myOrderList.get(position).getDeliveryBoyID().equals("")||myOrderList.get(position).getDeliveryBoyID()==null)
                    {
                        // layout_track.setVisibility(View.GONE);
                        Toast.makeText(Mcontext, "Your Order not dispatch", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        MySubscrdeliveryBoyId = myOrderList.get(position).getDeliveryBoyID();
                        // Toast.makeText(Mcontext, "AdapterDelivery"+deliveryBoyId, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Mcontext, TrackDeliveryActivity.class);
                        intent.putExtra("setFlag",0);
                        Mcontext.startActivity(intent);
                        ((Activity) Mcontext).finish();
                    }
                }
            });*/

      //  Toast.makeText(Mcontext, "DeliveryBoyId"+MySubscrdeliveryBoyId, Toast.LENGTH_SHORT).show();
     /*   TextView end_date=(TextView)convertView .findViewById(R.id.end_date);
        end_date.setText(myOrderList.get(position).getEndDate());*/


        TextView strt_date=(TextView)convertView.findViewById(R.id.strt_date);
        strt_date.setText(myOrderList.get(position).getStartDate());
/*        ListView product_list = (ListView)convertView.findViewById(R.id.subs_product_list);

        String listSting = myOrderList.get(position).getSubscriptionProduct();
        JSONArray jpitem = null;
        try {
            jpitem = new JSONArray(listSting);
            productListArray = new ArrayList<>();
            for (int j = 0; j < jpitem.length(); j++) {

                MysubscriptionProductBean bean= new MysubscriptionProductBean();

                JSONObject obj=new JSONObject();
                JSONObject jData = jpitem.getJSONObject(j);
                bean.setName(jData.optString("ProductName"));
                bean.setQty(jData.optInt("Productqty"));
                productListArray.add(bean);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        product_list.setAdapter(new MyOrderProductAdapter(productListArray,(Activity)Mcontext));*/
        return convertView;
    }

}
