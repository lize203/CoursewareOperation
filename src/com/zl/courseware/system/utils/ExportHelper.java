package com.zl.courseware.system.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.pathhelper.PathHelper;
import com.propertyhelper.PropertyHelper;

import edu.emory.mathcs.backport.java.util.Arrays;


/** 导出xls工具类 */
public class ExportHelper
{
	private static final Logger log            = LoggerFactory.getLogger(ExportHelper.class);
	private int                 sheetIndex     = 0;
	private int                 rowIndex       = 0;
	private int                 MAX_EXCELROW   = 65535;
	private int                 maxExportRow   = 20000;
	PropertyHelper              propertyHelper = new PropertyHelper(PathHelper.getClassesPath(ExportHelper.class)
	                                                   + "com/snc/system/config/path.properties");
	private WritableWorkbook    wwb;
	
	
	public ExportHelper()
	{
		if (propertyHelper.getProperty("sheet_max_row") != null)
		{
			maxExportRow = Integer.parseInt(propertyHelper.getProperty("sheet_max_row"));
		}
	}
	
	public String exportXls(List<ExcelTittleBean> titleBeans, List<?> data) throws IOException
	{
		String expPath = null;
		OutputStream out = null;
		try
		{
			String expDir = propertyHelper.getProperty("tempfile");
			/* 根据 系统if (PathUtil.isWindows()) { expDir =
			 * 环境propertyHelper.getProperty("excel_export_path_win"); } else {
			 * 指定expDir = propertyHelper.getProperty("excel_export_path_linux");
			 * } 对应FileUtility.checkDirectory(null, expDir); 的路劲 */
			expPath = PathUtil.getPath() + expDir + System.currentTimeMillis() + ".xls";
			out = new FileOutputStream(new File(expPath));
			wwb = Workbook.createWorkbook(out);
			if (data.isEmpty())
			{
				WritableSheet sheet = wwb.createSheet("sheet" + sheetIndex + (sheetIndex + 1), sheetIndex++);
				writeTitle(titleBeans, sheet);
			} else
			{
				int index = 0;
				int mod = 0;
				if (maxExportRow == 0)
				{
					index = 1;
					maxExportRow = MAX_EXCELROW;
				} else
				{
					index = data.size() / maxExportRow;
					mod = data.size() % maxExportRow;
					if (mod != 0)
					{
						index = index + 1;
					}
				}
				for (int i = 1; i <= index; i++)
				{
					rowIndex = 0;
					WritableSheet sheet = wwb.createSheet("sheet" + sheetIndex + (sheetIndex + 1), sheetIndex++);
					Object[] dest;
					if (i != index)
					{
						dest = new Object[maxExportRow];
						System.arraycopy(data.toArray(), (i - 1) * maxExportRow, dest, 0, maxExportRow);
					} else
					{
						if (mod == 0)
						{
							if (index == 1)
							{
								dest = data.toArray();
							} else
							{
								dest = new Object[maxExportRow];
								System.arraycopy(data.toArray(), (i - 1) * maxExportRow, dest, 0, maxExportRow);
							}
						} else
						{
							dest = new Object[mod];
							System.arraycopy(data.toArray(), (i - 1) * maxExportRow, dest, 0, mod);
						}
					}
					writeTitle(titleBeans, sheet);
					writeData(Arrays.asList(dest), sheet);
				}
			}
			wwb.write();
		} catch (Exception e)
		{
			log.error("导出报错" + e);
		} finally
		{
			wwb.close();
			out.flush();
			out.close();
		}
		return expPath;
	}
	
