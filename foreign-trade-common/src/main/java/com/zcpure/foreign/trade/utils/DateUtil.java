package com.zcpure.foreign.trade.utils;

import com.google.common.base.Strings;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DateUtil {
	public final static long MILLISECOND_OF_DAY = 24 * 60 * 60 * 1000L;

	public static Date stringToDate(String stringDate) {
		if (Strings.isNullOrEmpty(stringDate)) {
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return simpleDateFormat.parse(stringDate);
		} catch (ParseException e) {
		}
		return null;
	}

	public static Date stringToDate(String stringDate, String stringFormat) {
		if (Strings.isNullOrEmpty(stringDate)) {
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(stringFormat);
		try {
			return simpleDateFormat.parse(stringDate);
		} catch (ParseException e) {
		}
		return null;
	}

	public static String dateToString(Date date, String format) {
		if (date == null) {
			return null;
		}
		if (StringUtils.isBlank(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			return simpleDateFormat.format(date);
		} catch (Exception e) {
		}
		return "";
	}

	public static String dateToString(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return simpleDateFormat.format(date);
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * @param dateSub 时间戳毫秒
	 * @return
	 * @description: 时间戳转字符串 yyyy-MM-dd HH:mm:ss 格式
	 * @author Wiktor 2018年1月2日 下午6:35:10
	 */
	public static String timeStampToDateStr(String dateSub) {
		if (StringUtils.isBlank(dateSub)) {
			return "";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			long ldate = Long.parseLong(dateSub);
			Date date = new Date(ldate);
			return simpleDateFormat.format(date);
		} catch (Exception e) {
		}
		return "";
	}

	// 获取当天的开始时间
	public static Date getDayBegin() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	// 获取当天的结束时间
	public static Date getDayEnd() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	// 获取昨天的开始时间
	public static Date getBeginDayOfYesterday() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayBegin());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	// 获取昨天的结束时间
	public static Date getEndDayOfYesterDay() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayEnd());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 获取昨天日期
	 *
	 * @return
	 */
	public static String getYesterDay() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date time = cal.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(time);
	}

	// 获取明天的开始时间
	public static Date getBeginDayOfTomorrow() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayBegin());
		cal.add(Calendar.DAY_OF_MONTH, 1);

		return cal.getTime();
	}

	// 获取明天的结束时间
	public static Date getEndDayOfTomorrow() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayEnd());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	// 获取本周的开始时间
	public static Date getBeginDayOfWeek(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayofweek == 1) {
			dayofweek += 7;
		}
		cal.add(Calendar.DATE, 2 - dayofweek);
		return getDayStartTime(cal.getTime());
	}

	// 获取本周的结束时间
	public static Date getEndDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getBeginDayOfWeek(date));
		cal.add(Calendar.DAY_OF_WEEK, 6);
		Date weekEndSta = cal.getTime();
		return getDayEndTime(weekEndSta);
	}

	// 获取本月的开始时间
	public static Date getBeginDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 1, 1);
		return getDayStartTime(calendar.getTime());
	}

	public static Date getBeginDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return getDayStartTime(calendar.getTime());
	}

	// 获取本月的结束时间
	public static Date getEndDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 1, 1);
		int day = calendar.getActualMaximum(5);
		calendar.set(getNowYear(), getNowMonth() - 1, day);
		return getDayEndTime(calendar.getTime());
	}

	public static Date getEndDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		return getDayEndTime(calendar.getTime());
	}

	// 获取本年的开始时间
	public static Date getBeginDayOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getNowYear());
		// cal.set
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 1);

		return getDayStartTime(cal.getTime());
	}

	// 获取本年的结束时间
	public static Date getEndDayOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getNowYear());
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DATE, 31);
		return getDayEndTime(cal.getTime());
	}

	// 获取某个日期的开始时间
	public static Timestamp getDayStartTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d)
			calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0,
			0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	// 获取某个日期的结束时间
	public static Timestamp getDayEndTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d)
			calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23,
			59, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return new Timestamp(calendar.getTimeInMillis());
	}

	// 获取今年是哪一年
	public static Integer getNowYear() {
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return Integer.valueOf(gc.get(1));
	}

	// 获取本月是哪一月
	public static int getNowMonth() {
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return gc.get(2) + 1;
	}

	// 两个日期相减得到的天数
	public static int getDiffDays(Date beginDate, Date endDate) {

		if (beginDate == null || endDate == null) {
			throw new IllegalArgumentException("getDiffDays param is null!");
		}

		long diff = (endDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60 * 24);

		int days = new Long(diff).intValue();

		return days;
	}

	// 两个日期相减得到的秒数
	public static long dateDiff(Date beginDate, Date endDate) {
		long date1ms = beginDate.getTime();
		long date2ms = endDate.getTime();
		return (date2ms - date1ms) / 1000;
	}

	// 获取两个日期中的最大日期
	public static Date max(Date beginDate, Date endDate) {
		if (beginDate == null) {
			return endDate;
		}
		if (endDate == null) {
			return beginDate;
		}
		if (beginDate.after(endDate)) {
			return beginDate;
		}
		return endDate;
	}

	// 获取两个日期中的最小日期
	public static Date min(Date beginDate, Date endDate) {
		if (beginDate == null) {
			return endDate;
		}
		if (endDate == null) {
			return beginDate;
		}
		if (beginDate.after(endDate)) {
			return endDate;
		}
		return beginDate;
	}

	// 返回某月该季度的第一个月
	public static Date getFirstSeasonDate(Date date) {
		final int[] SEASON = {1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4};
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int sean = SEASON[cal.get(Calendar.MONTH)];
		cal.set(Calendar.MONTH, sean * 3 - 3);
		return cal.getTime();
	}

	// 返回某个日期下几天的日期
	public static Date getNextDay(Date date, int i) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + i);
		return cal.getTime();
	}

	// 返回某个日期前几天的日期
	public static Date getFrontDay(Date date, int i) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - i);
		return cal.getTime();
	}

	// 获取某年某月到某年某月按天的切片日期集合（间隔天数的集合）
	public static List getTimeList(int beginYear, int beginMonth, int endYear, int endMonth, int k) {
		List list = new ArrayList();
		if (beginYear == endYear) {
			for (int j = beginMonth; j <= endMonth; j++) {
				list.add(getTimeList(beginYear, j, k));

			}
		} else {
			{
				for (int j = beginMonth; j < 12; j++) {
					list.add(getTimeList(beginYear, j, k));
				}

				for (int i = beginYear + 1; i < endYear; i++) {
					for (int j = 0; j < 12; j++) {
						list.add(getTimeList(i, j, k));
					}
				}
				for (int j = 0; j <= endMonth; j++) {
					list.add(getTimeList(endYear, j, k));
				}
			}
		}
		return list;
	}

	// 获取某年某月按天切片日期集合（某个月间隔多少天的日期集合）
	public static List getTimeList(int beginYear, int beginMonth, int k) {
		List list = new ArrayList();
		Calendar begincal = new GregorianCalendar(beginYear, beginMonth, 1);
		int max = begincal.getActualMaximum(Calendar.DATE);
		for (int i = 1; i < max; i = i + k) {
			list.add(begincal.getTime());
			begincal.add(Calendar.DATE, k);
		}
		begincal = new GregorianCalendar(beginYear, beginMonth, max);
		list.add(begincal.getTime());
		return list;
	}

	// 获取某年某月的第一天日期
	public static Date getStartMonthDate(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		return calendar.getTime();
	}

	// 获取某年某月的最后一天日期
	public static Date getEndMonthDate(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		int day = calendar.getActualMaximum(5);
		calendar.set(year, month - 1, day);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	// 获取某年某月某日的开始时刻
	public static Date getStartDayDate(int year, int month, int day) {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);// 月份从0开始计算
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	// 获取某年某月某日的最后时刻
	public static Date getEndDayDate(int year, int month, int day) {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);// 月份从0开始计算
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 将毫秒值转为日期格式
	 *
	 * @param ms
	 * @return
	 */
	public static String msToDate(long ms) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(ms);
		String time = formatter.format(calendar.getTime());
		return time;
	}

	public static Date getEndDate(long times) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(times);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		// 毫秒数超过500 发生四舍五入现象、从23:59:59 变成第二天0时
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getEndDateDelayDay(int num) {
		Calendar calendar = Calendar.getInstance();
		long returnTimeMills = calendar.getTimeInMillis() + MILLISECOND_OF_DAY * num;
		calendar.setTimeInMillis(returnTimeMills);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		// 毫秒数超过500 发生四舍五入现象、从23:59:59 变成第二天0时
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static String get3daystart(Date dt) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.DATE, -3);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		return sdf.format(cal.getTime());
	}

	public static String get3daysend(Date dt) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MINUTE, 59);
		return sdf.format(cal.getTime());
	}

	public static String get7daystart(Date dt) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.DATE, -7);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		return sdf.format(cal.getTime());
	}

	public static String get7dayend(Date dt) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MINUTE, 59);
		return sdf.format(cal.getTime());
	}

	public static String get1monthstart(Date dt) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		return sdf.format(cal.getTime());
	}

	public static String get1monthend(Date dt) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MINUTE, 59);
		return sdf.format(cal.getTime());
	}

	/**
	 * "0天0小时6分钟0秒" 转换成秒
	 *
	 * @return
	 */
	public static Long getCnChangeSecs(String date) {
		Long result = 0L;
		Long day = 0L;
		Long hour = 0L;
		Long min = 0L;
		Long sec = 0L;
		if (!StringUtils.isEmpty(date)) {
			if (date.split("天").length > 1) {
				day = Long.parseLong(date.split("天")[0]);
				hour = Long.parseLong(date.split("天")[1].split("小时")[0]);
				min = Long.parseLong(date.split("天")[1].split("小时")[1].split("分钟")[0]);
				sec = Long.parseLong(date.split("天")[1].split("小时")[1].split("分钟")[1].split("秒")[0]);
			} else if (date.split("小时").length > 1) {
				day = 0L;
				hour = Long.parseLong(date.split("天")[1].split("小时")[0]);
				min = Long.parseLong(date.split("天")[1].split("小时")[1].split("分钟")[0]);
				sec = Long.parseLong(date.split("天")[1].split("小时")[1].split("分钟")[1].split("秒")[0]);
			} else if (date.split("分钟").length > 1) {
				day = 0L;
				hour = 0L;
				min = Long.parseLong(date.split("天")[1].split("小时")[1].split("分钟")[0]);
				sec = Long.parseLong(date.split("天")[1].split("小时")[1].split("分钟")[1].split("秒")[0]);
			} else {
				day = 0L;
				hour = 0L;
				min = 0L;
				sec = Long.parseLong(date.split("天")[1].split("小时")[1].split("分钟")[1].split("秒")[0]);
			}
			result = day * 24 * 60 * 60 + hour * 60 * 60 + min * 60 + sec;
		}
		return result;
	}

	/**
	 * 时间分钟、秒钟计算
	 *
	 * @param date   初始时间
	 * @param type   "SECOND"秒、"MINUTE"分钟、"HOUR"小时、"DAY"日
	 * @param offset 偏移量
	 * @return
	 */
	public static Date addDateMinut(Date date, String type, int offset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 24小时制
		if ("MINUTE".equals(type)) {
			cal.add(Calendar.MINUTE, offset);
		} else if ("SECOND".equals(type)) {
			cal.add(Calendar.SECOND, offset);
		} else if ("HOUR".equals(type)) {
			cal.add(Calendar.HOUR, offset);
		} else if ("DAY".equals(type)) {
			cal.add(Calendar.DATE, offset);
		}
		date = cal.getTime();
		cal = null;
		return date;
	}

	/**
	 * 时间比较 [如果firstTime晚于secondTime返回1，早于返回 -1 ，等于返回0 ]
	 *
	 * @param firstTime
	 * @param secondTime
	 * @param sdf        "HH:mm" 也可以是年月日
	 * @return
	 */
	public static Integer compareTime(String firstTime, String secondTime, SimpleDateFormat sdf) {
		try {
			long result = sdf.parse(firstTime).getTime() - sdf.parse(secondTime).getTime();
			return result < 0L ? Integer.valueOf(-1) : (result == 0L ? Integer.valueOf(0) : Integer.valueOf(1));
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 根據當前時間獲取20180910這樣的數
	 *
	 * @param type
	 * @return
	 */
	public static String getDateToNumber(String type) {
		if (StringUtils.isEmpty(type)) {
			type = "yyyyMMdd";
		}
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");//设置日期格式
		String newsNo = LocalDateTime.now().format(fmt);
		return newsNo;
	}

//	public static void main(String[] args) throws ParseException {
//		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//		String start = "13:01";
//		String end = "13:00";
//
//		long result = sdf.parse(start).getTime() - sdf.parse(end).getTime();
//		Integer aa = result < 0L ? Integer.valueOf(-1) : (result == 0L ? Integer.valueOf(0) : Integer.valueOf(1));
//		System.out.println(aa);
//
//        System.out.println(getYesterDay());
//	}
}
