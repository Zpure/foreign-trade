package com.zcpure.foreign.trade.utils.excel;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Excel操作工具类
 * <p>
 * 要求：POI_3.8+，Excel_2003+
 *
 * @author jiangyf
 * @date 2017年9月18日 下午6:26:23
 */
public class ExcelUtil<T> implements Serializable {
	private static final long serialVersionUID = -5963601668245436768L;

	/**
	 * 内存中保留 n 条数据，以免内存溢出，其余写入硬盘
	 */
	private static final int ROW_ACCESS_WINDOW_SIZE = 1000;
	/**
	 * 每个工作表存放最大记录数
	 */
	public static final int SHEET_MAX_ROWS = 50000;
	/**
	 * 默认工作表名
	 */
	public static final String DEFAULT_SHEET_NAME = "Sheet";
	/**
	 * 单元格默认列宽度
	 */
	private static final int DEFAULT_COLUMN_WIDTH = 15;
	/**
	 * 声明数据类
	 */
	private Class<T> clazz;

	/**
	 * 普通日期格式
	 */
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 英文日期格式
	 */
	private static final SimpleDateFormat SDF_EN = new SimpleDateFormat("EEE MMM d HH:mm:ss 'CST' yyyy", Locale.ENGLISH);

	public ExcelUtil(Class<T> clazz) {
		this.clazz = clazz;
	}

	public ExcelUtil() {
	}

	/**
	 * 创建工作簿(支持同类型数据分工作簿展示)
	 *
	 * @param dataList  导出数据
	 * @param sheetName 工作表名（传入null，表示使用默认工作表名）
	 * @return
	 */
	public Workbook createWorkbook(List<T> dataList, String fileName, String sheetName) throws Exception {
		// 根据是03之前还是03之后的版本创建不同的工作簿
		Workbook workbook = null;
		if (fileName.endsWith(".xls")) {
			workbook = new HSSFWorkbook();
		} else if (fileName.endsWith(".xlsx")) {
			if (dataList.size() <= ROW_ACCESS_WINDOW_SIZE) {
				workbook = new SXSSFWorkbook();
			} else {
				workbook = new SXSSFWorkbook(ROW_ACCESS_WINDOW_SIZE);
			}
		} else {
			throw new Exception("文件名或文件格式错误");
		}
		// 工作表名
		sheetName = (sheetName == null || sheetName.trim().length() == 0) ? DEFAULT_SHEET_NAME : sheetName;
		// 总记录数
		int totalRows = dataList != null && dataList.size() >= 0 ? dataList.size() : 0;
		// 工作表页数
		double sheetNum = getSheetNum(totalRows);
		// 获取数据所有属性
		List<Field> fields = getObjectFields();
		// 初始化工作表
		for (int sheetNo = 0; sheetNo < sheetNum; sheetNo++) {
			// 创建工作簿
			Sheet sheet = workbook.createSheet();
			// 设置工作表名
			workbook.setSheetName(sheetNo, sheetName + (sheetNo + 1));
			// 设置表格默认列宽度
			sheet.setDefaultColumnWidth(DEFAULT_COLUMN_WIDTH);
			// 标题行样式
			// CellStyle titleStyle = generateTitleStyle(workbook);
			// 数据行样式
			// CellStyle dataStyle = generateDataStyle(workbook);
			// 写入表头到Excel
			writeHeaderToExcel(sheet, sheetNo, fields);
			// 写入数据到Excel
			writeDataToExcel(sheet, sheetNo, dataList, totalRows, fields);
		}
		return workbook;
	}


	private final static Pattern DATA_STYLE = Pattern.compile("\\[(.*?)\\](.*)");

