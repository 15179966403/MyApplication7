package com.hrc.myapplication.model.biz;

import android.content.Context;

import com.hrc.myapplication.common.LogUtil;

/**
 * 用户管理
 */

public class UserManager {
    public static UserManager userManager;
    private Context context;
    private String imei;    //国际移动设备标识

    private UserManager(Context context){
        this.context=context;
        imei="15179966403";
    }

    public static UserManager getInstance(Context context){
        if (userManager==null)
            userManager=new UserManager(context);
        return userManager;
    }

    //用户登录处理方法
    public void login(String... args){
        LogUtil.d("用户管理","执行登录");
        LogUtil.d("用户管理","用户昵称："+args[0]);
        LogUtil.d("用户管理","用户头像："+args[1]);
        LogUtil.d("用户管理","用户密码："+args[2]);
        LogUtil.d("用户管理","用户设备："+args[3]);
    }
}
