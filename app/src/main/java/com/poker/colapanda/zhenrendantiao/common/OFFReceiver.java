package com.poker.colapanda.zhenrendantiao.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.poker.colapanda.zhenrendantiao.login.model.Screen;

import de.greenrobot.event.EventBus;


/**
 * 监听屏幕是否黑屏
 * Created by bd on 2017/3/9.
 */
public class OFFReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_SCREEN_OFF.equals(action)){
            EventBus.getDefault().post(new Screen());
        }
    }
}
