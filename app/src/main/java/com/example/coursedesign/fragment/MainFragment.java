package com.example.coursedesign.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.example.coursedesign.R;
import com.example.coursedesign.activity.BaseFragmentActivity;
import com.example.coursedesign.activity.ChooseDateActivity;
import com.example.coursedesign.activity.MainActivity;
import com.example.coursedesign.activity.MySingleTripActivity;
import com.example.coursedesign.activity.PicNewsActivity;
import com.example.coursedesign.adapter.AlbumAdapter;
import com.example.coursedesign.adapter.BannerAdapter;
import com.example.coursedesign.adapter.GalleryAdapter;
import com.example.coursedesign.adapter.ViewPagerAdapter;
import com.example.coursedesign.animation.DepthPageTransformer;
import com.example.coursedesign.bean.Banner;
import com.example.coursedesign.bean.PicNew;
import com.example.coursedesign.bean.Trip;
import com.example.coursedesign.controller.ActivityTransitionLauncher;
import com.example.coursedesign.utils.Utils;
import com.example.coursedesign.widget.BounceViewPager;
import com.example.coursedesign.widget.LoopViewPager;
import com.example.coursedesign.widget.pulltorefresh.RefreshLayout;
import com.example.coursedesign.widget.pulltorefresh.SwipeRefreshDirection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanqiang on 2016/3/17.
 */
public class MainFragment extends BaseFragment implements ViewPager.OnPageChangeListener, RefreshLayout.OnRefreshListener{

    private BounceViewPager viewpager;
    private ViewPagerAdapter pageAdapter;
    private ListView list_sport_music;
    private AlbumAdapter adapter;

    private View lunboView;
    private LoopViewPager ViewPager;
    private LinearLayout dotContainer;
    private List<ImageView> dotViewList = new ArrayList<>();

    private BannerAdapter bannerAdapter;
    private GalleryAdapter galleryAdapter;
    LinearLayout ll_create_new_trip;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        clear();
        View view = initViews(inflater, container);
        refreshList();

//        View view = inflater.inflate(R.layout.fragment_main,container,false);
        ((MainActivity) getActivity()).setToolbar(false, R.string.fragment_main);
        ((MainActivity) getActivity()).setShowMenu(true);

        ((BaseFragmentActivity) getActivity()).setMenuDrawable(R.drawable.create_trip_selector);
        ((BaseFragmentActivity)getActivity()).setMenugoToClass(ChooseDateActivity.class);

        ((MainActivity) getActivity()).setIndicator(viewpager);
//        init(view);
        return view;

    }

    public void stopRefresh() {
        super.stopRefresh();
        refreshList();
    }

    private void clear() {
        viewpager = null;
        pageAdapter = null;
        list_sport_music = null;
//        adapter = null;
        //rg_music = null;
    }

    /**
     * （本地）刷新游记列表
     */
    private void refreshList() {
        if (getList() != null && getListAdapter() != null) {
                getListAdapter().setList(getList());
        }
        if (getBannerList().size() > 0) {
            if(getActivity() == null){
                return;
            }
            showBanner();
        }else{
            lunboView.setVisibility(View.GONE);
        }
    }
    public void refreshTripList(){
        if(getTripList() != null && getGalleryAdapter()!=null){
            getGalleryAdapter().setTrip_list(getTripList());
        }
    }
    public void showBanner() {//显示banner

        if(getActivity()!=null){

            lunboView.setVisibility(View.VISIBLE);
            bannerAdapter = new BannerAdapter(getActivity(), getBannerList());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    Utils.dp2px(getContext(), 7), Utils.dp2px(getContext(), 7));
            params.setMargins(5, 1, 5, 1);
            dotContainer.removeAllViews();
            dotViewList.clear();

            for (int i = 0; i < getBannerList().size(); i++) {
                ImageView dot = new ImageView(getContext());
                dotContainer.addView(dot, params);
                dotViewList.add(dot);
            }

            ViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    for (int i = 0; i < dotViewList.size(); i++) {
                        if (i == position % getBannerList().size()) {
                            dotViewList.get(position % getBannerList().size()).setImageResource(R.drawable.dot_focused);
                        } else {
                            (dotViewList.get(i)).setImageResource(R.drawable.dot_normal);
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
        });
            ViewPager.setAdapter(bannerAdapter);
        }
    }

    private List<Banner> getBannerList() {
        return getMyConfig().getMemExchange().getListBanner();
    }

    private List<PicNew> getList() {
        return getMyConfig().getMemExchange().getListAlbum();
    }
    private List<Trip> getTripList(){
        return  getMyConfig().getMemExchange().getList_trip();
    }

    public GalleryAdapter getGalleryAdapter() {
        return galleryAdapter;
    }

    private View initViews(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);


        List<View> views = new ArrayList<>();
        //游记
        View TripsLayout = inflater.inflate(R.layout.fragment_music_viewpager_sport_music, null);

        RefreshLayout swipeLayout = (RefreshLayout) TripsLayout.findViewById(R.id.swipe_container);
        setSwipeRefreshLayout(swipeLayout);
        getSwipeRefreshLayout().setOnRefreshListener(this);

        list_sport_music = (ListView) TripsLayout.findViewById(R.id.list_sport_music);
        lunboView = inflater.inflate(R.layout.fragment_album_lunbo_viewpager, null);
        ViewPager = (LoopViewPager) lunboView.findViewById(R.id.act_login_banner);
        dotContainer = (LinearLayout) lunboView.findViewById(R.id.dot_container);

        list_sport_music.addHeaderView(lunboView);

        adapter = new AlbumAdapter(getActivity());
        list_sport_music.setAdapter(getListAdapter());//设置adapter
        list_sport_music.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == getList().size() + 1) {//最后一个

                } else {
                    getMyConfig().getMemExchange().setPicNew(getList().get(position - 1));
                    gotoPicNewsDetailActivity(view, position - 1);
                }
            }
        });
        views.add(TripsLayout);

        //全部行程
        View myMusicLayout = inflater.inflate(R.layout.fragment_main, null);
        Gallery gallery = (Gallery) myMusicLayout.findViewById(R.id.gallery_trips);
        galleryAdapter = new GalleryAdapter(getActivity());
        gallery.setOnItemClickListener(clickListener);
        gallery.setAdapter(galleryAdapter);

        gallery.setOnItemSelectedListener(selecteListener);
        views.add(myMusicLayout);
