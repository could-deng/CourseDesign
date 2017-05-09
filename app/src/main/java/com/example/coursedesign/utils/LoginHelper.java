package com.example.coursedesign.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.coursedesign.CourseDesignAPP;
import com.example.coursedesign.common.CommUtil;
import com.example.coursedesign.common.Constants;

/**
 * Created by yuanqiang on 2016/3/21.
 */
public class LoginHelper {
    /**
     * 判断用户是否登录
     * @param context
     * @return true代表登录过，参数可以从内存中获取，否则没有登录过；
     */
    public static boolean isLogined(Context context)
    {
        // 1.判断SharedPreference中是否有登录信息，有则返回true,否则返回false;(注意登出后一定要销毁掉SharedPre中的数据)
        SharedPreferences preferences = context.getSharedPreferences(Constants.SETTING, 0);
        String username = preferences.getString(Constants.USERNAME, "");
        String password = preferences.getString(Constants.PASSWORD, "");
        if ( CommUtil.isNotBlank(username) && CommUtil.isNotBlank(password))
        {
            //登录成功之后，在内存中存储一份用户信息数据，注销的时候注意，销毁。
            CourseDesignAPP.getInstance().setUsername(username);
            CourseDesignAPP.getInstance().setPassword(password);
            return true;
        }
        // 2.如果在内存和xml文件中都没有用户登录信息，则返回false
        return false;
    }
    /**
     * 登录成功后将用户的信息保存到SharedPre中
     *
     * @param context
     * @param username
     *            用户名
     * @param password
     *            密码
     */
    public static void saveLoginInfo(Context context, String username, String password)
    {
        SharedPreferences preferences = context.getSharedPreferences(Constants.SETTING,0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.USERNAME, username);
        editor.putString(Constants.PASSWORD, password);
        editor.commit();
        /** 同时保存一个数据到Application内存中 **/
        CourseDesignAPP.getInstance().setUsername(username);
        CourseDesignAPP.getInstance().setPassword(password);
    }

    /**
     *用户进行注销的时候  清楚用户登录信息
     *
     * @param context
     */
    public static void clearLoginInfo(Context context)
    {
        // 清除SharedPre中的数据
        SharedPreferences preferences = context.getSharedPreferences(Constants.SETTING, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.USERNAME, "");
        editor.putString(Constants.PASSWORD, "");
        editor.commit();
        // 清除内存中的数据
        CourseDesignAPP.getInstance().setUsername("");
        CourseDesignAPP.getInstance().setPassword("");
    }
}
