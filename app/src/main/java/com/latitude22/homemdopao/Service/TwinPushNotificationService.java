package com.latitude22.homemdopao.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.latitude22.homemdopao.MainActivity;
import com.latitude22.homemdopao.R;


/**
 * Created by wangm on 3/28/2018.
 */


public class TwinPushNotificationService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        super.onMessageReceived(remoteMessage);

        Log.e("Test", "Message received ["+ remoteMessage +"]");



        //Create Notification
        Intent intent = new Intent(this, MainActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1,
                intent, 0);

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new
                NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo_hdr)
                .setContentTitle("HomemDoPao")
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(this.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }
}