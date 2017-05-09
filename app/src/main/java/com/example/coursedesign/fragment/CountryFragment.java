package com.example.coursedesign.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.coursedesign.R;
import com.example.coursedesign.activity.HotDesitinationActivity;
import com.example.coursedesign.adapter.CountryGLAdapter;
import com.example.coursedesign.adapter.TypeDestinationAdapter;
import com.example.coursedesign.bean.Country;
import com.example.coursedesign.bean.HotPlace;
import com.example.coursedesign.bean.Place;
import com.example.coursedesign.bean.continents;
import com.example.coursedesign.common.NotifyByEventBus;
import com.example.coursedesign.common.Urls;
import com.example.coursedesign.widget.pulltorefresh.RefreshLayout;
import com.example.coursedesign.widget.pulltorefresh.SwipeRefreshDirection;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by yuanqiang on 2016/3/18.
 */
public class CountryFragment extends BaseFragment {

    public interface OnCountrySelectListener{
        public void onCountryselected(int position);
    }
    private com.example.coursedesign.widget.ExpandGridView fragment_work_lv;
    private RefreshLayout fragment_hot_des_refreshlo;
//    HotDesitnationAdapter hot_adapter ;
    private CountryGLAdapter countryGLAdapter;
    private TypeDestinationAdapter continentAdapter ;
    ListView fragment_work_type_lv;
    protected OnCountrySelectListener selectListener;

//    private ArrayList<continents> continents_list = new ArrayList<continents>();

//    private List<Country> countryArrayList = new ArrayList<>();
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            selectListener = (OnCountrySelectListener) activity;
        }
        catch (ClassCastException e){
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((HotDesitinationActivity) getActivity()).setNow_fragment(1);
        clear();
        View view = inflater.inflate(R.layout.fragment_choose_country,container,false);
        EventBus.getDefault().register(this);

        init(view);
        initMenu();
        refreshList();
        return view;
    }
    @Override
    public void stopRefresh() {
        super.stopRefresh();
        refreshList();
    }

    private void refreshList() {
        if (getContinentList() != null && getContinentAdapter() != null) {
            getContinentAdapter().setList(getContinentList());
        }
        if(getCountryArrayList() != null && getCountryGLAdapter() !=null){
            getCountryGLAdapter().setCountry_ls(getCountryArrayList());
        }
    }

    private List<continents> getContinentList() {
        return getMyConfig().getMemExchange().getList_continents();
    }

    private List<Country> getCountryArrayList() {
        return getMyConfig().getMemExchange().getList_country();
    }

    private void clear(){
        fragment_work_lv = null;
        fragment_hot_des_refreshlo = null;
        fragment_work_type_lv = null;
        countryGLAdapter = null;
        continentAdapter = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initMenu() {
        ((HotDesitinationActivity)getActivity()).setShowMenu(true);
        ((HotDesitinationActivity) getActivity()).setToolbar(true, R.string.title_activity_des);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void init(View view) {
        RefreshLayout swipeLayout = (RefreshLayout) view.findViewById(R.id.fragment_hot_des_refreshlo);
        setSwipeRefreshLayout(swipeLayout);
        getSwipeRefreshLayout().setDirection(SwipeRefreshDirection.BOTTOM);
        getSwipeRefreshLayout().setOnRefreshListener(onrefreshListener);

//		activity_work_lv = (PullToRefreshListView) findViewById(R.id.activity_work_lv);
        fragment_work_lv = (com.example.coursedesign.widget.ExpandGridView) swipeLayout.findViewById(R.id.fragment_choose_country_item_gv);
        fragment_work_type_lv = (ListView) view.findViewById(R.id.fragment_work_type_lv);
        continentAdapter = new TypeDestinationAdapter(getActivity());
        fragment_work_type_lv.setAdapter(getContinentAdapter());
        fragment_work_type_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ((HotDesitinationActivity) getActivity()).clear_country_data();
                getMyConfig().getMemExchange().setList_country(null);
                ((HotDesitinationActivity) getActivity()).startRefreshMainFragmentList();
                ((HotDesitinationActivity) getActivity()).setSelect_continent_id(getContinentAdapter().getList_place().get(position).getContinents_id());
                ((HotDesitinationActivity) getActivity()).sendCountrysListRequest(((HotDesitinationActivity) getActivity()).getSelect_continent_id());
            }
        });

        countryGLAdapter = new CountryGLAdapter((HotDesitinationActivity)getActivity());
        fragment_work_lv.setAdapter(getCountryGLAdapter());
        fragment_work_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectListener != null) {
//                    ((HotDesitinationActivity) getActivity()).setNow_fragment(2);
                    int click_country_id = getCountryGLAdapter().getCountry_ls().get(position).getCountry_id();
                    if(((HotDesitinationActivity) getActivity()).getSelect_country_id() != click_country_id){
                        getMyConfig().getMemExchange().clearList_city();
                    }
                    ((HotDesitinationActivity) getActivity()).setSelect_country_id(click_country_id);
                    selectListener.onCountryselected(HotDesitinationActivity.TOCITYFRAGMENT);
                }
            }
        });
    }

    RefreshLayout.OnRefreshListener onrefreshListener = new RefreshLayout.OnRefreshListener(){

        @Override
        public void onRefresh(SwipeRefreshDirection direction) {
            int id  =((HotDesitinationActivity)getActivity()).getSelect_continent_id();
            ((HotDesitinationActivity)getActivity()).sendCountrysListRequest(id);
        }
    };

    private TypeDestinationAdapter getContinentAdapter() {
        return continentAdapter;
    }

    private CountryGLAdapter getCountryGLAdapter() {
        return countryGLAdapter;
    }

//    /**
//     */
////    @Subscriber(tag = NotifyByEventBus.UPDATE_CONTINENTS)
//    public void updateContinent(List<continents> list) {
//        continents_list.clear();
//        for(int i = 0;i<list.size();i++) {
//            continents_list.add(list.get(i));
//        }
//        continentAdapter.notifyDataSetChanged();
//    }
//
//
//    public void updateCoutry(List<Country> list) {
//        countryArrayList.clear();
//        for(int i = 0;i<list.size();i++) {
//            countryArrayList.add(list.get(i));
//        }
//        countryGLAdapter.notifyDataSetChanged();
//    }

}
