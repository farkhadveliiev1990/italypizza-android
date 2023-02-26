package com.latitude22.homemdopao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.latitude22.homemdopao.Bean.CalenderCustomProductBean;
import com.latitude22.homemdopao.Bean.CalenderProductBean;
import com.latitude22.homemdopao.Bean.MysubscriptionProductBean;
import com.latitude22.homemdopao.R;
import com.norbsoft.typefacehelper.TypefaceHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vaio1 on 05-12-2017.
 */

public class CalenderSubscriptionProductAdapter extends BaseAdapter {
    Context Mcontext;
    ArrayList<CalenderProductBean> productListArray;

    public CalenderSubscriptionProductAdapter(ArrayList<CalenderProductBean> productListArray , Context mcontext) {
        this.productListArray  = productListArray;
        Mcontext = mcontext;
    }


    @Override
    public int getCount() {
        return productListArray.size();
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


        convertView = layoutInflater.inflate(R.layout.calender_subscription_product_list, null);

        TypefaceHelper.typeface(convertView);

        TextView name=(TextView)convertView .findViewById(R.id.name);
        name.setText(productListArray.get(position).getProductName());

        TextView qty=(TextView)convertView .findViewById(R.id.qty);
        qty.setText(productListArray.get(position).getProductQty());

        return convertView;
    }
}
