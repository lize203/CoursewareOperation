package com.zl.courseware.system.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/** excel处理工具类
 * */
public class ExcelUtility
{
	private ExcelUtility()
	{
		super();
	}
	
	
	private static final Logger log             = LoggerFactory.getLogger(ExcelUtility.class);
	public static final int     EXCEL_MAX_LAYER = 4;
	
	
	/** 添加一行类容
	 * 
	 * @param sheet 表单
	 * @param rowIndex 行坐标
	 * @param rowContent 该行中每个cell的内容 */
	public static void buildRow(HSSFSheet sheet, int rowIndex, String... rowContent)
	{
		HSSFRow row = sheet.createRow(rowIndex);
		HSSFCell cell = null;
		for (int i = 0; i < rowContent.length; i++)
		{
			cell = row.createCell(i);
			cell.setCellValue(rowContent[i]);
		}
	}
	
	/** 创建excel的一个sheet，并初始化标题
	 * 
	 * @param workbook excel工作簿
	 * @param sheetName sheet名
	 * @param headers 标题内容
	 * @return */
	public static HSSFSheet buildExcelHeader(HSSFWorkbook workbook, String sheetName, String[] headers)
	{
		HSSFSheet sheet = workbook.createSheet(sheetName);
		HSSFRow row = sheet.createRow(0);
		HSSFCell cells = null;
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		for (int i = 0; i < headers.length; i++)
		{
			cells = row.createCell(i);
			cells.setCellStyle(style);
			cells.setCellValue(headers[i]);
		}
		return sheet;
	}
	
	/** 插入一行带自动换行的记录
	 * 
	 * @param workbook excel工作簿
	 * @param sheet sheet
	 * @param rowIndex 行坐标
	 * @param rowContent 行内容 */
	public static void buildRichTextRow(HSSFWorkbook workbook, HSSFSheet sheet, int rowIndex, String[] rowContent)
	{
		HSSFRow row = sheet.createRow(rowIndex);
		HSSFCell cell = null;
		for (int i = 0; i < rowContent.length; i++)
		{
			cell = row.createCell(i);
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setWrapText(true);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(new HSSFRichTextString(rowContent[i]));
		}
	}
	
	/** 插入一行加粗样式的记录
	 * 
	 * @param workbook excel工作簿
	 * @param sheet sheet
	 * @param rowIndex 行坐标
	 * @param rowContent 行内容 */
	public static void buildfont(HSSFWorkbook workbook, HSSFSheet sheet, int rowIndex, String[] rowContent)
	{
		HSSFRow row = sheet.createRow(rowIndex);
		HSSFCell cell = null;
		for (int i = 0; i < rowContent.length; i++)
		{
			cell = row.createCell(i);
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			cellStyle.setFont(font);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(new HSSFRichTextString(rowContent[i]));
		}
	}
	
	/** 添加一个合并格式的记录
	 * 
	 * @param sheet sheet
	 * @param rowIndex 行坐标
	 * @param region 合并区域对象
	 * @param content 纪录内容 */
	public static void buildMergeCell(HSSFSheet sheet, int rowIndex, CellRangeAddress region, String content)
	{
		HSSFRow row = sheet.createRow(rowIndex);
		HSSFCell cell = row.createCell(1);
		sheet.addMergedRegion(region);
		cell.setCellValue(content);
	}
	
