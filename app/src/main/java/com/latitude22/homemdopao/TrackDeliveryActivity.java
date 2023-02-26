package com.latitude22.homemdopao;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.latitude22.homemdopao.Service.MapLocationService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.defaultValue;

public class TrackDeliveryActivity extends AppCompatActivity {
    String deliveryboyid, user_address;
    Intent i;
    private InternalBroadcastreciver receiver;
    public static final String MY_INTERNAL_BROADCAST = "com.latitude22.homemdopao.TrackDeliveryActivity.InternalBroadcastreciver";
    Fragment fragment = null;
    static android.support.v4.app.FragmentManager fragmentManager;
    GoogleMap googleMap = null;
    Double delvry_boy_lat, delvry_boy_long, user_lat, user_long;
    // AddressResultReceiver mResultReceiver;
    SessionManager sm;
    Double slat, slng;
    String s;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_delivery);
        startService(new Intent(getBaseContext(), MapLocationService.class));
        sm = new SessionManager(TrackDeliveryActivity.this);
        user_address = sm.getuseraddress();


       // startService(new Intent(getBaseContext(), MapLocationService.class));
        GetLocation();

        //  mResultReceiver = new AddressResultReceiver(null);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                Log.e("map", " Get google map -- " + map);
                googleMap = map;
            }
        });

        receiver = new InternalBroadcastreciver();
        IntentFilter filter1 = new IntentFilter(MY_INTERNAL_BROADCAST);
        registerReceiver(receiver, filter1);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);


      //  Toast.makeText(this, "" + user_address, Toast.LENGTH_SHORT).show();

        String user_custom_address = "303,anmol tower,above cakes 365,greater kailash road,old plasia,indore";
        user_address = user_address.replace("\r\n", "").trim();
        getLocationFromAddress(this,user_address);
      //  Toast.makeText(this, "UserAdd"+user_address, Toast.LENGTH_SHORT).show();

       // Toast.makeText(this, "TrcakDelivery" + delvry_boy_lat + "" + delvry_boy_long, Toast.LENGTH_LONG).show();
       // Toast.makeText(this, "UserlatLong" + user_lat + "" + user_lat, Toast.LENGTH_LONG).show();
        if (delvry_boy_lat == null || delvry_boy_long == null || user_lat == null || user_long == null)
        {

        } else {
            hideProgress();
            s = makeURL(delvry_boy_lat, delvry_boy_long, user_lat, user_long);
            MapSearch(s);
            LatLng destination = new LatLng(user_lat, user_long);
            LatLng source = new LatLng(delvry_boy_lat, delvry_boy_long);
            setUpMap(destination,source);
        }
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {

            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
        }
    }
    private void setUpMap(LatLng Destination, LatLng Source) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if(googleMap != null){
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setScrollGesturesEnabled(true);
            googleMap.getUiSettings().setTiltGesturesEnabled(true);
            googleMap.getUiSettings().setRotateGesturesEnabled(true);

            googleMap.addMarker(new MarkerOptions()
                    .position(Destination)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_user_marker))
            );
            googleMap.addMarker(new MarkerOptions()
                    .position(Source)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_map_icon))
            );
            CameraPosition camPos = new CameraPosition.Builder()
                    .target(getCenterCoordinate())
                    .zoom(14)
                    .build();
            CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
            googleMap.animateCamera(camUpd3);
        }
    }

    public LatLng getCenterCoordinate(){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(new LatLng(user_lat, user_long));
        builder.include(new LatLng(delvry_boy_lat, delvry_boy_long));
        LatLngBounds bounds = builder.build();
        return bounds.getCenter();
    }
    private void setMarker(LatLng latLng) {

        // googleMap.clear();
        // Toast.makeText(MainActivity.this,""+longitude+""+latitude,Toast.LENGTH_LONG).show();
       // googleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        //  googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        googleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_map_icon)).anchor(0.5f, 0.5f));

        /*CameraPosition cameraPosition = new CameraPosition.Builder()
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/

       // googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,14.0f));
       /* googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(14), 1000, null);*/
       //  googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }


    private String getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this);

        String address = "";
        try {
            address = geocoder
                    .getFromLocation(latLng.latitude, latLng.longitude, 1)
                    .get(0).getAddressLine(0);
        } catch (IOException e) {
        }

        return address;
    }
    public String makeURL(double sourcelat, double sourcelog, double destlat, double destlog) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString.append(Double.toString(sourcelog));
        urlString.append("&destination=");// to
        urlString.append(Double.toString(destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("&key=AIzaSyA6grFFyHeDcQgZBwsyqw6BQdr6YVDfNU8");
        return urlString.toString();
    }

    public void drawPath(String result) {
        try {
            Log.e("map", "path: " + result);
            //Tranform the string into a json object
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);
            Polyline line = googleMap.addPolyline(new PolylineOptions()
                    .addAll(list)
                    .width(10)
                    .color(Color.parseColor("#05b1fb"))//Google maps blue color
                    .geodesic(true)
            );
        } catch (JSONException e) {
            //   Toast.makeText(TrackDeliveryActivity.this, "" + e, Toast.LENGTH_LONG).show();
        }
    }

    public void MapSearch(String url) {
        Ion.with(this).load(url)

                .asString().setCallback(new FutureCallback<String>() {

            @Override
            public void onCompleted(Exception e, String result) {

                if (e == null) {
                    try {
                        drawPath(result);

                    } catch (Exception e1) {

                        //Toast.makeText(TrackDeliveryActivity.this, "AA- " + e1.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            }
            while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            }
            while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)), (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
    public void GetLocation() {

        Ion.with(getBaseContext()).load(Constants.BASE_URL + "webservice/usersdeliveryboylocation?")
                .setBodyParameter("deliveryboyid",getIntent().getStringExtra("boyId"))
              //  .setBodyParameter("id","7")
                .asString().setCallback(new FutureCallback<String>() {

            @Override
            public void onCompleted(Exception e, String result) {
                hideProgress();
                if (UtilityMethod.isStringNullOrBlank(result)) {
                    try {

                        UtilityMethod.alertforServerError(getBaseContext(),"0");
                    } catch (Exception e11) {

                    }
                } else {
                    hideProgress();
                      Log.d("result", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String status = jsonObject.getString("status");

                        if (status.equals("1")) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                            delvry_boy_lat = Double.valueOf(jsonObject1.getString("latitude"));
                            delvry_boy_long = Double.valueOf(jsonObject1.getString("longitude"));

                            if(user_lat == null || user_long == null){
                                user_lat = MapLocationService.lng;
                                user_long = MapLocationService.lng;
                            }

                            s = makeURL(delvry_boy_lat, delvry_boy_long, user_lat, user_long);
                            MapSearch(s);
                            LatLng destination = new LatLng(user_lat, user_long);
                            LatLng source = new LatLng(delvry_boy_lat, delvry_boy_long);
                            setUpMap(destination,source);
                        }
                        else if (status.equals("0")) {
                            hideProgress();
                        }

                    } catch (Exception e1) {
                        hideProgress();
                       // Toast.makeText(TrackDeliveryActivity.this, "" + e1.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.MAIN");
        //IntentFilter intentFilter = new IntentFilter(android.intent.action.MY_INTERNAL_BROADCAST);

      /*  receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                //extract our message from intent
                String msg_for_me = intent.getStringExtra("some_msg");
                //log our message value
                Log.i("InchooTutorial", msg_for_me);
            }
        };
        */
        //registering our receiver
        this.registerReceiver(receiver, intentFilter);
        startService(new Intent(getBaseContext(), MapLocationService.class));
    }

    @Override
    protected void onPause() {

        super.onPause();
        //unregister our receiver
        this.unregisterReceiver(this.receiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
       // startService(new Intent(getBaseContext(), MapLocationService.class));
    }

    private class InternalBroadcastreciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //  Toast.makeText(TrackDeliveryActivity.this,"Recive",Toast.LENGTH_SHORT).show();
          //  GetLocation();
        }
    }

//    public  void getlatlong(String address) {
//        Geocoder coder = new Geocoder(this);
//        List<android.location.Address> addresses;
//        try {
//            addresses = coder.getFromLocationName(address, 5);
//            if (addresses == null) {
//            }
//            android.location.Address location = addresses.get(0);
//           // user_lat = location.getLatitude();
//           // user_long = location.getLongitude();
//            Log.i("Lat", "" + lat);
//            Log.i("Lng", "" + lng);
//            LatLng latLng = new LatLng(lat, lng);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        LatLng p1 = null;

        strAddress = strAddress.replace("(\\r|\\n)", "");

        try {
            // May throw an IOException
            List<android.location.Address> address = coder.getFromLocationName(strAddress, 5);
            if (address == null && address.size() < 0) {
                //Toast.makeText(context, "Morada não encontrada", Toast.LENGTH_SHORT).show();;
            }
            android.location.Address location = address.get(0);
            user_lat = location.getLatitude();
            user_long = location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        }
        catch (IOException ex) {

            ex.printStackTrace();
        }
    }

    public void hideProgress() {
        if (progress != null) {
            try {
                progress.dismiss();
            } catch (Exception e) {

            }
        }
    }

    public void showProgress() {
        try {
            progress = new ProgressDialog(this);
            progress.setMessage("A carregar...");
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.show();
        } catch (Exception e) {

        }
    }


    public void alertforNoAddress() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(TrackDeliveryActivity.this);

        alertDialog.setMessage("Mapa indisponível, por favor espere ou tente mais tarde");
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        // Showing Alert Message
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
    super.onBackPressed();
    finish();

    }



    public void saveFireBasePushToken(String token){

    }
}