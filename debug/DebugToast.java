package com.wj.utils.debug;

import android.content.Context;
import android.widget.Toast;

/**
 * @author wangjiang
 * 
 * 
 *         包装Toast类，以便控制整个应用程序的Toast模式
 */
public final class DebugToast extends Debug {

	/**
	 * 是否开启Debug模式
	 */
	public static boolean IS_PRIVATE_DEBUG = true;

	public static void showShort(Context context, String text) {
		if (isDebug())
			Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static void showShort(Context context, int resId) {
		showShort(context, context.getString(resId));
	}

	public static void showLong(Context context, String text) {
		if (isDebug())
			Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}

	public static void showLong(Context context, int resId) {
		showLong(context, context.getString(resId));
	}

	/**
	 * @return 是否开启Debug模式
	 */
	private static boolean isDebug() {
		return IS_PUBLIC_DEBUG && IS_PRIVATE_DEBUG;
	}

}
