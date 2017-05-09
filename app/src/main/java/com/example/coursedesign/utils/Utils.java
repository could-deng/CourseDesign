package com.example.coursedesign.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;

public class Utils {
    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return  dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp){
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static int dp2px(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public static float sp2px(Context context, int px) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }

    /**获取今天凌晨的时间戳*/
    public static long getTimesMorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

//    /**
//     * 让抽屉菜单全屏
//     * */
//    public static void fixMinDrawerMargin(DrawerLayout drawerLayout) {
//        try {
//            Field f = DrawerLayout.class.getDeclaredField("mMinDrawerMargin");
//            f.setAccessible(true);
//            f.set(drawerLayout, 0);
////            Log.i("TT", "fixMinDrawerMargin");
//            drawerLayout.requestLayout();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 格式化
     */
    private static DecimalFormat dfs = null;

    public static DecimalFormat format(String pattern) {
        if (dfs == null) {
            dfs = new DecimalFormat();
        }
        dfs.setRoundingMode(RoundingMode.FLOOR);
        dfs.applyPattern(pattern);
        return dfs;
    }

    /**
     * 获取平方和
     *
     * @param a 参数1
     * @param b 参数2
     */
    public static float hypo(int a, int b) {
        return (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }
}
