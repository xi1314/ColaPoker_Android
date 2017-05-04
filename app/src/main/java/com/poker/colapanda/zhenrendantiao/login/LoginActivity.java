package com.poker.colapanda.zhenrendantiao.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.poker.colapanda.zhenrendantiao.R;
import com.poker.colapanda.zhenrendantiao.common.Constants;
import com.poker.colapanda.zhenrendantiao.common.OFFReceiver;
import com.poker.colapanda.zhenrendantiao.common.music.ClickMusic;
import com.poker.colapanda.zhenrendantiao.common.music.MusicServer;
import com.poker.colapanda.zhenrendantiao.common.network.ResultError;
import com.poker.colapanda.zhenrendantiao.common.widget.BaseActivity;
import com.poker.colapanda.zhenrendantiao.login.model.Home;
import com.poker.colapanda.zhenrendantiao.login.model.Open;
import com.poker.colapanda.zhenrendantiao.utils.CommonUtils;
import com.poker.colapanda.zhenrendantiao.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 登录界面
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText loginEtAccount;//账号
    private EditText loginEtPassWord;//密码
    private Button loginIvLogin;//登录
    private Button loginIvRegister;//注册
    private TextView loginSurrogate;//代理登录
    private TextView loginRetrievePassWord;//找回密码

    private String account, passWord;
    private ProgressDialog upDialog;
    public static Intent Serviceintent;
    public static boolean open = true;
    private ClickMusic clickMusic = new ClickMusic();
    public long backtime = 0;
    private String updateUrl;
    private boolean intents;//是不是跳转玩法页面


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Serviceintent = new Intent(LoginActivity.this, MusicServer.class);//背景音乐服务
        startService(Serviceintent);
        CommonUtils.addActivity(this);
        initView();
        setClickListener();
        EventBus.getDefault().register(this);
//        //广播 home键
//        registerReceiver(new InnerRecevier(), new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        //广播 屏幕是否黑屏
        registerReceiver(new OFFReceiver(), new IntentFilter(Intent.ACTION_SCREEN_OFF));
    }

    /**
     * 点击事件
     */
    private void setClickListener() {
        loginIvLogin.setOnClickListener(this);
        loginIvRegister.setOnClickListener(this);
        loginSurrogate.setOnClickListener(this);
        loginRetrievePassWord.setOnClickListener(this);
    }

    /**
     * 初始化View
     */
    private void initView() {
        loginEtAccount = (EditText) findViewById(R.id.login_et_account);
        if (!SPUtils.get(this, Constants.Comm.PHONE, "").equals("")) {
            loginEtAccount.setText((String) SPUtils.get(this, Constants.Comm.PHONE, ""));
            loginEtAccount.setSelection(loginEtAccount.getText().length());
        }
        loginEtPassWord = (EditText) findViewById(R.id.login_et_password);
        if (!SPUtils.get(this, Constants.Comm.PASSWORD, "").equals("")) {
            loginEtPassWord.setText((String) SPUtils.get(this, Constants.Comm.PASSWORD, ""));
            loginEtPassWord.setSelection(loginEtPassWord.getText().length());
        }
        loginIvLogin = (Button) findViewById(R.id.login_iv_login);
        loginIvRegister = (Button) findViewById(R.id.login_iv_register);
        loginSurrogate = (TextView) findViewById(R.id.login_surrogate);
        loginRetrievePassWord = (TextView) findViewById(R.id.login_retrieve_pass_word);

        loginSurrogate.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG);
        loginSurrogate.getPaint().setAntiAlias(true);
        loginRetrievePassWord.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG);
        loginRetrievePassWord.getPaint().setAntiAlias(true);
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        Uri content_url;
        switch (view.getId()) {
            case R.id.login_iv_login:
                clickMusic.start(this);
                account = loginEtAccount.getText().toString().trim();
                passWord = loginEtPassWord.getText().toString().trim();
                verification();
                break;
            case R.id.login_iv_register:
                clickMusic.start(this);
                CommonUtils.jump(this, RegisterActivity.class);
                intents = true;
                break;
            case R.id.login_surrogate:
                intent.setAction("android.intent.action.VIEW");
                content_url = Uri.parse("http://hvl.com.cn/agent");
                intent.setData(content_url);
                startActivity(intent);
                break;
            case R.id.login_retrieve_pass_word:
                intent.setAction("android.intent.action.VIEW");
                content_url = Uri.parse("http://hvl.com.cn/forget");
                intent.setData(content_url);
                startActivity(intent);
                break;
        }
    }

    /**
     * 验证账号密码是否为空是否符合规定
     */
    private void verification() {
        if (account.equals("")) {
            Toast.makeText(LoginActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (!CommonUtils.isMobileNO(account)) {
            Toast.makeText(LoginActivity.this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
            return;
        } else if (passWord.equals("")) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            getLogData();
        }
    }

    /**
     * 登录请求
     */

    private void getLogData() {
        upDialog = new ProgressDialog(LoginActivity.this);
        upDialog.setMessage("请稍后...");
        upDialog.setCancelable(false);
        HashMap<String, String> map = CommonUtils.getUpHashMap();
        map.put("phone", account);
        map.put("password", passWord);
        OkHttpUtils.post().params(map).url(Constants.Comm.HOST_LOGIN).build().execute(new StringCallback() {
            @Override
            public void onAfter() {
                super.onAfter();
                upDialog.dismiss();

            }

            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(LoginActivity.this, ResultError.MESSAGE_NULL, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                if (response == null) {
                    Toast.makeText(LoginActivity.this, ResultError.MESSAGE_ERROR, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        JSONObject object = new JSONObject(response);
                        String success = object.getString("success");

                        if (success.equals("true")) {
                            String token = object.getString("api_token");
                            CommonUtils.jumps(LoginActivity.this, PlaySelectionActivity.class, token);
                            intents = true;
//                            finish();
//                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            //保存账号密码
                            SPUtils.put(LoginActivity.this, Constants.Comm.PHONE, account);
                            SPUtils.put(LoginActivity.this, Constants.Comm.PASSWORD, passWord);

                        } else {
                            String message = object.getString("message");
                            updateUrl = object.getString("updateUrl");
                            if (updateUrl.equals("")){
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            }else {
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                Uri content_url =Uri.parse(updateUrl);
                                intent.setData(content_url);
                                startActivity(intent);
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                upDialog.show();
            }
        });

    }




    protected void onResume() {
        super.onResume();
        intents = false;
        if (!open) {
            startService(Serviceintent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!intents){
            stopService(Serviceintent);
            open = false;
        }


    }

    protected void onDestroy() {
        super.onDestroy();
        clickMusic.stop();
        EventBus.getDefault().unregister(this);
//        stopService(Serviceintent);
        CommonUtils.removeActivity(this);
    }

    public void onEventMainThread(Home event) {
        stopService(Serviceintent);
        open = false;
    }


    public void onEventMainThread(Open event) {
        startService(Serviceintent);
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
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                backtime = t;
                return true;
            }
            finish();
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }
}
