package com.example.thisotheraccount.notificationalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class create_page extends AppCompatActivity{

    //List<String> keyword = new ArrayList<>();
    String keyword = "";
    int index = 0;

    /*
    public String getKeywords() {
        StringBuilder sb = new StringBuilder();
        for (String s : keyword) {
            sb.append(s);
            sb.append("\t");
        }
        String keywords = sb.toString();
        return keywords;
    }

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_page);
        Button back = (Button)findViewById(R.id.back_button);
        Button ok = (Button)findViewById(R.id.ok_button);
        EditText box = (EditText) findViewById(R.id.box);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(create_page.this, MainActivity.class));
            }
        });

        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String word = box.getText().toString();
                //keyword.add(word);
                keyword = word;
                Log.v("New word is", keyword);


                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("EXTRA_KEYWORD_ID", keyword);
                startActivity(intent);
            }
        });


    }

}
