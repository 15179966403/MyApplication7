package com.hrc.myapplication.common;

import android.content.Context;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公共工具
 */

public class CommonUtil {
    public static final String APPURL="";
    public static final int VERSION_CODE=1;

    public static String getSystime(){
        String systime;
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddhhmmss",Locale.CHINA);
        systime=dateFormat.format(new Date(System.currentTimeMillis()));
        return systime;
    }

    /**
     * 注释文件长度
     * @param filesize 文件原始长度
     * @return 对应的B,K,M,G大小
     */
    public static String getFileSize(long filesize){
        DecimalFormat df=new DecimalFormat("#.00");
        StringBuffer sb=new StringBuffer();
        if (filesize<1034){
            sb.append(filesize);
            sb.append(" B");
        }else if (filesize<1048576){
            sb.append(df.format((double)filesize/1024));
            sb.append(" K");
        }else if (filesize<1073741834){
            sb.append(df.format((double)filesize/1048576));
            sb.append(" M");
        }else {
            sb.append(df.format((double)filesize/1073741834));
            sb.append(" G");
        }
        return sb.toString();
    }

    /**
     * 获取当前日期
     * @return 20170421
     */
    public static String getDate(){
        Date date=new Date(System.currentTimeMillis());
        String strs="";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd",Locale.CHINA);
        strs=sdf.format(date);
        return strs;
    }

    /**
     * 验证邮箱格式
     * @param email 输入的邮箱
     * @return 如果格式为邮箱的格式，则返回true
     */
    public static boolean verifyEmail(String email){
        Pattern pattern=Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)" +
                "|(([a-zA-Z0-9\\-]+\\.)+))" +
                "([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher matcher=pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean verifyPassword(String password){
        Pattern pattern=Pattern.compile("^[a-zA-Z0-9]{6,16}$");
        Matcher matcher=pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * 获取当前的版本号
     * @param context 上下文对象
     * @return 版本号
     */
    public static int getVersionCode(Context context){
        return 0;
    }
}
