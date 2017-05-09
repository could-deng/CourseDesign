///**
// *
// */
//package com.example.coursedesign.activity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.GridView;
//import android.widget.ListView;
//
//import com.example.coursedesign.R;
//import com.example.coursedesign.adapter.ChooseCityListViewAdapter;
//import com.example.coursedesign.adapter.ChooseCountryGridViewAdapter;
//import com.example.coursedesign.bean.City;
//import com.example.coursedesign.bean.Country;
//import com.example.coursedesign.common.Urls;
//import com.example.coursedesign.widget.ScrollLayout;
//
//import java.util.ArrayList;
//
///**
// * @author yuanqiang
// *
// */
//public class ChooseDesitinationActivity extends BaseFragmentActivity {
//	ScrollLayout choose_des__scrollLayout;
//	Button bt_coun1;
//	Button bt_city;
//
//	GridView fragment_des_country_gv;
//	ArrayList<Country> country_list;
//	ChooseCountryGridViewAdapter country_gridview_adapter;
//
//	ListView fragment_des_city_lv;
//	ArrayList<City> city_list;
//	ChooseCityListViewAdapter city_listview_adapter;
//
//	@Override
//	protected void onCreate(Bundle arg0) {
//		// TODO Auto-generated method stub
//		super.onCreate(arg0);
//		setContentView(R.layout.choose_desitination_layout);
//		init();
//	}
//	/**
//	 Description:
//	 Input:
//	 */
//	private void init() {
//		choose_des__scrollLayout = (ScrollLayout) findViewById(R.id.choose_des__scrollLayout);
//		country_list = new ArrayList<Country>();
//		city_list = new ArrayList<City>();
//
//		bt_coun1 = (Button) findViewById(R.id.bt_coun1);
//		bt_coun1.setOnClickListener(clickListener);
//		bt_city = (Button) findViewById(R.id.bt_city);
//		bt_city.setOnClickListener(clickListener);
//
//		//默认先显示第一screen
//		choose_des__scrollLayout.setToScreen(0);
//
//
//
//
//	}
//
//	OnClickListener clickListener =new OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.id.bt_coun1:
//				switchScrollLayoutView(1);
//				break;
//
//			case R.id.bt_city:
//				switchScrollLayoutView(0);
//				break;
//			default:
//				break;
//			}
//		}
//	};
//
//	/**
//	 * 跳转到Index的页面
//	 *
//	 * @param index
//	 */
//	public void switchScrollLayoutView(int index) {
//		// 先将所有的Menu标签初始化为最初的状态
////		setTabDefaultBackground();
//
//		switch (index) {
//		case 0:
//			initCountry();
//			break;
//		case 1:
//			initCity();
//			break;
////		case 2:
////			initFind();
////			break;
////		case 3:
////			initUserView();
////			break;
//		}
//		// 页面切换到对应的索引值
//		choose_des__scrollLayout.setToScreen(index);
//	}
//	/**
//	 Description:
//	 Input:
//	 */
//	private void initCity() {
//		for(int i =0; i<6 ; i++){
//			city_list.add(new City(Urls.countrys[i], Urls.imageUrls[i]));
//		}
//		city_listview_adapter = new ChooseCityListViewAdapter(city_list, this);
//		fragment_des_city_lv = (ListView) findViewById(R.id.fragment_des_city_lv);
//		fragment_des_city_lv.setAdapter(city_listview_adapter);
//	}
//	/**
//	 Description:
//	 Input:
//	 */
//	private void initCountry() {
//		for(int i =0; i<6 ; i++){
//			country_list.add(new Country(Urls.countrys[i], Urls.imageUrls[i]));
//		}
//		country_gridview_adapter = new ChooseCountryGridViewAdapter(country_list, this);
//		fragment_des_country_gv = (GridView) findViewById(R.id.fragment_des_country_gv);
//		fragment_des_country_gv.setAdapter(country_gridview_adapter);
//	}
//
//
//}
