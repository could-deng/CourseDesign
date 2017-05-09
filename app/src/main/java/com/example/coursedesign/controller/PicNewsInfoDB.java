package com.example.coursedesign.controller;

import android.database.sqlite.SQLiteDatabase;

import com.example.coursedesign.CourseDesignAPP;

/**
 * Created by yuanqiang on 2016/4/12.
 */
public class PicNewsInfoDB {
    public static final int MUSIC_LOCALE_START_ID = 100000;
    private PicNewsInfoDBOpenHelper helperReader;
    private PicNewsInfoDBOpenHelper helperWriter;


    private static PicNewsInfoDB instance;

    public PicNewsInfoDB() {
        super();
    }

    public static PicNewsInfoDB getInstance() {
        if (instance == null) {
            instance = new PicNewsInfoDB();
        }
        return instance;
    }

    private SQLiteDatabase getWritableDatabase() {
        //	if (helperWriter == null)
        helperWriter = new PicNewsInfoDBOpenHelper(CourseDesignAPP.getContext());
        return helperWriter.getWritableDatabase();
    }

    private SQLiteDatabase getReadableDatabase() {
        //	if (helperReader == null)
        helperReader = new PicNewsInfoDBOpenHelper(CourseDesignAPP.getContext());
        return helperReader.getReadableDatabase();
    }

    public void close() {
        if (helperReader != null)
            helperReader.close();
        helperReader = null;
        if (helperWriter != null)
            helperWriter.close();
        helperWriter = null;
    }
}
