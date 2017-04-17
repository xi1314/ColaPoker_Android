package com.poker.colapanda.zhenrendantiao.live;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poker.colapanda.zhenrendantiao.R;
import com.poker.colapanda.zhenrendantiao.common.Constants;
import com.poker.colapanda.zhenrendantiao.common.music.BetMusic;
import com.poker.colapanda.zhenrendantiao.common.music.ClickMusic;
import com.poker.colapanda.zhenrendantiao.common.music.CountdownMusic;
import com.poker.colapanda.zhenrendantiao.common.music.DealCardsMusic;
import com.poker.colapanda.zhenrendantiao.common.music.NotWinningMusic;
import com.poker.colapanda.zhenrendantiao.common.music.OpenMusic;
import com.poker.colapanda.zhenrendantiao.common.music.WinningMusic;
import com.poker.colapanda.zhenrendantiao.common.music.WithdrawMusic;
import com.poker.colapanda.zhenrendantiao.common.network.Result;
import com.poker.colapanda.zhenrendantiao.common.network.ResultError;
import com.poker.colapanda.zhenrendantiao.common.widget.BaseActivity;
import com.poker.colapanda.zhenrendantiao.live.adapter.HistoryAdapter;
import com.poker.colapanda.zhenrendantiao.live.adapter.HsvHistoryAdapter;
import com.poker.colapanda.zhenrendantiao.live.model.AllBet;
import com.poker.colapanda.zhenrendantiao.live.model.Datas;
import com.poker.colapanda.zhenrendantiao.live.model.Game;
import com.poker.colapanda.zhenrendantiao.live.model.History;
import com.poker.colapanda.zhenrendantiao.live.model.User;
import com.poker.colapanda.zhenrendantiao.live.model.UserBet;
import com.poker.colapanda.zhenrendantiao.login.LoginActivity;
import com.poker.colapanda.zhenrendantiao.utils.CommonUtils;
import com.poker.colapanda.zhenrendantiao.utils.ExitUtils;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 猜大小游戏直播间
 * Created by zhangze on 2017/3/10 14:35
 */
public class SpecificPointRoomActivity extends BaseActivity implements View.OnClickListener {
    private TXCloudVideoView specificRPlayerView;//播放窗口
    private TXLivePlayer specificRLivePlayer;//播放窗口
    private long backtime = 0;
    private ClickMusic clickMusic = new ClickMusic();
    private OpenMusic openMusic = new OpenMusic();
    private DealCardsMusic dealCardsMusic = new DealCardsMusic();
    private CountdownMusic countdownMusic = new CountdownMusic();
    private BetMusic betMusic = new BetMusic();
    private NotWinningMusic notWinningMusic = new NotWinningMusic();
    private WinningMusic winningMusic = new WinningMusic();
    private WithdrawMusic withdrawMusic = new WithdrawMusic();


    private String token;
    private TextView specificRoomTotalBettingOne;//总倍数
    private TextView specificRoomTotalBettingTwo;//总倍数
    private TextView specificRoomTotalBettingThree;//总倍数
    private TextView specificRoomTotalBettingFour;//总倍数
    private TextView specificRoomTotalBettingFive;//总倍数
    private TextView specificRoomTvBalance;//余额
    private TextView specificRoomTitle1;
    private TextView specificRoomTitleStatus;
    private TextView specificRoomtitleCountdown;
    private TextView specificRoomLiveNotTv;//未开播提示语
    private RelativeLayout specificRoomLiveNotBackground;//未开播背景
    private ImageView specificRoomOpen;//开牌
    private ImageView specificRoomIvVoice;//语音
    private ImageView specificRoomIvChip;//筹码
    private ImageView specificRoomWithdraw;//撤销
    private GridView specificRoomMgvHistory;//小历史
    private TextView specificRoomTitleWechat;

    private Button specificRoomBtCharge;//充值
    private Button specificRoomBtMention;//提现
    private Button specificRoomBtRetreat;//退出
    private GridView specificRoomGvHistory;

    private boolean mLive;//判断有没有播放视频
    private boolean mVudio;//判断语音有没有启动
    private boolean mOpen;//音乐有没有播放只播放一次
    private boolean dealCards;//音乐有没有播放只播放一次
    private boolean myOpen;//中没中奖
    private boolean mCard;//是否押注
    private boolean mclick;
    private boolean countdown;


