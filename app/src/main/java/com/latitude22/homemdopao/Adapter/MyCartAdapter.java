package com.latitude22.homemdopao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Update;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.latitude22.homemdopao.AddCart;
import com.latitude22.homemdopao.MainActivity;
import com.latitude22.homemdopao.MyCart;
import com.latitude22.homemdopao.R;
import com.latitude22.homemdopao.UtilityMethod;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vaio1 on 24-10-2017.
 */

public class MyCartAdapter extends BaseAdapter {

    ArrayList<AddCart> addCarts;
    Context Mcontext;

    public MyCartAdapter(ArrayList<AddCart> addCarts, Context Mcontext) {
        this.addCarts = addCarts;
        this.Mcontext = Mcontext;
    }

    @Override
    public int getCount() {
        return addCarts.size();
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


        convertView = layoutInflater.inflate(R.layout.mycartitem_list, null);

        ImageView payment_bread_img = (ImageView) convertView.findViewById(R.id.payment_cart_breadimg);

        try {
            if (addCarts.get(position).getProductImage() != null & !addCarts.get(position).getProductImage().equals("")) {


                Glide.with(Mcontext).load(addCarts.get(position).getProductImage())
                        .placeholder(R.drawable.bread_images)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(payment_bread_img);
                //  Picasso.with(Mcontext).load(addCarts.get(position).getProductImage()).error(R.drawable.bread_images).placeholder(R.drawable.bread_images).into(payment_bread_img);
            }

        } catch (Exception ae) {

        }

        TextView bread_name = (TextView) convertView.findViewById(R.id.payment_cart_breadname);
        bread_name.setText(addCarts.get(position).getProductname());

        //   EditText et_payment_qty = (EditText) convertView.findViewById(R.id.et_payment_qty);


     /*   TextView payment_item_price = (TextView) convertView.findViewById(R.id.payment_item_price);
        if (UtilityMethod.isStringNullOrBlank(String.valueOf(addCarts.get(position).getProductprice()))) {
            payment_item_price.setText("-");
        } else {
            payment_item_price.setText("" + addCarts.get(position).getProductprice());
        }*/

        TextView unit_price = (TextView) convertView.findViewById(R.id.unit_price);
        if (UtilityMethod.isStringNullOrBlank(String.valueOf(addCarts.get(position).getUnitprice()))) {
            unit_price.setText("-");
        } else {
            unit_price.setText("" + addCarts.get(position).getUnitprice());
        }
        // unit_price.setText(""+addCarts.get(position).getUnitprice());
        final TextView tv_detail_count = (TextView) convertView.findViewById(R.id.tv_detail_count);
        // TextView main_cart_quantity = (TextView) convertView.findViewById(R.id.main_cart_quantity);

        Button detail_minus_bt = (Button) convertView.findViewById(R.id.detail_minus_bt);

        Button detail_plus_bt = (Button) convertView.findViewById(R.id.detail_plus_bt);
       final TextView payment_net_amount = (TextView) convertView.findViewById(R.id.payment_net_amount);

        ImageView img_delete = (ImageView) convertView.findViewById(R.id.img_cart_delete);
        TextView tv_remove = (TextView) convertView.findViewById(R.id.tv_remove);
        tv_detail_count.setText("" + addCarts.get(position).getProductquantity());
        int dd = (int) (addCarts.get(position).getUnitprice() * addCarts.get(position).getProductquantity());
        payment_net_amount.setText("" + dd);

     /*   ImageView img_delete = (ImageView)convertView.findViewById(R.id.img_cart_delete);
        tv_detail_count.setText(""+addCarts.get(position).getProductquantity());
        int dd= (int) (addCarts.get(position).getProductprice()*addCarts.get(position).getProductquantity());
        payment_net_amount.setText(""+dd);*/
        tv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Delete().from(AddCart.class).where("cartId = ?", addCarts.get(position).getId()).execute();
                addCarts.remove(position);
                notifyDataSetChanged();
                QuntityTotal();
                PriceTotal(addCarts);
            }
        });

        detail_plus_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int values = Integer.parseInt(String.valueOf(tv_detail_count.getText()));
                int quantity = values + 1;
                tv_detail_count.setText("" + quantity);

                new Update(AddCart.class)
                        .set("productquantity = ?", quantity)
                        .where("productId = ?", addCarts.get(position).getProductId())
                        .execute();
                QuntityTotal();
                int dd = (int) (addCarts.get(position).getUnitprice() * addCarts.get(position).getProductquantity());
               payment_net_amount.setText("" + dd);
                PriceTotal(addCarts);
            }
        });

        detail_plus_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int values = Integer.parseInt(String.valueOf(tv_detail_count.getText()));
                int quantity = values+1;
                tv_detail_count.setText("" + quantity);

                new Update(AddCart.class)
                        .set("productquantity = ?",quantity)
                        .where("productId = ?", addCarts.get(position).getProductId())
                        .execute();
                QuntityTotal();
                int dd= (int) (addCarts.get(position).getUnitprice()*addCarts.get(position).getProductquantity());
                payment_net_amount.setText("" + dd);
                PriceTotal(addCarts);
            }
        });
        return convertView;
  }
    public double QuntityTotal(){
        int  total=0;
        ArrayList<AddCart>  addCarts= (ArrayList<AddCart>) AddCart.AddCart();
        for(int i=0;i<addCarts.size();i++){
            total =total+(addCarts.get(i).getProductquantity());
        }
        MainActivity.main_cart_quantity.setText("" + total);
        return  total;
    }
    public double PriceTotal(ArrayList<AddCart> Cartlist){
        double  total=0.0;
        for(int i=0;i<Cartlist.size();i++){
            total =total+(Cartlist.get(i).getUnitprice()*Cartlist.get(i).getProductquantity());
        }
        MyCart.cart_total_amount.setText("" + total);
        return  total;

    }
}
