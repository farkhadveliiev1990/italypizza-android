package com.latitude22.homemdopao;

import android.content.Context;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.app.Application;
import com.norbsoft.typefacehelper.TypefaceCollection;
import com.norbsoft.typefacehelper.TypefaceHelper;

import java.util.Locale;


/**
 * Created by admin on 29-07-2016.
 */
public class ApplicationController extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Configuration.Builder configurationBuilder = new Configuration.Builder(this);
        configurationBuilder.addModelClass(AddCart.class);

        TypefaceCollection typeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(getAssets(), "font/oswalt.ttf"))
                .create();
        TypefaceHelper.init(typeface);
        ActiveAndroid.initialize(this);


    }
  @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
      //super.attachBaseContext(LocaleHelper.onAttach(base, "pt"));
        MultiDex.install(this);
    }

}
