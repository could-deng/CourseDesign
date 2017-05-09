package com.example.coursedesign.bean;

import java.io.Serializable;
import cn.bmob.v3.BmobObject;

public class Banner extends BmobObject implements Serializable {

    private String title;
    private String backImage;


    private void clear() {
        title = null;
        backImage = null;
    }
    public Banner(){
        clear();
    }


    public Banner(String title, String backImage) {
        this.title = title;
        this.backImage = backImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackImage() {
        return backImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }
}
