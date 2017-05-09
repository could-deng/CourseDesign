package com.example.coursedesign.base;

import com.example.coursedesign.bean.Banner;
import com.example.coursedesign.bean.City;
import com.example.coursedesign.bean.Country;
import com.example.coursedesign.bean.DailyDiary;
import com.example.coursedesign.bean.PicNew;
import com.example.coursedesign.bean.Trip;
import com.example.coursedesign.bean.continents;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源中转站
 * Created by yuanqiang on 2016/3/30.
 */
public class MemExchange {
    private List<PicNew> listAlbum;//图片游记内容
    private List<DailyDiary> diary_list; // 游记中日记列表
    private List<Banner> listBanner;//滚轮内容
    private PicNew picNew;//目前进入的游记

    private List<continents> list_continents;//大洲
    private List<Country> list_country;//存储当前选择的某一大洲类型的全部国家列表，当点击其他大洲时，清除现有的缓存，重新存储新的。
    private List<City> list_city;
    private List<Trip> list_trip;




    private static MemExchange instance;
    public MemExchange() {
        clear();
    }

    public void clear(){
        list_continents =null;
        if (listAlbum != null) listAlbum.clear();
        listAlbum = null;
        if (listBanner != null) listBanner.clear();
        listBanner = null;
        picNew = null;
    }
    public static MemExchange getInstance() {
        if (instance == null) {
            instance = new MemExchange();
        }
        return instance;
    }

    //滚轮内容列表
    public List<Banner> getListBanner() {
        if (listBanner == null) listBanner = new ArrayList<>();
        return listBanner;
    }
    public void setListBanner(List<Banner> list) {
        if ((listBanner != null) && (list != listBanner)) listBanner.clear();
        listBanner = list;
    }


    //大洲列表
    public List<continents> getList_continents() {
        if (list_continents == null) list_continents = new ArrayList<>();
        return list_continents;
    }

    public void setList_continents(List<continents> list_continents) {
        if ((this.list_continents != null) && (list_continents != this.list_continents)) this.list_continents.clear();
        this.list_continents = list_continents;
    }

    //游记列表
    public List<PicNew> getListAlbum() {
        if (listAlbum == null) listAlbum = new ArrayList<>();
        return listAlbum;
    }
    public void setListAlbum(List<PicNew> list) {
        if ((listAlbum != null) && (list != listAlbum)) listAlbum.clear();
        listAlbum = list;
    }

    //游记中的日记列表
    public List<DailyDiary> getDiary_list() {
        if(diary_list == null) diary_list = new ArrayList<>();
        return diary_list;
    }

    public void setDiary_list(List<DailyDiary> diary_list) {
        if((diary_list !=null) && this.diary_list != diary_list) this.diary_list.clear();
        this.diary_list = diary_list;
    }

    //国家列表
    public List<Country> getList_country() {
        if(list_country == null) list_country = new ArrayList<>();
        return list_country;
    }
    public void setList_country(List<Country> list_country) {
        if ((this.list_country != null) && (list_country != this.list_country)) this.list_country.clear();
        this.list_country = list_country;
    }

    public void clear_hotDesitination_data(){
        list_country = null;
        list_continents = null;
        list_city = null;
    }
    //目前进入游记
    public PicNew getPicNew() {
        return picNew;
    }
    public void setPicNew(PicNew picNew) {
        this.picNew = picNew;
    }


    public List<City> getList_city() {
        if(list_city == null) list_city = new ArrayList<>();
        return list_city;
    }

    public void setList_city(List<City> list_city) {
        if ((this.list_city != null) && (list_city != this.list_city)) this.list_city.clear();
        this.list_city = list_city;
    }
    public void clearList_city(){
        if(list_city!=null) {
            list_city.clear();
        }
    }
//    将缓存中城市列表是否选择的状态更改
    public boolean updateCitySelected(City city,boolean isChecked){
        for(int i=0;i<getList_city().size();i++){
            if (getList_city().get(i).getCity_id() == city.getCity_id()){
                getList_city().get(i).setSelected(isChecked);
                break;
            }
        }
        return true;
    }

    //当前用户所有的行程列表
    public List<Trip> getList_trip() {
        return list_trip;
    }

    public void setList_trip(List<Trip> list_trip) {
        this.list_trip = list_trip;
    }
    public  void clearTripList(){
        if(list_trip!=null){
            list_trip.clear();
        }
    }
}