//        refreshMyMusicNum();

        pageAdapter = new ViewPagerAdapter(views, getActivity());
        viewpager = (BounceViewPager) view.findViewById(R.id.viewpager_music);
        viewpager.setAdapter(pageAdapter);
        viewpager.setPagerCount(pageAdapter.getCount());//设置页数,回弹效果必须有
        viewpager.setPageTransformer(true, new DepthPageTransformer());//设置切换效果
        viewpager.addOnPageChangeListener(this);

        return view;
    }

    /**
     * gallery的select监听器
     */
    AdapterView.OnItemSelectedListener selecteListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            galleryAdapter.setSelectItem(position);  //当滑动时，事件响应，调用适配器中的这个方法。
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    /**
     * gallery的点击监听器
     */
    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position == galleryAdapter.getCount()-1){
                //最后一个
                ((MainActivity)getActivity()).startActivity(new Intent(getContext(), ChooseDateActivity.class));
            }
            else{
                //不为最后一个
                Intent intent = new Intent(getContext(),MySingleTripActivity.class);
                intent.putExtra("if_create",false);
                intent.putExtra("new_objectid",galleryAdapter.getTrip_list().get(position).getObjectId());
                startActivity(intent);
            }
        }
    };

    /**
     * 跳转到新闻详情界面
     *
     */
    private void gotoPicNewsDetailActivity(View view, int pos) {
        if ((pos < 0) || (pos >= getList().size())) return;

        final Intent intent = new Intent(getActivity(), PicNewsActivity.class);

        intent.putExtra("news", getList().get(pos));

        ActivityTransitionLauncher.with(getActivity())
                .from(view)
                .image(getList().get(pos).getNew_pic_url())
                .launch(intent, true);//传入的是SimpleDraweeView所认可的路径字符串
    }

    private AlbumAdapter getListAdapter() {
        return adapter;
    }

//    private void init(View view) {
//        ll_create_new_trip = (LinearLayout) view.findViewById(R.id.ll_create_new_trip);
//        ll_create_new_trip.setOnClickListener(onclicklistener);
//    }
//    View.OnClickListener onclicklistener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()){
//                case R.id.ll_create_new_trip:
//                    Intent intent =new Intent(getActivity(),ChooseDateActivity.class);
//                    startActivity(intent);
//                    break;
//            }
//        }
//    };


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //当页面在滑动的时候会调用此方法,在滑动被停止之前，此方法会一直得到调用。
        //index:当前页面及你点击滑动页面,percent：当前页面偏移的百分比,offset：当前页面偏移的像素位置
    }

    @Override
    public void onPageSelected(int position) {
        //((RadioButton) rg_music.getChildAt(index)).setChecked(true);
        viewpager.setCurrentIndex(position);//设置标号,回弹效果必须有
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //此方法在状态改变时调用
        //state==1正在滑动,state==2滑动完成,state==0什么都没做
    }

    @Override
    public void onRefresh(SwipeRefreshDirection direction) {
        if (direction == SwipeRefreshDirection.TOP) {//下拉刷新回调
            ((MainActivity) getActivity()).startRefreshMusicFragmentList();
        }
    }
}
