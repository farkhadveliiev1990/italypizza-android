package com.latitude22.homemdopao;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.norbsoft.typefacehelper.TypefaceHelper;

import java.io.IOException;
import java.util.List;

public class ThankYouShop extends AppCompatActivity {
    ImageView header_img,goDirection;
    Button pay_shop_finish;
    Context mContext;
    Double shop_lat,shop_long;
    double latitude1,longitude1,latitude2,longitude2,latitude3,longitude3;
    TextView location_one;
    String shop_location_one,shop_location_two,shop_location_three;
    LinearLayout layoutDirection_one,layoutDirection_two,layoutDirection_three;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you_shop);
        TypefaceHelper.typeface(this);
        header_img = (ImageView)findViewById(R.id.header_img);
       // goDirection = (ImageView) findViewById(R.id.goDirection);
        pay_shop_finish = (Button) findViewById(R.id.pay_shop_finish);
        layoutDirection_one = (LinearLayout) findViewById(R.id.layoutDirection_one);
        layoutDirection_two = (LinearLayout) findViewById(R.id.layoutDirection_two);
        layoutDirection_three = (LinearLayout) findViewById(R.id.layoutDirection_three);

        longitude3 = -9.200664;
        latitude3 = 38.754507;

        longitude2 = -9.125064;

        latitude2 = 38.728623;
        latitude1 = 38.738086;
        longitude1 = -9.122505;
        header_img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(ThankYouShop.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        shop_location_one = "Calçada da Picheleira 118A, 1900-381 Lisboa, Portugal";
        shop_location_two = "Av. Mouzinho de Albuquerque 36B, 1170-105 Lisboa, Portugal";
        shop_location_three = "R. dos Arneiros 87A, 1500-066 Lisboa, Portugal";

       // getLocationFromAddress(mContext,shop_location_one.replace("\r\n", "").trim());
        /*Toast.makeText(mContext, ""+shop_lat, Toast.LENGTH_LONG).show();
        Toast.makeText(mContext, ""+shop_long, Toast.LENGTH_LONG).show();*/
        layoutDirection_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  getLocationFromAddress(mContext,shop_location_one.replace("\r\n", "").trim());

                Intent i = new Intent(ThankYouShop.this,ShopLocationActivity.class);
                // Passing latitude and longitude to the MapActivity

                i.putExtra("lat",+latitude1);
                i.putExtra("lng",+longitude1);

                /*Toast.makeText(mContext, ""+shop_lat, Toast.LENGTH_LONG).show();
                Toast.makeText(mContext, ""+shop_long, Toast.LENGTH_LONG).show();*/
                startActivity(i);
                finish();
            }
        });

        layoutDirection_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // getLocationFromAddress(mContext,shop_location_two.replace("\r\n", "").trim());
                Intent i = new Intent(ThankYouShop.this,ShopLocationActivity.class);
                // Passing latitude and longitude to the MapActiv


                i.putExtra("lat", +latitude2);
                i.putExtra("lng", +longitude2);

               /* Toast.makeText(mContext, ""+shop_lat, Toast.LENGTH_LONG).show();
                Toast.makeText(mContext, ""+shop_long, Toast.LENGTH_LONG).show();*/
                startActivity(i);
                finish();

            }
        });

        layoutDirection_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // getLocationFromAddress(mContext,shop_location_three.replace("\r\n", "").trim());
                Intent i = new Intent(ThankYouShop.this,ShopLocationActivity.class);
                // Passing latitude and longitude to the MapActiv


                i.putExtra("lat", +latitude3);
                i.putExtra("lng", +longitude3);

                /*Toast.makeText(mContext, ""+shop_lat, Toast.LENGTH_LONG).show();
                Toast.makeText(mContext, ""+shop_long, Toast.LENGTH_LONG).show();*/
                startActivity(i);
                finish();

            }
        });
        pay_shop_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ThankYouShop.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }


/*    public void getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        LatLng p1 = null;

        strAddress = strAddress.replace("(\\r|\\n)", "");

        try {
            // May throw an IOException
            List<Address> address = coder.getFromLocationName(strAddress, 5);
            if (address == null && address.size() < 0) {
                Toast.makeText(context, "Morada não encontrada", Toast.LENGTH_SHORT).show();;
            }
            android.location.Address location = address.get(0);
            shop_lat = location.getLatitude();
            shop_long = location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        }
        catch (IOException ex) {

            ex.printStackTrace();
        }
    }*/
}
