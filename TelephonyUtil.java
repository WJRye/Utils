package com.wj.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * @Title:
 * @Package
 * @Description: TODO(手机信息获取工具类，需要权限"android.permission.READ_PHONE_STATE")
 * @author wangjiang wangjiang7747@gmail.com
 * @date 2016-3-16 下午6:58:16
 * @version V1.0
 */
public final class TelephonyUtil {

	private TelephonyUtil() {
	}

	/**
	 * 获取运营商，中国移动，中国联通，中国电信
	 * 
	 * @return
	 */
	public static String getProviderr(Context context) {
		final String PROVIDER_UNKOWN = "未知";
		final String PROVIDER_CMCC = "中国移动";
		final String PROVIDER_CUCC = "中国联通";
		final String PROVIDER_CTCC = "中国电信";

		String provider = PROVIDER_UNKOWN;

		try {
			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String IMSI = telephonyManager.getSubscriberId();
			if (IMSI == null) {
				if (TelephonyManager.SIM_STATE_READY == telephonyManager
						.getSimState()) {
					String operator = telephonyManager.getSimOperator();
					if (operator != null) {
						if (operator.equals("46000")
								|| operator.equals("46002")
								|| operator.equals("46007")) {
							provider = PROVIDER_CMCC;
						} else if (operator.equals("46001")) {
							provider = PROVIDER_CUCC;
						} else if (operator.equals("46003")) {
							provider = PROVIDER_CTCC;
						}
					}
				}
			} else {
				if (IMSI.startsWith("46000") || IMSI.startsWith("46002")
						|| IMSI.startsWith("46007")) {
					provider = PROVIDER_CMCC;
				} else if (IMSI.startsWith("46001")) {
					provider = PROVIDER_CUCC;
				} else if (IMSI.startsWith("46003")) {
					provider = PROVIDER_CTCC;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return provider;
	}

	/**
	 * 
	 * 判断sim卡是否可用
	 * 
	 * @param context
	 *            上下文对象
	 * @return sim卡是否可用
	 */
	public static boolean isSimCanUse(Context context) {
		TelephonyManager tm = ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE));

		return TelephonyManager.SIM_STATE_READY == tm.getSimState() ? true
				: false;
	}

}
