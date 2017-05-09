/*
 * Copyright (C) 2013 Leszek Mzyk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.coursedesign.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.coursedesign.adapter.BannerAdapter;

public class LoopViewPager extends ViewPager {
    private BannerAdapter mAdapter;
    private Context ctx;

    private boolean autoable = true;

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
    }

    public LoopViewPager(Context context) {
        super(context);
        this.ctx = context;
    }

    public void setAdapter(BannerAdapter adapter) {
        mAdapter = adapter;
        super.setAdapter(mAdapter);
        setCurrentItem(adapter.getList().size() * 1000);
        startBanner();
    }

    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * 触摸停止计时器，抬起启动计时器
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 停止图片滚动
                stopImageTimerTask();
                break;
            case MotionEvent.ACTION_UP:
                // 开始图片滚动
                if (getAdapter().getCount() > 0 && autoable) {
                    startImageTimerTask();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                startImageTimerTask();
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 图片滚动任务
     */
    private void startImageTimerTask() {
        // 图片滚动
        if (mAdapter != null) {
            if (mAdapter.getCount() > 1 && isStop) {
                mHandler.postDelayed(mImageTimerTask, 3000);
                isStop = false;
            }
        }
    }

    private boolean isStop = true;

    /**
     * 停止头片滚动的任务
     */
    private void stopImageTimerTask() {
        if (mAdapter != null) {
            if (mAdapter.getCount() > 1 && !isStop) {
                mHandler.removeCallbacks(mImageTimerTask);
                isStop = true;
            }
        }
    }

    private Handler mHandler = new Handler();

    /**
     * 图片自动轮播Task
     */
    private Runnable mImageTimerTask = new Runnable() {
        @Override
        public void run() {
            if (mAdapter != null) {
                if (!isStop) {
                    setCurrentItem(getCurrentItem() + 1);
                    mHandler.postDelayed(mImageTimerTask, 3000);
                }
            }
        }
    };

    public void setAutoable(boolean b) {
        autoable = b;
    }

    public void startBanner() {
        stopImageTimerTask();
        startImageTimerTask();
    }

    public void stopBanner() {
        stopImageTimerTask();
    }
}
