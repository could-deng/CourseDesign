package com.example.coursedesign.bean;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/4/10.
 */
public class Trip extends BmobObject implements Serializable{
    private int start_city;
    private int end_city;
    private long start_time;
    private List<City> city_line;
    private String user_object_id;

    public Trip(int start_city, int end_city, long start_time, List<City> city_line, String user_object_id) {
        this.start_city = start_city;
        this.end_city = end_city;
        this.start_time = start_time;
        this.city_line = city_line;
        this.user_object_id = user_object_id;
    }

    public int getStart_city() {
        return start_city;
    }

    public void setStart_city(int start_city) {
        this.start_city = start_city;
    }

    public int getEnd_city() {
        return end_city;
    }

    public void setEnd_city(int end_city) {
        this.end_city = end_city;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public List<City> getCity_line() {
        return city_line;
    }

    public void setCity_line(List<City> city_line) {
        this.city_line = city_line;
    }
}
