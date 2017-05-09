/**
 * 
 */
package com.example.coursedesign;

import android.app.Application;
import android.content.Context;
import android.view.WindowManager;

import com.activeandroid.ActiveAndroid;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.easemob.chat.EMChat;
import com.example.coursedesign.common.CommUtil;
import com.example.coursedesign.common.Constants;
import com.example.coursedesign.utils.ImageLoaderHelper;
import com.example.coursedesign.utils.LogUtils;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.File;

import cn.bmob.v3.Bmob;

/**
 * @author yuanqiang
 * 
 */
public class CourseDesignAPP extends Application {

	public static final int NETWORK_TYPE_NONE = 0;
	public static final int NETWORK_TYPE_MOBILE = 1;
	public static final int NETWORK_TYPE_WIFI = 2;

	/**地图显示时镜头放大倍数*/
	public static final int MAP_CAMERA_ZOOM = -1;
//	/**地图显示时镜头最大放大倍数*/
//	public static final int MAP_CAMERA_ZOOM_MAX = 16;
//	/**地图显示时镜头最小放大倍数*/
//	public static final int MAP_CAMERA_ZOOM_MIN = 12;


	public static String TAG =  "CourseDesignAPP";
	// 全局变量（）
	private static CourseDesignAPP instance;
	private static Context applicationContext;
	//坐标
	public static LatLng now_location;
	private boolean isLOgin = false;

	private String username;
	private String password;

	public int Display_width;
	public int Display_height;
	@Override
	public void onCreate() {
		super.onCreate();
		LogUtils.i(TAG, "创建Application");
		initFile();
		/** 初始化操作数据库 **/
//		ActiveAndroid.initialize(this);

		applicationContext = this;
		instance = this;
		//环信
		EMChat.getInstance().init(applicationContext);
		//imageLoader
		ImageLoaderHelper.initImageLoader(applicationContext);
		//faceBook初始化
		Fresco.initialize(applicationContext);

		//百度地图SDK初始化
//		SDKInitializer.initialize(getApplicationContext());

		// 初始化 Bmob SDK
		// 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
		Bmob.initialize(this, Constants.BMOB_ID);

		WindowManager wm = (WindowManager) getContext()
				.getSystemService(Context.WINDOW_SERVICE);

		Display_width = wm.getDefaultDisplay().getWidth();
		Display_height = wm.getDefaultDisplay().getHeight();
	}

	/**
	 Description:初始化存储一些文件的文件目录
	 Input: 
	 */
	private void initFile() {
		File homeFile = new File(Constants.HOME_PATH);
		if(!homeFile.exists()){
			homeFile.mkdir();
		}
		File cameraFile = new File(Constants.HOME_PATH+ Constants.CAMERA_PATH);
		if(!cameraFile.exists()){
			cameraFile.mkdir();
		}
		File clipFile = new File(Constants.HOME_PATH+ Constants.CLIP_IMAGE);
		if(!clipFile.exists()){
			clipFile.mkdir();
		}
	}

	public LatLng getNow_location() {
		if (CommUtil.isBlank(now_location)) {
			//默认数据///可以从sharePerence上获取来当默认
			now_location = new LatLng(22.54824028610379,113.9427012205119);
		}
		return now_location;
	}

	public static void setNow_location(LatLng now_location) {
		CourseDesignAPP.now_location = now_location;
	}


	public static Context getContext() {
		return applicationContext;
	}

	public static CourseDesignAPP getInstance() {
		return instance;
	}

	public static void setInstance(CourseDesignAPP instance) {
		CourseDesignAPP.instance = instance;
	}

	public boolean isLOgin() {
		return isLOgin;
	}

	public void setLOgin(boolean isLOgin) {
		this.isLOgin = isLOgin;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
//		ActiveAndroid.dispose();
	}
}
