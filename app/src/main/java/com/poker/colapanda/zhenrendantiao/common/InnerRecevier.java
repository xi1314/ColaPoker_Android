package com.poker.colapanda.zhenrendantiao.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.poker.colapanda.zhenrendantiao.login.model.Home;

import de.greenrobot.event.EventBus;

/**
 * 监听是否点击Home键
 * Created by bd on 2017/3/9.
 */
public class InnerRecevier extends BroadcastReceiver {
    final String SYSTEM_DIALOG_REASON_KEY = "reason";

    final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

    final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
            String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
            if (reason != null) {
                //Home键被监听
                if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                    EventBus.getDefault().post(new Home());
                    //多任务键被监听
                } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {

                }
            }
        }
    }
}
