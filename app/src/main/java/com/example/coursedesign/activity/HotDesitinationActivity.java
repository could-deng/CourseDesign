/**
 * 
 */
package com.example.coursedesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.coursedesign.CourseDesignAPP;
import com.example.coursedesign.R;
import com.example.coursedesign.adapter.HorizontalListViewAdapter;
import com.example.coursedesign.bean.City;
import com.example.coursedesign.bean.Country;
import com.example.coursedesign.bean.continents;
import com.example.coursedesign.common.Constants;
import com.example.coursedesign.fragment.CityFragment;
import com.example.coursedesign.fragment.CountryFragment;
import com.example.coursedesign.fragment.mapFragment;
import com.example.coursedesign.utils.LogUtils;
import com.example.coursedesign.utils.ToastUtils;
import com.example.coursedesign.widget.HorizontalListView;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.simple.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * @author yuanqiang
 *
 */
public class HotDesitinationActivity extends BaseFragmentActivity implements CountryFragment.OnCountrySelectListener {
	public static final int TOCOUNTRYFRAGMENT = 0;
	public static final int TOCITYFRAGMENT = 1;
	public static final int CITYTOMAPFRAGMENT = 2;
	public static final int SELECT_CITY_INFO = 111;

	private FragmentManager fragmentManager;
	private CountryFragment countryFragment;
	private CityFragment cityFragment;
	private mapFragment mapfragment;
	private FrameLayout fragment_container;
	FragmentTransaction transaction;
	private int now_fragment;

	HorizontalListView horizontal_listview;
	private HorizontalListViewAdapter horizontalAdapter;
	private List<City> citys_selected;


	private LinearLayout ll_selected_citys;
	private TextView sum_city_selected;

	Long selected_date;

	private int select_continent_id = Constants.SEARCH_HOT_COUNTRY;
	private int select_country_id ;

	//国家
	int country_page = 0;
	int country_count =6;
	int country_extra = 0;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.hot_destination_layout);
		selected_date = getIntent().getExtras().getLong("date");
		EventBus.getDefault().register(this);
		initToolbar();
		initMenu();
		initview();
//		initcontent();
	}
	public void clear_country_data(){
		country_page = 0;
		country_count =6;
		country_extra = 0;
	}
	private void initMenu() {
		setUiTitle(getResources().getString(R.string.title_activity_des));
//		showMenu = true;
		setMenuDrawable(R.drawable.icon_next);
		setMenugoToClass(null);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_selector);
	}

	private void canNext(int select_list){
		if(select_list>0){
			setMenuDrawable(R.drawable.icon_next);
			setMenugoToClass(TripListActivity.class);
			Bundle bundle = new Bundle();
			bundle.putLong("date",selected_date);
			bundle.putSerializable("citys_selected", (Serializable) citys_selected);
			setBundle_next(bundle);
		}
		else if(select_list<=0){
			setMenuDrawable(R.drawable.ic_launcher);
			setMenugoToClass(null);
		}
	}
//	public void initSecondMenu(){
//		setMenuScondDrawable(R.drawable.ic_map);
//	}

//	/**
//	 */
//	@Override
//	protected void clickSecondMenu() {
//
//		super.clickSecondMenu();
//	}

	public void setNow_fragment(int now_fragment) {
		this.now_fragment = now_fragment;
	}
	public Integer getNow_fragment() {
		return now_fragment;
	}

	/**
	 */
	public void setToolbar(boolean showBack, int titleResId) {
		if (toolbar == null) return;
		toolbar.setNavigationIcon(null);
		toolbar.setNavigationOnClickListener(null);
		if (showBack) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_back);
			toolbar.setNavigationOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onBackPressed();
				}
			});
		} else {
			getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		}

		String title = getString(titleResId);
		if (!TextUtils.isEmpty(title)) {
			setUiTitle(title);
		}
	}
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		if(getNow_fragment()==1) {
			getMyConfig().getDatabase().restoreCity();
		}
	}
	public void setShowMenu(Boolean show){
		this.showMenu =show;
	}
