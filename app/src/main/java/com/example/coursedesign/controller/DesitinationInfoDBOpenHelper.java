package com.example.coursedesign.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.coursedesign.common.Constants;

/**
 * Created by yuanqiang on 2016/3/29.
 */
public class DesitinationInfoDBOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public DesitinationInfoDBOpenHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null,DATABASE_VERSION);
    }

    private void createTables(SQLiteDatabase db){
        //国家表
        String sSqlCreateCountry = "create table if not exists Country(country_id integer primary key,country_name String,continents_id integer,is_popolar integer,country_img String)";
        //城市表
        String sSqlCreateCity = "create table if not exists City(city_id Integer primary key,country_id Integer,city_name String,city_pic_url String,people_gone Integer,city_info String,laititude long,longtitude long,isSelected Integer)";
        //大洲表
        String sSqlCreateContinents = "create table if not exists continents(continents_id integer primary key,continents_name String)";
        //很多表
        db.execSQL(sSqlCreateCountry);
        db.execSQL(sSqlCreateCity);
        db.execSQL(sSqlCreateContinents);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
