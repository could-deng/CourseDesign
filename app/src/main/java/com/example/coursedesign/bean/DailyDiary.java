package com.example.coursedesign.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/4/17.
 */
public class DailyDiary extends BmobObject {
    private String pic_news_objectid;
    private String text;
    private String pic_url;


    public DailyDiary(String pic_news_objectid, String text, String pic_url) {
        this.pic_news_objectid = pic_news_objectid;
        this.text = text;
        this.pic_url = pic_url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
}
