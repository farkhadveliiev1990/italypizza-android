package com.latitude22.homemdopao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.latitude22.homemdopao.AddCart;
import com.latitude22.homemdopao.AddToCartActivity;
import com.latitude22.homemdopao.MainActivity;
import com.latitude22.homemdopao.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Anuved on 18-11-2016.
 */
public class AddToCartActivityAdapter  extends BaseAdapter {

    ArrayList<AddCart> addCarts;
    AddCart cartTable;
    Context contxt;
    public AddToCartActivityAdapter(ArrayList<AddCart> addCarts,Context contxt)
    {
        this.addCarts = addCarts;
        this.contxt = contxt;
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

        LayoutInflater layoutInflater = LayoutInflater.from(contxt);


        convertView = layoutInflater.inflate(R.layout.add_to_fragmentlist, null);

        TextView bread_name=(TextView)convertView .findViewById(R.id.cart_breadname);
        bread_name.setText(addCarts.get(position).getProductname());

        TextView quantity = (TextView)convertView.findViewById(R.id.bread_quantity);
        quantity.setText(""+addCarts.get(position).getProductquantity());

        ImageView img_delete = (ImageView)convertView.findViewById(R.id.img_cart_delete);
        ImageView cart_image = (ImageView)convertView.findViewById(R.id.cart_breadimg);

        try{
            if (addCarts.get(position).getProductImage()!=null&!addCarts.get(position).getProductImage().equals("")){

                Glide.with(contxt).load(addCarts.get(position).getProductImage())
                        .placeholder(R.drawable.bread_images)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(cart_image);
              //  Picasso.with(contxt).load(addCarts.get(position).getProductImage()).error(R.drawable.bread_images).placeholder(R.drawable.bread_images).into(cart_image);
            }

        }catch (Exception ae){

        }
        cartTable = new AddCart();
        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Delete().from(AddCart.class).where("cartId = ?", addCarts.get(position).getId()).execute();
                addCarts.remove(position);
                notifyDataSetChanged();
              //  AddToCartFragment frg= new AddToCartFragment();
              //  if (frg!=null){
               //    frg.PriceTotal(addCarts);
              //  }
                AddToCartActivity activity = new AddToCartActivity();
                if (activity!=null) {
                    activity.PriceTotal(addCarts);
                }
                QuntityTotal();
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
        MainActivity.main_cart_quantity.setText("" +total);
        return  total;
    }
}

