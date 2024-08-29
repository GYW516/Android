package com.gyw.collector;

import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

public class MediaPlayerCollector {
    public static List<MediaPlayer> mediaPlayers = new ArrayList<>();

    //将fragment加到共享fragment里
    public static void addMediaPlayer(MediaPlayer mediaPlayer){
        mediaPlayers.add(mediaPlayer);
    }


}
