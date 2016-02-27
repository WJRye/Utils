
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;

/**
 * @author wangjiang
 * 
 *         界面显示的工具类，获得手机状态栏的高度，导航栏的高度，ActionBar的高度等等。注：有些来自于网络， 谢谢他们提供的帮助。
 */
public final class DisplayUtil {

	private DisplayUtil() {
	}

	/**
	 * 
	 * 获得StatusBar的高度
	 * 
	 * @param context
	 *            上下文对象
	 * @return 状态栏的高度
	 */
	public static int getStatusBarHeight(Context context) {
		Resources resources = context.getResources();
		int resourceId = resources.getIdentifier("status_bar_height", "dimen",
				"android");
		int statusBarHeight = resources.getDimensionPixelSize(resourceId);
		return statusBarHeight;
	}

	/**
	 * 
	 * 获得NavigationBar的高度
	 * 
	 * @param context
	 *            上下文对象
	 * @return 底部导航栏的高度，有些设备有底部导航栏，有些设备没有底部导航栏
	 */
	public static int getNavigationBarHeight(Context context) {
		Resources resources = context.getResources();
		int resourceId = resources.getIdentifier("navigation_bar_height",
				"dimen", "android");
		int navigationBarHeight = resources.getDimensionPixelSize(resourceId);
		return navigationBarHeight;
	}

	/**
	 * 
	 * 获得ActionBar的高度
	 * 
	 * @param activity
	 *            当前的Activity对象
	 * @return ActionBar的高度
	 */
	public static int getActionBarHeight(Activity activity) {
		TypedValue tv = new TypedValue();
		int actionBarHeight = 0;
		if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize,
				tv, true)) {// 如果资源是存在的、有效的
			actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
					activity.getResources().getDisplayMetrics());
		}
		return actionBarHeight;
	}

	/**
	 * 将px转换为dp
	 * 
	 * @param context
	 *            上下文对象
	 * @param pxValue
	 *            像素值
	 * @return 转换后的dp值
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将dp转换为px
	 * 
	 * @param context
	 *            上下文对象
	 * @param dpValue
	 *            dp值
	 * @return 转换后的px值
	 */
	public static int dp2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * @param context
	 *            上下文对象
	 * @return 屏幕宽度
	 */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * @param context
	 *            上下文对象
	 * @return 屏幕高度
	 */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * @param context
	 *            上下文对象
	 * @return 获得系统默认的选择条目背景，有边界，需要API 11以上，另外在API 21以上时是有边界的波纹
	 */
	@SuppressLint("InlinedApi")
	public static Drawable getSelectableItemBackground(Context context) {
		TypedValue typedValue = new TypedValue();
		context.getTheme().resolveAttribute(
				android.R.attr.selectableItemBackground, typedValue, true);
		return context.getResources().getDrawable(typedValue.resourceId);
	}

	/**
	 * @param context
	 *            上下文对象
	 * @return 获得系统默认的选择条目背景，无边界波纹，需要API 21以上
	 */
	@SuppressLint("InlinedApi")
	public static Drawable getSelectableItemBackgroundBorderlessResId(
			Context context) {
		TypedValue typedValue = new TypedValue();
		context.getTheme().resolveAttribute(
				android.R.attr.selectableItemBackgroundBorderless, typedValue,
				true);
		return context.getResources().getDrawable(typedValue.resourceId);
	}

	/**
	 * 获取当前屏幕截图，包含状态栏，方法来自博客：http://blog.csdn.net/lmj623565791/article/details/
	 * 38965311，做了小许修改。注意：在Activity没有绘制完时，不能够调用该方法（不能放在onCreate()等方法中）。
	 * 
	 * @param activity
	 *            当前Activity
	 * @return 屏幕截图bitmap
	 * @throws OutOfMemoryError
	 *             内存溢出
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity)
			throws OutOfMemoryError {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		if (bmp == null)
			throw new IllegalAccessError("The cache is disabled!");
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 获取当前屏幕截图，不包含状态栏，方法来自博客：http://blog.csdn.net/lmj623565791/article/details/
	 * 38965311，做了小许修改。注意：在Activity没有绘制完时，不能够调用该方法（不能放在onCreate()等方法中）。
	 * 
	 * 
	 * @param activity
	 *            当前Activity
	 * @return 屏幕截图bitmap
	 * @throws OutOfMemoryError
	 *             内存溢出
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity)
			throws OutOfMemoryError {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		if (bmp == null)
			throw new IllegalAccessError("The cache is disabled!");
		int statusBarHeight = getStatusBarHeight(activity);
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return bp;

	}
}
