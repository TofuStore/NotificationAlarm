package com.example.thisotheraccount.notificationalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import static com.example.thisotheraccount.notificationalarm.AppConstant.DISMISS;

public class ActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.v("Got", "" + action);
        if(DISMISS.equals(action)){
            Log.v("Pressed", "Dismiss");
            AlarmPlayer.player.stop();
            AlarmPlayer.isPlaying = false;
        };
    }

}