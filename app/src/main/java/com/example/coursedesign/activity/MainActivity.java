package com.example.coursedesign.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.easemob.chat.EMChat;
import com.example.coursedesign.CourseDesignAPP;
import com.example.coursedesign.R;
import com.example.coursedesign.bean.Banner;
import com.example.coursedesign.bean.PicNew;
import com.example.coursedesign.bean.Trip;
import com.example.coursedesign.bean.continents;
import com.example.coursedesign.common.Constants;
import com.example.coursedesign.fragment.MainFragment;
import com.example.coursedesign.fragment.SecondFragment;
import com.example.coursedesign.service.LocationService;
import com.example.coursedesign.utils.LogUtils;
import com.example.coursedesign.utils.LoginHelper;
import com.example.coursedesign.utils.ToastUtils;
import com.example.coursedesign.widget.DetialGallery;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {
    public static final String TAG = "MainActivity";
//    private ResideMenu resideMenu;
//    private MainActivity mContext;
//    private ResideMenuItem itemHome;
//    private ResideMenuItem itemProfile;
//    private ResideMenuItem itemfriends;
//    private ResideMenuItem itemSettings;
//    private ResideMenuItem itemTripNearBy;
    private boolean islogined=false;

    Intent locationIntent;

    Button create_new_trip;
//    RadioGroup rg_nav;
//    RadioButton rg_rb1;
//    RadioButton rg_rb2;
//    RadioButton rg_rb3;
    MainFragment mainFragment;
//    SecondFragment secondFragment;
    private int previousNavIndex;//上一个选择的radio button下标

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtils.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        clear();
        setContentView(R.layout.activity_main);
        /** 保存屏幕宽度 **/
        Constants.screenWidth = getDisplayWidth();
        initToolbar();
//        initMenu();
        initView();
    }

    private void clear() {
        mainFragment = null;
//        secondFragment = null;
        previousNavIndex = -1;
    }
    private void initView() {
        locationIntent = new Intent(this, LocationService.class);
        showMenu = false;
//        rg_nav = (RadioGroup) findViewById(R.id.rg_nav);
//        rg_nav.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                //加的
//                if (getPreviousNavIndex() >= 0)
//                    ((RadioButton) rg_nav.getChildAt(getPreviousNavIndex())).setText("");//清空上一个标签名称
//
//                getSupportFragmentManager().popBackStackImmediate();
//                switch (checkedId){
//                    case R.id.rg_rb1:
//                        switchToFragment(0);
//                        ((RadioButton) findViewById(R.id.rg_rb1)).setChecked(true);
//                        break;
//                    case R.id.rg_rb2:
//                        switchToFragment(1);
//                        ((RadioButton) findViewById(R.id.rg_rb2)).setChecked(true);
//                        break;
//                    case R.id.rg_rb3:
//                        switchToFragment(2);
//                        ((RadioButton) findViewById(R.id.rg_rb3)).setChecked(true);
//                        break;
//                }
//            }
//        });
//        ((RadioButton) findViewById(R.id.rg_rb1)).setText(R.string.new_trip);
//        ((RadioButton) findViewById(R.id.rg_rb2)).setText(R.string.planed_trip);
//        ((RadioButton) findViewById(R.id.rg_rb3)).setText(R.string.finished_trip);
        switchToFragment(0);
    }
    public void setShowMenu(Boolean show){
        this.showMenu =show;
    }
    private void switchToFragment(int pageIndex) {
        Fragment fragment = new Fragment();
        String fragmentTag = "";
        switch (pageIndex) {
            case 0:
                fragmentTag = "mainFragment";
                fragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
                if (fragment == null) {
                    fragment = getMainFragment();
                } else {
                    return;
                }
                break;
            case 1:
//                fragmentTag = "secondFragment";
//                fragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
//                if (fragment == null) {
//                    fragment = getSecondFragment();
//                } else {
//                    return;
//                }
                break;
            case 2:
                break;
            default:
                break;
        }
        int animationIdIn = 0;
        int animationIdOut = 0;
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 0) {//当前显示的是二级fragment时,不用再设置切换动画
            if (getPreviousNavIndex() < 0) {
            } else if (getPreviousNavIndex() > pageIndex) {
                animationIdIn = R.anim.push_right_in;
                animationIdOut = R.anim.push_right_out;

            } else if (getPreviousNavIndex() < pageIndex) {
                animationIdIn = R.anim.push_left_in;
                animationIdOut = R.anim.push_left_out;
            } else {
                return;
            }
        }
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(animationIdIn, animationIdOut, 0, 0)
                .replace(R.id.mainContainer, fragment, fragmentTag).commit();
        this.previousNavIndex = pageIndex;
    }

    private int getPreviousNavIndex() {
        return previousNavIndex;
    }
    private MainFragment getMainFragment() {
        if (mainFragment == null) {
            mainFragment = new MainFragment();
            startRefreshMusicFragmentList();
        }
        return mainFragment;
    }
