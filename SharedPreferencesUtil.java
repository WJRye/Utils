package com.example.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @Title:
 * @Package
 * @Description: 
 *               TODO(包装SharedPreferences类，用于保存配置参数，该类的方法只是简化对SharedPreferences的操作
 *               )
 * @author wangjiang wangjiang7747@gmail.com
 * @date 2016-3-5 下午6:24:07
 * @version V1.0
 */
/**
 * @Title:
 * @Package
 * @Description: TODO(用一句话描述该文件做什么)
 * @author wangjiang wangjiang7747@gmail.com
 * @date 2016-3-5 下午7:00:28
 * @version V1.0
 */
public class SharedPreferencesUtil {

	private SharedPreferences mSharedPreferences;
	private SharedPreferences.Editor mEditor;

	private SharedPreferencesUtil(Context context, String name, int mode) {
		mSharedPreferences = context.getSharedPreferences(name, mode);
		mEditor = mSharedPreferences.edit();
	}

	/**
	 * 默认的文件名为该类的类名，存储模式为Activity.MODE_PRIVATE，参看getInstance(context, name,
	 * mode)方法
	 * 
	 * @param context
	 *            上下文对象
	 * @return SharedPreferencesUtil对象
	 */
	public static SharedPreferencesUtil getInstance(Context context) {
		return getInstance(context,
				SharedPreferencesUtil.class.getSimpleName(),
				Activity.MODE_PRIVATE);
	}

	/**
	 * @param context
	 *            上下文对象
	 * @param name
	 *            文件名字
	 * @param mode
	 *            存储模式( Activity.MODE_PRIVATE,Activity.MODE_APPEND,Activity.
	 *            MODE_WORLD_READABLE,Activity.MODE_WORLD_WRITEABLE)
	 * @return SharedPreferencesUtil对象
	 */
	public static SharedPreferencesUtil getInstance(Context context,
			String name, int mode) {
		return new SharedPreferencesUtil(context, name, mode);
	}

	/**
	 * 
	 * 注意：如果要存储集合类型，value只能是集合类型Set<String>
	 * 
	 */
	@SuppressWarnings("unchecked")
	@SuppressLint({ "CommitPrefEdits", "NewApi" })
	public void put(String key, Object value) {
		if (value instanceof Boolean) {
			mEditor.putBoolean(key, (Boolean) value);
		} else if (value instanceof Integer) {
			mEditor.putInt(key, (Integer) value);
		} else if (value instanceof Long) {
			mEditor.putLong(key, (Long) value);
		} else if (value instanceof Float) {
			mEditor.putFloat(key, (Float) value);
		} else if (value instanceof String) {
			mEditor.putString(key, (String) value);
		} else if (value instanceof Set<?>) {
			mEditor.putStringSet(key, (Set<String>) value);
		}
	}

	/**
	 * 
	 * 注意：如果获得存储的Set<String>类型值，defValue也必须为Set<String>类型
	 * 
	 */
	@SuppressLint("NewApi")
	@SuppressWarnings("unchecked")
	public Object get(String key, Object defValue) {
		Object object = null;
		if (defValue instanceof Boolean) {
			object = mSharedPreferences.getBoolean(key, (Boolean) defValue);
		} else if (defValue instanceof Integer) {
			object = mSharedPreferences.getInt(key, (Integer) defValue);
		} else if (defValue instanceof Long) {
			object = mSharedPreferences.getLong(key, (Long) defValue);
		} else if (defValue instanceof Float) {
			object = mSharedPreferences.getFloat(key, (Float) defValue);
		} else if (defValue instanceof String) {
			object = mSharedPreferences.getString(key, (String) defValue);
		} else if (defValue instanceof Set<?>) {
			object = mSharedPreferences.getStringSet(key,
					(Set<String>) defValue);
		}
		return object;
	}

	/**
	 * 详见SharedPreferences.Editor.remove()方法
	 * 
	 * @param key
	 */
	public void remove(String key) {
		mEditor.remove(key);
		apply();
	}

	/**
	 * @param keys
	 *            The names of the preference to remove
	 */
	public void remove(Set<String> keys) {
		Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			remove(key);
		}
	}

	/**
	 * 详见SharedPreferences.Editor.clear()方法
	 */
	public void clear() {
		mEditor.clear();
		apply();
	}

	/**
	 * 详见SharedPreferences.Editor.apply方法
	 */
	@SuppressLint("NewApi")
	public void apply() {
		mEditor.apply();
	}

	/**
	 * 详见SharedPreferences.Editor.commit()方法
	 * 
	 * 不提倡使用commit()方法，建议使用apply()方法,因为commit()方法在主线程中执行，可能会阻塞主线程,而apply()
	 * 方法时异步执行的
	 */
	public void commit() {
		mEditor.commit();
	}

	/**
	 * 详见SharedPreferences.contains(key)方法
	 * 
	 * @param key
	 * @return
	 */
	public boolean contains(String key) {
		return mSharedPreferences.contains(key);
	}

	/**
	 * 详见SharedPreferences.getAll()方法
	 * 
	 * @return
	 */
	public Map<String, ?> getAll() {
		return mSharedPreferences.getAll();
	}
}
