package com.latitude22.homemdopao.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.ParseException;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.latitude22.homemdopao.Bean.HistoryBean;
import com.latitude22.homemdopao.Bean.HistoryProductBean;
import com.latitude22.homemdopao.Bean.MyOrderListBean;
import com.latitude22.homemdopao.Bean.MysubscriptionProductBean;
import com.latitude22.homemdopao.R;
import com.norbsoft.typefacehelper.TypefaceHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by vaio1 on 24-11-2017.
 */

public class HistoryAdapter extends BaseAdapter {
    Context Mcontext;
    ArrayList<HistoryBean> historyArraylist;
    ArrayList<HistoryProductBean> productListArray ;
    Date date;
    String Startdate;
    public HistoryAdapter( ArrayList<HistoryBean> historyArraylist , Context mcontext) {
        this.historyArraylist  = historyArraylist;
        Mcontext = mcontext;
    }




    @Override
    public int getCount() {
        return historyArraylist.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(Mcontext);


        convertView = layoutInflater.inflate(R.layout.history_list_item, null);

        TypefaceHelper.typeface(convertView);

        TextView txt_subsc_status=(TextView)convertView .findViewById(R.id.txt_subsc_status);
        txt_subsc_status.setText(historyArraylist.get(position).getOrderCurrentStatus());


        TextView txt_item_no=(TextView)convertView .findViewById(R.id.txt_item_no);
        txt_item_no.setText("" + historyArraylist.get(position).getSubscriptionCount());

        TextView txt_order_id=(TextView)convertView .findViewById(R.id.txt_order_id);
        txt_order_id.setText("" + historyArraylist.get(position).getOrderId());


        TextView txt_subsc_name=(TextView)convertView .findViewById(R.id.txt_subsc_name);
        txt_subsc_name.setText(historyArraylist.get(position).getSubscriptionName());

       // Toast.makeText(Mcontext,"Subscription name"+historyArraylist.get(position).getSubscriptionName(),Toast.LENGTH_LONG).show();

        TextView txt_subsc_cost=(TextView)convertView .findViewById(R.id.txt_subsc_cost);
        txt_subsc_cost.setText(historyArraylist.get(position).getOrderCost());

       // Toast.makeText(Mcontext,"cost"+historyArraylist.get(position).getSubscriptionCost(),Toast.LENGTH_LONG).show();
        //txt_subsc_cost.setText(historyArraylist.get(position).getOrderCost());

        TextView txt_delivery=(TextView)convertView .findViewById(R.id.txt_delivery);
        txt_delivery.setText(historyArraylist.get(position).getNoOfDelivery());


        TextView txt_start_date=(TextView)convertView .findViewById(R.id.txt_start_date);
        txt_start_date.setText(historyArraylist.get(position).getStartDate());


        LinearLayout layout_payment = (LinearLayout)convertView.findViewById(R.id.layout_payment);

        TextView pymnet_status=(TextView)convertView .findViewById(R.id.pymnet_status);
        pymnet_status.setText(historyArraylist.get(position).getOrderPymentStatus());

        if(historyArraylist.get(position).getOrderPymentStatus().equals("Pendente"))
        {
            layout_payment.setVisibility(View.VISIBLE);
        }
        else
        {
            layout_payment.setVisibility(View.GONE);
        }

        if(historyArraylist.get(position).getOrderCurrentStatus().equals("Continuar"))
        {
            txt_subsc_status.setTextColor(ContextCompat.getColor(Mcontext,R.color.green));
        }
        if(historyArraylist.get(position).getOrderCurrentStatus().equals("Completa"))
        {
            txt_subsc_status.setTextColor(ContextCompat.getColor(Mcontext,R.color.orange));
        }
        if(historyArraylist.get(position).getOrderCurrentStatus().equals("Inátivo"))
        {
            txt_subsc_status.setTextColor(ContextCompat.getColor(Mcontext,R.color.red));
        }
        return convertView;
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

    }
}
