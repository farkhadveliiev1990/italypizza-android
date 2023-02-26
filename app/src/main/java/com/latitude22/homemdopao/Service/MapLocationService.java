package com.latitude22.homemdopao.Service;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.latitude22.homemdopao.Adapter.CalenderSubscriptionAdapter;
import com.latitude22.homemdopao.Adapter.MyOrderAdapter;
import com.latitude22.homemdopao.Adapter.MyOrderListAdapter;
import com.latitude22.homemdopao.CalenderActivity;
import com.latitude22.homemdopao.CalenderSubscriptionActivity;
import com.latitude22.homemdopao.Constants;
import com.latitude22.homemdopao.Fragments.MyOrderFragment;
import com.latitude22.homemdopao.SessionManager;
import com.latitude22.homemdopao.TrackDeliveryActivity;
import com.latitude22.homemdopao.UtilityMethod;

import org.json.JSONException;
import org.json.JSONObject;

import static android.R.attr.defaultValue;

/**
 * Created by Admin on 17-11-2016.
 */

public class MapLocationService extends Service
{
    public static final int Three_MINUTES = 120000; // 120 seconds
    public static Boolean isRunning = false;
    public  static Double slat,slng;

    String deliveryboyid;
    public LocationManager mLocationManager;
    public LocationUpdaterListener mLocationListener;
    public Location previousBestLocation = null;
    String id;
    ProgressDialog progress;
    SessionManager sm;

    public static double lat,lng;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationUpdaterListener();

        sm = new SessionManager(getBaseContext());
        id = sm.getuserid();
       // GetLocation();
        super.onCreate();

    }

    Handler mHandler = new Handler();
    Runnable mHandlerTask = new Runnable(){
        @Override
        public void run() {
            if (!isRunning) {

                startListening();
            }
           // Toast.makeText(getBaseContext(), "service", Toast.LENGTH_SHORT).show();
            Intent in = new Intent();
            in.setAction(TrackDeliveryActivity.MY_INTERNAL_BROADCAST);
            sendBroadcast(in);

            mHandler.postDelayed(mHandlerTask, Three_MINUTES);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


   /*     Bundle extras = intent.getExtras();
        deliveryboyid = extras.getString("deliveryBoyId");*/
        //deliveryboyid = intent.getStringExtra("deliveryBoyId");
      //  Toast.makeText(this, "Service"+CalenderSubscriptionAdapter.deliveryBoyId, Toast.LENGTH_SHORT).show();
        mHandlerTask.run();


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopListening();
        mHandler.removeCallbacks(mHandlerTask);
        super.onDestroy();
    }

    private void startListening() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (mLocationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);

            if (mLocationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER))
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
        }
        isRunning = true;
    }

    private void stopListening() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.removeUpdates(mLocationListener);
        }
        isRunning = false;
    }

    public class LocationUpdaterListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            if (isBetterLocation(location, previousBestLocation)) {
                previousBestLocation = location;
                lat = location.getLatitude();
                lng = location.getLongitude();

                try {

                        GetLocation();

                   // Toast.makeText(MapLocationService.this, "Service Source"+slat+""+slng, Toast.LENGTH_SHORT).show();
                    // Script to post location data to server..
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    stopListening();
                }
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }

        protected boolean isBetterLocation(Location location, Location currentBestLocation) {
            if (currentBestLocation == null) {
                // A new location is always better than no location
                return true;
            }

            // Check whether the new location fix is newer or older
            long timeDelta = location.getTime() - currentBestLocation.getTime();
            boolean isSignificantlyNewer = timeDelta > Three_MINUTES;
            boolean isSignificantlyOlder = timeDelta < -Three_MINUTES;
            boolean isNewer = timeDelta > 0;

            // If it's been more than two minutes since the current location, use the new location
            // because the user has likely moved
            if (isSignificantlyNewer) {
                return true;
                // If the new location is more than two minutes older, it must be worse
            } else if (isSignificantlyOlder) {
                return false;
            }

            // Check whether the new location fix is more or less accurate
            int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
            boolean isLessAccurate = accuracyDelta > 0;
            boolean isMoreAccurate = accuracyDelta < 0;
            boolean isSignificantlyLessAccurate = accuracyDelta > 200;

            // Check if the old and new location are from the same provider
            boolean isFromSameProvider = isSameProvider(location.getProvider(), currentBestLocation.getProvider());

            // Determine location quality using a combination of timeliness and accuracy
            if (isMoreAccurate) {
                return true;
            } else if (isNewer && !isLessAccurate) {
                return true;
            } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
                return true;
            }
            return false;
        }

        /**
         * Checks whether two providers are the same
         */
        private boolean isSameProvider(String provider1, String provider2) {
            if (provider1 == null) {
                return provider2 == null;
            }
            return provider1.equals(provider2);
        }

        public void GetLocation() {

            Ion.with(getBaseContext()).load(Constants.BASE_URL + "webservice/usersdeliveryboylocation?")
                    .setBodyParameter("deliveryboyid", MyOrderFragment.boyId)
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    hideProgress();
                    if (UtilityMethod.isStringNullOrBlank(result)) {
                        try {

                            UtilityMethod.alertforServerError(getBaseContext(), "0");
                        } catch (Exception e11) {

                        }
                    } else {
                        hideProgress();
                        //  Log.d("result", result);
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            String status = jsonObject.getString("status");

                            if (status.equals("1")) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                                slat = Double.valueOf(jsonObject1.getString("latitude"));
                                slng = Double.valueOf(jsonObject1.getString("longitude"));

                               // Toast.makeText(MapLocationService.this, "ServiceLatLong"+slat+slng, Toast.LENGTH_SHORT).show();
                               // Toast.makeText(MapLocationService.this, "deliveryBoyIdService"+CalenderSubscriptionAdapter.deliveryBoyId, Toast.LENGTH_SHORT).show();
                                hideProgress();
                            } else if (status.equals("0")) {
                                hideProgress();
                            }

                        } catch (JSONException e1) {
                            hideProgress();
                            //Toast.makeText(getBaseContext(), "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
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

}