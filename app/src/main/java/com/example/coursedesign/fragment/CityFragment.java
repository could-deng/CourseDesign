package com.example.coursedesign.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.coursedesign.R;
import com.example.coursedesign.activity.BaseFragmentActivity;
import com.example.coursedesign.activity.CityInfoActivity;
import com.example.coursedesign.activity.HotDesitinationActivity;
import com.example.coursedesign.adapter.ChooseCityListViewAdapter;
import com.example.coursedesign.bean.City;
import com.example.coursedesign.bean.Desitination_play_item;
import com.example.coursedesign.bean.Place;
import com.example.coursedesign.bean.continents;
import com.example.coursedesign.common.Constants;
import com.example.coursedesign.common.Urls;
import com.example.coursedesign.utils.ToastUtils;
import com.example.coursedesign.widget.ExpandGridView;
import com.example.coursedesign.widget.pulltorefresh.RefreshLayout;
import com.example.coursedesign.widget.pulltorefresh.SwipeRefreshDirection;
import com.facebook.common.internal.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanqiang on 2016/3/18.
 */
public class CityFragment extends BaseFragment {
    ChooseCityListViewAdapter city_listview_adapter;
    List<City> city_list;
    ListView fragment_des_city_lv;

    //第二个菜单项
    protected MenuItem menu_second;
    protected int MenuScondDrawable = R.drawable.ic_map;
    public int getMenuScondDrawable() {return MenuScondDrawable;}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        place = (Place) savedInstanceState.getSerializable(Constants.PLACE_TOPLAY);
//        ((HotDesitinationActivity) getActivity()).setNow_fragment(2);
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_choose_city,container,false);
        init(view);
        initMenu();
        refreshList();
        return view;
    }

    private void init(View view) {
        city_listview_adapter = new ChooseCityListViewAdapter(getActivity());
        fragment_des_city_lv = (ListView) view.findViewById(R.id.fragment_des_city_lv);
        fragment_des_city_lv.setAdapter(city_listview_adapter);
        fragment_des_city_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), CityInfoActivity.class);
                intent.putExtra("city", getCityList().get(position));
                (getActivity()).startActivityForResult(intent, HotDesitinationActivity.SELECT_CITY_INFO);
            }
        });
    }
    private void initMenu() {
        ((HotDesitinationActivity)getActivity()).setShowMenu(true);
        ((HotDesitinationActivity) getActivity()).setToolbar(true, R.string.title_activity_des);
        //菜单项
//        ((HotDesitinationActivity) getActivity()).setShowMenu(true);
//        ((BaseFragmentActivity) getActivity()).setMenuDrawable(R.drawable.next_selector);
//        ((HotDesitinationActivity) getActivity()).setSetClickToNext(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu_second = menu.add(0,Menu.FIRST +4,4," ").setIcon(getMenuScondDrawable());
        menu_second.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu_second.setActionView(R.layout.menu_layout);
        ImageView iv_menu = (ImageView) menu_second.getActionView().findViewById(R.id.iv_menu);
        iv_menu.setImageResource(getMenuScondDrawable());
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menu_second);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Menu.FIRST + 4://第二个菜单项
                    Log.i("BaseFragmentActivity", "clickSecondMenu()");
                ((HotDesitinationActivity)(getActivity())).pushToFragment(HotDesitinationActivity.CITYTOMAPFRAGMENT);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 从中介类中获取数据刷新界面
     */
    private void refreshList() {
        //城市数据
        if (getCityList() != null && getCity_listview_adapter() != null) {
            getCity_listview_adapter().setCity_list(getCityList());
        }
    }
    //中介类中获取城市数据
    private List<City> getCityList() {
        return getMyConfig().getMemExchange().getList_city();
    }

    public ChooseCityListViewAdapter getCity_listview_adapter() {
        return city_listview_adapter;
    }


    public void uploadCitysList(List<City> list){
        city_list.clear();
        city_list.addAll(list);
        city_listview_adapter.notifyDataSetChanged();
    }

    public void uploadSelectedCity(int city_position,boolean selected){
        city_list.get(city_position).setSelected(selected);
        city_listview_adapter.notifyDataSetChanged();
    }
    @Override
    public void stopRefresh(){
        super.stopRefresh();
        refreshList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getMyConfig().getMemExchange().setList_city(null);
    }
}
