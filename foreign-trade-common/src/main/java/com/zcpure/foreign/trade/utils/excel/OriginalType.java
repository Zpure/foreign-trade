package com.zcpure.foreign.trade.utils.excel;

/**
 * 描述
 *
 * @name OpBizValue.java
 * @copyright Copyright by fenqile.com
 * @author Paul
 * @version 2016年8月23日
 **/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通过导入类型
 *
 * @author kimhuhg
 */
public enum OriginalType {


	/**
	 * 通过导入excel 允许 xls 2003
	 */
	XLS("xls", "通过导入excel 允许 xls 2003"),

	/**
	 * 通过导入excel 允许 xls 2005+
	 */
	XLSX("xlsx", "通过导入excel 允许 xls 2003"),

	/**
	 * 通过导入 csv
	 */
	CSV("csv", "通过导入csv");


	/**
	 * 错误编码
	 */
	private String code;
	/**
	 * 错误描述
	 */
	private String msg;


	/**
	 * 错误编码以及对应的错误枚举对应关系
	 */
	private static Map<String, OriginalType> codeEnumMap = new HashMap<String, OriginalType>();

	/**
	 * 错误代码列表
	 */
	private List<OriginalType> errorList = new ArrayList<OriginalType>();

	/**
	 * 错误代码基类
	 *
	 * @param code 错误编码
	 * @param msg  错误描述
	 */
	OriginalType(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 根据错误编码返回对应的错误枚举
	 *
	 * @param code
	 * @return
	 */
	public OriginalType getValueByCode(String code) {

		if (codeEnumMap.isEmpty()) {
			for (OriginalType error : OriginalType.values()) {
				codeEnumMap.put(error.getValue(), error);
			}
		}
		return codeEnumMap.get(code);
	}

	/**
	 * 根据错误编码返回对应的错误信息
	 *
	 * @param code
	 * @return
	 */
	public String getMsgByCode(String code) {

		return getValueByCode(code).getMsg();
	}


	/**
	 * 获取错误信息列表
	 *
	 * @return
	 */
	public List<OriginalType> getErrorList() {
		return errorList;
	}

	/**
	 * 错误编码
	 *
	 * @return
	 */
	public String getValue() {
		return code;
	}

	/**
	 * 错误描述
	 *
	 * @return
	 */
	public String getMsg() {
		return msg;
	}

}
