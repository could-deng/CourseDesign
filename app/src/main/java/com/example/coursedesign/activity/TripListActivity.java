package com.example.coursedesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.coursedesign.R;
import com.example.coursedesign.bean.City;
import com.example.coursedesign.bean.Trip;
import com.example.coursedesign.utils.LogUtils;
import com.example.coursedesign.utils.ToastUtils;
import com.example.coursedesign.widget.dslv.DragSortListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by yuanqiang on 2016/4/7.
 */
public class TripListActivity extends BaseFragmentActivity {
    List<City> city ;
//    List<Integer> cityid;
    List<Integer> days;
    List<City> all_citys;
    long selected_date;
    private ArrayAdapter<String> adapter;
    private DragSortListView list;
    private String start_city;
    private String end_city;

    private TextView date_begin;

    private DragSortListView.DropListener onDrop =
            new DragSortListView.DropListener() {
                @Override
                public void drop(int from, int to) {
                    if (from != to) {
                        String item = adapter.getItem(from);
                        adapter.remove(item);
                        adapter.insert(item, to);
                        list.moveCheckState(from, to);
                        String city_sum="";
                        for(int i =0;i<adapter.getCount();i++){
                            city_sum+=adapter.getItem(i)+"+";
                        }
//                        List<City>排序
                        City be_move = city.get(from);
                        city.remove(from);
                        city.add(to, be_move);

                        Log.i("123",city_sum);
                    }
                }
            };

    private DragSortListView.RemoveListener onRemove =
            new DragSortListView.RemoveListener() {
                @Override
                public void remove(int which) {
                    String item = adapter.getItem(which);
                    adapter.remove(item);
                    list.removeCheckState(which);
                    city.remove(which);//去除List<City>中单元
                }
            };

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_triplist);
        initToolbar();
        initMenu();
        init();
    }
    private void init() {
        city = (List<City>) getIntent().getExtras().getSerializable("citys_selected");
//        cityid = new ArrayList<Integer>();
        days = new ArrayList<Integer>();
        all_citys = new ArrayList<City>();//每一天的城市列表
        selected_date = getIntent().getExtras().getLong("date");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Log.i("123",format.format(selected_date));
        ToastUtils.toast(TripListActivity.this, format.format(selected_date));
        if(city == null){
            return;
        }
        date_begin = (TextView) findViewById(R.id.date_begin);
        date_begin.setText(format.format(selected_date));

        list = (DragSortListView) findViewById(R.id.list);
//        String[] array = getResources().getStringArray(R.array.jazz_artist_names);
        List<String> array = new ArrayList<>();
        for(int i = 0;i<city.size();i++){
            array.add(city.get(i).getCity_name());
        }
//        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(array));
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_checkable, R.id.text, array);

//        DragSortListViewAdapter adapter = new DragSortListViewAdapter(this,R.layout.list_item_checkable,array,days) ;

        list.setAdapter(adapter);

        list.setDropListener(onDrop);
        list.setRemoveListener(onRemove);

    }

    /**
     * 初始化toolbar
     */
    private void initMenu() {
        //Menu中间字
        setUiTitle("调整顺序和天数");
        //Menu右边图标，及跳转的activity
        showMenu = true;
        setMenuDrawable(R.drawable.compelete);
        //Menu左边图标显示
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_back);

    }

    @Override
    protected void clickRightDoSomeThing() {
        super.clickRightDoSomeThing();
//        for(int i =0;i<city.size();i++){
//            cityid.add(city.get(i).getCity_id());
//        }
        for(int i=0;i<city.size();i++){
            days.add(2);
        }
//        根据城市排序列表和时间列表生成全部城市列表
//        for(int i =0;i<city.size();i++) {//城市列表
            for (int j = 0; j < days.size(); j++) {//天数列表
                int percity_day =days.get(j);
                while (percity_day>0) {
                    all_citys.add(city.get(j));//总共
                    percity_day--;
                }
            }
//        }
        BmobUser bmobUser = BmobUser.getCurrentUser(TripListActivity.this);
        String user_id;
        if(bmobUser != null){
            // 允许用户使用应用
            user_id = bmobUser.getObjectId();
        }else{
            //缓存用户对象为空时， 可打开用户注册界面…
            ToastUtils.toast(TripListActivity.this,"未成功登录");
            startActivityFromThisActivity(LoginActivity.class);
            return;
        }
        Trip trip = new Trip(1,1,selected_date,all_citys,user_id);
        trip.save(TripListActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtils.toast(TripListActivity.this, "添加行程成功");
                Intent intent = new Intent(TripListActivity.this,MySingleTripActivity.class);
                intent.putExtra("if_create",true);
                startActivity(intent);
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.toast(TripListActivity.this, "添加行程失败");
                LogUtils.i("123", s.toString());
            }
        });
    }
}
