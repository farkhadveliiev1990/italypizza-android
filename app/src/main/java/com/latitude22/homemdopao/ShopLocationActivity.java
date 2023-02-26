package com.latitude22.homemdopao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ShopLocationActivity extends AppCompatActivity {

    double latitude,longitude;
    double latitude1,longitude1,latitude2,longitude2,latitude3,longitude3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_location);

        longitude3 = -9.200664;
        latitude3 = 38.754507;

        longitude2 = -9.125064;

        latitude2 = 38.728623;
        latitude1 = 38.738086;
        longitude1 = -9.122505;





         latitude = getIntent().getDoubleExtra("lat", 0);


         longitude = getIntent().getDoubleExtra("lng", 0);

        LatLng position = new LatLng(latitude, longitude);

       /* LatLng position1 = new LatLng(38.738086, -9.122505);

        LatLng position2 = new LatLng(38.728623, -9.125064);

        LatLng position3 = new LatLng(38.754507, -9.200664);*/
        // Instantiating MarkerOptions class
        MarkerOptions options = new MarkerOptions();
       /* MarkerOptions options1 = new MarkerOptions();
        MarkerOptions options2 = new MarkerOptions();
        MarkerOptions options3 = new MarkerOptions();*/
        // Setting position for the MarkerOptions

        options.position(position);
        //options1.position(position1);
        //options2.position(position2);
        //options3.position(position3);


        // Setting title for the MarkerOptions
       options.title("Homem Do Pao");
        /*options1.title("Homem Do Pão");
        options2.title("Homem Do Pão");
        options3.title("Homem Do Pão");*/

        // Setting snippet for the MarkerOptions
      //  options.snippet("Latitude:"+latitude+",Longitude:"+longitude);
        /*options1.snippet("Calçada da Picheleira 118A, 1900-381 Lisboa");
        options2.snippet("Av. Mouzinho de Albuquerque 36B, 1170-105 Lisboa");
        options3.snippet("R. dos Arneiros 87A, 1500-066 Lisboa");*/

        // Getting Reference to SupportMapFragment of activity_map.xml
        SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

        // Getting reference to google map
        //////////////////////////////////////////////////GoogleMap googleMap = fm.getMap();

        // Adding Marker on the Google Map
       /////////////////////////////////////////////////// googleMap.addMarker(options);
       /* googleMap.addMarker(options1);
        googleMap.addMarker(options2);
        googleMap.addMarker(options3);
*/
        // Creating CameraUpdate object for position
        CameraUpdate updatePosition = CameraUpdateFactory.newLatLng(position);
       /* CameraUpdate updatePosition1 = CameraUpdateFactory.newLatLng(position1);
        CameraUpdate updatePosition2 = CameraUpdateFactory.newLatLng(position2);
        CameraUpdate updatePosition3 = CameraUpdateFactory.newLatLng(position3);*/

        // Creating CameraUpdate object for zoom
        CameraUpdate updateZoom = CameraUpdateFactory.zoomBy(1);

        // Updating the camera position to the user input latitude and longitude
     //   googleMap.moveCamera(updatePosition);

        // Applying zoom to the marker position
     //   googleMap.animateCamera(updateZoom);

        ///////////////////////////////////////////////////googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
       /* googleMap.moveCamera(CameraUpdateFactory.newLatLng(position1));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position2));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position3));*/
            /////////////////////////////////////////googleMap.animateCamera(CameraUpdateFactory.zoomTo(14), 1000, null);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ShopLocationActivity.this,ThankYouShop.class);
        startActivity(i);
        finish();
    }
}
