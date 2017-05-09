package com.example.coursedesign.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
	private List<View> views;
	private List<String> titles;

	@SuppressWarnings("unused")
	private Context context;

	/**
	 * 初始化ViewPagerAdapter
	 * */
	public ViewPagerAdapter(Context context){
		this.views = null;
		this.titles = null;
		this.context = context;
	}

	/**
	 * 设置视图列表
	 * @param views	视图列表
	 */
	public void setViews(List<View> views){
		this.views = views;
	}

	/**
	 * 设置标题列表
	 * @param titles	标题列表
	 */
	public void setTitles(List<String> titles){
		this.titles = titles;
	}

	/**
	 * 设置标题列表
	 * @param titleStr	标题列表
	 */
	public void setTitles(String[] titleStr){
		if(this.titles == null)
			this.titles = new ArrayList<>();

		this.titles.clear();
		Collections.addAll(this.titles, titleStr);
	}

	/**
	 *
	 * @return 获取视图列表
	 */
	public List<View> getViews(){
		return views;

	}

	/**
	 *
	 * @return 获取标题列表
	 */
	public List<String> getTitles(){
		return titles;
	}

	/**
	 *
	 * @param index 指定位置
	 * @return 获取指定位置视图
	 */
	public View getViewOfIndex(int index){
		if(getViews() == null)return null;
		if(index < 0 || index >= getCount())return null;
		return getViews().get(index);
	}

	/**
	 *
	 * @param index	指定位置
	 * @return 获取指定位置标题
	 */
	public String getTitleOfIndex(int index){
		if(getTitles() == null)return null;
		if(index < 0 || index >= getTitles().size())return null;
		return getTitles().get(index);
	}

    /**
     * 初始化ViewPagerAdapter
     * @param views 滑动page布局
     * */
    public ViewPagerAdapter(List<View> views, Context context){
        this.views = views;
        this.context = context;
    }

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(getViewOfIndex(position));
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		if(container != null) {
			container.addView(getViewOfIndex(position));
		}
		return getViewOfIndex(position);

	}

	@Override
	public int getCount() {
		if(getViews() == null)return 0;
		return getViews().size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if(getTitles() == null)return null;
		return getTitleOfIndex(position);
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return (view==obj);
	}

}
