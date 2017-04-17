package com.poker.colapanda.zhenrendantiao.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.ClipboardManager;
import android.widget.Toast;

import com.poker.colapanda.zhenrendantiao.common.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * APP通用工具类
 * Created by xiaomengli on 16/5/17.
 */
public class CommonUtils {
    /**
     * isMobie
     * 手机号是否合法验证
     *
     * @param mobiles 手机号
     * @return 返回值
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^1([3][0-9])\\d{8}$|^1([4][7])\\d{8}$|^1" +
                "([5][^4,\\\\D])\\d{8}$|^1([7][0-9])\\d{8}$|^1([8][0-9])\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证密码是否是数字与字母的组合
     *
     * @param password 密码
     * @return 返回值
     */
    public static boolean isPassword(String password) {
        boolean ret;
        Pattern p = Pattern.compile("\\w{6,16}+");
//        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z" +
//                "!@#$%\\^&*(){}\\[\\]+-=|;:'<,>.?/]{6,12}$");
        Matcher m = p.matcher(password);
        ret = m.matches();
        return ret;
    }
    /**
     *
     * 验证码是否符合4
     *
     * @param checkcodes 验证码
     * @return 返回值
     */
    public static boolean isCheckCode(String checkcodes) {
        Pattern p = Pattern
                .compile("^\\d{6}$");
        Matcher m = p.matcher(checkcodes);
        return m.matches();
    }
    public static boolean isEmail(String email) {
        Pattern p = Pattern
                .compile("^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    //添加Activity 入栈
    public static void addActivity(Activity activity) {
        ExitUtils.getInstance().addActivity(activity);
    }

    //移除Activity 出栈
    public static void removeActivity(Activity activity) {
        ExitUtils.getInstance().removeActivity(activity);
    }




    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f" };

    /**
     *
     * @Method: byteArrayToHexString
     * @Description: (转换字节数组为16进制字串 )
     * @param b 字节数组
     * @return String (16进制字串 )
     * @author : kangzhenhua
     * @CreateDate : 2016年8月1日 下午7:39:29
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }

    /**
     *
     * @Method: byteToHexString
     * @Description: (转换byte到16进制 )
     * @param b 要转换的byte
     * @return String (16进制格式 )
     * @author : kangzhenhua
     * @CreateDate : 2016年8月1日 下午7:40:07
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     *
     * @Method: MD5Encode
     * @Description: (MD5编码 )
     * @param origin 原始字符串
     * @return String (经过MD5加密之后的结果 )
     * @author : kangzhenhua
     * @CreateDate : 2016年8月1日 下午7:40:40
     */
    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(resultString.getBytes("UTF-8"));
            resultString = byteArrayToHexString(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }









    /**
     * 多个标题栏切换Fragment通用方法
     *
     * @param mContent 用于置换页面的第三个参数
     * @param from     当前Fragment
     * @param to       切换后的Fragment
     * @param FrameId  FrameLayout的Id
     * @param ctx      Activity的上下文
     */
    public static Fragment switchFragmentContent(Fragment mContent, Fragment from, Fragment to, int FrameId, FragmentActivity ctx) {
        if (mContent != to) {
            mContent = to;
            FragmentTransaction tran = ctx.getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) {
                tran.hide(from).add(FrameId, to);
            } else {
                tran.hide(from).show(to);
            }
            tran.commitAllowingStateLoss();
        }
        return mContent;
    }

    /**
     * 打电话
     *
     * @param callNum
     * @param ctx
     * @return
     */
    public static boolean callPhone(String callNum, Context ctx) {
        try {
            Uri uri = Uri.parse("tel:" + callNum);
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            ctx.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(ctx, "抱歉，拨打电话失败！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //dp转px
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) (dipValue * (scale / 160));
    }

    //获取图库图片
    public static String getPictirePath(Uri imageUri, Context context) {
        String picturePath = "";
        if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(imageUri, filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();
            }
        } else {
            picturePath = imageUri.getPath();
        }
        return picturePath;
    }

    //判断服务是否启动
    public static boolean isWorked(String className, Context context) {
        ActivityManager myManager = (ActivityManager) context
                .getApplicationContext().getSystemService(
                        Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    //判断服务是否启动
    public static boolean isServiceRunning(String className, Context mContext) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList
                = activityManager.getRunningServices(100);
        if (!(serviceList.size()>0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            //Toast.makeText(mContext,"服务名称:" + serviceList.get(i).service.getClassName() + "",Toast.LENGTH_SHORT).show();
            if (serviceList.get(i).service.getClassName().equals(className)) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    /**
     * 检测对应系统
     * @param propName
     * @return
     */
    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(
                    new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
        return line;
    }

    /**
     * 检测其它系统信息
     *
     * @param context
     * @return
     */
    public static boolean checkSamsungSystem(Context context) {
        if (!CommonUtils.getSystemProperty("ro.samsung.ui.version.name").equals(null)) {
            String phoneInfo = CommonUtils.getSystemProperty("ro.samsung.ui.version.name");
            if (phoneInfo.length() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测网络是否可用
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 复制文字到剪切板上面
     * @param content 复制内容
     * @param context 上下文获得管理器
     */
    public static boolean copy(String content, Context context) {
        boolean ret = true;
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        if (cmb.getText() != null) {
            if (!cmb.getText().toString().equals(content)) {
                cmb.setText(content);
                ret = true;
            } else {
                ret = false;
            }
        } else {
            cmb.setText(content);
            ret = true;
        }
        return ret;
    }

    /**
     * 权限检查
     * @param context 上下文
     * @param permission 权限
     * @return 是否有这个权限
     */
    public static boolean checkPermission(Context context, String permission) {
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * 多少秒转化为时间格式
     * @param second
     * @return
     */
    public static String formatSecond(double second){
        String  html="0秒";
        if(second >= 0){
            Double s=(Double) second;
            String format;
            Object[] array;
            Integer hours =(int) (s/(60*60));
            Integer minutes = (int) (s/60-hours*60);
            Integer seconds = (int) (s-minutes*60-hours*60*60);
            if(hours>0){
                if (minutes >= 10 && seconds >= 10) {
                    format = "%1$,d:%2$,d:%3$,d";
                    array = new Object[]{hours, minutes, seconds};
                } else if (minutes >= 10 && seconds < 10) {
                    format = "%1$,d:%2$,d:0%3$,d";
                    array = new Object[]{hours, minutes, seconds};
                } else if (minutes < 10 && seconds >= 10) {
                    format = "%1$,d:0%2$,d:%3$,d";
                    array = new Object[]{hours, minutes, seconds};
                } else {
                    format = "%1$,d:0%2$,d:0%3$,d";
                    array = new Object[]{hours, minutes, seconds};
                }
            } else if(minutes>0){
                if (minutes >= 10 && seconds >= 10) {
                    format="00:%1$,d:%2$,d";
                    array=new Object[]{minutes,seconds};
                } else if (minutes >= 10 && seconds < 10) {
                    format="00:%1$,d:0%2$,d";
                    array=new Object[]{minutes,seconds};
                } else if (minutes < 10 && seconds >= 10) {
                    format="00:0%1$,d:%2$,d";
                    array=new Object[]{minutes,seconds};
                } else {
                    format="00:0%1$,d:0%2$,d";
                    array=new Object[]{minutes,seconds};
                }
            }else{
                if (seconds >= 10) {
                    format = "00:00:%1$,d";
                    array = new Object[]{seconds};
                } else {
                    format = "00:00:0%1$,d";
                    array = new Object[]{seconds};
                }
            }
            html= String.format(format, array);
        }
        return html;
    }

    /**
     * 余额保留两位小数
     * @param money 余额
     * @return
     */
    public static String formatDouble(int money) {
        double s = money / 100.00;
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(s);
    }

    /**
     * Activite跳转
     */
    public static void jump(Activity activity,Class cla){
        Intent intent = new Intent(activity,cla);
        activity.startActivity(intent);
    }
    /**
     * Activite跳转带参数
     */
    public static void jumps(Activity activity,Class cla,Object object){
        Intent intent = new Intent(activity,cla);
        intent.putExtra("token", (String) object);
        activity.startActivity(intent);
    }
    /**
     * 获得统一上传参数
     *
     * @return
     */
    public static HashMap<String, String> getUpHashMap() {
        HashMap<String, String> map = new HashMap<>();
        //设备类型
        map.put("platform", Constants.Comm.PLATFORM);
        //版本号
        map.put("version",Constants.Comm.VERSION_NAME);
        return map;
    }


}
