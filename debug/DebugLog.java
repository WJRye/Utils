

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

	public static void v(String tag, String msg) {

		if (isDebug())
			Log.v(tag, msg);

	}

	public static void v(String tag, String msg, Throwable tr) {

		if (isDebug())
			Log.v(tag, msg, tr);

	}

	public static void d(String tag, String msg) {

		if (isDebug())
			Log.d(tag, msg);

	}

	public static void d(String tag, String msg, Throwable tr) {

		if (isDebug())
			Log.d(tag, msg, tr);

	}

	public static void i(String tag, String msg) {

		if (isDebug())
			Log.i(tag, msg);

	}

	public static void i(String tag, String msg, Throwable tr) {

		if (isDebug())
			Log.i(tag, msg, tr);

	}

	public static void w(String tag, String msg) {

		if (isDebug())
			Log.w(tag, msg);

	}

	public static void w(String tag, String msg, Throwable tr) {

		if (isDebug())
			Log.w(tag, msg, tr);

	}

	public static void e(String tag, String msg) {

		if (isDebug())
			Log.e(tag, msg);

	}

	public static void e(String tag, String msg, Throwable tr) {

		if (isDebug())
			Log.e(tag, msg, tr);

	}

	public static void wtf(String tag, String msg) {

		if (isDebug())
			Log.wtf(tag, msg);

	}

	public static void wtf(String tag, String msg, Throwable tr) {

		if (isDebug())
			Log.wtf(tag, msg, tr);

	}

	public static void println(int priority, String tag, String msg) {

		if (isDebug())
			Log.println(priority, tag, msg);

	}

	/**
	 * @return 是否开启Debug模式
	 */
	private static boolean isDebug() {
		return IS_PUBLIC_DEBUG && IS_PRIVATE_DEBUG;
	}

	public static void v(String msg) {

		if (isDebug())
			Log.v(TAG, msg);

	}

	public static void v(String msg, Throwable tr) {

		if (isDebug())
			Log.v(TAG, msg, tr);

	}

	public static void d( String msg) {

		if (isDebug())
			Log.d(TAG, msg);

	}

	public static void d(String msg, Throwable tr) {

		if (isDebug())
			Log.d(TAG, msg, tr);

	}

	public static void i(String msg) {

		if (isDebug())
			Log.i(TAG, msg);

	}

	public static void i(String msg, Throwable tr) {

		if (isDebug())
			Log.i(TAG, msg, tr);

	}

	public static void w(String msg) {

		if (isDebug())
			Log.w(TAG, msg);

	}

	public static void w(String msg, Throwable tr) {

		if (isDebug())
			Log.w(TAG, msg, tr);

	}

	public static void e( String msg) {

		if (isDebug())
			Log.e(TAG, msg);

	}

	public static void e(String msg, Throwable tr) {

		if (isDebug())
			Log.e(TAG, msg, tr);

	}

	public static void wtf( String msg) {

		if (isDebug())
			Log.wtf(TAG, msg);

	}

	public static void wtf( String msg, Throwable tr) {

		if (isDebug())
			Log.wtf(TAG, msg, tr);

	}

	public static void println(int priority, String msg) {

		if (isDebug())
			Log.println(priority, TAG, msg);

	}
}
