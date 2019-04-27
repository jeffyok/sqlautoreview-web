package com.jeffy.sqlautoreview.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * ExcelUtils tool
 * 
 * Created:May 15, 2012.
 * 
 * @author jeffychen
 * @version $Revision: 1.5 $ $Date: 2012/07/05 07:26:34 $
 */
public class ExcelUtils {
	private static String sheetName = "data";

	private POIFSFileSystem fs = null;

	private HSSFWorkbook workBook;
	private XSSFWorkbook xssfWorkBook;

	private HSSFSheet sheet;

	private HSSFRow row;

	private HSSFCell cell;

	private HSSFFont font;

	private HSSFCellStyle cellStyle;

	private HSSFCellStyle headCellStyle;

	private FileOutputStream fileOut;
	SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public ExcelUtils() {
		workBook = new HSSFWorkbook();
	}

	/**
	 * create excel table response byes[]
	 * 
	 * @param list
	 * @param headerMap
	 * @param reportTitle
	 *            table header return byte[]
	 */
	public byte[] outputExcel(List<Map> list, Map headerMap, String reportTitle) {
		byte[] bytes = null;
		try {
			this.createSheet(headerMap, reportTitle);
			bytes = this.createCvsBytes(list, headerMap, reportTitle);
		} catch (Exception ex) {
			System.out.print(ex);
		}
		return bytes;
	}

