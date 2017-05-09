package com.example.coursedesign.fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.SupportMapFragment;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.coursedesign.R;
import com.example.coursedesign.activity.HotDesitinationActivity;
import com.example.coursedesign.base.AMapHelper;
import com.example.coursedesign.bean.City;

import java.util.List;

/**
 * Created by yuanqiang on 2016/4/16.
 */
public class mapFragment extends BaseFragment {

    private com.amap.api.maps2d.MapView mapView;
    private AMap aMap;
    AMapHelper mapHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        init(view,savedInstanceState);
        initMenu();
        initPoint();
        return view;
    }

    private void initPoint() {
        List<City> citys_list = getMyConfig().getMemExchange().getList_city();

        com.amap.api.maps2d.model.LatLng center = new com.amap.api.maps2d.model.LatLng(citys_list.get(0).getLaititude(),citys_list.get(0).getLongtitude());


        for(int i = 0;i<citys_list.size() ;i++) {
            City city = citys_list.get(i);
            com.amap.api.maps2d.model.LatLng ll = new com.amap.api.maps2d.model.LatLng(city.getLaititude(),city.getLongtitude());
            getMapHelper().addPoint(city.getCity_name(),ll);
        }
        getMapHelper().relocatePosition(center, true, true);
//        CameraUpdateFactory.zoomBy(200);
    }

    private void initMenu() {
        ((HotDesitinationActivity)getActivity()).setShowMenu(true);
        ((HotDesitinationActivity) getActivity()).setToolbar(true, R.string.title_activity_des);
    }

    public AMapHelper getMapHelper() {
        return mapHelper;
    }
    //    private void init(View view) {
//        mapview = (MapView) view.findViewById(R.id.mapview);
//        mBaiduMap = mapview.getMap();
//        //定义Maker坐标点
//        LatLng point = new LatLng(39.963175, 116.400244);
//        //构建Marker图标
//        BitmapDescriptor bitmap = BitmapDescriptorFactory
//                .fromResource(R.drawable.ic_traffic_walk);
//        //构建MarkerOption，用于在地图上添加Marker
//        OverlayOptions option = new MarkerOptions()
//                .position(point)
//                .icon(bitmap);
//        //在地图上添加Marker，并显示
//        mBaiduMap.addOverlay(option);
//    }

    /**
     * 高德地图的相关初始化
     */
    private void init(View view,Bundle savedInstanceState) {
        mapView = (com.amap.api.maps2d.MapView) view.findViewById(R.id.amapview);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        mapHelper = new AMapHelper(mapView,false);
        getMapHelper().onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMapHelper().onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        getMapHelper().onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getMapHelper().onDestroy();
    }
}
