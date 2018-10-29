package com.zcpure.foreign.trade.utils;

import java.net.Inet4Address;
import java.util.Calendar;

/**
 * 唯一单号生成器
 */
public class UniqueNoUtils {

	public enum UniqueNoType {
		SO, // 订单号
		GC // 货品编号(goods_code)
	}

	private static Calendar cal = Calendar.getInstance();
	private static int seq = 0;
	private static final int ROTATION = 999;
	private static int ipMix = 0;

	/**
	 * 订单号生成规则：时间戳（精确到秒）+最后一段的IP地址+序列号
	 *
	 * @return 唯一订单号
	 */
	public static synchronized String next(UniqueNoType type) {
		if (seq > ROTATION) {
			seq = 0;
		}
		checkinfo();
		cal.setTimeInMillis(System.currentTimeMillis());
		return type + String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%2$03d%3$03d", cal, ipMix, seq++).substring(2);
	}

	private static void checkinfo() {
		if (ipMix == 0) {
			try {
				String ipAddress = Inet4Address.getLocalHost().getHostAddress();
				String[] ipAddresses = ipAddress.split("\\.");
				ipMix = Integer.parseInt(ipAddresses[3]);
			} catch (Exception e) {
				ipMix = 1;
			}
		}
	}

	public static synchronized String next() {
		if (seq > ROTATION) {
			seq = 0;
		}
		checkinfo();
		cal.setTimeInMillis(System.currentTimeMillis());
		return String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%2$03d%3$03d", cal,
			ipMix, seq++);
	}

	public static synchronized String nextNew(UniqueNoType type, String storeCode) {
		Integer storeId = 0;
		if (seq > ROTATION) {
			seq = 0;
		}
		try {
			String ipAddress = Inet4Address.getLocalHost().getHostAddress();
			String[] ipAddresses = ipAddress.split("\\.");
			ipMix = Integer.parseInt(ipAddresses[3]);
			if (storeCode.length() > 2) {
				storeCode = storeCode.substring(storeCode.length() - 2);
			}
			storeId = Integer.parseInt(storeCode);
		} catch (Exception e) {
			ipMix = 1;
		}

		cal.setTimeInMillis(System.currentTimeMillis());
		//日期+门店code+3位序号
		return type + String.format("%1$tY%1$tm%1$td%2$02d%3$03d%4$03d", cal, storeId, ipMix, seq++).substring(2);
	}

	public static synchronized Long getId() {
		cal.setTimeInMillis(System.currentTimeMillis());
		String str = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", cal) + RandomUtil.generateNumString(4);
		return Long.parseLong(str);
	}

}