//    private SecondFragment getSecondFragment(){
//        if(secondFragment == null){
//            secondFragment = new SecondFragment();
//        }
//        return secondFragment;
//    }

    public void startRefreshMusicFragmentList() {
        if (getMyConfig().getNetworkType() == Constants.NETWORK_TYPE_NONE) {
            //没有网络进行提示
            ToastUtils.toast(MainActivity.this,getResources().getString(R.string.check_network));
//            getAlbumInfo();
//            getBannerInfo();
//            getMusicFragment().startRefresh();
//            String info = getResources().getString(R.string.check_network);
//            showAppMessage(info, AppMsg.STYLE_CONFIRM);
        } else {
            sendAlbumListRequest();
            sendBannerRequest();
        }
    }
    private void sendAlbumListRequest(){
        BmobQuery<PicNew> query = new BmobQuery<PicNew>();
        //执行查询方法
        query.findObjects(this, findPicNewListener);
    }
    private void sendBannerRequest(){
        BmobQuery<Banner> query = new BmobQuery<Banner>();
        //执行查询方法
        query.findObjects(this, findBannerListener);

    }


    FindListener findBannerListener = new FindListener<Banner>() {
        @Override
        public void onSuccess(List<Banner> list) {
            if(list!=null) {
                getMyConfig().getMemExchange().setListBanner(list);
            }
            stopRefreshMusicFragmentList();
        }

        @Override
        public void onError(int i, String s) {
            Log.i(getMyConfig().getTag(),s.toString());
            ToastUtils.toast(MainActivity.this,s.toString());
        }
    };
    /**
     * 查询游记回调
     */
    FindListener findPicNewListener = new FindListener<PicNew>(){

        @Override
        public void onSuccess(List<PicNew> list) {
//            List<PicNew> albumListFromDB = getCutomAlbumListFromDB();
//            if (albumListFromDB.size() > 0 && albumListFromDB.size() > 0) {
//                if (albumList == null) albumList = new ArrayList<>();
//                albumList.addAll(albumListFromDB);
//            }
            if (list != null) {
                getMyConfig().getMemExchange().setListAlbum(list);
            }
            stopRefreshMusicFragmentList();
        }

        @Override
        public void onError(int i, String s) {
            Log.i(getMyConfig().getTag(),s.toString());
        }
    };
    public void stopRefreshMusicFragmentList() {
        getMainFragment().stopRefresh();
    }

    private List<PicNew> getCutomAlbumListFromDB() {
//        List<Album> list = getMyConfig().getDatabase().getAllCustomAlbumList();
//        return getMyConfig().getDatabase().getAllCustomAlbumList();
        return null;
    }

    /**
     * 设置 toolbar
     *
     * @param showBack   是否显示返回按钮
     * @param titleResId toolbar标题字符串资源ID
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
            if (titleResId == R.string.fragment_main) {//最左边设成（登录/个人设置）入口
                toolbar.setNavigationIcon(R.drawable.ic_user_head_def_round);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        if (LoginHelper.isLogined(MainActivity.this)) {
                            intent.setClass(MainActivity.this,SettingActivity.class);
                        } else {
                            intent.setClass(MainActivity.this, LoginActivity.class);
                        }
                        startActivity(intent);
                    }
                });
            }
        }

        String title = getString(titleResId);
        if (!TextUtils.isEmpty(title)) {
            setUiTitle(title);
        }
    }
    /**
     * 获取手机屏幕的参数
     *
     * @return
     */
    private int getDisplayWidth() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels; // 屏幕宽度（像素）
        int height = metric.heightPixels;
        return width;
    }

    /**
     Description:初始化菜单效果
     Input:
     */
    private void initMenu() {
//      临时注释
//        //如果该activity在后台被kill掉，不进onnewInten（），进oncreate（）
//        if(EMChat.getInstance().isLoggedIn()){
//            islogined = true;
//            //开启定位服务
////			startService(locationIntent);
//        }
//        else{
//            islogined = false;
//            //开启定位服务
////    		stopService(locationIntent);
//        }





//        mContext =this;
//        // attach to current activity;
//        resideMenu = new ResideMenu(this);
//        resideMenu.setUse3D(true);
//        resideMenu.setBackground(R.drawable.menu_background);
//        resideMenu.attachToActivity(this);
//        resideMenu.setMenuListener(menuListener);
//        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
//        resideMenu.setScaleValue(0.6f);
//
//        // create menu items;
//        itemHome     = new ResideMenuItem(this, R.drawable.icon_home,     R.string.my_trip);
//        islogined = LoginHelper.isLogined(MainActivity.this);//判断是否登录过
//        itemProfile  = new ResideMenuItem(this, R.drawable.icon_profile,  islogined?(R.string.private_set):(R.string.login_profile));
//        itemfriends = new ResideMenuItem(this, R.drawable.icon_calendar, R.string.friends);
//        itemSettings = new ResideMenuItem(this, R.drawable.icon_settings, R.string.setting);
//        itemTripNearBy = new ResideMenuItem(this, R.drawable.icon_home, R.string.trip_near_by);
//        itemHome.setOnClickListener(this);
//        itemProfile.setOnClickListener(this);
//        itemfriends.setOnClickListener(this);
//        itemSettings.setOnClickListener(this);
//        itemTripNearBy.setOnClickListener(this);
//
//        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
//        resideMenu.addMenuItem(itemTripNearBy, ResideMenu.DIRECTION_LEFT);
//        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
//        resideMenu.addMenuItem(itemfriends, ResideMenu.DIRECTION_RIGHT);
//        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_RIGHT);













//        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
//            }
//        });
//        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
//            }
//        });
    }



