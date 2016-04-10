package com.wj.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

/**
 * @Title:
 * @Package
 * @Description: TODO(获取资源的帮助类，主要放在BaseActivity和BaseAdapter里面)
 * @author wangjiang wangjiang7747@gmail.com
 * @date 2016-4-10 下午8:39:32
 * @version V1.0
 */
public class ResourceHelper {

	private Context mContext;

	private ResourceHelper(Context context) {
		mContext = context;
	}

	private static class ResourceHelperHolder {
		private static ResourceHelper INSTANCE = null;

		private static ResourceHelper getInstance(Context context) {
			if (INSTANCE == null) {
				INSTANCE = new ResourceHelper(context);
			}
			return INSTANCE;
		}
	}

	public static ResourceHelper getInstance(Context context) {
		return ResourceHelperHolder
				.getInstance(context.getApplicationContext());
	}

	public String getString(int resId) {
		return mContext.getString(resId);
	}

	public String getString(int resId, Object... formatArgs) {
		return mContext.getString(resId, formatArgs);
	}

	public int getColor(int id) {
		return mContext.getResources().getColor(id);
	}

	public ColorStateList getColorStateList(int id) {
		return mContext.getResources().getColorStateList(id);
	}

	public int getDimensionPixelSize(int id) {
		return mContext.getResources().getDimensionPixelSize(id);
	}

	public int getId(String name) {
		return mContext.getResources().getIdentifier(name, "id",
				mContext.getPackageName());
	}

	public int getIdentifier(String name, String defType) {
		return mContext.getResources().getIdentifier(name, defType,
				mContext.getPackageName());
	}

	public boolean getBoolean(int id) {
		return mContext.getResources().getBoolean(id);
	}

	public String[] getStringArray(int id) {
		return mContext.getResources().getStringArray(id);
	}

	public int[] getIntArray(int id) {
		return mContext.getResources().getIntArray(id);
	}

	public int getInteger(int id) {
		return mContext.getResources().getInteger(id);
	}

	public Drawable getDrawable(int id) {
		return mContext.getResources().getDrawable(id);
	}
}