	/**
	 * 写入数据到Excel
	 *
	 * @param sheet     工作表
	 * @param sheetNo   第几个工作表
	 * @param dataList  数据集
	 * @param totalRows 总记录数
	 * @param fields    数据属性集
	 * @throws Exception
	 */
	private void writeDataToExcel(Sheet sheet, int sheetNo, List<T> dataList, int totalRows,
	                              List<Field> fields) throws Exception {
		// 创建内容列
		int startNo = sheetNo * SHEET_MAX_ROWS;
		int endNo = Math.min(startNo + SHEET_MAX_ROWS, totalRows);
		for (int j = startNo; j < endNo; j++) {
			Row row = sheet.createRow(j + 1 - startNo);
			// 得到导出对象.
			T obj = (T) dataList.get(j);
			for (int k = 0; k < fields.size(); k++) {
				// 获得field
				Field field = fields.get(k);
				// 设置实体类私有属性可访问
				field.setAccessible(true);
				ExcelColumn attr = field.getAnnotation(ExcelColumn.class);
//				//判断是否导出的字段
//				if(!attr.isExport()){
//					continue;
//				}

				int col = k;
				// 根据指定的顺序获得列号
				if (StringUtils.isNotBlank(attr.column())) {
					col = getExcelCol(attr.column());
				}
				// 创建单元格
				Cell cell = row.createCell(col);
				// 如果数据存在就填入,不存在填入空格
				Class<?> classType = (Class<?>) field.getType();
				String value = null;
				if (field.get(obj) != null && classType.isAssignableFrom(Date.class)) {
					String dateStr = String.valueOf(field.get(obj));
					Date date;
					try {
						date = SDF.parse(dateStr);
					} catch (ParseException ex) {
						date = SDF_EN.parse(dateStr);
					}
					value = SDF.format(date);
				} else {
					value = field.get(obj) == null ? "" : String.valueOf(field.get(obj));
				}
				// 设置数据行样式
				if (value == null || value.isEmpty()) {
					cell.setCellValue("");
				} else {
					Matcher matcher = DATA_STYLE.matcher(value);
					if (matcher.matches()) {
						String backgroupColor = matcher.group(1);
						CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
						cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						cellStyle.setFillForegroundColor(backgroupColor.startsWith("0x")
							? Short.valueOf(backgroupColor.substring(2), 16)
							: Short.valueOf(backgroupColor));
						cell.setCellStyle(cellStyle);
						cell.setCellValue(matcher.group(2));
					} else {
						cell.setCellValue(value);
					}
				}
			}
		}
	}

	/**
	 * 写入表头到Excel
	 *
	 * @param sheet   工作表
	 * @param sheetNo 第几个工作表
	 * @param fields  数据属性集
	 */
	private void writeHeaderToExcel(Sheet sheet, int sheetNo, List<Field> fields) {
		// 创建工作表第一行，存放表头
		Row row = sheet.createRow(0);
		for (int cellNum = 0; cellNum < fields.size(); cellNum++) {
			// 获取属性
			Field field = fields.get(cellNum);
			// 获取注解信息
			ExcelColumn attr = field.getAnnotation(ExcelColumn.class);
			// 根据指定的顺序获得列号
			int col = cellNum;

			if (StringUtils.isNotBlank(attr.column())) {
				col = getExcelCol(attr.column());
			}
			// 创建列
			Cell cell = row.createCell(col);
			// 设置列宽
			sheet.setColumnWidth(sheetNo,
				(int) ((attr.name().getBytes().length <= 4 ? 6 : attr.name().getBytes().length) * 1.5
					* 256));
			// 设置列中写入内容为String类型
			cell.setCellType(Cell.CELL_TYPE_STRING);
			// 写入列名
			cell.setCellValue(attr.name());
			// 如果设置了提示信息则鼠标放上去提示
			// if (StringUtils.isNotBlank(attr.prompt())) {
			// setHSSFPrompt(sheet, "", attr.prompt(), 1, 100, col,
			// col);
			// }
			// 如果设置了combo属性则本列只能选择不能输入
			// if (attr.combo().length > 0) {
			// setHSSFValidation(sheet, attr.combo(), 1, 100, col, col);
			// }
		}
	}

