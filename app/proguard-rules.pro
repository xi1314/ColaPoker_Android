#Add project specific ProGuard rules here.
#By default, the flags in this file are appended to flags specified
#in D:\android\Android\sdk/tools/proguard/proguard-android.txt
#You can edit the include path and order by changing the proguardFiles
#directive in build.gradle.
#
#For more details, see
#http://developer.android.com/guide/developing/tools/proguard.html
#
#Add any project specific keep options here:
#
# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {public *;}

-keepclassmembers class * extends android.app.Activity {                        # 保持自定义控件类不被混淆
   public void *(android.view.View);
}

-keep class com.poker.colapanda.zhenrendantiao.live.model.**{ *; }
-keep class com.poker.colapanda.zhenrendantiao.common.network.Result{ *; }
-keep public class * implements java.io.Serializable {*;}
#保持R文件不被混淆
-keep public class [com.poker.colapanda.zhenrendantiao].R$*{
public static final int *;
}
-keepclassmembers class ** {
    public void onEvent*(**);
    void onEvent*(**);
}
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}
-dontwarn android.content.Context
# #okhttp
#-dontwarn com.squareup.okhttp.**
#-keep class com.squareup.okhttp.** { *;}
#-dontwarn okio.**

#okhttputils
-dontwarn com.zhy.http.**
-keep class com.zhy.http.**{*;}
-keep interface com.zhy.http.**{*;}

#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-keep interface okhttp3.**{*;}

#okio
-dontwarn okio.**
-keep class okio.**{*;}
-keep interface okio.**{*;}

#litepal
-dontwarn org.litepal.*
-keep class org.litepal.** { *; }
-keep enum org.litepal.**
-keep interface org.litepal.** { *; }
-keep public class * extends org.litepal.**


##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
# Application classes that will be serialized/deserialized over Gson
##---------------End: proguard configuration for Gson  ----------

#语音混淆
 -keep class io.agora.**{*;}

 #腾讯云直播
-keep class com.tencent.**{*;}
-dontwarn com.tencent.**
-keep class tencent.**{*;}
-dontwarn tencent.**
-keep class qalsdk.**{*;}
-dontwarn qalsdk.**



