package com.latitude22.homemdopao;

/**
 * Created by Anuved on 05-08-2016.
 */


        import android.app.Application;
import android.graphics.Typeface;

import com.norbsoft.typefacehelper.TypefaceCollection;
import com.norbsoft.typefacehelper.TypefaceHelper;

/**
 * Created by admin on 30-01-2016.
 */
public class MotorTraderFont extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceCollection typeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(getAssets(), "font/oswalt.ttf"))
                .create();
        TypefaceHelper.init(typeface);
    }
}