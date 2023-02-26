package com.latitude22.homemdopao;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Anuved on 22-11-2016.
 */
public class InternetConnection {

    public static boolean isInternetOn(Context mContext) {
        NetworkInfo localNetworkInfo = ((ConnectivityManager) mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        return (localNetworkInfo != null) && (localNetworkInfo.isConnected());
    }
}
