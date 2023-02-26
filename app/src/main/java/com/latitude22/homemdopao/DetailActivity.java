package com.latitude22.homemdopao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.norbsoft.typefacehelper.TypefaceHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    ActionBar actionBar;
    TextView detail_breadName, detail_price, detail_breaddescrip,header_name;
    ImageView detail_breadImg, header_img, cart;
    Button addcart;
    static Context Mcntxt;
    public int Quntity, Productid, cart_amount, cart_quantity, qnt, cart_Val;
    Double productPrice;
    String productName, productImage;
    public static TextView detail_cart_quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail);
        TypefaceHelper.typeface(this);
        Mcntxt = this;
        final TextView tv_detail_count = (TextView) findViewById(R.id.tv_detail_count);
        detail_cart_quantity = (TextView) findViewById(R.id.detail_cart_quantity);
        detail_cart_quantity.setText("" + QuntityTotal());
        addcart = (Button) findViewById(R.id.addcart);
        header_name = (TextView) findViewById(R.id.header_name);
        Button detail_minus_bt = (Button) findViewById(R.id.detail_minus_bt);

        Button detail_plus_bt = (Button) findViewById(R.id.detail_plus_bt);


        //  Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        //  setActionBar(tb);
        actionBar = getSupportActionBar();
        //  actionBar.hide();
        cart = (ImageView) findViewById(R.id.cart);
        header_img = (ImageView) findViewById(R.id.header_img);
        detail_breadName = (TextView) findViewById(R.id.detail_breadName);
        detail_price = (TextView) findViewById(R.id.detail_price);
        detail_breaddescrip = (TextView) findViewById(R.id.detail_breaddescrip);
        detail_breadImg = (ImageView) findViewById(R.id.detail_breadImg);

        LinearLayout toolbar_detail = (LinearLayout) findViewById(R.id.detail_header);
        // cart_amount = Integer.parseInt(String.valueOf(tv_detail_count.getText()));
        //  cart_quantity = Integer.parseInt(String.valueOf(detail_cart_quantity.getText()));
        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuntityTotal();
                AddCart addCart = new AddCart();
                Intent i = getIntent();
                cart_amount = Integer.parseInt(String.valueOf(tv_detail_count.getText()));
                cart_quantity = Integer.parseInt(String.valueOf(detail_cart_quantity.getText()));
                if (cart_amount == 0) {
                    Toast.makeText(DetailActivity.this, "Adicionar Item", Toast.LENGTH_SHORT).show();
                } else {
                    if (i != null) {
                        productName = i.getStringExtra("Productname");
                        productPrice = i.getDoubleExtra("Productprice", 0);
                        productImage = i.getStringExtra("ProductImage");
                        String productDescription = i.getStringExtra("ProductDescription");
                        Productid = i.getIntExtra("Productid", 0);
                        // Toast.makeText(DetailActivity.this, "Add to cart successfully", Toast.LENGTH_SHORT).show();
                        // tv_detail_count.setText("" + 0);
                    }
                    List<AddCart> s = new Select()
                            .from(AddCart.class)
                            .where("productId = ?", Productid)
                            .execute();
                    if (s.size() > 0) {
                        cart_Val = cart_amount + s.get(0).getProductquantity();
                        new Update(AddCart.class)
                                .set("productquantity = ?", cart_Val)
                                .where("productId = ?", Productid)
                                .execute();
                        //addCart.update();
                        // cart_Val = cart_amount + cart_quantity;
                        MainActivity.main_cart_quantity.setText("" + QuntityTotal());
                        // detail_cart_quantity.setText(""+QuntityTotal());
                        detail_cart_quantity.setText("" + QuntityTotal());
                        tv_detail_count.setText("" + 1);
                        Toast.makeText(DetailActivity.this, "Adicionado ao carrinho", Toast.LENGTH_SHORT).show();

                    } else {
                        addCart.productname = productName;
                        addCart.productquantity = cart_amount;
                        addCart.productprice = productPrice;
                        addCart.productId = Productid;
                        addCart.productImage = productImage;
                        addCart.save();
                        detail_cart_quantity.setText("" + QuntityTotal());
                        tv_detail_count.setText("" + 1);
                        Toast.makeText(DetailActivity.this, "Adicionado ao carrinho", Toast.LENGTH_SHORT).show();
                    }

                }
                    /*
                    if (i != null) {
                        String productName = i.getStringExtra("Productname");
                        Double productPrice = i.getDoubleExtra("Productprice", 0);
                        String productImage = i.getStringExtra("ProductImage");
                        String productDescription = i.getStringExtra("ProductDescription");
                        int Productid = i.getIntExtra("Productid",0);
                        addCart.productname = productName;
                        addCart.productquantity = Quntity;
                        addCart.productprice = productPrice;
                        addCart.productId = Productid;
                        addCart.productImage = productImage;
                        addCart.save();
                        Toast.makeText(DetailActivity.this, "Add to cart successfully", Toast.LENGTH_SHORT).show();
                        tv_detail_count.setText("" + 0);
                    }
                   */
            }

            // detail_cart_quantity.setText(""+QuntityTotal());

        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(DetailActivity.this, MyCart.class);
                startActivity(intent1);
                finish();
            }
        });
        header_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setdata();
        //detail_cart_quantity.setText(""+QuntityTotal());
        detail_plus_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int values = Integer.parseInt(String.valueOf(tv_detail_count.getText()));
                int quantity = values + 1;
                Quntity = quantity;
                tv_detail_count.setText("" + quantity);
            }
        });
        detail_minus_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int values = Integer.parseInt(String.valueOf(tv_detail_count.getText()));
                int quantity = values - 1;
                if (quantity >= 0) {
                    tv_detail_count.setText("" + quantity);
                    Quntity = quantity;
                }
            }
        });
    }
    public void setdata() {
        Intent i = getIntent();
        if (i != null) {

            String productName = i.getStringExtra("Productname");
            Double productPrice = i.getDoubleExtra("Productprice", 0);
            String productImage = i.getStringExtra("ProductImage");
            String productDescription = i.getStringExtra("ProductDescription");
            detail_breadName.setText(productName);
            detail_price.setText("" + productPrice);
            detail_breaddescrip.setText(productDescription);
            header_name.setText(""+productName);

            try {
                if (productImage != null & !productImage.equals("")) {

                    Glide.with(this).load(productImage)
                            .placeholder(R.drawable.bread_images)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(detail_breadImg);
                  //  Picasso.with(this).load(productImage).error(R.drawable.bread_images).placeholder(R.drawable.bread_images).into(detail_breadImg);
                }

            } catch (Exception ae) {

            }
        }
    }

    public int QuntityTotal() {
        int total = 0;
        ArrayList<AddCart> addCarts = (ArrayList<AddCart>) AddCart.AddCart();
        for (int i = 0; i < addCarts.size(); i++) {
            total = total + (addCarts.get(i).getProductquantity());
        }
        MainActivity.main_cart_quantity.setText("" + total);
        return total;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