	/** 解析excel
	 * 
	 * @param is 输入流
	 * @param row_start 起始行
	 * @return 对象数组类型的集合 */
	public static List<Object[]> readExcelContent(InputStream is, Integer row_start)
	{
		POIFSFileSystem fs = null;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		HSSFRow row = null;
		List<Object[]> data = new ArrayList<Object[]>();
		try
		{
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e)
		{
			log.error("错误信息", e);
		}
		sheet = wb.getSheetAt(0);
		// 总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		// 总列数
		int colNum = row.getPhysicalNumberOfCells();
		int row_start_init = 0;
		/* 指定解析开始行、结束行、开始列、结束列 */
		if (row_start != null)
		{
			row_start_init = row_start;
		}
		Object[] objs;
		if (rowNum > 0)
		{
			for (int i = row_start_init; i <= rowNum; i++)
			{
				row = sheet.getRow(i);
				objs = new Object[colNum];
				for (int j = 0; j < colNum; j++)
				{
					Cell cell = row.getCell(j);
					if (cell != null)
					{
						if (cell.getCellType() == Cell.CELL_TYPE_STRING)
						{
							objs[j] = cell.getStringCellValue() == null ? "" : cell.getStringCellValue();
						} else
						{
							DecimalFormat df = new DecimalFormat("#");
							int value = (int) cell.getNumericCellValue();
							objs[j] = value == 0 ? "" : df.format(cell.getNumericCellValue());
						}
					}
				}
				data.add(objs);
			}
		}
		return data;
	}
	
	/** 解析excel内容。
	 * 
	 * @param is excel文件输入流
	 * @return 对象数据类型的集合 */
	public static List<Object[]> readExcel(InputStream is)
	{
		POIFSFileSystem fs = null;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		HSSFRow row = null;
		List<Object[]> content = new ArrayList<Object[]>();
		try
		{
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e)
		{
			log.error("错误信息", e);
		}
		sheet = wb.getSheetAt(0);
		// 总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		// 总列数
		int colNum = row.getPhysicalNumberOfCells();
		if (rowNum > 0)
		{
			for (int i = 1; i <= rowNum; i++)
			{
				Object[] obj = new Object[12];
				row = sheet.getRow(i);
				for (int j = 0; j < colNum; j++)
				{
					Cell cell = row.getCell(j);
					if (cell != null)
					{
						if (j == 0)
						{
							obj[0] = cell.getStringCellValue() == null ? "" : cell.getStringCellValue();
						} else if (j == 1)
						{
							obj[1] = cell.getStringCellValue() == null ? "" : cell.getStringCellValue();
						} else if (j == 2)
						{
							obj[2] = cell.getStringCellValue() == null ? "" : cell.getStringCellValue();
						} else if (j == 3)
						{
							obj[3] = cell.getStringCellValue() == null ? "" : cell.getStringCellValue();
						} else if (j == 4)
						{
							obj[4] = cell.getStringCellValue() == null ? "" : cell.getStringCellValue();
						} else if (j == 5)
						{
							obj[5] = cell.getStringCellValue() == null ? "" : cell.getStringCellValue();
						} else if (j == 6)
						{
							obj[6] = cell.getStringCellValue() == null ? "" : cell.getStringCellValue();
						} else if (j == 7)
						{
							obj[7] = cell.getStringCellValue() == null ? "" : cell.getStringCellValue();
						} else if (j == 8)
						{
							obj[8] = cell.getStringCellValue() == null ? "" : cell.getStringCellValue();
						} else if (j == 9)
						{
							obj[9] = cell.getStringCellValue() == null ? "" : cell.getStringCellValue();
						} else if (j == 10)
						{
							if (cell.getCellType() == Cell.CELL_TYPE_STRING)
							{
								obj[10] = cell.getStringCellValue() == null ? "" : cell.getStringCellValue();
							} else
							{
								DecimalFormat df = new DecimalFormat("#");
								int value = (int) cell.getNumericCellValue();
								obj[10] = value == 0 ? "" : df.format(cell.getNumericCellValue());
							}
						} else if (j == 11)
						{
							obj[11] = cell.getStringCellValue() == null ? "" : cell.getStringCellValue();
						}
					}
				}
				content.add(obj);
			}
		}
		return content;
	}
	
	public static void build(XSSFWorkbook workbook, XSSFSheet sheet, int rowIndex, String... rowContent)
	{
		XSSFRow row = sheet.createRow(rowIndex);
		XSSFCell cell = null;
		for (int i = 0; i < rowContent.length; i++)
		{
			cell = row.createCell(i);
			XSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setWrapText(true);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(new XSSFRichTextString(rowContent[i]));
		}
	}
	
