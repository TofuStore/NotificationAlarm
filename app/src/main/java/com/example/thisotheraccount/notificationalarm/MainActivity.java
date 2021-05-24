package com.example.thisotheraccount.notificationalarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

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
    public String display = "";
    public int alarmNumber;
    public boolean checked = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        loadArray(getApplicationContext());

        new ReadNotifications().setListener(this);

        Intent inn = getIntent();
        Bundle b = inn.getExtras();

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.alarms_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        loadSpinner(spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                alarmNumber = position;
                saveSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                alarmNumber = 0;
            }

        });

        if(b!=null)
        {
            String j = (String) b.get("EXTRA_KEYWORD_ID");
            addstring = j;
            keywords.add(j);
            saveArray();
            Log.v("keywords length is", "" + keywords.size());
        }
        Log.v("addstring is", "" + addstring);


        final TextView wordlist = findViewById(R.id.textView);
        for(int i=0; i<keywords.size(); i++){
            display = display + keywords.get(i) + "\n";
        }
        //Log.v("number of words", "" + keywords.size());
        wordlist.setText(display);

        //turn this into a button
        startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = getString(R.string.app_name);
                String description = getString(R.string.app_name);
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("chungus", name, importance);
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "chungus")
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

        Button btn = findViewById(R.id.clearButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keywords.clear();
                saveArray();
                wordlist.setText("");
            }

        });

    }


    @Override
    public void setValue (String packageName) {
        teststring = packageName;
        Log.v("teststring", "is " + teststring);
/*
        Intent inn = getIntent();
        Bundle b = inn.getExtras();

        if(b!=null)
        {
            String j = (String) b.get("EXTRA_KEYWORD_ID");
            addstring = j;
            keywords.add(j);
        }
        Log.v("addstring is", "" + addstring);

 */
        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        if(checkBox.isChecked()){
            for(int i=0; i<keywords.size(); i++){
                Log.v("number of words", "" + keywords.size());
                if(teststring.contains(keywords.get(i))){
                    if(AlarmPlayer.isPlaying == false) {
                        AlarmPlayer.SoundPlayer(getApplicationContext(), findAlarm(alarmNumber));
                        showNotification(keywords.get(i));
                        break;
                    }
                }
            }
        }else{
            String teststring2 = teststring.toLowerCase();
            for(int i=0; i<keywords.size(); i++){
                Log.v("lowercase word", "" + keywords.get(i).toLowerCase());
                if(teststring2.contains(keywords.get(i).toLowerCase())){
                    if(AlarmPlayer.isPlaying == false) {
                        AlarmPlayer.SoundPlayer(getApplicationContext(), findAlarm(alarmNumber));
                        showNotification(keywords.get(i));
                        break;
                    }
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

    public boolean saveArray()
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor mEdit1 = sp.edit();
        /* sKey is an array */
        mEdit1.putInt("Status_size", keywords.size());

        for(int i=0;i<keywords.size();i++)
        {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, keywords.get(i));
        }

        return mEdit1.commit();
    }

    public static void loadArray(Context mContext)
    {
        SharedPreferences mSharedPreference1 = PreferenceManager.getDefaultSharedPreferences(mContext);
        keywords.clear();
        int size = mSharedPreference1.getInt("Status_size", 0);

        for(int i=0;i<size;i++)
        {
            keywords.add(mSharedPreference1.getString("Status_" + i, null));
        }

    }

    public void saveSpinner(){
        SharedPreferences sharedPref = getSharedPreferences("FileName",0);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putInt("userChoiceSpinner",alarmNumber);
        prefEditor.commit();
    }

    public void loadSpinner(Spinner spinner){
        SharedPreferences sharedPref = getSharedPreferences("FileName",MODE_PRIVATE);
        int spinnerValue = sharedPref.getInt("userChoiceSpinner",-1);
        if(spinnerValue != -1) {
            // set the selected value of the spinner
            spinner.setSelection(spinnerValue);
        }
    }

    public int findAlarm(int num){
        if(num == 0){
            return R.raw.fart;
        }
        if(num == 1){
            return R.raw.fullmetal;
        }
        if(num == 2){
            return R.raw.chungus;
        }
        return 0;
    }


}