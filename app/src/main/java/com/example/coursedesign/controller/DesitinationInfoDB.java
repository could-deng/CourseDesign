package com.example.coursedesign.controller;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.coursedesign.CourseDesignAPP;
import com.example.coursedesign.bean.City;
import com.example.coursedesign.bean.Country;
import com.example.coursedesign.bean.continents;
import com.example.coursedesign.common.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanqiang on 2016/3/29.
 */
public class DesitinationInfoDB {

    private DesitinationInfoDBOpenHelper helperReader;
    private DesitinationInfoDBOpenHelper helperWriter;

    private static DesitinationInfoDB instance;

    public DesitinationInfoDB() {
        super();
    }

    public static DesitinationInfoDB getInstance() {
        if(instance == null){
            instance = new DesitinationInfoDB();
        }
        return instance;
    }

    private SQLiteDatabase getWritableDatabase() {
        //	if (helperWriter == null)
        helperWriter = new DesitinationInfoDBOpenHelper(CourseDesignAPP.getContext());
        return helperWriter.getWritableDatabase();
    }

    private SQLiteDatabase getReadableDatabase() {
        //	if (helperReader == null)
        helperReader = new DesitinationInfoDBOpenHelper(CourseDesignAPP.getContext());
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


    /**
     * 搜索特定国家列表
     *
     * @param continentId 若为-1，则搜索全部热门国家，若不为-1，则为大洲的id
     *@param iLimit 代表返回结果数
     * @return 音乐的id的列表
     */
    public List<Country> getCountryByContinentId(int continentId,int iLimit) {
        String sSql="";
        String sLimit = " order by country_id asc ";
        if(continentId == Constants.SEARCH_HOT_COUNTRY){
            sSql = "select * from Country where is_popolar = 1";
        }
        else{
            sSql = "select * from Country where continents_id = " + continentId;
        }
        if (iLimit > 0)
            sLimit += " limit 0, " + iLimit;
        sSql+=sLimit;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sSql, null);
        if (cursor == null || cursor.isClosed()) {
            db.close();
            return null;
        }
        List<Country> list = new ArrayList<>();
        try {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                boolean ispopular = false;
                if(cursor.getInt(cursor.getColumnIndex("is_popolar")) == 1){
                    ispopular = true;
                }
                Country country = new Country(cursor.getInt(cursor.getColumnIndex("country_id")), cursor.getString(cursor.getColumnIndex("country_name")),
                        cursor.getString(cursor.getColumnIndex("country_img")), cursor.getInt(cursor.getColumnIndex("continents_id")),
                        ispopular);
                list.add(country);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return list;
    }

    /**
     * 根据国家ID获取全部城市列表
     * @param country_id
     * @return
     */
    public List<City> getCityByCountryId(int country_id){
        String sSql="";
        sSql = "select * from City where country_id = " + country_id;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sSql, null);
        if (cursor == null || cursor.isClosed()) {
            db.close();
            return null;
        }
        List<City> list = new ArrayList<>();
        try {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                boolean isselected = false;
                int ll =(cursor.getInt(cursor.getColumnIndex("isSelected")));
                if((cursor.getInt(cursor.getColumnIndex("isSelected"))) == 1 ){
                    isselected = true;
                }
                City city = new City(cursor.getInt(cursor.getColumnIndex("city_id")),cursor.getInt(cursor.getColumnIndex("country_id")), cursor.getString(cursor.getColumnIndex("city_name")),
                        cursor.getString(cursor.getColumnIndex("city_pic_url")),cursor.getInt(cursor.getColumnIndex("people_gone")), cursor.getString(cursor.getColumnIndex("city_info")),
                        cursor.getDouble(cursor.getColumnIndex("laititude")),cursor.getDouble(cursor.getColumnIndex("longtitude")),isselected);
                list.add(city);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return list;
    }

    public List<continents> getContinents(){
        String sSql="";
        sSql = "select * from continents";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sSql, null);
        if (cursor == null || cursor.isClosed()) {
            db.close();
            return null;
        }
        List<continents> list = new ArrayList<>();
        try {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                continents con = new continents(cursor.getInt(cursor.getColumnIndex("continents_id")),cursor.getString(cursor.getColumnIndex("continents_name")));
                list.add(con);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return list;
    }
    /**
     * 更新
     * @param ischecked
     */
    public boolean updateCitySelecte(City city,boolean ischecked){
        if (city == null)
            return false;
        Integer checked =ischecked?1:0;
        String sSql = "update City set isSelected="
                + checked;
        sSql += " where city_id=" + city.getCity_id();

        return executeSql(sSql);
    }
    public boolean restoreCity(){
        String sSql = "update City set isSelected="
                + 0;
//        sSql += " where city_id=" + city.getCity_id();
        return executeSql(sSql);
    }

    public boolean addCountryList(List<Country> list) {
        if ((list == null) || (list.size() <= 0))
            return false;
        for (int i = 0; i < list.size(); i++) {
            if (checkCounryExist(list.get(i)))
                continue;
            addCountry(list.get(i));
        }
        return true;
    }
    public boolean checkCounryExist(Country country) {
        if (country == null)
            return false;
        String sSql = "select country_id from Country where country_id=" + country.getCountry_id();
        return isExistBySql(sSql);
    }

    /**
     * 添加国家
     *
     */
    public boolean addCountry(Country country) {
        String sSql = "select * from Country where country_id =" + country.getCountry_id();
        if (isExistBySql(sSql)) return true;
        sSql = "insert into Country(country_id, country_name, continents_id, is_popolar, country_img) values(?,?,?,?,?)";
        Object[] array = new Object[]{country.getCountry_id(),country.getCountry_name(),country.getContinents_id(), (country.is_popolar())?1:0,country.getCountry_img()};
        return excuteSql(sSql, array);
    }

    public boolean addCity(City city){
        String sSql = "select * from City where city_id =" +city.getCity_id();
        if (isExistBySql(sSql)) return true;
        sSql = "insert into City(city_id, country_id, city_name, city_pic_url, people_gone, city_info, laititude, longtitude, isSelected) values(?,?,?,?,?,?,?,?,?)";
        Object[] array = new Object[]{city.getCity_id(), city.getCountry_id(),city.getCity_name(), city.getCity_pic_url(),
            city.getPeople_gone(),city.getCity_info(),city.getLaititude(),city.getLongtitude(),0};//新增数据时默认加最后一个字段（未选中）
        return excuteSql(sSql, array);
    }

    public boolean addContinents(continents con){
        String sSql = "select * from continents where continents_id =" +con.getContinents_id();
        if (isExistBySql(sSql)) return true;
        sSql = "insert into continents(continents_id, continents_name) values(?,?)";
        Object[] array = new Object[]{con.getContinents_id(), con.getContinents_name()};
        return excuteSql(sSql, array);
    }

    public boolean addContinentList(List<continents> list) {
        if ((list == null) || (list.size() <= 0))
            return false;
        for (int i = 0; i < list.size(); i++) {
            if (checkContinentExist(list.get(i)))
                continue;
            addContinents(list.get(i));
        }
        return true;
    }
    public boolean checkContinentExist(continents con) {
        if (con == null)
            return false;
        String sSql = "select continents_id from continents where continents_id=" + con.getContinents_id();
        return isExistBySql(sSql);
    }

    public boolean addCityList(List<City> list) {
        if ((list == null) || (list.size() <= 0))
            return false;
        for (int i = 0; i < list.size(); i++) {
            if (checkCityExist(list.get(i)))
                continue;
            addCity(list.get(i));
        }
        return true;
    }
    public boolean checkCityExist(City city) {
        if (city == null)
            return false;
        String sSql = "select city_id from City where city_id=" + city.getCity_id();
        return isExistBySql(sSql);
    }


    /**
     * 执行SQL语句
     * @param sql
     * @return
     */
    private boolean executeSql(String sql) {
        return excuteSql(sql, null);
    }

    /**
     * 执行SQL语句
     * @param sql
     * @param array
     * @return
     */
    private boolean excuteSql(String sql, Object[] array) {
        if (sql == null || sql.isEmpty())
            return false;

        boolean bSuccess = false;
        Log.d(CourseDesignAPP.TAG, sql);
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            if (array == null) db.execSQL(sql);
            else db.execSQL(sql, array);
            bSuccess = true;
        } catch (SQLiteException e) {
            bSuccess = false;
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
        return bSuccess;

    }

    /**
     * 判断数据库是否已经存在该数据
     * @param sSql 执行搜索集合的sql
     * @return
     */
    private boolean isExistBySql(String sSql) {
        if (sSql == null || sSql.isEmpty())
            return false;

        Cursor cursor;
        SQLiteDatabase db = getReadableDatabase();
        cursor = db.rawQuery(sSql, null);
        if (cursor == null || cursor.isClosed())
            return false;
        boolean bExist = false;
        try {
            if (cursor.getCount() > 0)
                bExist = true;
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return bExist;

    }
}
