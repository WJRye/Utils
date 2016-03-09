package com.example.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

/**
 * @Title:
 * @Package
 * @Description: TODO(该类用于打开设置相关界面，告诉用户相关的设置操作。注：如果是在Fragment中打开，需要API 11以上)
 * @author wangjiang wangjiang7747@gmail.com
 * @date 2016-3-9 下午6:08:18
 * @version V1.0
 */
@SuppressLint("NewApi")
public class SettingsUtil {

	private SettingsUtil() {
	}

	private static void openSettings(Object object, String action,
			int requestCode) {
		Intent intent = new Intent(action);
		if (object instanceof Activity) {
			((Activity) object).startActivityForResult(intent, requestCode);
		} else if (object instanceof Fragment) {
			((Fragment) object).startActivityForResult(intent, requestCode);
		}
	}

	/**
	 * 打开系统辅助设置界面,设置可防性，如果context是Fragment,需要API 11以上
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openAccessibilitySettings(Object context, int requestCode) {
		openSettings(context,
				android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS,
				requestCode);
	}

	/**
	 * 打开账户和同步设置界面，主要用于添加账户
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openAddAccount(Object context, int requestCode) {
		openSettings(context, android.provider.Settings.ACTION_ADD_ACCOUNT,
				requestCode);
	}

	/**
	 * 打开飞行模式设置界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openAirplaneModeSettings(Object context, int requestCode) {
		openSettings(context,
				android.provider.Settings.ACTION_AIRPLANE_MODE_SETTINGS,
				requestCode);
	}

	/**
	 * 打开APN设置界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openApnSettings(Object context, int requestCode) {
		openSettings(context, android.provider.Settings.ACTION_APN_SETTINGS,
				requestCode);
	}

	/**
	 * 打开应用程序设置界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openApplicationSettings(Object context, int requestCode) {
		openSettings(context,
				android.provider.Settings.ACTION_APPLICATION_SETTINGS,
				requestCode);
	}

	/**
	 * 打开蓝牙设置界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openBluetoothSettings(Object context, int requestCode) {
		openSettings(context,
				android.provider.Settings.ACTION_BLUETOOTH_SETTINGS,
				requestCode);
	}

	/**
	 * 打开日期设置界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openDateSettings(Object context, int requestCode) {
		openSettings(context, android.provider.Settings.ACTION_DATE_SETTINGS,
				requestCode);
	}

	/**
	 * 打开查看手机信息界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openDeviceInfoSettings(Object context, int requestCode) {
		openSettings(context,
				android.provider.Settings.ACTION_DEVICE_INFO_SETTINGS,
				requestCode);
	}

	/**
	 * 打开手机显示设置界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openDisplaySettings(Object context, int requestCode) {
		openSettings(context,
				android.provider.Settings.ACTION_DISPLAY_SETTINGS, requestCode);
	}

	/**
	 * 打开输入法设置界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openInputMethodSettings(Object context, int requestCode) {
		openSettings(context,
				android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS,
				requestCode);
	}

	/**
	 * 打开SD卡和手机内存设置界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openInternalStorageSettings(Object context,
			int requestCode) {
		openSettings(context,
				android.provider.Settings.ACTION_INTERNAL_STORAGE_SETTINGS,
				requestCode);
	}

	/**
	 * 打开语言和键盘设置界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openLocaleSettings(Object context, int requestCode) {
		openSettings(context, android.provider.Settings.ACTION_LOCALE_SETTINGS,
				requestCode);
	}

	/**
	 * 打开位置和安全设置界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openLocationSourcSettings(Object context, int requestCode) {
		openSettings(context,
				android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS,
				requestCode);
	}

	/**
	 * 打开应用程序管理设置界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openManageApplicationsSettings(Object context,
			int requestCode) {
		openSettings(context,
				android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS,
				requestCode);
	}

	/**
	 * 打开SD卡和手机内存设置界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openMemoryCardSettings(Object context, int requestCode) {
		openSettings(context,
				android.provider.Settings.ACTION_MEMORY_CARD_SETTINGS,
				requestCode);
	}

	/**
	 * 打开隐私设置界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openPrivacySettings(Object context, int requestCode) {
		openSettings(context,
				android.provider.Settings.ACTION_PRIVACY_SETTINGS, requestCode);
	}

	/**
	 * 打开搜索设置界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openSearchSettings(Object context, int requestCode) {
		openSettings(context, android.provider.Settings.ACTION_SEARCH_SETTINGS,
				requestCode);
	}

	/**
	 * 打开安全设置界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openSecuritySettings(Object context, int requestCode) {
		openSettings(context,
				android.provider.Settings.ACTION_SECURITY_SETTINGS, requestCode);
	}

	/**
	 * 打开设置界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openSettings(Object context, int requestCode) {
		openSettings(context, android.provider.Settings.ACTION_SETTINGS,
				requestCode);
	}

	/**
	 * 打开声音界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openSoundSettings(Object context, int requestCode) {
		openSettings(context, android.provider.Settings.ACTION_SOUND_SETTINGS,
				requestCode);
	}

	/**
	 * 打开账户与同步界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openSyncSettings(Object context, int requestCode) {
		openSettings(context, android.provider.Settings.ACTION_SYNC_SETTINGS,
				requestCode);
	}

	/**
	 * 打开用户字典设置界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openUserDictionarySettings(Object context,
			int requestCode) {
		openSettings(context,
				android.provider.Settings.ACTION_USER_DICTIONARY_SETTINGS,
				requestCode);
	}

	/**
	 * 打开无线和网络(Wifi)设置界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openWifiSettings(Object context, int requestCode) {
		openSettings(context, android.provider.Settings.ACTION_WIFI_SETTINGS,
				requestCode);
	}

	/**
	 * 打开无线和网络设置界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openWirelessSettings(Object context, int requestCode) {
		openSettings(context,
				android.provider.Settings.ACTION_WIRELESS_SETTINGS, requestCode);
	}

	/**
	 * 打开无线和网络(Wlan)设置界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openWifiIpSettings(Object context, int requestCode) {
		openSettings(context,
				android.provider.Settings.ACTION_WIFI_IP_SETTINGS, requestCode);
	}

	/**
	 * 打开显示设置界面，以允许快速启动快捷键的配置
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openQuickLaunchSettings(Object context, int requestCode) {
		openSettings(context,
				android.provider.Settings.ACTION_QUICK_LAUNCH_SETTINGS,
				requestCode);
	}

	/**
	 * 打开选择网络运营商的显示设置界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openNetworkOperatorSettings(Object context,
			int requestCode) {
		openSettings(context,
				android.provider.Settings.ACTION_NETWORK_OPERATOR_SETTINGS,
				requestCode);
	}

	/**
	 * 打开显示设置界面,以允许应用程序开发相关的设置配置
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openApplicationDevelopmentSettings(Object context,
			int requestCode) {
		openSettings(
				context,
				android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS,
				requestCode);
	}

	/**
	 * 打开选择2G/3G设置界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param requestCode
	 *            请求码
	 */
	public static void openDataRoamingSettings(Object context, int requestCode) {
		openSettings(context,
				android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS,
				requestCode);
	}

