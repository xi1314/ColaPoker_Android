package com.poker.colapanda.zhenrendantiao.common.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.poker.colapanda.zhenrendantiao.R;

/**
 * 播放背景音乐
 * Created by bd on 2017/3/8.
 */
public class MusicServer extends Service {
    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bg);
        mediaPlayer.seekTo(0);
        mediaPlayer.setLooping(true);
    }

    @Override
    public IBinder onBind(Intent intent) {
// TODO Auto-generated method stub
        mediaPlayer.start();
        return null;

    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        mediaPlayer.start();
    }


    @Override
    public void onDestroy() {
// TODO Auto-generated method stub
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer=null;//回收资源

    }
    @Override
    public boolean onUnbind(Intent intent) {
        mediaPlayer.stop();
        return super.onUnbind(intent);

    }
}

