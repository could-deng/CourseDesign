package com.example.coursedesign.fragment;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.coursedesign.base.MyConfig;
import com.example.coursedesign.widget.pulltorefresh.RefreshLayout;

/**
 * Created by yuanqiang on 2016/3/17.
 * @ClassName: BaseFragment
 * @Description: 主界面fragment用到的基础fragment
 * @date 2015年6月29日12:34:03
 *
 */
public abstract class BaseFragment extends Fragment
{
    /** Fragment当前状态是否可见 */
    protected boolean isVisible;
    /** 标志位，标志已经初始化完成 */
    protected boolean isPrepared;
    /** 是否已被加载过一次，第二次就不再去请求数据了 */
    protected boolean mHasLoadedOnce;

    private RefreshLayout swipeLayout;//下拉刷新和上拉加载布局

    /** setUserVisibleHint是在onCreateView之前调用的 **/
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint())
        {
            isVisible = true;
            onVisible();
        } else
        {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见
     */
    protected void onVisible()
    {
//        lazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible()
    {
    }

    /**
     * 延迟加载 子类必须重写此方法(每一次可见都会调用)
     */
//    protected abstract void lazyLoad();

//    public abstract void turnToTab(int tab);
    protected void setSwipeRefreshLayout(RefreshLayout refreshLayout){
        this.swipeLayout = refreshLayout;
    }
    protected RefreshLayout getSwipeRefreshLayout(){
        return swipeLayout;
    }

    protected MyConfig getMyConfig() {
        return MyConfig.getInstance();
    }

    public void stopRefresh() {
        if (getSwipeRefreshLayout() == null) return;
        getSwipeRefreshLayout().setRefreshing(false);
        Log.d(getMyConfig().getTag(), "stopRefresh");
    }
    public void startRefresh(){
        if (getSwipeRefreshLayout() == null) return;
        getSwipeRefreshLayout().setRefreshing(true);
        Log.d(getMyConfig().getTag(), "startRefresh");
    }
}
