package com.poker.colapanda.zhenrendantiao.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;



import java.util.LinkedList;
import java.util.List;


/**
 * Activity进栈出栈的控制类
 * Created by xiaomengli on 16/5/17.
 */
public class ExitUtils {
    private List<Activity> activityList = new LinkedList<Activity>();
    private static ExitUtils exitUtils;

    private ExitUtils() {

    }
    //单例
    public static ExitUtils getInstance() {
        if (exitUtils == null) {
            exitUtils = new ExitUtils();
        }
        return exitUtils;
    }

    //入栈方法
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    //出栈方法
    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    //退出App
    public void exit(Context context) {
        for (Activity activity : activityList) {
            activity.finish();
        }
//        Intent intents = new Intent(YidaApplication.appContext, ReceiveMessageService.class);
//        YidaApplication.appContext.stopService(intents);
//        MobclickAgent.onKillProcess(context);
        System.exit(0);
    }

//    //单点登录
//    public void LoginOut(Context context) {
//        while (activityList.size() > 2) {
//            activityList.get(2).finish();
//        }
//        //System.exit(0);
//        Intent intents = new Intent(YidaApplication.appContext, ReceiveMessageService.class);
//        YidaApplication.appContext.stopService(intents);
//        User user = User.getInstance();
//        user.userType = User.TYPE_DEFAULT_USER;
//        user.uid = "";
//        User.saveUser(user, YidaApplication.appContext);
//        EventBus.getDefault().post(user);
//        Logout logout = new Logout();
//        TiOnline tiOnline = new TiOnline();
//        logout.setPosition(0);
//        EventBus.getDefault().post(logout);
//        EventBus.getDefault().post(tiOnline);
//    }

//    //连退两页
//    public void BackSafty() {
//        if (activityList.size() > 2) {
//            activityList.get(activityList.size() - 1).finish();
//            activityList.get(activityList.size() - 2).finish();
//        }
//    }
//
//    //回首页
//    public void BackHome() {
//        for (Activity activity : activityList) {
//            if (activity instanceof ChatActivity) {
//                activity.finish();
//            }
//        }
//    }
}
