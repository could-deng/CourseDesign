package com.example.coursedesign.activity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.coursedesign.R;
import com.example.coursedesign.adapter.MySingleTripListViewAdapter;
import com.example.coursedesign.bean.City;
import com.example.coursedesign.bean.Trip;
import com.example.coursedesign.controller.AsyncImageLoader;
import com.example.coursedesign.utils.LogUtils;
import com.example.coursedesign.utils.ToastUtils;
import com.example.coursedesign.widget.PullToZoomListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/4/10.
 */
public class MySingleTripActivity extends BaseFragmentActivity {
    PullToZoomListView pulltozoomlistview;
    Boolean if_create;
    private List<City> cityList;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date date;
    MySingleTripListViewAdapter adapter;
    String firstImageUri;
    String the_trip_objectId ;

    private AsyncImageLoader asyncImageLoader;
    @Override
    protected void onCreate(Bundle arg0) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(arg0);
        setContentView(R.layout.activity_my_single_trip);
        initToolbar();
        initMenu();
        init();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    private void init() {
        firstImageUri = "";
        asyncImageLoader = new AsyncImageLoader();
        date = new Date();
        if_create = getIntent().getBooleanExtra("if_create", false);
        BmobQuery<Trip> query = new BmobQuery<Trip>();
        query.setLimit(1);
        if(if_create){//新创建
            query.order("-createdAt");
        }
        else{//选择gallery进入
            the_trip_objectId = getIntent().getStringExtra("new_objectid");
            query.addWhereEqualTo("objectId",the_trip_objectId);
        }

        query.findObjects(this, new FindListener<Trip>() {
            @Override
            public void onSuccess(List<Trip> list) {
                Trip the_list = list.get(0);
                cityList = the_list.getCity_line();
                String d = format.format(the_list.getStart_time());
                firstImageUri = the_list.getCity_line().get(0).getCity_pic_url();
                try {
                    date = format.parse(d);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                initData();
            }
            @Override
            public void onError(int i, String s) {
                ToastUtils.toast(MySingleTripActivity.this, "加载失败");
                LogUtils.i("123",s.toString());
            }
        });
        pulltozoomlistview = (PullToZoomListView) findViewById(R.id.pulltozoomlistview);

    }

    private void initData() {
        adapter = new MySingleTripListViewAdapter(cityList, date, this);
        pulltozoomlistview.setAdapter(adapter);
        if(firstImageUri == null ||firstImageUri.isEmpty()) return;
        //如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
        Drawable cacheImage = asyncImageLoader.loadDrawable(firstImageUri,new AsyncImageLoader.ImageCallback() {
            //请参见实现：如果第一次加载url时下面方法会执行
            public void imageLoaded(Drawable imageDrawable) {
//                run_bg_relative_layout.setImageDrawable(imageDrawable);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    pulltozoomlistview.getHeaderView().setImageDrawable(imageDrawable);
                }
            }
        });
        if(cacheImage!=null){
//            run_bg_relative_layout.setImageDrawable(cacheImage);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                pulltozoomlistview.getHeaderView().setImageDrawable(cacheImage);
            }
        }
//        pulltozoomlistview.getHeaderView().setImageResource(R.drawable.splash01);
        pulltozoomlistview.getHeaderView().setScaleType(ImageView.ScaleType.CENTER_CROP);
    }


    private void initMenu() {
        toolbar.setNavigationIcon(R.drawable.icon_tabbar_home_selected);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityFromThisActivity(MainActivity.class);
            }
        });
    }


}