	/**
	 * 打开应用程序详细信息界面，用于告诉用户需要授予应用程序权限，如果是Object是Fragment，需要API 11以上
	 * 
	 * @param fragment
	 * @param packageName
	 *            应用程序包名
	 * @param requesetCode
	 *            请求码
	 */
	@SuppressLint("NewApi")
	public static void openApplicationDetailsSettings(Object context,
			String packageName, int requestCode) {

		final String SCHEME = "package";
		// 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.1及之前版本)
		final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";
		// 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.2)
		final String APP_PKG_NAME_22 = "pkg";
		// InstalledAppDetails所在包名
		final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";
		// InstalledAppDetails类名
		final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";

		Intent intent = new Intent();
		final int apiLevel = Build.VERSION.SDK_INT;
		if (apiLevel >= 9) {
			// 2.3（ApiLevel 9）以上，使用SDK提供的接口
			// intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
			intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
			Uri uri = Uri.fromParts(SCHEME, packageName, null);
			intent.setData(uri);
		} else {
			// 2.3以下，使用非公开的接口（查看InstalledAppDetails源码）
			// 2.2和2.1中，InstalledAppDetails使用的APP_PKG_NAME不同。
			final String appPkgName = (apiLevel == 8 ? APP_PKG_NAME_22
					: APP_PKG_NAME_21);
			intent.setAction(Intent.ACTION_VIEW);
			intent.setClassName(APP_DETAILS_PACKAGE_NAME,
					APP_DETAILS_CLASS_NAME);
			intent.putExtra(appPkgName, packageName);
		}

		if (context instanceof Activity) {
			((Activity) context).startActivityForResult(intent, requestCode);
		} else if (context instanceof Fragment) {
			((Fragment) context).startActivityForResult(intent, requestCode);
		}

	}

}