	/**
	 * create all row output to byte[]
	 * 
	 * @param list
	 * @param headerMap
	 * @return
	 */
	private byte[] createCvsBytes(List<Map> list, Map headerMap,
			String reportTitle) {
		byte[] bytes = null;
		ByteArrayOutputStream out = null;
		Map obj;
		try {
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					if (StringUtils.isNotEmpty(reportTitle)) {// 有标题从第三行开始
																// 没有标题从第二行开始
						row = sheet.createRow(i + 2);
					} else {

						row = sheet.createRow(i + 1);
					}
					obj = list.get(i);
					this.createCell(row, obj, headerMap);
				}
			}
			out = new ByteArrayOutputStream();
			workBook.write(out);
			bytes = out.toByteArray();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}

	/**
	 * create sheet
	 * 
	 * @param headerMap
	 */
	private void createSheet(Map<String, String> headerMap, String reportTitle) {
		try {
			sheet = workBook.createSheet(ExcelUtils.sheetName);
			sheet.setDefaultColumnWidth(20);
			headCellStyle = workBook.createCellStyle();
			headCellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
			headCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			headCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			headCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			headCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			headCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			headCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			HSSFFont font = workBook.createFont();
			font.setColor(HSSFColor.VIOLET.index);
			font.setFontHeightInPoints((short) 12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			headCellStyle.setFont(font);
			cellStyle = workBook.createCellStyle();
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

			HSSFFont font2 = workBook.createFont();
			font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			cellStyle.setFont(font2);
			if (StringUtils.isNotEmpty(reportTitle)) {
				// 产生表格标题行

				int count = 0;
				for (String value : headerMap.values()) {
					count++;
				}
				CellRangeAddress region = new CellRangeAddress(0, 0, 0,count - 1);
				sheet.addMergedRegion(region);
				HSSFRow rowTitle = sheet.createRow(0);
				HSSFCell cellTitle = rowTitle.createCell(0);
				cellTitle.setCellValue(reportTitle);
				HSSFCellStyle style = workBook.createCellStyle();
				HSSFFont titleFont = workBook.createFont();
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				titleFont.setFontHeightInPoints((short) 18);
				style.setFont(titleFont);
				cellTitle.setCellStyle(style);
				// 表头行
				row = sheet.createRow(1);
			} else {
				// 不需要设置标题
				row = sheet.createRow(0);
			}
			Iterator<String> iterator = headerMap.values().iterator();
			for (int i = 0; iterator.hasNext(); i++) {
				cell = row.createCell(i);
				cell.setCellStyle(headCellStyle);
				// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue((String) iterator.next());
			}
		} catch (Exception ex) {
			System.out.print(ex);
		}
	}

	/**
	 * create row cell
	 * 
	 * @param row
	 * @param obj
	 * @param headerMap
	 */
	private void createCell(HSSFRow row, Map obj, Map<String, String> headerMap) {
		try {
			Iterator<String> iterator = headerMap.keySet().iterator();
			for (int i = 0; iterator.hasNext(); i++) {
				cell = row.createCell(i);
				cell.setCellStyle(cellStyle);
				String propertieValue = iterator.next();
				// //cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				// Class<? extends Object> classz = obj.getClass();
				// Field[] fields = classz.getDeclaredFields();
				//
				// //support super class,just fit with BaseDto
				// if (obj.getClass().getSuperclass() != null ) {
				// Field[] superFields =
				// obj.getClass().getSuperclass().getDeclaredFields();
				// fields = (Field[]) ArrayUtils.addAll(fields, superFields);
				// }
				//
				// Object result = null;
				// for (int j = 0; j < fields.length; j++) {
				// Field field = fields[j];
				// String fieldName = field.getName();
				// //headerMap's key maybe for example a.b,a is object b is a's
				// properties
				// if (propertieValue.contains(".")) {
				// if (fieldName.equals(propertieValue.substring(0,
				// propertieValue.indexOf(".")))) {
				// Method method = classz.getMethod(getMethodName(field), new
				// Class[] {});
				// Object tempObjct = method.invoke(obj, new Object[] {});
				// if (tempObjct != null) {
				// Class<? extends Object> tempClassz = tempObjct.getClass();
				// Field tempField =
				// tempClassz.getDeclaredField(propertieValue.substring(propertieValue.indexOf(".")
				// + 1));
				// Method tempMethod =
				// tempClassz.getMethod(getMethodName(tempField), new Class[]
				// {});
				// result = tempMethod.invoke(tempObjct, new Object[] {});
				// }
				// break;
				// }
				// } else {
				// if (fieldName.equals(propertieValue)) {
				// Method method = classz.getMethod(getMethodName(field), new
				// Class[] {});
				// result = method.invoke(obj, new Object[] {});
				// break;
				// }
				// }
				// }

				// if (result != null) {
				// if (result instanceof Boolean) {
				// boolean bValue = (Boolean) result;
				// textValue = "Y";
				// if (!bValue) {
				// textValue = "N";
				// }
				// } else if (result instanceof Date) {
				// Date date = (Date) result;
				// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				// textValue = sdf.format(date);
				// } else {
				// // other
				// textValue = result.toString();
				// }
				// }
				String textValue = null;

				if (obj.get(propertieValue) instanceof Date) {
					if (null != obj.get(propertieValue))
						textValue = sd.format(obj.get(propertieValue));
				} else {
					if (null != obj.get(propertieValue))
						textValue = obj.get(propertieValue).toString();
				}

				cell.setCellValue(textValue);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * get Method Name by field
	 * 
	 * @param field
	 * @return
	 */
	private String getMethodName(Field field) {
		String getMethodName = "";
		if (field.getType().getName().equals("boolean")) {
			getMethodName = "is"
					+ field.getName().substring(0, 1).toUpperCase()
					+ field.getName().substring(1);
		} else {
			getMethodName = "get"
					+ field.getName().substring(0, 1).toUpperCase()
					+ field.getName().substring(1);
		}
		return getMethodName;
	}

	/**
	 * 
	 * TODO(这里用一句话描述这个方法的作用)
	 * 
	 * @author wangjunjie
	 * @Title: getAllData
	 * @param sheetIndex
	 *            Sheet表格序数
	 * @param fromRowIndex
	 *            从第几行开始遍历
	 * @return
	 * @Return: Object[][] 返回值
	 */
	public Object[][] getAllData(MultipartFile file, int sheetIndex,
			int fromRowIndex) {
		Sheet sheet = null;
		try {
			// fs = new POIFSFileSystem(file.getInputStream());

			if (file.getOriginalFilename().endsWith(".xls")) {
				workBook = new HSSFWorkbook(file.getInputStream());
				sheet = workBook.getSheetAt(sheetIndex);
			} else if (file.getOriginalFilename().endsWith(".xlsx")) {
				xssfWorkBook = new XSSFWorkbook(file.getInputStream());
				sheet = xssfWorkBook.getSheetAt(sheetIndex);
			} else {
				System.out.println("您输入的excel格式不正确");
				return null;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int rowCount = (sheet.getLastRowNum() + 1) - fromRowIndex, colCount = sheet
				.getRow(fromRowIndex).getLastCellNum();
		System.out.println("excel rowCount:" + rowCount + " colCount:"
				+ colCount);
		Object[][] data = new Object[rowCount][colCount];// getLastRowNum 最后一行索引
		int count = 0, actualRowCount = 0;
		Boolean specialFlag = false;
		for (int i = fromRowIndex; i < sheet.getLastRowNum() + 1; i++) {
			if (sheet.getRow(i) == null) {
				specialFlag = true;
				break;
			}
			actualRowCount++;
			for (int j = 0; j < colCount; j++) {
				Cell c = sheet.getRow(i).getCell(j);

				Object value = null;
				if (c == null || c.getCellType() == Cell.CELL_TYPE_BLANK) {
					value = null;
				} else if (c.getCellType() == Cell.CELL_TYPE_STRING) {
					value = c.getStringCellValue();
				} else if (c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					value = c.getNumericCellValue();
				} else {
					// throw new Exception("不支持的类型");
					value = "不支持的类型";
				}
				data[count][j] = value;
			}
			count++;

		}
		if (specialFlag) {
			Object[][] newData = new Object[actualRowCount][colCount];
			System.arraycopy(data, 0, newData, 0, actualRowCount);
			return newData;
		}
		return data;

	}

	/**
	 * 导出Excel
	 * 
	 * @author wangjunjie
	 * @Title: export
	 * @param beanName
	 *            导出对象名称，如输入sku,导出文件名sku_20160418084351_193670.xls
	 * @param list
	 *            导出对象列表
	 * @param headerMap
	 *            excel表头与对象属性对应键值对
	 * @param reportTitle
	 *            excel 导出标题
	 */
	public void exportExcel(List<Map<String,Object>> list, Map<String,String> headerMap, String reportTitle,
			HttpServletResponse httpServletResponse) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		String filename = reportTitle + "_" + format.format(new Date())+ ".xls";
		ServletOutputStream servletOutputStream = null;
		try {
			httpServletResponse.setHeader("Content-disposition",
					"attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
			httpServletResponse.setContentType("application/x-download");
			httpServletResponse.setContentType("application/vnd.ms-excel");
			servletOutputStream = httpServletResponse.getOutputStream();
			this.createSheet(headerMap, reportTitle);
			Map obj;
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					if (StringUtils.isNotEmpty(reportTitle)) {// 有标题从第三行开始
						// 没有标题从第二行开始
						row = sheet.createRow(i + 2);
					} else {

						row = sheet.createRow(i + 1);
					}
					obj = list.get(i);
					this.createCell(row, obj, headerMap);
				}
			}
			workBook.write(servletOutputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				servletOutputStream.flush();
				servletOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
