package com.latitude22.homemdopao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.latitude22.homemdopao.Bean.CategoryBean;
import com.latitude22.homemdopao.Bean.DrawerItemBean;
import com.latitude22.homemdopao.R;

import java.util.ArrayList;

/**
 * Created by vaio1 on 21-03-2018.
 */

public class CategoryAdapter extends BaseAdapter {
    ArrayList<CategoryBean> arrayList;
    Context Mcontext;

    public CategoryAdapter(ArrayList<CategoryBean> arrayList,Context Mcontext)
    {
        this.arrayList = arrayList;
        this.Mcontext = Mcontext;
    }

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
        convertView = inflater.inflate(R.layout.custom_category_product, null);

        TextView category_name = (TextView)  convertView.findViewById(R.id.category_name);
        category_name.setText(arrayList.get(position).getCategoryName());

        return convertView;
    }
}