//	public void setShowSecondMenu(Boolean show){
//		this.showSecondMenu =show;
//	}

	public int getSelect_continent_id() {
		return select_continent_id;
	}

	public void setSelect_continent_id(int select_continent_id) {
		this.select_continent_id = select_continent_id;
	}

	public int getSelect_country_id() {
		return select_country_id;
	}

	public void setSelect_country_id(int select_country_id) {
		this.select_country_id = select_country_id;
	}

	/**
	 Description:
	 Input: 
	 */
	private void initview() {
//		RefreshLayout swipeLayout = (RefreshLayout) findViewById(R.id.activity_hot_des_refreshlo);
//		setSwipeRefreshLayout(swipeLayout);
//		getSwipeRefreshLayout().setOnRefreshListener(onrefreshListener);
//
////		activity_work_lv = (PullToRefreshListView) findViewById(R.id.activity_work_lv);
//		activity_work_lv = (ListView) findViewById(R.id.activity_work_lv);
//		activity_work_type_lv = (ListView) findViewById(R.id.activity_work_type_lv);
		citys_selected = new ArrayList<City>();
		ll_selected_citys = (LinearLayout) findViewById(R.id.ll_selected_citys);
		ll_selected_citys.setVisibility(View.GONE);

		sum_city_selected = (TextView) findViewById(R.id.sum_city_selected);


		horizontal_listview = (HorizontalListView) findViewById(R.id.horizontal_listview);
		horizontalAdapter = new HorizontalListViewAdapter(this,citys_selected);
		horizontal_listview.setAdapter(horizontalAdapter);

		fragmentManager = getSupportFragmentManager();
		fragment_container = (FrameLayout) findViewById(R.id.fragment_container);
		initFragment();

	}

	private void initFragment() {
		String fragmentTag = "countryFragment";
		Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
		if (fragment == null) {
			fragment = getChooseCountryFragment();
		} else {
			return;
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.fragment_container, fragment, fragmentTag).commit();
		now_fragment = 1;
	}

	private CountryFragment getChooseCountryFragment(){
		if (countryFragment == null) {
			countryFragment = new CountryFragment();
			startRefreshCountryFragmentList();
		}
		return countryFragment;
	}
	public CityFragment getChooseCityFragment(){
		if (cityFragment == null) {
			cityFragment = new CityFragment();
		}
		return cityFragment;
	}

	public mapFragment getMapFragment() {
		if(mapfragment == null){
			mapfragment = new mapFragment();
		}
		return mapfragment;
	}

	public CityFragment getExistedCityFragment(){
		return cityFragment;
	}

	public void uploadSelectedCity(City city,boolean add){
		ll_selected_citys.setVisibility(View.VISIBLE);
		if(add) {
			citys_selected.add(city);
		}
		else{
			citys_selected.remove(city);
		}
		if(citys_selected.size()>0){
			ll_selected_citys.setVisibility(View.VISIBLE);
			sum_city_selected.setText(citys_selected.size() + "");
		}
		else{
			ll_selected_citys.setVisibility(View.GONE);
		}
		horizontalAdapter.notifyDataSetChanged();
		canNext(citys_selected.size());
	}



	/**
	 */
	private void startRefreshCountryFragmentList() {
//		List<Country> countryListFromDB = getCutomCountryListFromDB();
//		showProgressDialog();
		List<continents> db_con_list = getMyConfig().getDatabase().getContinents();
		if(db_con_list.size()==0) {
//			if (getMyConfig().getMemExchange().getList_continents().size() == 0) {
				if (getMyConfig().getNetworkType() == CourseDesignAPP.NETWORK_TYPE_NONE) {
					String info = getResources().getString(R.string.check_network);
					ToastUtils.toast(HotDesitinationActivity.this, info);
				} else {
					sendContinentsListRequest();
				}
//			}
		}
		else{

			getMyConfig().getMemExchange().setList_continents(db_con_list);
			stopRefreshMainFragmentList();

		}

		List<Country> db_country_list = getMyConfig().getDatabase().getCountryByContinentId(Constants.SEARCH_HOT_COUNTRY,6);
		if(db_country_list.size() ==0) {
//			if (getMyConfig().getMemExchange().getList_country().size() == 0) {
				if (getMyConfig().getNetworkType() == CourseDesignAPP.NETWORK_TYPE_NONE) {
					String info = getResources().getString(R.string.check_network);
					ToastUtils.toast(HotDesitinationActivity.this, info);
				} else {
					startRefreshMainFragmentList();
					sendCountrysListRequest(select_continent_id);
				}
//			}
		}
		else{
			if(db_country_list.size() ==6){
				country_page++;
				country_extra =0;
			}
			else{
				country_extra = db_country_list.size();
			}
			getMyConfig().getMemExchange().setList_country(db_country_list);
			stopRefreshMainFragmentList();
		}
	}
