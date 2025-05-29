package DataProviders;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtilities {

	public static Object[][] readExcelData(String filePath, String sheetName) throws IOException {
		FileInputStream fis = new FileInputStream(filePath);
		Workbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheet(sheetName);

		int rowCount = sheet.getPhysicalNumberOfRows();
		int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

		Object[][] data = new Object[rowCount - 1][colCount];

		for (int i = 1; i < rowCount; i++) {
			Row row = sheet.getRow(i);
			for (int j = 0; j < colCount; j++) {
				Cell cell = row.getCell(j);
				data[i - 1][j] = getCellValueAsString(cell);
			}
		}

		workbook.close();
		fis.close();
		return data;
	}

	private static String getCellValueAsString(Cell cell) {
		if (cell == null) return "";

		switch (cell.getCellType()) {
			case STRING:
				return cell.getStringCellValue();
			case _NONE:
				break;
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					return cell.getDateCellValue().toString();
				} else {
					// Remove .0 if it's a whole number
					double d = cell.getNumericCellValue();
					if (d == (long) d) {
						return String.valueOf((long) d);
					} else {
						return String.valueOf(d);
					}
				}
			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
			case FORMULA:
				return cell.getCellFormula();
			case BLANK:
				return "";
			case ERROR:
				break;
			default:
				return "";
		}
	return "";}
}
