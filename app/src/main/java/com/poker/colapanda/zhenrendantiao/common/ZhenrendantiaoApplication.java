package com.poker.colapanda.zhenrendantiao.common;

import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.poker.colapanda.zhenrendantiao.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import org.litepal.LitePalApplication;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiaomengli on 16/5/16.
 * Application配置
 */
public class ZhenrendantiaoApplication extends LitePalApplication {
    public static Context appContext;
    public static boolean mute = true ;
    //连接超时
    public final static int CONNECTTIMEOUT = 10;
    public final static int READTIMEOUT = 20;
    public final static int WRITETIMEOUT = 10;
    //缓存上限
    public final static int DISKCACHESIZE = 5 * 1024 * 1024;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;

        //网络类初始化
        OkHttpUtils.getInstance().setConnectTimeout(CONNECTTIMEOUT, TimeUnit.SECONDS);
        OkHttpUtils.getInstance().setReadTimeout(READTIMEOUT, TimeUnit.SECONDS);
        OkHttpUtils.getInstance().setWriteTimeout(WRITETIMEOUT, TimeUnit.SECONDS);
        OkHttpUtils.getInstance().setCertificates();
        //极光相关配置
        //JPushInterface.init(this);

        //ImageLoader相关配置全局初始化一次就行
        File cacheDir = StorageUtils.getOwnCacheDirectory(this, "imageloader/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .diskCache(new UnlimitedDiscCache(cacheDir))
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(DISKCACHESIZE)
                .diskCacheFileCount(100)
                .writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(config);
        if (!(boolean) SPUtils.get(this,"mute",false)){
           mute = true;
        }else {
            mute = false;
        }

    }
}
