package com.latitude22.homemdopao;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.latitude22.homemdopao.Service.MapLocationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    public static final int MULTIPLE_PERMISSIONS = 10;
    ActionBar actionBar;
    SessionManager sm;
    Context Mcontext;

    String[] permissions= new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

      //  MultiDex.install(this);
        actionBar = getSupportActionBar();
 //       actionBar.hide();
        Mcontext=this;
        sm =new SessionManager(Mcontext);
        if (checkPermissions()){
            displaySplashScreen();
            //  permissions  granted.
        }

      /*  new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                if (Utility.isStringNullOrBlank(sm.getuserid())) {

                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                } else {

                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }

         }

        }, 3000);

*/
    }
    private void displaySplashScreen()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                if (Utility.isStringNullOrBlank(sm.getuserid()))
                {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                } else
                {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        }, 3000);
    }
    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(this,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    displaySplashScreen();
                }
                else
                {
                    checkPermissions();
                }
                return;
            }
        }
    }

    }