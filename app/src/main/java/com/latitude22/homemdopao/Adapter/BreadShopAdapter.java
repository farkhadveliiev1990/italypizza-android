package com.latitude22.homemdopao.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.latitude22.homemdopao.AddCart;
import com.latitude22.homemdopao.Bean.BreadShopData;
import com.latitude22.homemdopao.MainActivity;
import com.latitude22.homemdopao.R;
import com.norbsoft.typefacehelper.TypefaceHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anuved on 08-08-2016.
 */
public class BreadShopAdapter extends BaseAdapter {

    Context Mcontext;
    ArrayList<BreadShopData> breadShopList = new ArrayList<>();
    public int cart_amount,cart_quantity,cart_Val;


    public BreadShopAdapter(ArrayList<BreadShopData> breadShopList, Context mcontext) {
        this.breadShopList = breadShopList;
        Mcontext = mcontext;
        Log.i("StoreResult ", " BreadShopAdapter "+breadShopList.size());
    }

    @Override
    public int getCount() {
        return breadShopList.size();
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

        convertView = layoutInflater.inflate(R.layout.featuredlistitem, null);

        TypefaceHelper.typeface(convertView);
        ImageView item_image =(ImageView)convertView .findViewById(R.id.tab1_breadimg);
        //item_image.setImageResource(Integer.parseInt(breadShopList.get(position).getProduct_Image()));
        final TextView list_text=(TextView)convertView .findViewById(R.id.tab1_breadname);
        list_text.setText(breadShopList.get(position).getProduct_name());

        TextView list_price=(TextView)convertView .findViewById(R.id.tv_bread_price);
        list_price.setText(breadShopList.get(position).getProduct_Price().toString());

        try{
            if (breadShopList.get(position).getProduct_Image()!=null&!breadShopList.get(position).getProduct_Image().equals("")){


                Glide.with(Mcontext).load(breadShopList.get(position).getProduct_Image())
                        .placeholder(R.drawable.bread_images)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(item_image);
             //   Picasso.with(Mcontext).load(breadShopList.get(position).getProduct_Image()).error(R.drawable.bread_images).placeholder(R.drawable.bread_images).into(item_image);
            }

        }catch (Exception ae){

        }
        final TextView tv_detail_count = (TextView) convertView.findViewById(R.id.tv_detail_count);
        tv_detail_count.setText("" +1);
       // TextView main_cart_quantity = (TextView) convertView.findViewById(R.id.main_cart_quantity);

        Button detail_minus_bt = (Button) convertView.findViewById(R.id.detail_minus_bt);

        Button detail_plus_bt = (Button) convertView.findViewById(R.id.detail_plus_bt);

        Button bt_addToCart = (Button)convertView.findViewById(R.id.bt_addToCart);
        detail_plus_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               int values = Integer.parseInt(String.valueOf(tv_detail_count.getText()));
                int quantity = values+1;
            tv_detail_count.setText(""+quantity);
        }
        });

        detail_minus_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int values = Integer.parseInt(String.valueOf(tv_detail_count.getText()));
                int quantity = values-1;
                if(quantity>=1) {
                    tv_detail_count.setText(""+quantity);
                }
            }
        });

        final AddCart addCart = new AddCart();
        bt_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               cart_amount = Integer.parseInt(String.valueOf(tv_detail_count.getText()));
                cart_quantity = Integer.parseInt(String.valueOf(MainActivity.main_cart_quantity.getText()));
              //  QuntityTotal();
                if(cart_amount==0) {
                Toast.makeText(Mcontext,"Adicionar Produto",Toast.LENGTH_SHORT).show();
                }
              /*  {
                    List<AddCart> s = new Select()
                            .from(AddCart.class)
                            .where(productId + " = ?", addCart.productId)
                            .execute();
                }*/
                else {
                    List<AddCart> s = new Select()
                            .from(AddCart.class)
                            .where("productId = ?",breadShopList.get(position).getProduct_id())
                            .execute();
                    if (s.size()>0){
                        cart_Val = cart_amount + s.get(0).getProductquantity();
                        new Update(AddCart.class)
                                .set("productquantity = ?", cart_Val)
                                .where("productId = ?", breadShopList.get(position).getProduct_id())
                                .execute();
                     //addCart.update();
                    }else {
                        addCart.productname = breadShopList.get(position).getProduct_name();
                        addCart.productquantity = cart_amount;
                        addCart.productprice = breadShopList.get(position).getProduct_Price();
                        addCart.productId = breadShopList.get(position).getProduct_id();
                        addCart.productImage = breadShopList.get(position).getProduct_Image();
                        addCart.save();
                    }
                   // int cart_Value = cart_amount +cart_quantity;
                    MainActivity.main_cart_quantity.setText(""+QuntityTotal());
//                  DetailActivity.detail_cart_quantity.setText(""+QuntityTotal());
                    tv_detail_count.setText(""+1);
                    Toast.makeText(Mcontext,"Adicionado ao carrinho",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return convertView;
    }
 /*   public double QuntityTotal(){
        int  total=0;
        ArrayList<AddCart>  addCarts= (ArrayList<AddCart>) AddCart.AddCart();
        for(int i=0;i<addCarts.size();i++){
            total =total+(addCarts.get(i).getProductquantity());
        }
        MainActivity.main_cart_quantity.setText("" +total);
        return total;
    }
*/
    public int QuntityTotal(){
        int  total=0;
        ArrayList<AddCart>  addCarts= (ArrayList<AddCart>) AddCart.AddCart();
        for(int i=0;i<addCarts.size();i++){
            total =total+(addCarts.get(i).getProductquantity());
        }
        MainActivity.main_cart_quantity.setText(""+total);
        return total;
    }
}
