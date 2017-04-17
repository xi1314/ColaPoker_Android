package com.poker.colapanda.zhenrendantiao.common.music;

import android.content.Context;
import android.media.MediaPlayer;

import com.poker.colapanda.zhenrendantiao.R;

/**
 * 发牌音乐
 * Created by bd on 2017/3/9.
 */
public class DealCardsMusic {
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private boolean ss;
    public void start (Context context) {
        if (!ss) {
            mediaPlayer = MediaPlayer.create(context, R.raw.wait_open);
            ss = true;
        }
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
        }
    }
    public void stop(){
        ss = false;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        mediaPlayer = null;
    }

}
