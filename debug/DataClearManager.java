package com.wj.utils.debug;

import java.io.File;

import android.content.Context;

/**
 * 清除缓存类
 * 
 * @author hongfeijia（我的同事）and wangjiang
 * 
 */
public class DataClearManager {

	/**
	 * 内部存储文件的根路径
	 */
	private static final String DATA_PATH = "/data/data/";
	/**
	 * 内部存储文件数据库的路径
	 */
	private static final String DATABASES = "/databases";

	/**
	 * 内部存储偏好参数文件的路径
	 */
	private static final String SHARED_PREFS = "/shared_prefs";

	/**
	 * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
	 * 
	 * @param context
	 *            上下文对象
	 */
	public static void clearInternalCache(Context context) {
		deleteFilesByDirectory(context.getCacheDir());
	}

	/**
	 * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
	 * 
	 * @param context
	 *            上下文对象
	 */
	public static void clearDatabases(Context context) {
		deleteFilesByDirectory(new File(DATA_PATH + context.getPackageName()
				+ DATABASES));
	}

	/**
	 * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
	 * 
	 * @param context
	 *            上下文对象
	 */
	public static void clearSharedPreference(Context context) {
		deleteFilesByDirectory(new File(DATA_PATH + context.getPackageName()
				+ SHARED_PREFS));
	}

	/**
	 * 按名字清除本应用数据库
	 * 
	 * @param context
	 *            上下文对象
	 * @param dbName
	 *            数据库的名字
	 */
	public static void deleteDatabase(Context context, String dbName) {
		context.deleteDatabase(dbName);
	}

	/**
	 * 清除/data/data/com.xxx.xxx/files下的内容
	 * 
	 * @param context
	 *            上下文对象
	 */
	public static void clearFiles(Context context) {
		deleteFilesByDirectory(context.getFilesDir());
	}

	/**
	 * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
	 * 
	 * @param filePath
	 *            文件路径
	 */
	public static void clearCustomFile(String filePath) {
		deleteFilesByDirectory(new File(filePath));
	}

	/**
	 * 删除SharedPreference下除了指定文件的所有文件，如果传入的directory是个文件，将不做处理
	 * 
	 * @param context
	 *            上下文对象
	 * @param specifiedFile
	 *            指定的文件
	 */
	public static void clearSharedPreference(Context context,
			File... specifiedFiles) {
		deleteSpecifiedFiles(new File(DATA_PATH + context.getPackageName()
				+ SHARED_PREFS), specifiedFiles);
	}

	/**
	 * 清除本应用所有的数据
	 * 
	 * @param context
	 *            上下文对象
	 * @param filepath
	 *            文件路径
	 */
	public static void clearApplicationData(Context context, String... filepath) {
		clearInternalCache(context);
		// cleanExternalCache(context);
		clearDatabases(context);
		clearSharedPreference(context);
		clearFiles(context);
		for (String filePath : filepath) {
			clearCustomFile(filePath);
		}

	}

	/**
	 * 删除指定文件下的文件，如果传入的directory是个文件，将不做处理
	 * 
	 * @param directory
	 *            指定的文件夹
	 * @param specifiedFile
	 *            一定要是文件要是目录无效
	 * 
	 */
	public static void deleteSpecifiedFiles(File directory,
			File... specifiedFiles) {

		if (directory != null && directory.exists() && directory.isDirectory()) {

			File[] files = directory.listFiles();
			if (files != null) {

				for (File tempFile : files) {

					if (tempFile.isDirectory()) {
						deleteSpecifiedFiles(tempFile, specifiedFiles);
					} else {
						boolean hasFile = false;
						for (File specifiedFile : specifiedFiles) {
							if (specifiedFile.isFile()) {
								if (tempFile.getAbsolutePath().equals(
										specifiedFile.getAbsolutePath())) {
									hasFile = true;
									break;
								}
							}
						}
						if (hasFile) {
							tempFile.delete();
						}
					}
				}
			}
		}

	}

	/**
	 * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
	 * 
	 * @param directory
	 *            指定的文件夹
	 */
	private static void deleteFilesByDirectory(File directory) {
		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File item : directory.listFiles()) {
				if (item.isDirectory()) {
					deleteFilesByDirectory(new File(item.getPath()));
				} else {
					item.delete();
				}
			}
		}
	}

}