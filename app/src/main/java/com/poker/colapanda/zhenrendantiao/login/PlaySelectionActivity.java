package com.poker.colapanda.zhenrendantiao.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.poker.colapanda.zhenrendantiao.R;
import com.poker.colapanda.zhenrendantiao.common.music.ClickMusic;
import com.poker.colapanda.zhenrendantiao.live.ColorRoomActivity;
import com.poker.colapanda.zhenrendantiao.live.SpecificPointRoomActivity;
import com.poker.colapanda.zhenrendantiao.login.model.Open;
import com.poker.colapanda.zhenrendantiao.utils.CommonUtils;
import com.poker.colapanda.zhenrendantiao.utils.ExitUtils;

import de.greenrobot.event.EventBus;

public class PlaySelectionActivity extends Activity implements View.OnClickListener{
    private Button playSelectionColor;
    private Button playSelectionSpecific;
    private Button playSelectionRetreat;
    public static String token;
    private long backtime = 0;
    private ClickMusic clickMusic = new ClickMusic();
    private boolean sssss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_selection);
        CommonUtils.addActivity(this);
        initView();
        setListener();
    }

    private void setListener() {
        playSelectionColor.setOnClickListener(this);
        playSelectionSpecific.setOnClickListener(this);
        playSelectionRetreat.setOnClickListener(this);
    }

    private void initView() {
        playSelectionColor = (Button) findViewById(R.id.play_selection_color);
        playSelectionSpecific = (Button) findViewById(R.id.play_selection_specific);
        playSelectionRetreat = (Button) findViewById(R.id.play_selection_retreat);

    }

    @Override
    public void onClick(View view) {
        token = getIntent().getStringExtra("token");
        clickMusic.start(this);
        switch (view.getId()){
            case R.id.play_selection_retreat:
                sssss = true;
                finish();
                break;
            case R.id.play_selection_color:
                CommonUtils.jumps(PlaySelectionActivity.this, ColorRoomActivity.class, token);
                break;
            case R.id.play_selection_specific:
                CommonUtils.jumps(PlaySelectionActivity.this, SpecificPointRoomActivity.class, token);
                break;
        }
    }

    protected void onResume() {
        super.onResume();
        if (!LoginActivity.open) {
            EventBus.getDefault().post(new Open());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!sssss){
            stopService(LoginActivity.Serviceintent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clickMusic.stop();
        CommonUtils.removeActivity(this);
    }
    /**
     * 重写back 点击两次退出APP
     *
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            clickMusic.start(this);
            long t = System.currentTimeMillis();
            if (t - backtime > 3000) {
                Toast.makeText(this, "再按一次退出游戏", Toast.LENGTH_SHORT).show();
                backtime = t;
                return true;
            }
            ExitUtils.getInstance().exit(PlaySelectionActivity.this);
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }
}