//	private list<Country> getCutomCountryListFromDB(){
//		List<Country> list = getMyConfig().getDesitinationDataBase();
//		return list;
//	}
	public  void startRefreshCityFragmentList(int country_id){
//		showProgressDialog();
		if (getMyConfig().getNetworkType() == CourseDesignAPP.NETWORK_TYPE_NONE) {
			String info = getResources().getString(R.string.check_network);
			ToastUtils.toast(HotDesitinationActivity.this,info);
		} else {
			sendCitysListRequest(country_id);
		}
	}

	private void sendCitysListRequest(int country_id){
		BmobQuery<City> query = new BmobQuery<City>();
		query.order("city_id");
		query.addWhereEqualTo("country_id", country_id);
		query.findObjects(this,findCitysListener);

	}
	/**
	 */
	private void sendContinentsListRequest(){
		BmobQuery<continents> query = new BmobQuery<continents>();
            query.findObjects(this, findContinentsListener);
	}
	/**
	 */
	public void sendCountrysListRequest(int continents_id){
		BmobQuery<Country> query = new BmobQuery<Country>();
		query.order("country_id");//createdAt
		query.setSkip(country_page * country_count + country_extra);

		if(continents_id != Constants.SEARCH_HOT_COUNTRY){
			query.addWhereEqualTo("continents_id", continents_id);
			query.setLimit(6);
		}else{
			query.addWhereEqualTo("is_popolar", true);
			query.setLimit(country_count);
		}
		query.findObjects(this, findCoutrysListener);
	}


	public void startRefreshMainFragmentList(){
		getChooseCountryFragment().startRefresh();
	}


	/**
	 */
	FindListener findCitysListener = new FindListener<City>() {
		@Override
		public void onSuccess(List<City> list) {
			if(list.size()>0) {
				List<City> cityListFromDB = getCityListFromDB(getSelect_country_id());
				if (cityListFromDB.size() > 0) {
					if (list == null) list = new ArrayList<>();
					list.addAll(cityListFromDB);
				}
				getMyConfig().getDatabase().addCityList(list);//数据库
				getMyConfig().getMemExchange().setList_city(list);//中介类
				stopRefreshCityFragment();
			}
			else{
				ToastUtils.toast(HotDesitinationActivity.this,"没有更多数据");
			}
		}
		@Override
		public void onError(int i, String s) {
//			mProDialog.dismiss();
			ToastUtils.toast(HotDesitinationActivity.this, s.toString());
			LogUtils.i("123",s.toString());
		}
	};

	/**
	 */
	FindListener findCoutrysListener = new FindListener<Country>(){

		@Override
		public void onSuccess(List<Country> list) {
//			countryFragment.updateCoutry(list);
			if(list.size()>0) {
				getMyConfig().getDatabase().addCountryList(list);
				getMyConfig().getMemExchange().getList_country().addAll(list);
				stopRefreshMainFragmentList();
				if(list.size() == 6){
					country_page++;
					country_extra = 0;
				}
				else{
					country_extra = list.size();
				}
			}
			else{
				ToastUtils.toast(HotDesitinationActivity.this,"没有更多数据");
				stopRefreshMainFragmentList();
			}
//			List<Country> albumListFromDB = getMyConfig().getDatabase().getCountryByContinentId(getSelect_continent_id() ,6);
//			if (albumListFromDB.size() > 0 && albumListFromDB.size() > 0) {
//				if (list == null) list = new ArrayList<>();
//				list.addAll(albumListFromDB);
//			}
//			getMyConfig().getDatabase().addCountryList(list);
//
//			getMyConfig().getMemExchange().setList_country(getMyConfig().getDatabase().getCountryByContinentId(getSelect_continent_id(),6));
//			stopRefreshMainFragmentList();
		}


		@Override
		public void onError(int i, String s) {
			ToastUtils.toast(HotDesitinationActivity.this, s.toString());
		}
	};


	private List<City> getCityListFromDB(int country_id){
		return getMyConfig().getDatabase().getCityByCountryId(country_id);
	}

	public void updateCitySelected(City city,boolean ischecked){
		boolean ii =getMyConfig().getDatabase().updateCitySelecte(city,ischecked);
		getMyConfig().getMemExchange().updateCitySelected(city,ischecked);

	}
	FindListener findContinentsListener = new FindListener<continents>(){
		@Override
		public void onSuccess(List<continents> list) {
//			mProDialog.dismiss();
			if(list !=null){
				getMyConfig().getMemExchange().setList_continents(list);
				getMyConfig().getDatabase().addContinentList(list);
			}
			stopRefreshMainFragmentList();
//			EventBus.getDefault().post(list, NotifyByEventBus.UPDATE_CONTINENTS);
		}

		@Override
		public void onError(int code, String msg) {
//			mProDialog.dismiss();
			ToastUtils.toast(HotDesitinationActivity.this, msg.toString());
		}
	};

	/**
	 */
	public void stopRefreshMainFragmentList() {
		getChooseCountryFragment().stopRefresh();
	}

	/**
	 */
	public void stopRefreshCityFragment(){
		getChooseCityFragment().stopRefresh();
	}


	public void pushToFragment(int pageIndex) {
		Fragment fragment = new Fragment();
		String fragmentTag = "";
		switch (pageIndex) {
			case HotDesitinationActivity.TOCOUNTRYFRAGMENT:
				fragmentTag = "countryFragment";
				fragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
				if (fragment == null) {
					fragment = getChooseCountryFragment();
				} else {
					return;
				}
				setNow_fragment(1);
				break;
			case HotDesitinationActivity.TOCITYFRAGMENT:
				fragmentTag = "cityFragment";
				fragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
				if (fragment == null) {
					fragment = getChooseCityFragment();
				} else {
					return;
				}
//				if(getMyConfig().getMemExchange().getList_city().size() ==0){//中介类
					if(getCityListFromDB(getSelect_country_id()).size() ==0){//数据库
						startRefreshCityFragmentList(getSelect_country_id());
					}
					else{
						getMyConfig().getMemExchange().setList_city(getCityListFromDB(getSelect_country_id()));
					}
//				}else{
//					//
//				}

				setNow_fragment(2);
				break;
			case HotDesitinationActivity.CITYTOMAPFRAGMENT:
				fragmentTag = "mapFragment";
				fragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
				if(fragment == null){
					fragment = getMapFragment();
				}else{
					return;
				}
				setNow_fragment(2);
				break;
			default:
				break;
		}
//		if(pageIndex == HotDesitinationActivity.CITYTOMAPFRAGMENT){
//			getSupportFragmentManager().beginTransaction()
//					.replace(R.id.fragment_container, fragment, fragmentTag).commit();
//			this.now_fragment = pageIndex;
//		}
//		else {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fragment_container, fragment, fragmentTag)
					.addToBackStack(fragmentTag).commit();
			this.now_fragment = pageIndex;
//		}
	}


	@Override
	public void onCountryselected(int position) {
		pushToFragment(position);
	}

//	public void showProgressDialog() {
//		runOnUiThread(new Runnable() {
//			@Override
//			public void run() {
//				if (null == mProDialog) {
//					mProDialog = ProgressDialog.show(HotDesitinationActivity.this, null, "loading");
//				} else {
//					mProDialog.show();
//				}
//			}
//		});
//	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LogUtils.i("123", "onDestory");
		getMyConfig().getMemExchange().clear_hotDesitination_data();
		EventBus.getDefault().unregister(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			if(requestCode == HotDesitinationActivity.SELECT_CITY_INFO){
				City city  = (City) data.getExtras().getSerializable("select_city");
				updateCitySelected(city, true);
				getChooseCityFragment().getCity_listview_adapter().updateCity(city, true);
				uploadSelectedCity(city, true);

			}
		}
	}
}
