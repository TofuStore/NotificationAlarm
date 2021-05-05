package com.example.thisotheraccount.notificationalarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.thisotheraccount.notificationalarm.AppConstant.DISMISS;

public class MainActivity extends AppCompatActivity implements MyListener{
    private ReadNotifications myService;
    private boolean bound = false;

    static List<String> keywords = new ArrayList<>();
    public String teststring = "";
    public String addstring = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        new ReadNotifications().setListener(this);

        //turn this into a button
        startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = getString(R.string.app_name);
                String description = getString(R.string.app_name);
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("NotificationAlarm", name, importance);
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "NotificationAlarm")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Chungus")
                .setContentText("beeg chungus")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("beeg beeg chungus"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, create_page.class));
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.create_button);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationManager.notify(1, builder.build());
            }
        });
    }

    @Override
    public void setValue (String packageName) {
        teststring = packageName;
        Log.v("teststring", "is " + teststring);

        Intent inn = getIntent();
        Bundle b = inn.getExtras();

        if(b!=null)
        {
            String j = (String) b.get("EXTRA_KEYWORD_ID");
            addstring = j;
            keywords.add(j);
        }
        Log.v("addstring is", "" + addstring);

        for(int i=0; i<keywords.size(); i++){

            if(teststring.contains(keywords.get(i))){
                if(AlarmPlayer.isPlaying == false) {
                    AlarmPlayer.SoundPlayer(getApplicationContext(), R.raw.fullmetal);
                    showNotification(keywords.get(i));
                    break;
                }
            }
        }

    }

    private void showNotification(String word){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("NotificationAlarm", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }



        NotificationCompat.Builder builder3 = new NotificationCompat.Builder(this, "NotificationAlarm")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Notification Detected")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("notification has word: " + word))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent dismissIntent = new Intent(this, ActionReceiver.class);
        dismissIntent.setAction(DISMISS);
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(this, 0, dismissIntent, 0);

        builder3.addAction(R.drawable.ic_launcher_background, "Dismiss", dismissPendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(1, builder3.build());
    }


}