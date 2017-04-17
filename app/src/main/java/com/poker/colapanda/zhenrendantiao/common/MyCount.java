package com.poker.colapanda.zhenrendantiao.common;

import android.os.CountDownTimer;
import android.widget.RelativeLayout;

/**
 * Created by zhangze on 2017/3/28 9:57
 */
public class MyCount extends CountDownTimer {
    RelativeLayout rl;


    public MyCount(long millisInFuture, long countDownInterval, RelativeLayout rl) {
        super(millisInFuture, countDownInterval);
        this.rl = rl;
    }


    @Override
    public void onTick(long l) {
        if (l / 1000 == 6) {
            rl.setPressed(true);
        }
        if (l / 1000 == 5) {
            rl.setPressed(false);
        }
        if (l / 1000 == 4) {
            rl.setPressed(true);
        }
        if (l / 1000 == 3) {
            rl.setPressed(false);
        }
        if (l / 1000 == 2) {
            rl.setPressed(true);
        }
        if (l / 1000 == 1) {
            rl.setPressed(false);
        }

    }

    @Override
    public void onFinish() {
        rl.setPressed(false);
    }
}