    private HistoryAdapter historyAdapter;
    private HsvHistoryAdapter hsvHistoryAdapter;
    private Game game = new Game();
    private User user = new User();
    private UserBet userBet = new UserBet();
    private AllBet allBet = new AllBet();
    private History history = new History();

    private int FIVE = 5;
    private int TWENTY = 20;
    private int FIFTY = 50;
    private int HUNDRED = 100;
    private int TWO_HUNDRED = 200;
    private int FIVE_HUNDRED = 500;
    private int A_THOUSAND = 1000;
    //    private  int THREE_THOUSAND = 3000;
    private int INTEGRATION = FIVE;


    private int number = 7;

    private int balance;
    private int DELYED = 1000;


    Handler handler = new Handler();

    //计时器 每秒请求一次
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getGameData();
            handler.postDelayed(this, DELYED);
        }
    };

//    private RtcEngine mRtcEngine;//语音
//    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
//        @Override
//        public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            });
//        }
//
//        public void onAudioRouteChanged(int routing) {
//            int i = routing;
//        }
//    };
//    private AudioManager audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_point_room);
        CommonUtils.addActivity(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//屏幕常亮
        initView();
        token = getIntent().getStringExtra("token");
        getGameData();
        specificRLivePlayer = new TXLivePlayer(this);
        specificRLivePlayer.setPlayerView(specificRPlayerView);
        handler.postDelayed(runnable, DELYED);
        setOnClickListener();
//        mRtcEngine = RtcEngine.create(this, Constants.Comm.VOICE_KEY, mRtcEventHandler);
//        mRtcEngine.setEnableSpeakerphone(true);
        hideBottomUIMenu();
        specificRLivePlayer.setPlayListener(new ITXLivePlayListener() {
            @Override
            public void onPlayEvent(int i, Bundle bundle) {
                if (i == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
                    specificRoomLiveNotBackground.setVisibility(View.VISIBLE);
                    specificRoomLiveNotTv.setText("主播正在赶来的路上");
                }
                if (i == TXLiveConstants.PLAY_EVT_PLAY_LOADING) {
                    specificRoomLiveNotBackground.setVisibility(View.VISIBLE);
                    specificRoomLiveNotTv.setText("正在加载  请稍后...");
                }
                if (i == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
                    specificRoomLiveNotBackground.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNetStatus(Bundle bundle) {

            }
        });
//        setVolumeControlStream(AudioManager.STREAM_MUSIC);
//        audio = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
    }

    private void setOnClickListener() {
        specificRoomBtCharge.setOnClickListener(this);
        specificRoomBtMention.setOnClickListener(this);
        specificRoomBtRetreat.setOnClickListener(this);
//        specificRoomIvVoice.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                int action = motionEvent.getAction();
//                if (action == motionEvent.ACTION_DOWN) {
//                    mRtcEngine.muteLocalAudioStream(false);
//                } else if (action == motionEvent.ACTION_UP) {
//                    mRtcEngine.muteLocalAudioStream(true);
//                }
//                return false;
//            }
//        });
        specificRoomIvChip.setOnClickListener(this);
        specificRoomWithdraw.setOnClickListener(this);

    }

    private void initView() {
        specificRPlayerView = (TXCloudVideoView) findViewById(R.id.specific_room_video_view);
        specificRoomTotalBettingOne = (TextView) findViewById(R.id.specific_room_total_betting_one);
        specificRoomTotalBettingTwo = (TextView) findViewById(R.id.specific_room_total_betting_two);
        specificRoomTotalBettingThree = (TextView) findViewById(R.id.specific_room_total_betting_three);
        specificRoomTotalBettingFour = (TextView) findViewById(R.id.specific_room_total_betting_four);
        specificRoomTotalBettingFive = (TextView) findViewById(R.id.specific_room_total_betting_five);
        specificRoomBtCharge = (Button) findViewById(R.id.specific_room_bt_charge);
        specificRoomBtMention = (Button) findViewById(R.id.specific_room_bt_mention);
        specificRoomBtRetreat = (Button) findViewById(R.id.specific_room_bt_retreat);
        specificRoomTvBalance = (TextView) findViewById(R.id.specific_room_tv_balance);
        specificRoomTitle1 = (TextView) findViewById(R.id.specific_room_title1);
        specificRoomTitleStatus = (TextView) findViewById(R.id.specific_room_title_status);
        specificRoomtitleCountdown = (TextView) findViewById(R.id.specific_room_title_countdown);
        specificRoomOpen = (ImageView) findViewById(R.id.specific_room_open);
        specificRoomGvHistory = (GridView) findViewById(R.id.specific_room_gv_history);
        specificRoomIvVoice = (ImageView) findViewById(R.id.specific_room_iv_voice);
        specificRoomIvChip = (ImageView) findViewById(R.id.specific_room_iv_chip);
        specificRoomLiveNotBackground = (RelativeLayout) findViewById(R.id.specific_room_live_not_background);
        specificRoomLiveNotTv = (TextView) findViewById(R.id.specific_room_live_not_tv);
        specificRoomWithdraw = (ImageView) findViewById(R.id.specific_room_withdraw);
        specificRoomMgvHistory = (GridView) findViewById(R.id.specific_room_mgv_history);
        specificRoomTitleWechat = (TextView) findViewById(R.id.specific_room_title_wechat);
    }

    private void init() {
        specificRoomTvBalance.setText(user.getBalance() + "");
        specificRoomTotalBettingOne.setText(allBet.getSpades() + "");
        specificRoomTotalBettingTwo.setText(allBet.getHearts() + "");
        specificRoomTotalBettingThree.setText(allBet.getClubs() + "");
        specificRoomTotalBettingFour.setText(allBet.getDiamonds() + "");
        specificRoomTotalBettingFive.setText(allBet.getJokers() + "");
        specificRoomTitle1.setText("第" + game.getIssue() + "期 " + "第" + game.getGame_number() + "局");
        specificRoomTitleStatus.setText(game.getStatus_content());
        //判断是否发牌即播放发牌音乐
        if (game.getStatus_content().equals("正在发牌")) {
            if (!dealCards) {
                dealCardsMusic.start(this);
                dealCards = true;
            }
        }
        //是否开牌即播放开牌音乐
        if (game.getStatus_content().equals("正在开牌")) {
            if (!mOpen) {
                openMusic.start(this);
                mOpen = true;
                dealCards = false;
                balance = user.getBalance();
            }
        }
        specificRoomtitleCountdown.setText(" 剩余 " + game.getCountdown() + " 秒");
        //投注倒计时3秒即播放音乐
        if (game.getCountdown().equals("2") && game.getStatus_content().equals("正在投注")) {
            countdownMusic.start(this);
            countdown = true;
            mOpen = false;
        }
        //是否开牌
        if (game.getOpen().equals("")) {
            specificRoomOpen.setImageResource(this.getResources().getIdentifier("room_not_open_card", "drawable", "com.poker.colapanda.zhenrendantiao"));
            myOpen = false;
        } else {
            specificRoomOpen.setImageResource(this.getResources().getIdentifier(game.getOpen(), "drawable", "com.poker.colapanda.zhenrendantiao"));
        }
        //中没中奖
        if (mCard) {
            if (!game.getOpen().equals("") && !myOpen) {
                if (balance == user.getBalance()) {
                    notWinningMusic.start(this);
                    myOpen = true;
                } else {
                    winningMusic.start(this);
                    myOpen = true;
                }
                mCard = false;
            }
        }
        setAdapter();
        //语音是否连接
//        if (!mVudio) {
//            mRtcEngine.joinChannel(null, game.getLive_url_md5(), "", 0);
//            mRtcEngine.enableAudio();
//            mRtcEngine.muteLocalAudioStream(true);
//            mVudio = true;
//        }
        specificRoomTitleWechat.setText(game.getWechat());
    }


    private void setAdapter() {
        String[] strings = new String[49];
        for (int i = 0; i < 49; i++) {
            strings[i] = "s";
        }
        for (int y = 0; y < history.getCard().length; y++) {
            strings[y] = history.getCard()[y];
        }
        if (historyAdapter == null) {
            historyAdapter = new HistoryAdapter(this, strings);
            specificRoomGvHistory.setAdapter(historyAdapter);
        } else {
            historyAdapter.card = strings;
            historyAdapter.notifyDataSetChanged();
        }

        specificRoomMgvHistory.setNumColumns(history.getTranslate().size());
        specificRoomMgvHistory.setScaleX(-1);
        if (hsvHistoryAdapter == null) {
            hsvHistoryAdapter = new HsvHistoryAdapter(this, history.getTranslate());
            specificRoomMgvHistory.setAdapter(hsvHistoryAdapter);
        } else {
            hsvHistoryAdapter.list = history.getTranslate();
            hsvHistoryAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        Uri content_url;
        switch (view.getId()) {
            case R.id.specific_room_bt_charge:
                clickMusic.start(this);
                intent.setAction("android.intent.action.VIEW");
                content_url = Uri.parse(game.getRecharges_url() + "?token=" + token);
                intent.setData(content_url);
                startActivity(intent);
                break;
            case R.id.specific_room_bt_mention:
                clickMusic.start(this);
                intent.setAction("android.intent.action.VIEW");
                content_url = Uri.parse(game.getWithdraws_url() + "?token=" + token);
                intent.setData(content_url);
                startActivity(intent);
                break;
            case R.id.specific_room_bt_retreat:
                clickMusic.start(this);
                setLoginLog();
                break;
            case R.id.specific_room_iv_chip:
                setChip();
                betMusic.start(this);
                break;
            case R.id.specific_room_withdraw:
                clickMusic.start(this);
                setDiaLog();

                break;
        }
    }

    private void setLoginLog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您确定退出登录吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clickMusic.start(SpecificPointRoomActivity.this);
                dialog.dismiss();
                CommonUtils.jump(SpecificPointRoomActivity.this, LoginActivity.class);
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clickMusic.start(SpecificPointRoomActivity.this);
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setDiaLog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("提醒：");
//        builder.setMessage("若练此功，必先自宫！");
        builder.setMessage("您确定撤销投注吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                withdrawMusic.start(SpecificPointRoomActivity.this);
                getWithdawData();
            }
        });
//      builder.setPositiveButton("即使自宫，也未必成功", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                withdrawMusic.start(ColorRoomActivity.this);
//                getWithdawData();
//            }
//        });

//        builder.setNegativeButton("若不自宫一定不会成功", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clickMusic.start(SpecificPointRoomActivity.this);
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 撤销投注
     */
    private void getWithdawData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("issue", game.getIssue());
        map.put("game_number", game.getGame_number());
        map.put("api_token", token);
        OkHttpUtils.post().params(map).url(Constants.Comm.HOST_WITHDAW).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(SpecificPointRoomActivity.this, ResultError.MESSAGE_NULL, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    boolean success = object.getBoolean("success");
                    if (!success) {
                        String message = object.getString("message");
                        Toast.makeText(SpecificPointRoomActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        mCard = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 切换筹码
     */
    private void setChip() {
        number = number - 1;
        if (number == 6) {
            specificRoomIvChip.setImageResource(R.drawable.twenty);
            INTEGRATION = TWENTY;
        }
        if (number == 5) {
            specificRoomIvChip.setImageResource(R.drawable.fifty);
            INTEGRATION = FIFTY;
        }
        if (number == 4) {
            specificRoomIvChip.setImageResource(R.drawable.hundred);
            INTEGRATION = HUNDRED;
        }
        if (number == 3) {
            specificRoomIvChip.setImageResource(R.drawable.two_hundred);
            INTEGRATION = TWO_HUNDRED;
        }
        if (number == 2) {
            specificRoomIvChip.setImageResource(R.drawable.five_hundred);
            INTEGRATION = FIVE_HUNDRED;
        }
        if (number == 1) {
            specificRoomIvChip.setImageResource(R.drawable.a_thousand);
            INTEGRATION = A_THOUSAND;
        }
        if (number == 0) {
            specificRoomIvChip.setImageResource(R.drawable.five);
            INTEGRATION = FIVE;
            number = 7;
        }

    }

    /**
     * 押注请求
     *
     * @param i 押注筹码
     * @param s 押注牌色
     */
    private void getGameCommit(int i, String s) {
        HashMap<String, String> map = new HashMap<>();
        map.put("suit", s);
        map.put("amount", String.valueOf(i));
        map.put("api_token", token);
        OkHttpUtils.post().params(map).url(Constants.Comm.HOST_THROW_INTO).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(SpecificPointRoomActivity.this, ResultError.MESSAGE_NULL, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                if (response == null) {
                    Toast.makeText(SpecificPointRoomActivity.this, ResultError.MESSAGE_ERROR, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        JSONObject object = new JSONObject(response);
                        boolean success = object.getBoolean("success");
                        if (!success) {
                            String message = object.getString("message");
                            Toast.makeText(SpecificPointRoomActivity.this, message, Toast.LENGTH_SHORT).show();
                        } else {
                            mCard = true;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();

            }
        });

    }

    /**
     * 获取当前牌局信息
     */
    private void getGameData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("api_token", token);
        OkHttpUtils.get().params(map).url(Constants.Comm.HOST_GAME).build().execute(new BuildCallBack() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
            }

            @Override
            public void onError(Call call, Exception e) {

//                Toast.makeText(ColorRoomActivity.this, ResultError.MESSAGE_NULL, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Result<Datas> response) {
                if (response != null) {
                    if (!response.getSuccess()) {
                        Toast.makeText(SpecificPointRoomActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        if (response.getData() != null) {
                            game = response.getData().getGame();
                            user = response.getData().getUser();
                            userBet = response.getData().getUser_bet();
                            allBet = response.getData().getAll_bet();
                            history = response.getData().getHistory();
                            if (!mLive) {
                                String flvUrl = game.getLive_url();
                                specificRLivePlayer.startPlay(flvUrl, TXLivePlayer.PLAY_TYPE_VOD_FLV);
                                mLive = true;
                            }
                            init();
                            specificRoomGvHistory.setSelection(history.getCard().length - 1);
                        } else {
                            Toast.makeText(SpecificPointRoomActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }


    // 自动解析必备
    private abstract class BuildCallBack extends Callback<Result<Datas>> {
        @Override
        public Result<Datas> parseNetworkResponse(Response response) throws Exception {
            String string = response.body().string();
            Result<Datas> build = null;
            try {
                build = new Gson().fromJson(string,
                        new TypeToken<Result<Datas>>() {
                        }.getType());

            } catch (Exception e) {
                return null;
            }
            if (build == null) {
                Toast.makeText(SpecificPointRoomActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                return null;
            }
            return build;
        }
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE;
            decorView.setSystemUiVisibility(uiOptions);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        handler.postDelayed(runnable, DELYED);
    }

    @Override
    protected void onStop() {
        super.onStop();
        specificRLivePlayer.stopPlay(true); // true代表清除最后一帧画面
        specificRPlayerView.onDestroy();
        mLive = false;
        handler.removeCallbacks(runnable);
//        mRtcEngine.leaveChannel();
        if (dealCards) {
            dealCardsMusic.stop();
        }
        if (mOpen) {
            openMusic.stop();
        }
        if (mclick) {
            clickMusic.stop();
        }
        if (countdown) {
            countdownMusic.stop();
        }
        if (myOpen && balance == user.getBalance()) {
            notWinningMusic.stop();
        }
        if (myOpen && balance != user.getBalance()) {
            winningMusic.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        specificRLivePlayer.stopPlay(true); // true代表清除最后一帧画面
        specificRPlayerView.onDestroy();
        handler.removeCallbacks(runnable);
//        mRtcEngine.leaveChannel();
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
            mclick = true;
            long t = System.currentTimeMillis();
            if (t - backtime > 3000) {
                Toast.makeText(this, "再按一次退出游戏", Toast.LENGTH_SHORT).show();
                backtime = t;
                return true;
            }
            ExitUtils.getInstance().exit(SpecificPointRoomActivity.this);
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

//    /**
//     * 调节多媒体音量
//     * @param keyCode
//     * @param event
//     * @return
//     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_VOLUME_UP:
//                audio.adjustStreamVolume(
//                        AudioManager.STREAM_MUSIC,
//                        AudioManager.ADJUST_RAISE,
//                        AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
//                return true;
//            case KeyEvent.KEYCODE_VOLUME_DOWN:
//                audio.adjustStreamVolume(
//                        AudioManager.STREAM_MUSIC,
//                        AudioManager.ADJUST_LOWER,
//                        AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
//                return true;
//            default:
//                break;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


}
