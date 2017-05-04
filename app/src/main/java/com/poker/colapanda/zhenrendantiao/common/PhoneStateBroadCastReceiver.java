package com.poker.colapanda.zhenrendantiao.common;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.poker.colapanda.zhenrendantiao.live.ColorRoomActivity;
import com.poker.colapanda.zhenrendantiao.live.SpecificPointRoomActivity;
import com.poker.colapanda.zhenrendantiao.login.model.Home;
import com.poker.colapanda.zhenrendantiao.login.model.Open;

import de.greenrobot.event.EventBus;

/**
 * Created by zhangze on 2017/4/26 10:14
 * 监听电话
 */
public class PhoneStateBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果是拨打电话
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {

        } else {
            // 如果是来电
            TelephonyManager tManager = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            switch (tManager.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING://铃声
                    ZhenrendantiaoApplication.mute = false;
                    EventBus.getDefault().post(new Home());
                    if (SpecificPointRoomActivity.specificLivePlayer != null) {
                        SpecificPointRoomActivity.specificLivePlayer.setMute(true);
                    }
                    if (ColorRoomActivity.colorLivePlayer != null) {
                        ColorRoomActivity.colorLivePlayer.setMute(true);
                    }

                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK://接

                    break;
                case TelephonyManager.CALL_STATE_IDLE://挂
                    ZhenrendantiaoApplication.mute = true;
                    if (SpecificPointRoomActivity.specificLivePlayer != null) {
                        SpecificPointRoomActivity.specificLivePlayer.setMute(false);
                    }
                    if (ColorRoomActivity.colorLivePlayer != null) {
                        ColorRoomActivity.colorLivePlayer.setMute(false);
                    }
                    if (ZhenrendantiaoApplication.ssssss) {
                        EventBus.getDefault().post(new Open());
                    }
                    break;
            }
        }
    }
}
