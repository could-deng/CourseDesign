package com.example.coursedesign.utils;

import android.util.Log;

/**
 * 
 * @author Jansin Miao 2013年4月8日 开发阶段将下面LOG_LEVEL 设置为6这样所有的log都能显示，
 *         在发布的时候我们将LOG_LEVEL 设置为0.这样log就非常方便管理了
 * 
 */
public class LogUtils
{
	public static int LOG_LEVEL = 6;
	public static int ERROR = 1;
	public static int WARN = 2;
	public static int INFO = 3;
	public static int DEBUG = 4;
	public static int VERBOS = 5;

	public static final String TAG = "log_course_design";
	
	/**
	 * 
	 * @param tag 标签，当为""时，默认为"dw"
	 * @param msg 要输出的提示信息
	 */
	public static void e(String tag, String msg)
	{
		if (LOG_LEVEL > ERROR)
			Log.e(TAG + tag, msg);
	}

	/**
	 * 
	 * @param tag 标签，当为""时，默认为"dw"
	 * @param msg 要输出的提示信息
	 */
	public static void w(String tag, String msg)
	{
		if (LOG_LEVEL > WARN)
			Log.w(TAG + tag, msg);

	}
	
	/**
	 * 
	 * @param tag 标签，当为""时，默认为"dw"
	 * @param msg 要输出的提示信息
	 */

	public static void i(String tag, String msg)
	{
		if (LOG_LEVEL > INFO)
			Log.i(TAG + tag, msg);
	}

	/**
	 * 
	 * @param tag 标签，当为""时，默认为"dw"
	 * @param msg 要输出的提示信息
	 */
	public static void d(String tag, String msg)
	{
		if (LOG_LEVEL > DEBUG)
			Log.d(TAG + tag, msg);
	}

	/**
	 * 
	 * @param tag 标签，当为""时，默认为"dw"
	 * @param msg 要输出的提示信息
	 */
	public static void v(String tag, String msg)
	{
		if (LOG_LEVEL > VERBOS)
			Log.v(TAG + tag, msg);
	}

}
