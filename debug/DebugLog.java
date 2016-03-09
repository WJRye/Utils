package com.example.utils.usercase.debug;

import android.util.Log;

/**
 * @author wangjiang
 * 
 *         包装Log类，以便控制整个应用程序的Log模式
 */
public final class DebugLog extends Debug {

	/**
	 * 是否开启Debug模式
	 */
	public static boolean IS_PRIVATE_DEBUG = true;

	private DebugLog() {

	}

	public static void v(String tag, Object msg) {

		if (isDebug())
			Log.v(tag, msg.toString());

	}

	public static void v(String tag, Object msg, Throwable tr) {

		if (isDebug())
			Log.v(tag, msg.toString(), tr);

	}

	public static void d(String tag, Object msg) {

		if (isDebug())
			Log.d(tag, msg.toString());

	}

	public static void d(String tag, Object msg, Throwable tr) {

		if (isDebug())
			Log.d(tag, msg.toString(), tr);

	}

	public static void i(String tag, Object msg) {

		if (isDebug())
			Log.i(tag, msg.toString());

	}

	public static void i(String tag, Object msg, Throwable tr) {

		if (isDebug())
			Log.i(tag, msg.toString(), tr);

	}

	public static void w(String tag, Object msg) {

		if (isDebug())
			Log.w(tag, msg.toString());

	}

	public static void w(String tag, Object msg, Throwable tr) {

		if (isDebug())
			Log.w(tag, msg.toString(), tr);

	}

	public static void e(String tag, Object msg) {

		if (isDebug())
			Log.e(tag, msg.toString());

	}

	public static void e(String tag, Object msg, Throwable tr) {

		if (isDebug())
			Log.e(tag, msg.toString(), tr);

	}

	public static void wtf(String tag, Object msg) {

		if (isDebug())
			Log.wtf(tag, msg.toString());

	}

	public static void wtf(String tag, Object msg, Throwable tr) {

		if (isDebug())
			Log.wtf(tag, msg.toString(), tr);

	}

	public static void println(int priority, String tag, Object msg) {

		if (isDebug())
			Log.println(priority, tag, msg.toString());

	}

	/**
	 * @return 是否开启Debug模式
	 */
	private static boolean isDebug() {
		return IS_PUBLIC_DEBUG && IS_PRIVATE_DEBUG;
	}

	public static void v(Object msg) {

		if (isDebug())
			Log.v(TAG, msg.toString());

	}

	public static void v(Object msg, Throwable tr) {

		if (isDebug())
			Log.v(TAG, msg.toString(), tr);

	}

	public static void d(Object msg) {

		if (isDebug())
			Log.d(TAG, msg.toString());

	}

	public static void d(Object msg, Throwable tr) {

		if (isDebug())
			Log.d(TAG, msg.toString(), tr);

	}

	public static void i(Object msg) {

		if (isDebug())
			Log.i(TAG, msg.toString());

	}

	public static void i(Object msg, Throwable tr) {

		if (isDebug())
			Log.i(TAG, msg.toString(), tr);

	}

	public static void w(Object msg) {

		if (isDebug())
			Log.w(TAG, msg.toString());

	}

	public static void w(Object msg, Throwable tr) {

		if (isDebug())
			Log.w(TAG, msg.toString(), tr);

	}

	public static void e(Object msg) {

		if (isDebug())
			Log.e(TAG, msg.toString());

	}

	public static void e(Object msg, Throwable tr) {

		if (isDebug())
			Log.e(TAG, msg.toString(), tr);

	}

	public static void wtf(Object msg) {

		if (isDebug())
			Log.wtf(TAG, msg.toString());

	}

	public static void wtf(Object msg, Throwable tr) {

		if (isDebug())
			Log.wtf(TAG, msg.toString(), tr);

	}

	public static void println(int priority, Object msg) {

		if (isDebug())
			Log.println(priority, TAG, msg.toString());

	}
}
