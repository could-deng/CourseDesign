package com.example.coursedesign.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by yuanqiang on 2016/3/18.
 */
public class continents extends BmobObject {
    private int continents_id;
    private String continents_name;

    public continents(int continents_id, String continents_name) {
        this.continents_id = continents_id;
        this.continents_name = continents_name;
    }

    public int getContinents_id() {
        return continents_id;
    }

    public void setContinents_id(int continents_id) {
        this.continents_id = continents_id;
    }

    public String getContinents_name() {
        return continents_name;
    }

    public void setContinents_name(String continents_name) {
        this.continents_name = continents_name;
    }
}
