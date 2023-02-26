package com.latitude22.homemdopao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.latitude22.homemdopao.Bean.FixedplanBean;
import com.latitude22.homemdopao.Bean.MyOrderBean;
import com.latitude22.homemdopao.Bean.MyOrderListBean;
import com.latitude22.homemdopao.R;
import com.norbsoft.typefacehelper.TypefaceHelper;

import java.util.ArrayList;

/**
 * Created by Anuved on 27-08-2016.
 */
public class MyOrderAdapter extends BaseAdapter {

    Context Mcontext;
    ArrayList<MyOrderListBean> Myorderlist;

    public MyOrderAdapter( ArrayList<MyOrderListBean> Myorderlist , Context mcontext) {
        this.Myorderlist  = Myorderlist;
        Mcontext = mcontext;
    }
    @Override
    public int getCount() {
        return Myorderlist.size();
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


        convertView = layoutInflater.inflate(R.layout.myorder_list, null);

        TypefaceHelper.typeface(convertView);

  /*      TextView tv_productname=(TextView)convertView .findViewById(R.id.prdct_name);
        tv_productname.setText(Myorderlist.get(position).getProductName());

        TextView prdct_qty=(TextView)convertView .findViewById(R.id.txt_pendingNo);
        prdct_qty.setText( Myorderlist.get(position).getQty());*/

        return convertView;
    }
}
