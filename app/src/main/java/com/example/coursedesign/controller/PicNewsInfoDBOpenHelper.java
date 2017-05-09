package com.example.coursedesign.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.coursedesign.common.Constants;

/**
 * Created by yuanqiang on 2016/4/12.
 */
public class PicNewsInfoDBOpenHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 5;
    public PicNewsInfoDBOpenHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createTables(SQLiteDatabase db) {
        String sSqlCreateMusicInfo = "create table if not exists MusicInfo(Id integer primary key, AlbumName String, AlbumUrl String, AlbumUrl2 String, Author String, Bpm String, "
                + "EncodingType String, Name String, Scene String, Genre String, TimeStamp String, TrackLength integer, Url String, Introduction String, DownloadCount integer, CollectCount integer, ShareCount integer,IsDown integer, IsCollect String, LocaleFlag integer)";
        db.execSQL(sSqlCreateMusicInfo);
    }

}
