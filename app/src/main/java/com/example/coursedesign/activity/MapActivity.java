/**
 * 
 */
package com.example.coursedesign.activity;

import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.coursedesign.CourseDesignAPP;
import com.example.coursedesign.R;
import com.example.coursedesign.common.CommonMethod;
import com.example.coursedesign.common.NotifyByEventBus;
import com.example.coursedesign.utils.LogUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/**
 * @author yuanqiang
 *
 */
public class MapActivity extends BaseFragmentActivity {
	MapView mapView;
	BaiduMap mBaiduMap;
	
	//默认一种百度地图图层//可通过bar更改
	int level = CommonMethod.getLevelFromRadim(0 * 8 + 200);
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.map_layout);
		EventBus.getDefault().register(this);// 注册订阅事件
		initview();
		initLocation();
	}

	/**
	 Description:初始化坐标
	 Input: 
	 */
	private void initLocation() {
		LatLng latLng = CourseDesignAPP.getInstance().getNow_location();
		drawSafeRange(latLng, level);
	}

	//通知更改坐标并重新绘制iew
	@Subscriber(tag = NotifyByEventBus.UPDATE_LOCATION)
	public void update_location(LatLng latLng){
		LogUtils.i("NotifyByEventBus.UPDATE_LOCATION", "update_location()");
		drawSafeRange(latLng, level);
	}
	
	
	private void initview() {
		mapView  =  (MapView) findViewById(R.id.map_layout_bmapView);
		mBaiduMap = mapView.getMap();
	}
	
	/**
	 * Description:绘制定点 Input: 经纬度坐标、地图图层
	 */
	private void drawSafeRange(LatLng latLng, int level) {
		mBaiduMap.clear();
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(latLng, level);
		mBaiduMap.animateMapStatus(u);
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.trajectory_head);
		OverlayOptions option = new MarkerOptions().position(latLng).icon(
				bitmap);
		// 在地图上添加Marker，并显示
		mBaiduMap.addOverlay(option);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mBaiduMap.setMyLocationEnabled(false);
		mapView.onDestroy();
		mapView = null;
		EventBus.getDefault().unregister(this);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mapView.onResume();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mapView.onPause();
	}
}
