package com.poker.colapanda.zhenrendantiao.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.poker.colapanda.zhenrendantiao.R;
import com.poker.colapanda.zhenrendantiao.common.widget.BaseActivity;
import com.poker.colapanda.zhenrendantiao.common.music.ClickMusic;
import com.poker.colapanda.zhenrendantiao.common.Constants;
import com.poker.colapanda.zhenrendantiao.common.InnerRecevier;
import com.poker.colapanda.zhenrendantiao.common.OFFReceiver;
import com.poker.colapanda.zhenrendantiao.common.network.ResultError;
import com.poker.colapanda.zhenrendantiao.login.model.Open;
import com.poker.colapanda.zhenrendantiao.utils.CommonUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Request;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    private EditText registerEtAccount;//手机号
    private EditText registerEtPassWord;//密码
    private EditText registerEtAgency;//代理
    private EditText registerEtCode;//验证码
    private Button registerBtSendCode;//发送
    private Button registerBtOk;//确定
    private Button registerBtCancel;//取消
    private String account,passWord,code,agency;
    private ProgressDialog upDialog;
    private TimeCount time;
    private ClickMusic clickMusic = new ClickMusic();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        CommonUtils.addActivity(this);
        initView();
        setClickListener();
        //广播 home键
        registerReceiver(new InnerRecevier(), new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        //广播 屏幕是否黑屏
        registerReceiver(new OFFReceiver(), new IntentFilter(Intent.ACTION_SCREEN_OFF));
    }

    private void setClickListener() {
        registerBtSendCode.setOnClickListener(this);
        registerBtOk.setOnClickListener(this);
        registerBtCancel.setOnClickListener(this);
    }

    private void initView() {
        registerEtAccount = (EditText) findViewById(R.id.register_et_account);
        registerEtPassWord = (EditText) findViewById(R.id.register_et_password);
        registerEtCode = (EditText) findViewById(R.id.register_et_code);
        registerBtSendCode = (Button) findViewById(R.id.register_bt_send_code);
        registerBtOk = (Button) findViewById(R.id.register_bt_ok);
        registerBtCancel = (Button) findViewById(R.id.register_bt_cancel);
        registerEtAgency = (EditText) findViewById(R.id.register_et_agency);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_bt_send_code:
                account = registerEtAccount.getText().toString().trim();
                verification();

                break;
            case R.id.register_bt_ok:
                clickMusic.start(this);
                account = registerEtAccount.getText().toString().trim();
                passWord = registerEtPassWord.getText().toString().trim();
                code = registerEtCode.getText().toString().trim();
                if (registerEtAgency.getText().toString().trim().equals(null)){
                    agency = "";
                }else {
                    agency = registerEtAgency.getText().toString().trim();
                }
                verifications();

                break;
            case R.id.register_bt_cancel:
                clickMusic.start(this);
                finish();
                break;
        }
    }
    /**
     * 验证账号密码是否为空是否符合规定
     */
    private void verifications() {
        if (account.equals("")) {
            Toast.makeText(RegisterActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
        } else if (!CommonUtils.isMobileNO(account)) {
            Toast.makeText(RegisterActivity.this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
        } else if (code.equals("")) {
            Toast.makeText(RegisterActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
        } else if (!CommonUtils.isCheckCode(code)) {
            Toast.makeText(RegisterActivity.this, "验证码格式不正确", Toast.LENGTH_SHORT).show();
        } else if (passWord.equals("")) {
            Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
        } else if (!CommonUtils.isPassword(passWord)) {
            Toast.makeText(RegisterActivity.this, "密码格式不正确", Toast.LENGTH_SHORT).show();
        } else {
            //提交注册信息
            getRegisterData();
        }
    }

    /**
     * 注册请求
     */
    private void getRegisterData() {
        upDialog = new ProgressDialog(RegisterActivity.this);
        upDialog.setMessage("请稍后...");
        upDialog.setCancelable(false);
        HashMap<String,String> map = new HashMap<>();
        map.put("phone",account);
        map.put("password",passWord);
        map.put("captcha",code);
        map.put("agent",agency);
        OkHttpUtils.post().params(map).url(Constants.Comm.HOST_REGISTER).build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                upDialog.show();
            }
            @Override
            public void onAfter() {
                super.onAfter();
                upDialog.dismiss();

            }
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(RegisterActivity.this, ResultError.MESSAGE_NULL, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                if (response == null) {
                    Toast.makeText(RegisterActivity.this, ResultError.MESSAGE_ERROR, Toast.LENGTH_SHORT).show();
                } else {

                    try {
                        JSONObject object = new JSONObject(response);
                        String success = object.getString("success");
                        if (success.equals("true")){
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            String message = object.getString("message");
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }
    /**
     * 验证账号是否为空是否符合规定
     */
    private void verification() {
        if (account.equals("")) {
            Toast.makeText(RegisterActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            if (!CommonUtils.isMobileNO(account)) {
                Toast.makeText(RegisterActivity.this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
            } else {
                //发送验证码
                getCodeData();
            }
        }
    }

    /**
     * 验证码请求
     */
    private void getCodeData() {
        HashMap<String,String> map = new HashMap<>();
        map.put("phone",account);
        OkHttpUtils.post().params(map).url(Constants.Comm.HOST_REGISTER_CODE).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(RegisterActivity.this, ResultError.MESSAGE_NULL, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                if (response == null) {
                    Toast.makeText(RegisterActivity.this, ResultError.MESSAGE_ERROR, Toast.LENGTH_SHORT).show();
                } else {

                    try {
                        JSONObject object = new JSONObject(response);
                        String success = object.getString("success");
                        if (success.equals("true")){
                            time = new TimeCount(30000,1000);
                            time.start();
                        }else {
                            Toast.makeText(RegisterActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
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

    class  TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程显示
        @Override
        public void onTick(long millisUntilFinished) {
            registerBtSendCode.setEnabled(false);
            registerBtSendCode.setTextColor(getResources().getColor(R.color.color_gray));
            registerBtSendCode.setText("成功 " + String.valueOf(millisUntilFinished / 1000)+"S");
        }
        //计时完成触发
        @Override
        public void onFinish() {
            registerBtSendCode.setEnabled(true);
            registerBtSendCode.setTextColor(getResources().getColor(R.color.log_bt_color));
            registerBtSendCode.setText("立即发送");
        }
    }
}
