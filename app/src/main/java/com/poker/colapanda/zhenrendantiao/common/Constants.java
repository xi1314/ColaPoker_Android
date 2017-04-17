package com.poker.colapanda.zhenrendantiao.common;

import com.poker.colapanda.zhenrendantiao.utils.SDCardUtils;

import java.io.File;

/**
 * Created by bd on 2017/3/8.
 * 全局常量类
 */
public class Constants {
    public static class Comm{
        public static final String BASE_URL = "http://hvl.com.cn/";//线上服务器地址
        public static final String HOST_LOGIN = BASE_URL + "api/signIn";//登录地址
        public static final String HOST_REGISTER_CODE = BASE_URL + "api/captchas";//验证码地址
        public static final String HOST_REGISTER = BASE_URL + "api/signUp";//验证码地址
        public static final String HOST_GAME = BASE_URL + "api/gameData";//获取当前游戏信息
        public static final String HOST_THROW_INTO = BASE_URL + "api/gameCommit";//投注
        public static final String HOST_WITHDAW = BASE_URL + "api/gameRevoke";//撤销
        public static final String VERSION_NAME = "1.0";//版本号
        public static final String PLATFORM = "android";//设备类型
        public static final String PHONE = "phone";//手机号
        public static final String PASSWORD = "password";//密码

        public static final String VOICE_KEY = "0242dd8ffafc469c8edd2d1e0bafcf6b";//语音秘钥





    }
    /**
     * app的存储路径
     */
    public static final String CACHE_PATH;
    public static final String CACHE_TMP_PATH;
    static {
        String root = SDCardUtils.getSDCardPath();
        CACHE_PATH = root + "Zhenrendantiao";
        CACHE_TMP_PATH = CACHE_PATH + File.separator + ".data";
    }
}
