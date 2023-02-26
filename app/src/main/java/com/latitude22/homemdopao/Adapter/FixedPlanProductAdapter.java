package com.latitude22.homemdopao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.latitude22.homemdopao.Bean.FixedPlanProductBean;
import com.latitude22.homemdopao.Bean.FixedplanBean;
import com.latitude22.homemdopao.R;

import java.util.ArrayList;

/**
 * Created by Anuved on 26-08-2016.
 */
public class FixedPlanProductAdapter extends BaseAdapter {


    Context Mcontext;
    ArrayList<FixedPlanProductBean> fixedplanlist;

    public FixedPlanProductAdapter( ArrayList<FixedPlanProductBean> fixedplanlist , Context mcontext) {
        this.fixedplanlist  = fixedplanlist;
        Mcontext = mcontext;
    }


    @Override
    public int getCount() {
        return fixedplanlist.size();
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


        convertView = layoutInflater.inflate(R.layout.fixedplan_detail_productlist, null);

        TextView tv_productname=(TextView)convertView .findViewById(R.id.tv_productname);
        tv_productname.setText( fixedplanlist.get(position).getFixedProductName());

        TextView product_price=(TextView)convertView .findViewById(R.id.product_price);
        product_price.setText( ""+fixedplanlist.get(position).getFixedProductPrice());

        return convertView;
    }
}
