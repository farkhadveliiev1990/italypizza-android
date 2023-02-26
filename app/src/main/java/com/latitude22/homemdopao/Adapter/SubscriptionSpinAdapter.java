package com.latitude22.homemdopao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.latitude22.homemdopao.Bean.DrawerItemBean;
import com.latitude22.homemdopao.Bean.SubscriptionBean;
import com.latitude22.homemdopao.R;

import java.util.ArrayList;

/**
 * Created by vaio1 on 29-03-2018.
 */

public class SubscriptionSpinAdapter extends BaseAdapter {

    ArrayList<SubscriptionBean> arrayList;
    Context mcontext;

    public SubscriptionSpinAdapter(ArrayList<SubscriptionBean> arrayList, Context mcontext) {
        this.arrayList = arrayList;
        Mcontext = mcontext;
    }

    Context Mcontext;

    @Override
    public int getCount() {
        return arrayList.size();
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
        LayoutInflater inflater = LayoutInflater.from(Mcontext);
        convertView = inflater.inflate(R.layout.subscription_spin_item, null);

        TextView tv1 = (TextView)  convertView.findViewById(R.id.subcription_name);
        tv1.setText(arrayList.get(position).getSubscriptionName());

        TextView tv2 = (TextView)  convertView.findViewById(R.id.no_of_dys);
        tv2.setText(arrayList.get(position).getNoofdays());


        return convertView;
    }
}
