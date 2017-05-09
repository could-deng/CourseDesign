package com.example.coursedesign.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by yuanqiang on 2016/3/21.
 */
public class User extends BmobUser {
    private String user_id;
    private String user_name;
    private String user_pwd;

    public User(String user_name, String user_pwd) {
        super();
        this.user_name = user_name;
        this.user_pwd = user_pwd;
    }

    public String getUser_id() {
        return user_id;
    }


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }
}
