package com.beepermd.utilities;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	static XSSFWorkbook workbook;
	static XSSFSheet sheet;

	public static String sheet_path = "Files//testingnew.xlsx";

	@SuppressWarnings("deprecation")
	public ExcelUtil(String excelPath, String sheetName) {
		try {
			workbook = new XSSFWorkbook(excelPath);
			sheet = workbook.getSheet(sheetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getRowCount() {
		int rowCount = 0;
		try {
			rowCount = sheet.getPhysicalNumberOfRows();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			e.printStackTrace();
		}
		return rowCount;
	}

	public String getCellDataString(int rowNum, int colNum) {
		DataFormatter dataformatter = new DataFormatter();
		String cellData = null;
		try {

			cellData = dataformatter.formatCellValue(sheet.getRow(rowNum).getCell(colNum));

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			e.printStackTrace();
		}
		return cellData;
	}

	public void getCellDataNumber(int rowNum, int colNum) {
		try {
			double cellData = sheet.getRow(rowNum).getCell(colNum).getNumericCellValue();
			System.out.println(cellData);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			e.printStackTrace();
		}
	}

	public int getColCount() {
		int colCount = 0;
		try {
			colCount = sheet.getRow(0).getPhysicalNumberOfCells();
		

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			e.printStackTrace();
		}
		return colCount;
	}

}