package Utilities;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelSheetWriter {

	public class FormExcelWriter {

		private final String filePath;
		private final Workbook workbook;
		private final Sheet sheet;
		private int currentRow = 0;

		public FormExcelWriter(String filePath) {
			this.filePath = filePath;
			this.workbook = new XSSFWorkbook();
			this.sheet = workbook.createSheet("Form Data");
		}

		// Log field name and value directly
		public void writeField(String fieldName, String value) {
			Row row = sheet.createRow(currentRow++);
			row.createCell(0).setCellValue(fieldName);
			row.createCell(1).setCellValue(value);
		}

		// Overloaded method to log from WebElement
		public void writeField(String fieldName, WebElement element) {
			writeField(fieldName, element.getAttribute("value"));
		}

		// Save the Excel file
		public void save() {
			try (FileOutputStream out = new FileOutputStream(new File(filePath))) {
				workbook.write(out);
				workbook.close();
			}  catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
