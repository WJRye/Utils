package com.wj.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * @Title:
 * @Package
 * @Description: TODO(判断网络状态的相关类，需要权限"android.permission.ACCESS_NETWORK_STATE")
 * @author wangjiang wangjiang7747@gmail.com
 * @date 2016-3-16 下午5:10:39
 * @version V1.0
 */
public final class NetworkUtil {

	private NetworkUtil() {
	}

	/**
	 * 判断网络是否连接
	 * 
	 * @param context
	 *            上下文对象
	 * @return 是否连接到网络
	 */
	public static boolean isNetworkAvailable(Context context) {
		if (context != null) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager
					.getActiveNetworkInfo();
			if (networkInfo != null) {
				return networkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 判断是否连接到wifi
	 * 
	 * @param context
	 *            上下文对象
	 * @return 是否连接到wifi
	 */
	public static boolean isWifiAvailable(Context context) {
		return isAvailable(context, ConnectivityManager.TYPE_WIFI);
	}

	/**
	 * 判断是否连接到移动网络
	 * 
	 * @param context
	 *            上下文对象
	 * @return 是否连接到移动网络
	 */
	public static boolean isMobileAvailable(Context context) {
		return isAvailable(context, ConnectivityManager.TYPE_MOBILE);
	}

	private static boolean isAvailable(Context context, int type) {
		if (context != null) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager.getNetworkInfo(type);
			if (networkInfo != null) {
				return networkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 获得当前连接到的网络类型
	 * 
	 * @param context
	 *            上下文对象
	 * @return 网络类型，-1为不知网络类型
	 */
	public static int getConnectedType(Context context) {
		if (context != null) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager
					.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isAvailable()) {
				return networkInfo.getType();
			}
		}
		return -1;
	}

	private static final int NETWORK_TYPE_UNAVAILABLE = -1;
	// private static final int NETWORK_TYPE_MOBILE = -100;
	private static final int NETWORK_TYPE_WIFI = -101;

	private static final int NETWORK_CLASS_WIFI = -101;
	private static final int NETWORK_CLASS_UNAVAILABLE = -1;
	/** Unknown network class. */
	private static final int NETWORK_CLASS_UNKNOWN = 0;
	/** Class of broadly defined "2G" networks. */
	private static final int NETWORK_CLASS_2_G = 1;
	/** Class of broadly defined "3G" networks. */
	private static final int NETWORK_CLASS_3_G = 2;
	/** Class of broadly defined "4G" networks. */
	private static final int NETWORK_CLASS_4_G = 3;

	// 适配低版本手机
	/** Network type is unknown */
	public static final int NETWORK_TYPE_UNKNOWN = 0;
	/** Current network is GPRS */
	public static final int NETWORK_TYPE_GPRS = 1;
	/** Current network is EDGE */
	public static final int NETWORK_TYPE_EDGE = 2;
	/** Current network is UMTS */
	public static final int NETWORK_TYPE_UMTS = 3;
	/** Current network is CDMA: Either IS95A or IS95B */
	public static final int NETWORK_TYPE_CDMA = 4;
	/** Current network is EVDO revision 0 */
	public static final int NETWORK_TYPE_EVDO_0 = 5;
	/** Current network is EVDO revision A */
	public static final int NETWORK_TYPE_EVDO_A = 6;
	/** Current network is 1xRTT */
	public static final int NETWORK_TYPE_1xRTT = 7;
	/** Current network is HSDPA */
	public static final int NETWORK_TYPE_HSDPA = 8;
	/** Current network is HSUPA */
	public static final int NETWORK_TYPE_HSUPA = 9;
	/** Current network is HSPA */
	public static final int NETWORK_TYPE_HSPA = 10;
	/** Current network is iDen */
	public static final int NETWORK_TYPE_IDEN = 11;
	/** Current network is EVDO revision B */
	public static final int NETWORK_TYPE_EVDO_B = 12;
	/** Current network is LTE */
	public static final int NETWORK_TYPE_LTE = 13;
	/** Current network is eHRPD */
	public static final int NETWORK_TYPE_EHRPD = 14;
	/** Current network is HSPA+ */
	public static final int NETWORK_TYPE_HSPAP = 15;

	public static final String TYPE_UNKOWN = "未知";
	public static final String TYPE_NONE = "无";
	public static final String TYPE_WIFI = "Wi-Fi";
	public static final String TYPE_2G = "2G";
	public static final String TYPE_3G = "3G";
	public static final String TYPE_4G = "4G";

	/**
	 * 获取手机连接的网络类型2G/3G/4G/WIFI
	 * 
	 * @see http://blog.csdn.net/hknock/article/details/37650917
	 * 
	 * @return 2G/3G/4G/WIFI
	 */
	public static String getCurrentNetworkType(Context context) {
		int networkClass = getNetworkClass(context);
		String type = null;
		switch (networkClass) {
		case NETWORK_CLASS_UNAVAILABLE:
			type = TYPE_NONE;
			break;
		case NETWORK_CLASS_WIFI:
			type = TYPE_WIFI;
			break;
		case NETWORK_CLASS_2_G:
			type = TYPE_2G;
			break;
		case NETWORK_CLASS_3_G:
			type = TYPE_3G;
			break;
		case NETWORK_CLASS_4_G:
			type = TYPE_4G;
			break;
		case NETWORK_CLASS_UNKNOWN:
			type = TYPE_UNKOWN;
			break;
		default:
			type = TYPE_UNKOWN;
			break;
		}
		return type;
	}

	private static int getNetworkClassByType(int networkType) {
		int networkClassType = NETWORK_CLASS_UNKNOWN;
		switch (networkType) {
		case NETWORK_TYPE_UNAVAILABLE:
			networkClassType = NETWORK_CLASS_UNAVAILABLE;
			break;
		case NETWORK_TYPE_WIFI:
			networkClassType = NETWORK_CLASS_WIFI;
			break;
		case NETWORK_TYPE_GPRS:
		case NETWORK_TYPE_EDGE:
		case NETWORK_TYPE_CDMA:
		case NETWORK_TYPE_1xRTT:
		case NETWORK_TYPE_IDEN:
			networkClassType = NETWORK_CLASS_2_G;
			break;
		case NETWORK_TYPE_UMTS:
		case NETWORK_TYPE_EVDO_0:
		case NETWORK_TYPE_EVDO_A:
		case NETWORK_TYPE_HSDPA:
		case NETWORK_TYPE_HSUPA:
		case NETWORK_TYPE_HSPA:
		case NETWORK_TYPE_EVDO_B:
		case NETWORK_TYPE_EHRPD:
		case NETWORK_TYPE_HSPAP:
			networkClassType = NETWORK_CLASS_3_G;
			break;
		case NETWORK_TYPE_LTE:
			networkClassType = NETWORK_CLASS_4_G;
			break;
		default:
			networkClassType = NETWORK_CLASS_UNKNOWN;
			break;
		}
		return networkClassType;
	}

	private static int getNetworkClass(Context context) {
		int networkType = NETWORK_TYPE_UNKNOWN;
		try {
			final NetworkInfo network = ((ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE))
					.getActiveNetworkInfo();
			if (network != null && network.isAvailable()
					&& network.isConnected()) {
				int type = network.getType();
				if (type == ConnectivityManager.TYPE_WIFI) {
					networkType = NETWORK_TYPE_WIFI;
				} else if (type == ConnectivityManager.TYPE_MOBILE) {
					TelephonyManager telephonyManager = (TelephonyManager) context
							.getSystemService(Context.TELEPHONY_SERVICE);
					networkType = telephonyManager.getNetworkType();
				}
			} else {
				networkType = NETWORK_TYPE_UNAVAILABLE;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return getNetworkClassByType(networkType);
	}
}
