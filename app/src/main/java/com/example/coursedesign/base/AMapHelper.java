package com.example.coursedesign.base;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.GroundOverlay;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.example.coursedesign.CourseDesignAPP;
import com.example.coursedesign.R;

/**
 * 高德地图工具类
 * Created by yuanqiang on 2016/4/16.
 */
public class AMapHelper {
    private MapView mapView;
    private AMap aMap;
    private GroundOverlay darkLayout;//地图暗色遮罩
    private BitmapDescriptor mMarkFlag;//标识点图标
    private int iTrailLineWidth;//轨迹线条宽度
    private int iScreenWidth;//屏幕像素宽
    private int iScreenHeight;//屏幕像素高
    private BitmapDescriptor bitmapStart = BitmapDescriptorFactory
            .fromResource(R.drawable.bg_map_city_selected);
    private LatLng latlng = new LatLng(36.061, 103.834);
    /**
     * 创建高德地图帮助
     *
     * @param mapView 高德地图view
     * @param bEnableLocate 是否需要定位,true:是,false:否
     * */
    public AMapHelper(View mapView, boolean bEnableLocate) {
//        clear();
        this.mapView = (MapView) mapView;
        init(CourseDesignAPP.getContext());
    }

    /**
     * 地图初始化配置
     *
     * @param context 上下文
     * */
    public void init(Context context) {
        if (context == null)
            return;

        mMarkFlag = BitmapDescriptorFactory.fromResource(R.drawable.ic_traffic_walk);
        iTrailLineWidth = (int) context.getResources().getDimension(
                R.dimen.line_width);
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        iScreenWidth = dm.widthPixels;
        iScreenHeight = dm.heightPixels;
    }

    private boolean isMapViewValid() {
        return (mapView != null);
    }
    /**
     * 地图创建,注意:一定要在Activity或Fragment onCreate方法中调用
     * */
    public void onCreate(Bundle savedInstanceState) {
        if (!isMapViewValid())
            return;
        mapView.onCreate(savedInstanceState);
        setupMap();
    }
    private void setupMap() {
        if (!isMapViewValid())
            return;
        mapView.setDrawingCacheEnabled(true);
        getAMap().getUiSettings().setZoomControlsEnabled(false);//隐藏地图缩放控件
//        setEnableLocate(bEnableLocate);
    }
    /** 获取高德地图实例*/
    public AMap getAMap() {
        if ((aMap == null) && (mapView != null))
            aMap = mapView.getMap();
        return aMap;
    }

    /**
     * 在地图上加上图标
     * @param title
     * @param latLng
     */
    public void addPoint(String title,LatLng latLng){
//        MarkerOptions mark = new MarkerOptions();
//        mark.icon(bitmapStart).position(latlng);
//        getAMap().addMarker(mark);
        Marker marker = aMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .icon(bitmapStart));
        marker.showInfoWindow();
    }

    /**
     * 暂停地图绘制,注意:一定要在Activity或Fragment onPause方法中调用
     * */
    public void onPause() {
        if (!isMapViewValid())
            return;
        mapView.onPause();
    }

    /**
     * 恢复地图绘制,注意:一定要在Activity或Fragment onResume方法中调用
     * */
    public void onResume() {
        if (!isMapViewValid())
            return;
        mapView.onResume();
    }

    /**
     * 地图销毁,注意:一定要在Activity或Fragment onDestroy方法中调用
     * */
    public void onDestroy() {
        if (!isMapViewValid())
            return;
        mapView.onDestroy();
        mapView = null;
        releaseResource();
    }

    private void releaseResource() {
        clear();
    }

    private void clear() {

    }

    /**
     * 重新移动地图镜头
     *
     * @param pos 定位点
     * @param bAnimateCamera 动画显示 true:是,false:否
     * @param bShowLocationPos 是否显示带圆圈效果定位点
     *
     * */
    public void relocatePosition(LatLng pos, boolean bAnimateCamera, boolean bShowLocationPos) {
        if (aMap == null)
            return;

//        lastLocateTime = System.currentTimeMillis();//Calendar.getInstance().getTimeInMillis();
        if (bAnimateCamera) {
            getAMap().animateCamera(
                    CameraUpdateFactory.newCameraPosition(new CameraPosition(pos,
                            CourseDesignAPP.MAP_CAMERA_ZOOM, 0, 0)), 1000, null);
        }
//        if (bShowLocationPos) forceShowLocationPoint(pos);
    }

}
