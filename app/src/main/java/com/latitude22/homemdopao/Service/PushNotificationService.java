package com.latitude22.homemdopao.Service;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.latitude22.homemdopao.SessionManager;


/**
 * Created by wangm on 3/28/2018.
 */

public class PushNotificationService extends FirebaseInstanceIdService {  //ID service
    private static final String TAG = "FirebaseIDService";

    private FirebaseAuth firAuth;
    private DatabaseReference fireTokenRef;

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.e("test", "token refresh : " + refreshedToken);
        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
       // PrefManager.savePrefString(Constants.PREF_FCM_TOKEN, token);

        SessionManager sm = new SessionManager(this);

        if(!sm.getuserid().isEmpty()){
            Log.e("test", "Refreshed uid: " + sm.getuserid());
            String uid = sm.getuserid() + "_android";
            fireTokenRef =  FirebaseDatabase.getInstance().getReference("/tokens/" + uid);
            fireTokenRef.push().setValue(token);
        }else{
            Log.e("test", "Refreshed token: " + token);
        }
    }
}