//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return resideMenu.dispatchTouchEvent(ev);
//    }

    @Override
    public void onClick(View view) {
//        if (view == itemHome){
////            changeFragment(new HomeFragment());
////            startActivityFromThisActivity(HotDesitinationActivity.class);
//            startActivityFromThisActivity(MyTripActivity.class);
//        }else if (view == itemProfile){
//            if(!islogined){
//                startActivityFromThisActivity(LoginActivity.class);
//            }
//            else{
//                ToastUtils.toast(this, "私人定制");
//            }
//        }else if (view == itemfriends){
////            changeFragment(new CalendarFragment());
//        }else if (view == itemSettings){
////            changeFragment(new SettingsFragment());
//            startActivityFromThisActivity(SettingActivity.class);
//        }else if(view == itemTripNearBy){
//            //周边游
//            startActivityFromThisActivity(MapActivity.class);
//        }
//        resideMenu.closeMenu();
    }

//    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
//        @Override
//        public void openMenu() {
////            Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void closeMenu() {
////            Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
//        }
//    };

//    private void changeFragment(Fragment targetFragment){
//        resideMenu.clearIgnoredViewList();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.main_fragment, targetFragment, "fragment")
//                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                .commit();
//    }

    // What good method is to access resideMenu？
//    public ResideMenu getResideMenu(){
//        return resideMenu;
//    }


    @Override
    protected void onResume() {
        LogUtils.i(TAG, "onresume()");
        super.onResume();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        LogUtils.i(TAG, "onNewIntent()");
        super.onNewIntent(intent);
        if(CourseDesignAPP.getInstance().isLOgin()){
            //开启定位服务
	    	if(locationIntent!=null){
//			startService(locationIntent);
            }
            refreshTripList();
            getMyConfig().getMemExchange().setList_trip(null);//网络获取行程数据
            //登录状态保存到application上
        }
        else{
            //停止定位服务
    		if(locationIntent!=null){
//    		stopService(locationIntent);
            }
            refreshTripList();
            //清空行程数据
//            getMyConfig().getMemExchange().clearTripList();
            //注销状态保存到application上
        }
//        itemProfile.setTitle(LoginHelper.isLogined(MainActivity.this) ? (R.string.private_set) : (R.string.login_profile));
        setIntent(intent);
    }

    /**
     * 访问行程列表数据
     */
    private void refreshTripList() {
        if(CourseDesignAPP.getInstance().isLOgin()) {
            BmobQuery<Trip> query = new BmobQuery<>();
            query.addWhereEqualTo("user_object_id", BmobUser.getCurrentUser(getApplicationContext()).getObjectId());
            query.findObjects(this, findTripListener);
        }
        else{
            getMyConfig().getMemExchange().clearTripList();
            torefreshTripList();
        }
    }
    FindListener findTripListener = new FindListener<Trip>() {
        @Override
        public void onSuccess(List<Trip> list) {
            if(list!=null){
                getMyConfig().getMemExchange().setList_trip(list);
                torefreshTripList();
            }
        }

        @Override
        public void onError(int i, String s) {
            ToastUtils.toast(MainActivity.this,s.toString());
        }
    };

    private void torefreshTripList() {
        getMainFragment().refreshTripList();
    }


    /**
     * 设置标题是否有viewPager
     *
     * @param viewPager
     */
    public void setIndicator(ViewPager viewPager) {
        setUiIndicator(viewPager);
    }

}