	/**
	 * 生成并设置标题行样式
	 *
	 * @param workbook
	 * @return
	 */
	@SuppressWarnings("unused")
	private CellStyle generateTitleStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setFillBackgroundColor(HSSFColor.SKY_BLUE.index); // 填充的背景颜色
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);// 填充图案
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 设置边框样式
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 顶边框
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		style.setFont(generateTitleFont(workbook));// 字体样式
		return style;
	}

	/**
	 * 生成并设置数据行样式
	 *
	 * @param workbook
	 * @return
	 */
	@SuppressWarnings("unused")
	private CellStyle generateDataStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(generateDataFont(workbook));
		return style;
	}

	/**
	 * 生成并设置标题行字体
	 *
	 * @param workbook
	 * @return
	 */
	private Font generateTitleFont(Workbook workbook) {
		Font font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);// 字体颜色
		font.setFontHeightInPoints((short) 12);// 字号
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		return font;
	}

	/**
	 * 生成并设置数据行字体
	 *
	 * @param workbook
	 * @return
	 */
	private Font generateDataFont(Workbook workbook) {
		Font font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		return font;
	}

	/**
	 * 将Excel中A,B,C,D,E列映射成0,1,2,3
	 *
	 * @param col 大写字母列名
	 * @return int 数字列名
	 */
	public int getExcelCol(String col) {
		col = col.toUpperCase();
		// 从-1开始计算，字母从1开始运算。这种总数下来算数正好相同。
		int count = -1;
		char[] cs = col.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
		}
		return count;
	}

	/**
	 * 获取数据类的所有属性
	 *
	 * @return
	 */
	private List<Field> getObjectFields() {
		// 得到所有定义字段
		Field[] allFields = clazz.getDeclaredFields();
		List<Field> fields = new ArrayList<Field>();
		// 得到所有field并存放到一个list中
		for (Field field : allFields) {
			//过滤不需要导出的字段
			if (field.isAnnotationPresent(ExcelColumn.class)
				&& field.getAnnotation(ExcelColumn.class).isExport()) {
				fields.add(field);
			}
		}
		return fields;
	}

	/**
	 * 获取工作表数量
	 *
	 * @param totalRows 总记录数
	 * @return
	 */
	private int getSheetNum(int totalRows) {
		if (totalRows == 0) {
			return 1;
		}
		int count = totalRows / SHEET_MAX_ROWS;
		int remain = totalRows % SHEET_MAX_ROWS;
		if (remain > 0) {
			return ++count;
		}
		return count;
	}

	/**
	 * 写入Excel到HTTP响应
	 *
	 * @param dataList  数据集
	 * @param fileName  文件名
	 * @param sheetName 工作表名（传入null，表示使用默认工作表名）
	 * @param response  响应对象
	 * @throws Exception
	 */
	public void writeToHttpResponse(List<T> dataList, String fileName, String sheetName,
	                                HttpServletResponse response) throws Exception {
		Workbook workbook = createWorkbook(dataList, fileName, sheetName);
		writeToHttpResponse(workbook, fileName, response);
	}

	/**
	 * 写入Excel到HTTP响应
	 *
	 * @param workbook 工作簿
	 * @param fileName 文件名
	 * @param response 响应对象
	 * @throws Exception
	 */
	public void writeToHttpResponse(Workbook workbook, String fileName, HttpServletResponse response)
		throws Exception {
		// 以流形式下载文件
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		// 把创建好的workbook以字节流的形式写进缓冲中
		workbook.write(os);
		// 读取os字节数组
		InputStream is = new ByteArrayInputStream(os.toByteArray());
		// 设置response弹出下载框
		// 清空response
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition",
			"attachment;fileName=" + new String(fileName.getBytes(), "iso-8859-1"));
		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		BufferedInputStream bis = new BufferedInputStream(is);
		// 获取缓冲数据
		byte[] buffer = new byte[bis.available()];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buffer, 0, buffer.length))) {
			toClient.write(buffer, 0, bytesRead);
		}
		toClient.flush();
		bis.close();
		toClient.close();
	}

	/**
	 * 写入Excel到指定文件地址
	 *
	 * @param dataList  数据集
	 * @param fileName  文件名
	 * @param sheetName 工作表名
	 */
	public ByteArrayOutputStream writeToFileStream(List<T> dataList, String fileName, String sheetName) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			createWorkbook(dataList, fileName, sheetName).write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return os;
	}

	/**
	 * 读取excel数据
	 *
	 * @param importFile
	 * @param fileType
	 * @param modeList
	 * @return
	 */
	public static List<Map<String, Object>> readXLSData(MultipartFile importFile, OriginalType fileType,
	                                                    List<ImportBaseModel> modeList) throws Exception {
		// 目前仅支持
		// 取第一个sheet
		Sheet sheet = getSheet(importFile, fileType);
		int rowSum = sheet.getLastRowNum();
		int colSum = sheet.getRow(0).getPhysicalNumberOfCells();
		// 检查头部信息
		checkXLSDataHead(sheet, rowSum, colSum, modeList);
		// 获取内容值
		return readXLSData(sheet, rowSum, colSum, modeList);
	}

	/**
	 * 检查XLS的头部数据是否一致
	 *
	 * @param sheet  xls sheet对象
	 * @param rowSum 总行数
	 * @param colSum 总列数
	 */
	private static void checkXLSDataHead(Sheet sheet, int rowSum, int colSum,
	                                     List<ImportBaseModel> modeList) {
		// 判断是否存在导入商品
		if (rowSum < 1) {
			throw new IllegalArgumentException("商品导入数量不能小于1");
		}

		// 文件头部长度不一致
		if (colSum != modeList.size()) {
			throw new IllegalArgumentException("文件头部长度不一致");
		}

		// 设置每个单元格为纯文本格式
		for (int j = 0; j < rowSum; j++) {
			Row row = sheet.getRow(j);
			if (row == null) {
				break;
			}

			for (int x = 0; x < colSum; x++) {
				Cell cell = row.getCell(x);
				String cellValue = "";
				if (cell != null) {
					if (cell.getCellType() != Cell.CELL_TYPE_STRING) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
					}
					cellValue = cell.getStringCellValue();
				}
				// 如果为第一行解析并校验数据
				if (j == 0) {
					// 对比该列的名称与实际是否一致
					String value = modeList.get(x).getTags();
					if (!value.equalsIgnoreCase(cellValue)) {
						throw new IllegalArgumentException("对比该列的名称与实际不一致");
					}
				}
			}
		}
	}

	/**
	 * 获取XLS sheet数据集
	 *
	 * @param importFile 导入的文件流
	 * @return sheet XLS sheet对象
	 * @throws IOException
	 */
	public static Sheet getSheet(MultipartFile importFile, OriginalType fileType) throws IOException {
		InputStream is = importFile.getInputStream();
		Workbook workbook = null;
		try {
			if (fileType == null) {
				if (importFile.getName().endsWith("xls")) {
					workbook = new HSSFWorkbook(is); // 支持office2003
				} else {
					workbook = new XSSFWorkbook(is); // 支持office2007
				}
			} else {
				if (fileType.equals(OriginalType.XLSX)) {
					workbook = new XSSFWorkbook(is); // 支持office2007
				} else if (fileType.equals(OriginalType.XLS)) {
					workbook = new HSSFWorkbook(is); // 支持office2003
				}
			}
		} catch (Exception e) {

		}
		// 取第一个sheet
		Sheet sheet = workbook.getSheetAt(0);
		return sheet;
	}

	/**
	 * 检查XLS的头部数据是否一致
	 *
	 * @param sheet  xls sheet对象
	 * @param rowSum 总行数
	 * @param colSum 总列数
	 * @return List<Map> 综合数据的集合
	 */
	private static List<Map<String, Object>> readXLSData(Sheet sheet, int rowSum, int colSum,
	                                                     List<ImportBaseModel> modeList) {
		List<Map<String, Object>> list = new ArrayList<>();
		for (int j = 1; j <= rowSum; j++) {
			Row row = sheet.getRow(j);
			if (row == null) {
				break;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("_no", j);
			for (int x = 0; x < colSum; x++) {
				Cell cell = row.getCell(x);
				String cellValue = "";
				if (cell != null) {
					System.out.print("colSum:" + x + "/rowSum:" + j + "/");
					System.out.print("cellType:" + cell.getCellType() + "/");
//                    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC && HSSFDateUtil.isCellDateFormatted(cell)) {
//                        Date dateCellValue = cell.getDateCellValue();
//                        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        cellValue = formater.format(dateCellValue);
//                    } else {
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cellValue = cell.getStringCellValue();
//                    }
					System.out.print("值:" + cellValue);
					System.out.println();
				}
				// 获取列对应的key
				String key = modeList.get(x).getKey();
				// 转换index 和key的关系
				map.put(key, cellValue.trim());
			}
			list.add(map);
		}
		return list;
	}

	/**
	 * 读取excel数据
	 *
	 * @param importFile
	 * @param fileType
	 * @return
	 */
	public List<T> readXLSData(MultipartFile importFile, OriginalType fileType) throws Exception {
		// 目前仅支持
		List<ImportBaseModel> modeList = new ArrayList<>();
		List<Field> fields = getObjectFields();
		for (int i = 0; i < fields.size(); i++) {
			Field field = fields.get(i);
			// 设置实体类私有属性可访问
			field.setAccessible(true);
			ExcelColumn attr = field.getAnnotation(ExcelColumn.class);
			int col = i;
			// 根据指定的顺序获得列号
			if (StringUtils.isNotBlank(attr.column())) {
				col = getExcelCol(attr.column());
			}
			ImportBaseModel importBaseModel = ImportBaseModel.gernateModel(col, field.getName(), attr.name());
			modeList.add(importBaseModel);
		}
		// 取第一个sheet
		Sheet sheet = getSheet(importFile, fileType);
		int rowSum = sheet.getLastRowNum();
		int colSum = sheet.getRow(0).getPhysicalNumberOfCells();
		// 检查头部信息
		checkXLSDataHead(sheet, rowSum, colSum, modeList);
		// 获取内容值
		List<Map<String, Object>> dataMap = readXLSData(sheet, rowSum, colSum, modeList);
		List<T> dataList = new ArrayList<>();
		for (Map x : dataMap) {
			x.remove("_no");
			boolean isAllNull = true;
			for (Object object : x.values()) {
				if (!"".equals(String.valueOf(object))) {
					isAllNull = false;
					break;
				}
			}
			T t = clazz.newInstance();
			if (!isAllNull) {
				BeanUtils.populate(t, x);
				dataList.add(t);
			}
		}
		return dataList;
	}
}
