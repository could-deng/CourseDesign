/**
 * 
 */
package com.example.coursedesign.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coursedesign.R;
import com.example.coursedesign.base.MyConfig;
import com.example.coursedesign.utils.ExitAppUtils;
import com.example.coursedesign.widget.ToolbarIndicator;
import com.example.coursedesign.widget.pulltorefresh.RefreshLayout;

/**
 * 
 * @ClassName: BaseFragmentActivity.java
 * @Description: FragmentActivity基础类
 * @author: cj
 * @date: 2016年1月8日 上午9:36:41
 */
public class BaseFragmentActivity extends AppCompatActivity {
	private static final String TAG = "BaseFragmentActivity";
	public Activity mContext;

	private String mPageName;
	//    private boolean fragmentMode;
	protected Toolbar toolbar;
	private TextView tv_title;
//	private MyHandler myHandler;

	private Bundle bundle_next  = new Bundle();//放toolbar右键的跳转所带参数
	private RefreshLayout swipeLayout;//下拉刷新和上拉加载布局
	private ToolbarIndicator toolbarIndicator;
	/**
	 *
	 */
	//第一个菜单项
	protected MenuItem menu_first;//导航菜单
	protected boolean showMenu = false;//是否显示toolbar右边的菜单键
	protected int MenuDrawable = R.drawable.ic_launcher;
	protected Class menugoToClass =null;
	protected boolean setClickToNext = false;//是否重写自定义菜单点击事件

//	//第二个菜单项
//	protected MenuItem menu_second;
//	protected boolean showSecondMenu = false;//是否显示toolbar右边第二个菜单键
//	protected int MenuScondDrawable = R.drawable.ic_launcher;

	public BaseFragmentActivity() {
	}

//	public int getMenuScondDrawable() {return MenuScondDrawable;}
//	public void setMenuScondDrawable(int menuScondDrawable) {MenuScondDrawable = menuScondDrawable;	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//		getWindow().setSoftInputMode(
//				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
//						| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//
		ExitAppUtils.getInstance().addActivity(this);
		mContext = this;
	}
	/**
	 * 初始化tool bar
	 */
	protected void initToolbar() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		tv_title = (TextView) findViewById(R.id.tv_title);
		toolbarIndicator = (ToolbarIndicator) findViewById(R.id.indicator);
		setSupportActionBar(toolbar);
		final ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			if (toolbar.getTitle() != null) {
				setUiTitle(toolbar.getTitle().toString());
			}
			actionBar.setDisplayShowTitleEnabled(false);//取消默认的title
		}
	}
	/**
	 * 设置界面标题,自定义标题居中
	 */
	protected void setUiTitle(String title) {
		if (title != null && tv_title != null) {
			toolbarIndicator.setVisibility(View.GONE);
			tv_title.setVisibility(View.VISIBLE);
			tv_title.setText(title);
		}
	}
	public void setMenuDrawable(int drawable){this.MenuDrawable = drawable;	}
	public void setMenugoToClass(Class menugoToClass){
		this.menugoToClass = menugoToClass;
	}
	private int getMenuDrawable(){return MenuDrawable;	}
	private Class getMenugoToClass(){
		return menugoToClass;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (showMenu) {//第一个菜单项
			menu_first = menu.add(0, Menu.FIRST + 5, 5, R.string.hello_world).setIcon(getMenuDrawable());
			menu_first.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);//总是显示
			menu_first.setActionView(R.layout.menu_music_playing);//????
			ImageView equalizer = (ImageView) menu_first.getActionView().findViewById(R.id.equalizer);
			equalizer.setImageResource(getMenuDrawable());
			equalizer.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onOptionsItemSelected(menu_first);
				}
			});
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home://返回
				finish();
				break;

			case Menu.FIRST + 5://第一个菜单项
				if(!setClickToNext) {
					if (getMenugoToClass() != null) {
						clickToolbarRight(getMenugoToClass());
					}
					else{
						clickRightDoSomeThing();
					}
				}
				else{
					clickRightMenu();
				}
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	protected void clickRightDoSomeThing() {
		Log.i("BaseFragmentActivity","clickRightDoSomeThing()");
	}

	protected void clickRightMenu() {
		Log.i("BaseFragmentActivity","clickRightMenu()");
	}
	protected void clickSecondMenu(){
		Log.i("BaseFragmentActivity","clickSecondMenu()");
	}
	@Override
	protected void onResume() {
		super.onResume();
//		LoginHelper.isLogined(getApplicationContext());
//		DecentWorldApp.getInstance().setCurrentActivity(this);
//		LogUtils.i("BaseFragmentActivity,CurrentActivity", DecentWorldApp
//				.getInstance().getCurrentActivity().getLocalClassName());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ExitAppUtils.getInstance().delActivity(this);
	}

	protected void startIntent(Class clazz) {
		startActivity(new Intent(mContext, clazz));
		finish();
	}

	//Activity跳转
	protected void startActivityFromThisActivity(Class goToClass){
		Intent intent = new Intent(this,goToClass);
		startActivity(intent);
	}
	/**点击Toolbar最右menu去的activity
	 */
	protected void clickToolbarRight(Class class_to_right){
		Intent intent = new Intent(this,class_to_right);
		intent.putExtras(bundle_next);
		startActivity(intent);
	}
	/**
	 * 设置最右menu去的activity所带bundle参数
	 * @param bundle_next
	 */
	protected void setBundle_next(Bundle bundle_next) {
		this.bundle_next = bundle_next;
	}


	protected MyConfig getMyConfig() {
		return MyConfig.getInstance();
	}


	protected void setSwipeRefreshLayout(RefreshLayout refreshLayout){
		this.swipeLayout = refreshLayout;
	}
	protected RefreshLayout getSwipeRefreshLayout(){
		return swipeLayout;
	}


	protected void sendNetRequest(String url, int iRequestType) {
//		if (getMyConfig().getNetworkType() == FitmixConstant.NETWORK_TYPE_NONE) {
//			showAppMessage(R.string.check_network, AppMsg.STYLE_INFO);
//		}
//		getRequestManager().request(url, getWeakHandler(), iRequestType);
	}

	/**
	 * 设置界面标题,viewpager
	 */
	protected void setUiIndicator(ViewPager viewpager) {
		if (toolbarIndicator != null && viewpager != null) {
			tv_title.setVisibility(View.GONE);
			toolbarIndicator.setVisibility(View.VISIBLE);
			toolbarIndicator.setViewPager(viewpager);
		}
	}

}
