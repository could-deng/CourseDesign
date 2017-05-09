/**
 * 
 */
package com.example.coursedesign.common;

import android.os.Environment;

import java.io.File;

/**
 * @author yuanqiang
 *
 */
public class Constants {

	public static final int NETWORK_TYPE_NONE = 0;
	public static final int NETWORK_TYPE_MOBILE = 1;
	public static final int NETWORK_TYPE_WIFI = 2;


	public static final String DATABASE_NAME = "dyq.db";


	public static String HOME_PATH = Environment.getExternalStorageDirectory() + File.pathSeparator +"courseDesign";
	public static String CAMERA_PATH = "/cameraPic/";
	public static String CLIP_IMAGE = "/clipImage/";
	public static String DEFAULT_BLANK = "";
	public static final String TAG = "CourseDesign";
	public static final String DEFAULT_SPACE = " ";
	//bmob的配置ID
	public static final String BMOB_ID = "19b40400fa28b82a1a740b94446d4711";

	//SharePreference标示
	public static final String SETTING = "setting";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";

	/** 适配手机参数 **/
	public static int screenWidth = 0;
	
	//点击查看具体景点的情况
	public static final String PLACE_TOPLAY = "city_toplay";

	public static final int SEARCH_HOT_COUNTRY = 0;//搜索全部热门的国家
}
