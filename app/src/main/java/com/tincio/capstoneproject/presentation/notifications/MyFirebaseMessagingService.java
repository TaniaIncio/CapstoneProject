package com.tincio.capstoneproject.presentation.notifications;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by juan on 9/07/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

    }
}