	/** excel中写入标题
	 * 
	 * @param titleBeans
	 * @throws WriteException */
	private void writeTitle(List<ExcelTittleBean> titleBeans, WritableSheet sheet) throws WriteException
	{
		// 头行备注样式
		WritableFont remarkfont = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
		remarkfont.setColour(jxl.format.Colour.DARK_RED);
		WritableCellFormat remarkformat = new WritableCellFormat(remarkfont);
		remarkformat.setAlignment(Alignment.LEFT);
		remarkformat.setVerticalAlignment(VerticalAlignment.CENTRE);
		remarkformat.setWrap(true);
		// 标题样式
		WritableFont titlefont = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
		WritableCellFormat titleformat = new WritableCellFormat(titlefont);
		titleformat.setAlignment(Alignment.CENTRE);
		titleformat.setBackground(jxl.format.Colour.CORAL);
		titleformat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
		titleformat.setVerticalAlignment(VerticalAlignment.CENTRE);
		titleformat.setWrap(true);
		Label label;
		for (ExcelTittleBean excelTittleBean : titleBeans)
		{
			if (Integer.parseInt(excelTittleBean.getRowfrom()) == 0)
			{
				label = new Label(Integer.parseInt(excelTittleBean.getColfrom()), Integer.parseInt(excelTittleBean
				        .getRowfrom()), excelTittleBean.getValue(), remarkformat);
			} else
			{
				label = new Label(Integer.parseInt(excelTittleBean.getColfrom()), Integer.parseInt(excelTittleBean
				        .getRowfrom()), excelTittleBean.getValue(), titleformat);
			}
			sheet.addCell(label);
			if (Integer.parseInt(excelTittleBean.getRowfrom()) > rowIndex)
			{
				rowIndex = Integer.parseInt(excelTittleBean.getRowfrom());
			}
			if (excelTittleBean.getColto() != null || excelTittleBean.getRowto() != null)
			{
				sheet.mergeCells(
				        Integer.parseInt(excelTittleBean.getColfrom()),
				        Integer.parseInt(excelTittleBean.getRowfrom()),
				        Integer.parseInt(excelTittleBean.getColto()),
				        Integer.parseInt(excelTittleBean.getRowto()));
				if (Integer.parseInt(excelTittleBean.getRowto()) > rowIndex)
				{
					rowIndex = Integer.parseInt(excelTittleBean.getRowto());
				}
			}
		}
	}
	
	/** excel写入内容
	 * 
	 * @param data
	 * @param sheet
	 * @param cellformat
	 * @throws WriteException */
	private void writeData(List<?> data, WritableSheet sheet) throws WriteException
	{
		WritableCellFormat cellformat = new WritableCellFormat(NumberFormats.TEXT);
		cellformat.setAlignment(Alignment.CENTRE);
		cellformat.setVerticalAlignment(VerticalAlignment.CENTRE);
		rowIndex++;
		Label label;
		WritableSheet sheet_index = sheet;
		for (int i = 0; i < data.size(); i++)
		{
			label = new Label(0, rowIndex, i + 1 + "", cellformat);
			sheet_index.addCell(label);
			Map<Integer, String> objs = (Map<Integer, String>) data.get(i);
			for (Integer index : objs.keySet())
			{
				label = new Label(index, rowIndex, objs.get(index), cellformat);
				sheet_index.setRowView(0, 1600, false);
				sheet_index.addCell(label);
			}
			rowIndex++;
			if (rowIndex == MAX_EXCELROW - 1)
			{
				rowIndex = 0;
				sheet_index = wwb.createSheet("mz" + sheetIndex + (sheetIndex + 1), sheetIndex++);
			}
		}
	}
	
	public List<String[]> readData(File file, int startRow) throws BiffException, IOException
	{
		List<String[]> dataList = new ArrayList<String[]>();
		String[] ss;
		// 构造Workbook（工作薄）对象
		Workbook wb = Workbook.getWorkbook(file);
		// 获得了Workbook对象之后，就可以通过它得到Sheet（工作表）对象了
		Sheet[] sheet = wb.getSheets();
		if (sheet != null && sheet.length > 0)
		{
			// 对每个工作表进行循环
			for (int i = 0; i < sheet.length; i++)
			{
				// 得到当前工作表的行数
				int rowNum = sheet[i].getRows();
				// int start = 0;指定从第几行开始读数据
				for (int j = startRow; j < rowNum; j++)
				{
					// 得到当前行的所有单元格
					Cell[] cells = sheet[i].getRow(j);
					if (cells != null && cells.length > 0 && !"".equals(cells[0].getContents())
					        && cells[0].getContents() != null)
					{
						/* 从指定行开始读数据 if (start == 0) { if
						 * ("1".equals(cells[0].getContents())) { start = 1; }
						 * else { //continue; } }else */
						ss = new String[cells.length];
						// 对每个单元格进行循环
						for (int k = 1; k < cells.length; k++)
						{
							// 读取当前单元格的值
							String cellValue = cells[k].getContents();
							ss[k - 1] = cellValue;
						}
						dataList.add(ss);
					}
				}
			}
		}
		// 最后关闭资源，释放内存
		wb.close();
		return dataList;
	}
}
