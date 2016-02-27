/**  
 * Filename:    DateFormatUtil.java  
 * Description:   
 * Copyright:   Copyright (c)2013  
 * Company:    Run 
 * @author:     Rye  
 * @version:    1.0  
 * Create at:   2015-9-19 下午3:30:39  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2015-9-19    Rye             1.0        1.0 Version  
 */


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;

/**
 * <br>
 * <b> Class: </b> DateFormatUtil<br>
 * <b> Description: </b> 聊天、通电话的时间格式化显示
 * 
 * @author wangjiang
 * @version 1.0 <b> Date: </b>2015-9-19
 */
@SuppressLint("SimpleDateFormat")
public class DateFormatUtil {
	private static final char DURATION_HOUR = '时';
	private static final char DURATION_MIN = '分';
	private static final char DURATION_SEC = '秒';
	private static final String PATTERN_YY_MM_DD = "yy/MM/dd";
	private static final String PATTERN_YY_MM_D = "yy/MM/d";
	private static final String PATTERN_YY_M_DD = "yy/M/dd";
	private static final String PATTERN_YY_M_D = "yy/M/d";
	private static final String PATTERN_MM_D = "MM月d日";
	private static final String PATTERN_MM_DD = "MM月dd日";
	private static final String PATTERN_M_DD = "M月dd日";
	private static final String PATTERN_M_D = "M月d日";
	private static final String PATTERN_HH_MM = "HH:mm";
	private static final String PATTERN_HH_M = "HH:m";
	private static final String PATTERN_H_MM = "H:mm";
	private static final String PATTERN_H_M = "HH:m";
	private static final String PATTERN_YESTERDAY = "昨天";
	private static final String PATTERN_MINUTE_BEFORE = "分钟前";
	private static final String PATTERN_JSUT_NOW = "刚刚";
	// 日期格式“年月日”
	public static final int PATTERN_TYPE_YMD = 1;
	// 日期格式“月日”
	public static final int PATTERN_TYPE_MD = 2;

	private DateFormatUtil() {
	}

	/**
	 * <br>
	 * <b> Description: </b> 将毫秒转换为年月日或者月日的日期格式
	 * 
	 * @param date
	 *            毫秒
	 * @param patternType
	 *            年月日或者月日
	 * @return 年月日或者月日的日期格式
	 */
	public static String dateToYMD(long date, int patternType) {
		Calendar calendar = Calendar.getInstance();
		Date d = new Date(date);
		calendar.setTime(d);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		SimpleDateFormat sdf = null;
		// 以“10天”为分割线
		int dayOfMonth = 10;
		// 以“10月”为分割线
		if (month >= Calendar.NOVEMBER) {
			if (day >= dayOfMonth) {
				if (patternType == PATTERN_TYPE_YMD) {
					sdf = new SimpleDateFormat(PATTERN_YY_MM_DD);
				} else if (patternType == PATTERN_TYPE_MD) {
					sdf = new SimpleDateFormat(PATTERN_MM_DD);
				}
			} else {
				if (patternType == PATTERN_TYPE_YMD) {
					sdf = new SimpleDateFormat(PATTERN_YY_MM_D);
				} else if (patternType == PATTERN_TYPE_MD) {
					sdf = new SimpleDateFormat(PATTERN_MM_D);
				}
			}
		} else {
			// 小于10月
			if (day >= dayOfMonth) {
				if (patternType == PATTERN_TYPE_YMD) {
					sdf = new SimpleDateFormat(PATTERN_YY_M_DD);
				} else if (patternType == PATTERN_TYPE_MD) {
					sdf = new SimpleDateFormat(PATTERN_M_DD);
				}
			} else {
				if (patternType == PATTERN_TYPE_YMD) {
					sdf = new SimpleDateFormat(PATTERN_YY_M_D);
				} else if (patternType == PATTERN_TYPE_MD) {
					sdf = new SimpleDateFormat(PATTERN_M_D);
				}
			}
		}
		return sdf.format(d);
	}

	/**
	 * <br>
	 * <b> Description: </b> 将毫秒转换为HH-mm格式的时间
	 * 
	 * @param date
	 *            毫秒
	 * @return HH-mm格式的时间
	 */
	public static String dateToHM(long date) {
		return new SimpleDateFormat(PATTERN_HH_MM).format(new Date(date));
	}

