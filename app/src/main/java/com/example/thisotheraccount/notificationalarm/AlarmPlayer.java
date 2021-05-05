package com.example.thisotheraccount.notificationalarm;

import android.content.Context;
import android.media.MediaPlayer;

public class AlarmPlayer {
    public static MediaPlayer player;
    public static boolean isPlaying = false;
    public static void SoundPlayer(Context ctx, int raw_id){
        player = MediaPlayer.create(ctx, raw_id);
        player.setLooping(true); // Set looping
        player.setVolume(100, 100);

        //player.release();
        player.start();
        isPlaying = true;
    }
}
