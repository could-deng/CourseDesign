package com.example.coursedesign.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.example.coursedesign.R;
import com.example.coursedesign.utils.Utils;

import static android.support.v4.view.ViewPager.OnPageChangeListener;


public class ToolbarIndicator extends LinearLayout implements OnPageChangeListener {

//    private final static int DEFAULT_INDICATOR_WIDTH = 3;
//    private final static int TEXT_SIZE = 20;
    private ViewPager mViewpager;
//    private int mIndicatorMargin;
    private int mIndicatorColor;//指示器颜色
    private float mIndicatorWidth;
    private float mIndicatorHeight;
    private int mTextColor;
    private float mTextSize;
    private int mCurrentPosition = 0;

    private float mTranslationX;
    private int mTabWidth;
    private int mTabCount;
    private String[] mTitles;
    private Paint mPaint;
    private RadioGroup radioGroup;
//    private ToolbarIndicator.OnTabClickListener mListener;

//    public interface OnTabClickListener {
//        void onTabClick(int index);
//    }
//
//    /**
//     * 设置tab点击回调事件
//     */
//    public void setOnTabClickListener(ToolbarIndicator.OnTabClickListener listener) {
//        mListener = listener;
//    }

    public ToolbarIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public ToolbarIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
        handleTypedArray(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(mIndicatorColor);
        mPaint.setStrokeWidth(mIndicatorHeight);//9.0f
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        TypedArray typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.SimpleViewPagerIndicator);

        mIndicatorColor = typedArray.getColor(R.styleable.SimpleViewPagerIndicator_indicator_color, ContextCompat.getColor(getContext(), R.color.colorAccent));
        mIndicatorWidth = typedArray.getDimension(R.styleable.SimpleViewPagerIndicator_indicator_width, 60);
        mIndicatorWidth = Utils.dp2px(getContext().getResources(), mIndicatorWidth);//dp转px
        mIndicatorHeight = typedArray.getDimension(R.styleable.SimpleViewPagerIndicator_indicator_height, 1);
        mIndicatorHeight = Utils.dp2px(getContext().getResources(), mIndicatorHeight);//dp转px
        mTextColor = typedArray.getColor(R.styleable.SimpleViewPagerIndicator_text_color, ContextCompat.getColor(getContext(), R.color.textColorPrimary));
        mTextSize = typedArray.getDimension(R.styleable.SimpleViewPagerIndicator_text_size, 18);
        typedArray.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mTabCount > 0) {
            mTabWidth = w / mTabCount;
        }
    }


    public void setIndicatorColor(int indicatorColor) {
        this.mIndicatorColor = indicatorColor;
    }

    public void setViewPager(ViewPager viewPager) {
        mViewpager = viewPager;
        mCurrentPosition = mViewpager.getCurrentItem();
        mViewpager.addOnPageChangeListener(this);
        onPageSelected(mCurrentPosition);

        String[] mTitles = {getContext().getString(R.string.all_trip),getContext().getString(R.string.xincheng)};
        this.mTitles = mTitles;
        mTabCount = mTitles.length;
        generateTitleView();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        scroll(position, positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();
        canvas.translate(mTranslationX, getHeight() - 2);
        canvas.drawLine((mTabWidth - mIndicatorWidth) / 2, 0, (mTabWidth + mIndicatorWidth) / 2, 0, mPaint);
        canvas.restore();
    }

    public void scroll(int position, float offset) {
        /**
         * <pre>
         *  0-1:position=0 ;1-0:postion=0;
         * </pre>
         */
        mTranslationX = getWidth() / mTabCount * (position + offset);
        if (radioGroup != null && position < radioGroup.getChildCount()) {
            if (radioGroup.getChildAt(position) instanceof RadioButton) {
                ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);

            }
        }
        invalidate();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    private void generateTitleView() {
        if (getChildCount() > 0)
            this.removeAllViews();

        //创建RadioGroup
        radioGroup = new RadioGroup(getContext());
        radioGroup.setOrientation(RadioGroup.HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        radioGroup.setLayoutParams(layoutParams);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            }
        });

        int count = mTitles.length;
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked}, // unchecked
                new int[]{android.R.attr.state_checked}  // checked
        };
        int[] colors = new int[]{
                mTextColor,
                mIndicatorColor
        };

        ColorStateList radioButtonTextColor = new ColorStateList(states, colors);

        radioGroup.setWeightSum(count);
        for (int i = 0; i < count; i++) {
            RadioButton radioButton = new RadioButton(getContext());
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(0, LayoutParams.MATCH_PARENT, 1);

            radioButton.setGravity(Gravity.CENTER);
            radioButton.setButtonDrawable(new StateListDrawable());//android:button="@null"
            radioButton.setTextColor(radioButtonTextColor);
            radioButton.setText(mTitles[i]);
            radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
            final int index = i;
            radioButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (mListener != null) {
//                        Log.e("TTTTT","onClick");
//                        mListener.onTabClick(index);
//                    }
                    mViewpager.setCurrentItem(index);
                }
            });
            radioGroup.addView(radioButton, i, lp);
        }
        addView(radioGroup);
    }

    /**
     * 设置文字颜色
     *
     * @param colorResId 颜色的资源ID
     */
    public void setTextColor(int colorResId) {
//        int color = ContextCompat.getColor(getContext(), colorResId);
        mTextColor = ContextCompat.getColor(getContext(), colorResId);

        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked}, // unchecked
                new int[]{android.R.attr.state_checked}  // checked
        };
        int[] colors = new int[]{
                mTextColor,
                mIndicatorColor
        };

        ColorStateList radioButtonTextColor = new ColorStateList(states, colors);

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            radioButton.setTextColor(radioButtonTextColor);
        }
    }
}
