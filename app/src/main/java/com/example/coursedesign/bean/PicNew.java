package com.example.coursedesign.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by yuanqiang on 2016/4/12.
 */
public class PicNew extends BmobObject {
    private String new_title;
    private String new_pic_url;
    private String trip_sum;
    private List<DailyDiary> list_diary;

    public PicNew(String new_title, String new_pic_url, String trip_sum) {
        this.new_title = new_title;
        this.new_pic_url = new_pic_url;
        this.trip_sum = trip_sum;
    }

    public PicNew(String new_title, String new_pic_url, String trip_sum, List<DailyDiary> list_diary) {
        this.new_title = new_title;
        this.new_pic_url = new_pic_url;
        this.trip_sum = trip_sum;
        this.list_diary = list_diary;
    }

    public String getNew_title() {
        return new_title;
    }

    public void setNew_title(String new_title) {
        this.new_title = new_title;
    }

    public String getNew_pic_url() {
        return new_pic_url;
    }

    public void setNew_pic_url(String new_pic_url) {
        this.new_pic_url = new_pic_url;
    }

    public List<DailyDiary> getList_diary() {
        return list_diary;
    }

    public void setList_diary(List<DailyDiary> list_diary) {
        this.list_diary = list_diary;
    }

    public String getTrip_sum() {
        return trip_sum;
    }

    public void setTrip_sum(String trip_sum) {
        this.trip_sum = trip_sum;
    }
}
