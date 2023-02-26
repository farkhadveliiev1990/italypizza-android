package com.latitude22.homemdopao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.latitude22.homemdopao.Bean.DrawerItemBean;
import com.latitude22.homemdopao.R;

import java.util.ArrayList;

/**
 * Created by Anuved on 12-08-2016.
 */
public class DrawerItemAdapter extends BaseAdapter {
    ArrayList<DrawerItemBean> drawerItemBeans;
    Context Mcontext;

    public DrawerItemAdapter(ArrayList<DrawerItemBean> drawerItemBeans,Context Mcontext)
    {
     this.drawerItemBeans = drawerItemBeans;
        this.Mcontext = Mcontext;
    }

    @Override
    public int getCount() {
        return drawerItemBeans.size();
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
        convertView = inflater.inflate(R.layout.custom_drawer_item, null);

        TextView tv1 = (TextView)  convertView.findViewById(R.id.drawer_item_name);
        tv1.setText(drawerItemBeans.get(position).getItem_name());

        ImageView img1 = (ImageView)  convertView.findViewById(R.id.drawer_item_icon);
        img1.setImageResource(drawerItemBeans.get(position).getItem_icon());

        return convertView;
    }
}
