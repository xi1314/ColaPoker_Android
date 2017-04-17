package com.poker.colapanda.zhenrendantiao.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.poker.colapanda.zhenrendantiao.R;
import com.poker.colapanda.zhenrendantiao.common.music.ClickMusic;
import com.poker.colapanda.zhenrendantiao.live.ColorRoomActivity;
import com.poker.colapanda.zhenrendantiao.live.SpecificPointRoomActivity;
import com.poker.colapanda.zhenrendantiao.login.model.Open;
import com.poker.colapanda.zhenrendantiao.login.model.Play;
import com.poker.colapanda.zhenrendantiao.utils.CommonUtils;

import de.greenrobot.event.EventBus;

public class PlaySelectionActivity extends Activity implements View.OnClickListener{
    private Button playSelectionColor;
    private Button playSelectionSpecific;
    private ClickMusic clickMusic = new ClickMusic();

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
    }

    private void initView() {
        playSelectionColor = (Button) findViewById(R.id.play_selection_color);
        playSelectionSpecific = (Button) findViewById(R.id.play_selection_specific);

    }

    @Override
    public void onClick(View view) {
        String token = getIntent().getStringExtra("token");
        clickMusic.start(this);
        switch (view.getId()){
            case R.id.play_selection_color:
                CommonUtils.jumps(PlaySelectionActivity.this, ColorRoomActivity.class, token);
                EventBus.getDefault().post(new Play());
                finish();
                break;
            case R.id.play_selection_specific:
                CommonUtils.jumps(PlaySelectionActivity.this, SpecificPointRoomActivity.class, token);
                EventBus.getDefault().post(new Play());
                finish();
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
    protected void onDestroy() {
        super.onDestroy();
        clickMusic.stop();
        CommonUtils.removeActivity(this);
    }
}
