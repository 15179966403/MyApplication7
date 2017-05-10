package com.hrc.myapplication.common;

import android.util.Log;

/**
 * 日志打印工具类
 */

public class LogUtil {
    public static final String TAG="新闻随意看升级版";
    private static final int Verbose=0;
    private static final int Debug=1;
    private static final int Info=2;
    private static final int Warn=3;
    private static final int Error=4;
    private static final int Nothing=5;
    private static final int Leavel=0;

    public static void v(String tag,String msg){
        if (Leavel<=Verbose){
            Log.v(tag,msg);
        }
    }

    public static void v(String msg){
        v(LogUtil.TAG,msg);
    }

    public static void d(String tag,String msg){
        if (Leavel<=Debug){
            Log.d(tag,msg);
    }
    }

    public static void d(String msg){
        d(LogUtil.TAG,msg);
    }

    public static void i(String tag,String msg){
        if (Leavel<=Info){
            Log.i(tag,msg);
        }
    }

    public static void i(String msg){
        i(TAG,msg);
    }

    public static void w(String tag,String msg){
        if (Leavel<=Warn)
            Log.w(tag,msg);
    }

    public static void w(String msg){
        w(TAG,msg);
    }

    public static void e(String tag,String msg){
        if (Leavel<=Error){
            Log.e(tag,msg);
        }
    }

    public static void e(String msg){
        e(TAG,msg);
    }
}