	/**
	 * <br>
	 * <b> Description: </b> 将毫秒转换为日期格式
	 * 
	 * @param srcDate
	 *            毫秒
	 * @return 日期格式
	 */
	public static String formatDate(long srcDate) {
		if (srcDate == 0L)
			return null;
		// 需要格式化的时间
		Calendar srcCalendar = Calendar.getInstance();
		Date srcD = new Date(srcDate);
		srcCalendar.setTime(srcD);
		// 当前时间
		Calendar desCalendar = Calendar.getInstance();
		Date desD = new Date(System.currentTimeMillis());
		desCalendar.setTime(desD);
		// 结果的时间格式
		String result = null;
		int year = (desCalendar.get(Calendar.YEAR) - srcCalendar
				.get(Calendar.YEAR));
		// 两个时间的差以“1年”为分割线
		if (year < 1) {
			int day = desCalendar.get(Calendar.DAY_OF_YEAR)
					- srcCalendar.get(Calendar.DAY_OF_YEAR);
			// 两个时间的差以“1天”为分割线
			if (day < 1) {
				int hour = desCalendar.get(Calendar.HOUR_OF_DAY)
						- srcCalendar.get(Calendar.HOUR_OF_DAY);
				// 两个时间的差以“1小时”为分割线
				if (hour < 1) {
					// 两个时间相差多少秒
					int second = (desCalendar.get(Calendar.MINUTE) * 60 + desCalendar
							.get(Calendar.SECOND))
							- (srcCalendar.get(Calendar.MINUTE) * 60 + srcCalendar
									.get(Calendar.SECOND));
					if (second >= 60) {
						// 多少分钟以前
						result = second / 60 + PATTERN_MINUTE_BEFORE;
					} else {
						// 刚刚
						result = PATTERN_JSUT_NOW;
					}
				} else if (hour == 1) {
					int second = (desCalendar.get(Calendar.MINUTE) * 60
							+ desCalendar.get(Calendar.SECOND) + 3600)
							- (srcCalendar.get(Calendar.MINUTE) * 60 + srcCalendar
									.get(Calendar.SECOND));
					if (second >= 3600) {
						// 大于一个小时
						result = dateToHM(srcDate);
					} else if (second >= 60 && second < 3600) {
						// 一个小时以内，多少分钟以前
						result = second / 60 + PATTERN_MINUTE_BEFORE;
					} else {
						// 刚刚
						result = PATTERN_JSUT_NOW;
					}
				} else if (hour > 1) {
					result = dateToHM(srcDate);
				}
			} else if (day == 1) {
				result = PATTERN_YESTERDAY;
			} else if (day > 1) {
				result = dateToYMD(srcDate, PATTERN_TYPE_MD);
			}
		} else if (year == 1) {
			// 两个时间相差一年但相差一天
			int desMonth = desCalendar.get(Calendar.MONTH);
			int srcMonth = srcCalendar.get(Calendar.MONTH);
			if (desMonth == Calendar.JANUARY && srcMonth == Calendar.DECEMBER) {
				if (1 == desCalendar.get(Calendar.DAY_OF_MONTH)
						&& 31 == srcCalendar.get(Calendar.DAY_OF_MONTH)) {
					result = PATTERN_YESTERDAY;
				} else {
					result = dateToYMD(srcDate, PATTERN_TYPE_YMD);
				}
			} else {
				result = dateToYMD(srcDate, PATTERN_TYPE_YMD);
			}
		} else if (year > 1) {
			result = dateToYMD(srcDate, PATTERN_TYPE_YMD);
		}

		return result;
	}

	/**
	 * @param duration
	 *            通话时长，多少秒
	 * @return xx时xx分xx秒
	 */
	public static String formatDuration(long duration) {
		long sec = 0l;
		long min = 0l;
		long hour = 0l;
		if (duration > 0 && duration < 60) {
			sec = duration;
		} else if (duration > 60 && duration < 3600) {
			min = duration / 60;
			sec = duration % 60;
		} else if (duration > 3600) {
			hour = duration / 3600;
			min = duration % 3600 / 60;
			sec = duration % 3600 % 60;
		}
		StringBuilder sb = new StringBuilder();
		if (hour != 0l) {
			sb.append(hour);
			sb.append(DURATION_HOUR);
		}
		if (min != 0l || hour != 0l) {
			sb.append(min);
			sb.append(DURATION_MIN);
		}
		sb.append(sec);
		sb.append(DURATION_SEC);
		return sb.toString();
	}
}
