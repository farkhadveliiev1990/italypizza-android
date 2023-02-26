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
import com.latitude22.homemdopao.Bean.HistoryProductBean;
import com.latitude22.homemdopao.Bean.MysubscriptionProductBean;
import com.latitude22.homemdopao.R;
import com.norbsoft.typefacehelper.TypefaceHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.latitude22.homemdopao.R.id.fixedplan_breadimg;

/**
 * Created by vaio1 on 25-11-2017.
 */

public class HistoryProductAdapter extends BaseAdapter {

    Context Mcontext;
    ArrayList<HistoryProductBean> historyProductBeanArrayList;

    public HistoryProductAdapter(ArrayList<HistoryProductBean> historyProductBeanArrayList , Context mcontext) {
        this.historyProductBeanArrayList  = historyProductBeanArrayList;
        Mcontext = mcontext;
    }


    @Override
    public int getCount() {
        return historyProductBeanArrayList.size();
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


        convertView = layoutInflater.inflate(R.layout.history_product_list_item, null);

        TypefaceHelper.typeface(convertView);

        ImageView product_img = (ImageView)convertView.findViewById(R.id.product_img_history);

        try {
            if (historyProductBeanArrayList.get(position).getProductImage() != null & !historyProductBeanArrayList.get(position).getProductImage().equals("")) {


                Glide.with(Mcontext).load(historyProductBeanArrayList.get(position).getProductImage())
                        .placeholder(R.drawable.bread_images)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(product_img);
                //  Picasso.with(Mcontext).load(historyProductBeanArrayList.get(position).getProductImage()).error(R.drawable.bread_images).placeholder(R.drawable.bread_images).into(product_img);
            }

        } catch (Exception ae) {

        }

        TextView product_name=(TextView)convertView .findViewById(R.id.product_name);
        product_name.setText(historyProductBeanArrayList.get(position).getProductName());

        TextView prdct_price=(TextView)convertView .findViewById(R.id.prdct_price);
        prdct_price.setText(historyProductBeanArrayList.get(position).getProductPrice());

        TextView prdct_qty=(TextView)convertView .findViewById(R.id.prdct_qty);
        prdct_qty.setText(""+historyProductBeanArrayList.get(position).getProductQty());

        return convertView;
    }
}
