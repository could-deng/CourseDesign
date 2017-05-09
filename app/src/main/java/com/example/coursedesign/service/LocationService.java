/**
 * 
 */
package com.example.coursedesign.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.example.coursedesign.CourseDesignAPP;
import com.example.coursedesign.common.NotifyByEventBus;
import com.example.coursedesign.listener.MyLocationListener;
import com.example.coursedesign.listener.MyLocationListener.location;
import com.example.coursedesign.utils.LogUtils;

import org.simple.eventbus.EventBus;

/**
 * @author yuanqiang
 *
 */
public class LocationService extends Service implements location{
	
	private static final String TAG = "LocationService";
	//百度地图
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	
	private double latitude;
	private double longtitude;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		LogUtils.i(TAG, "oncreate()");
		super.onCreate();
		//睡兩秒
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	@Override
	public void onDestroy() {
		LogUtils.i(TAG, "onDestroy()");
		super.onDestroy();
		
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mLocationClient = new LocationClient(getApplicationContext());
		mLocationClient.registerLocationListener( myListener );    //注册监听函数
		LocationClientOption option = new LocationClientOption();
	        option.setLocationMode(LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
	        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
	        int span=2000;
	        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
	        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
	        option.setOpenGps(true);//可选，默认false,设置是否使用gps
	        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
	        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
	        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
	        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死  
	        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
	        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
	        mLocationClient.setLocOption(option);
	        
	        ((MyLocationListener) myListener).setlocationback(this);
	        
	        //开始定位
	        mLocationClient.start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void doupload(double laititude, double longtitude) {
		this.latitude =laititude;
		this.longtitude = longtitude;
		LatLng now_location = new LatLng(latitude, longtitude);
		CourseDesignAPP.setNow_location(now_location);
		//发送eventbus订阅事件
		EventBus.getDefault().post(now_location, NotifyByEventBus.UPDATE_LOCATION);
	}
	
	public double getLatitude() {
		return latitude;
	}
	public double getLongtitude() {
		return longtitude;
	}
	
}
