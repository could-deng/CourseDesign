package com.example.coursedesign.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coursedesign.R;
import com.example.coursedesign.base.MyConfig;
import com.example.coursedesign.bean.Banner;
import com.example.coursedesign.bean.PicNew;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class BannerAdapter extends PagerAdapter {
    private List<Banner> mData;
    private LayoutInflater mInflater;
    private Context mContext;
    private SimpleDraweeView bannerCover;
    private Queue<View> mReusableViews;
    private final int BANNER_TYPE_WEB_PAGE = 2;
    private final int  BANNER_TYPE_MUISC= 3;

    public BannerAdapter(Context context, List<Banner> list) {
        if(context == null) return;
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        mReusableViews = new ArrayDeque<>(list.size());
        mData = list;
    }

    private LayoutInflater getLayoutInflater() {
        return mInflater;
    }

    private Context getContext() {
        return mContext;
    }

    public void setList(List<Banner> list) {
        if ((this.mData != null) && (list != this.mData)) this.mData.clear();
        this.mData = list;
        notifyDataSetChanged();
    }

    public List<Banner> getList() {
        return mData;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
    private MyConfig getMyConfig() {
        return MyConfig.getInstance();
    }


    private void gotoPlayingScreen(int index) {
//        Intent intent = new Intent(getContext(), PlayMusicActivity.class);
//        intent.putExtra("isBanner", true);
//        intent.putExtra("index", index);
//        getContext().startActivity(intent);
    }
    /**
     * 启动webView
     */
    private void startWebViewActivity(PicNew banner) {
//        Intent intent = new Intent();
//        intent.setClass(getContext(), WebViewActivity.class);
//        intent.putExtra("url", banner.getTypeValue());
//        intent.putExtra("title", banner.getTitle());
//        getContext().startActivity(intent);
    }
    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = mReusableViews.poll();
        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.fm_banner_cover, container, false);
        }

        bindData(view, position % mData.size());
        container.addView(view);
        return view;
    }

    private void bindData(View v, int position) {
        bannerCover = (SimpleDraweeView) v.findViewById(R.id.img_banner_cover);
        final Banner info = mData.get(position);
        if (info != null) {
            bannerCover.setImageURI(Uri.parse(info.getBackImage()));
//            bannerCover.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (info.getType() == BANNER_TYPE_MUISC) {
//                        Log.e("TTTTT","!------1----->");
//                        Music music = info.getMusic();
//                        if(music!=null){
//                            Log.e("TTTTT","!------2----->");
//                            List<Music> list = new ArrayList<>();
//                            list.add(music);
//                            getMyConfig().getPlayer().setPlayList(list);
//                            gotoPlayingScreen(0);
//                        }
//                    }else if(info.getType() == BANNER_TYPE_WEB_PAGE){
//                        startWebViewActivity(info);
//                    }
//                }
//            });
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mReusableViews.add((View) object);
    }

}