	public static void buildFistRow(XSSFSheet sheet, int rowIndex)
	{
		sheet.createRow(rowIndex);
	}
	
	public static XSSFSheet buildExcelHeader(XSSFWorkbook workbook, String sheetName, String... headers)
	{
		XSSFSheet sheet = workbook.createSheet(sheetName);
		XSSFRow row = sheet.createRow(0);
		XSSFCell cells = null;
		sheet.setDefaultColumnWidth((short) 30);
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		XSSFFont font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		for (int i = 0; i < headers.length; i++)
		{
			cells = row.createCell(i);
			cells.setCellStyle(style);
			cells.setCellValue(headers[i]);
		}
		return sheet;
	}
	
	public static void buildMergeCell(XSSFSheet sheet, int rowIndex, CellRangeAddress region, String content)
	{
		XSSFRow row = sheet.createRow(rowIndex);
		XSSFCell cell = row.createCell(1);
		sheet.addMergedRegion(region);
		cell.setCellValue(content);
	}
	
	public static void buildRow(XSSFSheet sheet, int rowIndex, String... rowContent)
	{
		XSSFRow row = sheet.createRow(rowIndex);
		XSSFCell cell = null;
		for (int i = 0; i < rowContent.length; i++)
		{
			cell = row.createCell(i);
			cell.setCellValue(rowContent[i]);
		}
	}
	
	public static void buildRichTextRow(XSSFWorkbook workbook, XSSFSheet sheet, int rowIndex, String... rowContent)
	{
		XSSFRow row = sheet.createRow(rowIndex);
		XSSFCell cell = null;
		for (int i = 0; i < rowContent.length; i++)
		{
			cell = row.createCell(i);
			XSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setWrapText(true);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(new XSSFRichTextString(rowContent[i]));
		}
	}
	
	/** 读取xls内容。
	 * 
	 * @param is
	 * @return List<String[]> */
	public static List<String[]> parseExcelContent(InputStream is)
	{
		POIFSFileSystem fs = null;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		HSSFRow row = null;
		List<String[]> contentArrList = new ArrayList<String[]>();
		try
		{
			HashMap<String[], Object> hMap = new HashMap<String[], Object>();
			try
			{
				fs = new POIFSFileSystem(is);
				wb = new HSSFWorkbook(fs);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			// 总页数
			for (int num = 0; num < wb.getNumberOfSheets(); num++)
			{
				sheet = wb.getSheetAt(num);
				// 总行数
				int rowNum = sheet.getLastRowNum();
				row = sheet.getRow(0);
				// 空白页返回
				if (null == row)
				{
					break;
				}
				Cell cell = null;
				// 遍历第一行确定总列数
				int colNum = row.getPhysicalNumberOfCells();
				if (rowNum > 0)
				{
					row = sheet.getRow(0);
					for (int k = 0; k < colNum; k++)
					{
						cell = row.getCell(k);
						if (cell == null)
						{
							colNum = k;
							break;
						}
					}
					if (rowNum > 1)
					{
						// 循环行
						for (int i = 1; i <= rowNum; i++)
						{
							row = sheet.getRow(i);
							if (row == null)
								break;
							String temp = "";
							String[] arr = new String[colNum];
							// 循环列
							for (int j = 0; j < colNum; j++)
							{
								cell = row.getCell(j);
								if (cell != null)
								{
									if (cell.getCellType() == Cell.CELL_TYPE_STRING)
									{
										String cellValue = cell.getStringCellValue();
										if (cellValue == null || cellValue == "" || "0.0".equals(cellValue))
										{
											break;
										} else
										{
											cellValue = cellValue.trim();
											arr[j] = (cellValue);
										}
									} else
									{
										String cellValue = cell.getNumericCellValue() + "";
										if (cellValue == null || cellValue == "" || "0.0".equals(cellValue))
										{
											break;
										} else
										{
											cellValue = cellValue.trim();
											arr[j] = (cellValue);
										}
									}
								}
							}
							if (arr.length > 0 && arr[0] != null)
							{
								contentArrList.add(arr);
							}
						}
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return contentArrList;
	}
}
