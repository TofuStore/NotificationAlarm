package com.example.thisotheraccount.notificationalarm;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.RequiresPermission;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.Calendar;

public class ReadNotifications extends NotificationListenerService {

    Context context;
    static MyListener myListener;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    //private static Calendar LocalBroadcastManager;
    static String title = "";
    static String text = "";
    static String nPackage= "";
    static String test_string = "";


    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        // read notifications that are posted
        Log.v("Notification is", "posted");
        title = "" + sbn.getNotification().extras.getString("android.title");
        text = "" + sbn.getNotification().extras.getString("android.text");
        nPackage = sbn.getPackageName();
        Log.v("Notification title is", title);
        Log.v("Notification text is", text);
        Log.v("Package text is", nPackage);

        myListener.setValue(title + " " + text + " " + nPackage);
    }

    public void setListener (MyListener myListener) {
        ReadNotifications. myListener = myListener ;
    }


}
