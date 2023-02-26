package com.latitude22.homemdopao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.latitude22.homemdopao.Bean.MyOrderListBean;
import com.latitude22.homemdopao.Bean.MyorderProductBean;
import com.latitude22.homemdopao.Bean.MysubscriptionProductBean;
import com.latitude22.homemdopao.R;
import com.norbsoft.typefacehelper.TypefaceHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vaio1 on 17-11-2017.
 */

public class MyOrderProductAdapter extends BaseAdapter {

    Context Mcontext;
    ArrayList<MysubscriptionProductBean> myOrderproductList;

    public MyOrderProductAdapter(ArrayList<MysubscriptionProductBean> myOrderList , Context mcontext) {
        this.myOrderproductList  = myOrderList;
        Mcontext = mcontext;
    }


    @Override
    public int getCount() {
        return myOrderproductList.size();
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


        convertView = layoutInflater.inflate(R.layout.myorderproduct_list, null);

        TypefaceHelper.typeface(convertView);

        TextView name=(TextView)convertView .findViewById(R.id.product_name);
        name.setText(myOrderproductList.get(position).getName());

        TextView product_price=(TextView)convertView .findViewById(R.id.product_price);
        product_price.setText(myOrderproductList.get(position).getProductPrice());

        TextView qty=(TextView)convertView .findViewById(R.id.product_qty);
        qty.setText(""+myOrderproductList.get(position).getQty());

        ImageView product_img = (ImageView) convertView.findViewById(R.id.product_img);

        try{
            if (myOrderproductList.get(position).getProductImage()!=null&!myOrderproductList.get(position).getProductImage().equals("")){

                Glide.with(Mcontext).load(myOrderproductList.get(position).getProductImage())
                        .placeholder(R.drawable.bread_images)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(product_img);
                // Picasso.with(Mcontext).load(myOrderproductList.get(position).getProductImage()).error(R.drawable.bread_images).placeholder(R.drawable.bread_images).into(product_img);
            }

        }
        catch (Exception ae){

        }

        return convertView;
    }
}
