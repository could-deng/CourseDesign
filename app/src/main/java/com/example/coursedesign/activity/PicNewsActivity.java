package com.example.coursedesign.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.coursedesign.R;
import com.example.coursedesign.adapter.PicNewsListviewAdapter;
import com.example.coursedesign.bean.Country;
import com.example.coursedesign.bean.DailyDiary;
import com.example.coursedesign.bean.PicNew;
import com.example.coursedesign.controller.ActivityTransition;
import com.example.coursedesign.controller.ExitActivityTransition;
import com.example.coursedesign.utils.ThreadManager;
import com.example.coursedesign.utils.ToastUtils;
import com.example.coursedesign.widget.ListViewForScrollView;
import com.example.coursedesign.widget.pulltorefresh.RefreshLayout;
import com.example.coursedesign.widget.pulltorefresh.SwipeRefreshDirection;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by yuanqiang on 2016/4/12.
 */
public class PicNewsActivity extends BaseFragmentActivity{

    private SimpleDraweeView img_album_cover;//内容上的图
    private SimpleDraweeView img_temp;//转场临时图标
    private ExitActivityTransition exitTransition;//退场动画
    private PicNew picNew;
    private LinearLayout mainLayout;

    private ListView lv_pic_news;//显示全部日记的列表
    private PicNewsListviewAdapter adapter;

    private RefreshLayout rl_trip_list;
    int page = 0;
    int page_count =6;
    int sum_extra = 0;

    @Override
    protected void onCreate(Bundle arg0) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(arg0);
        setContentView(R.layout.activity_new_detail);
        initToolbar();
        initMenu();
        initViews();
        startRreshList();
        Intent intent = getIntent();
        if (intent != null) {
            exitTransition = ActivityTransition
                    .with(intent)
                    .to(img_temp)
                    .interpolator(new FastOutSlowInInterpolator())
                    .start(arg0);

            ThreadManager.getMainHandler().postDelayed(new Runnable() {
                @Override
                public void run() {//动画结束后,显示本来的布局,隐藏临时图标
                    mainLayout.setVisibility(View.VISIBLE);
                    img_temp.setVisibility(View.GONE);

                }
            }, 1000);
        } else {
            mainLayout.setVisibility(View.GONE);
            img_temp.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取数据
     */
    private void startRreshList() {
        if(getMyConfig().getMemExchange().getPicNew()!=null){
            refreshOnce();
        }
        sendDailyDiaryRequest(getMyConfig().getMemExchange().getPicNew().getObjectId());

        //重新获取其他大数据
    }

    private void refreshOnce() {
        if (getPicNew() == null) return;
        if (TextUtils.isEmpty(getPicNew().getNew_pic_url())) {//默认
            img_album_cover.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(R.drawable.bg_ugc_personal)).build());
        } else {
            img_album_cover.setImageURI(Uri.parse(getPicNew().getNew_pic_url()));
        }
        adapter = new PicNewsListviewAdapter(PicNewsActivity.this);
//        adapter.setList(getMyConfig().getMemExchange().getPicNew().getList_diary());
        adapter.setList(getMyConfig().getMemExchange().getDiary_list());
        lv_pic_news.setAdapter(adapter);
    }

    public PicNew getPicNew() {
        if (picNew == null) picNew = getMyConfig().getMemExchange().getPicNew();
        return picNew;
    }

    private void initViews() {
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        img_temp = (SimpleDraweeView) findViewById(R.id.img_temp);
        img_album_cover = (SimpleDraweeView) findViewById(R.id.img_album_cover);
        lv_pic_news = (ListView) findViewById(R.id.lv_pic_news);
        View view = LayoutInflater.from(this).inflate(R.layout.trip_sum_content, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_trip_sum);
        tv.setText(getPicNew().getTrip_sum());
        lv_pic_news.addHeaderView(view);
        rl_trip_list = (RefreshLayout) findViewById(R.id.rl_trip_list);
        rl_trip_list.setDirection(SwipeRefreshDirection.BOTTOM);
        setSwipeRefreshLayout(rl_trip_list);
        getSwipeRefreshLayout().setOnRefreshListener(refreshListener);

    }

    //	初始化toolbar
    private void initMenu() {
        //Menu中间字
        setUiTitle(getPicNew().getNew_title());
        //Menu右边图标，及跳转的activity
        showMenu = false;
        //Menu左边图标显示
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_back);
    }

    RefreshLayout.OnRefreshListener refreshListener = new RefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh(SwipeRefreshDirection direction) {
            sendDailyDiaryRequest(getMyConfig().getMemExchange().getPicNew().getObjectId());
        }
    };


    private void sendDailyDiaryRequest(String trip_object_id) {
        BmobQuery<DailyDiary> query = new BmobQuery<DailyDiary>();
        query.order("createdAt");
        query.setSkip(page * page_count + sum_extra);

        query.addWhereEqualTo("pic_news_objectid", trip_object_id);
        query.setLimit(5);

        query.findObjects(this, findCoutrysListener);
        startRefresh();
    }
    FindListener findCoutrysListener = new FindListener<DailyDiary>(){

        @Override
        public void onSuccess(List<DailyDiary> list) {
            if(list.size()>0) {
                getMyConfig().getMemExchange().getDiary_list().addAll(list);
                stopRefresh();
                if(list.size() == 5){
                    page++;
                    sum_extra = 0;
                }
                else{
                    sum_extra = list.size();
                }
            }
            else{
                ToastUtils.toast(PicNewsActivity.this, "没有更多数据");
                stopRefresh();
            }
        }

        @Override
        public void onError(int i, String s) {
            stopRefresh();
            Log.i("123",s.toString());
        }
    };


    public void startRefresh() {
        rl_trip_list.setRefreshing(true);
    }

    public void stopRefresh() {
        if (rl_trip_list!=null) {
            rl_trip_list.setRefreshing(false);
            //适配器notifydatasetChange
            adapter.setList(getMyConfig().getMemExchange().getDiary_list());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//复写返回操作,回退动画
        switch (item.getItemId()) {
            case android.R.id.home://返回
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (exitTransition != null) {
            //先隐藏本来布局
            mainLayout.setVisibility(View.GONE);
            img_temp.setVisibility(View.VISIBLE);
            exitTransition.exit(this);//退场动画
        } else {
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getMyConfig().getMemExchange().setDiary_list(null);
    }
}
