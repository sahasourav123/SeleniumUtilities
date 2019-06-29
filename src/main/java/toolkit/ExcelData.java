package toolkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utility.CustomLogger;

public class ExcelData {
	
	CustomLogger logger;
	
	public ExcelData() {
		logger = new CustomLogger();
	}

	/**
	 * Read data from excel worksheet and return data as 2D array
	 * Fill No_Data if any cell is [Blank] 
	 * @param srcFile
	 * @param sheetName
	 * @return 2-D Array containing the data
	 */
	public String[][] readData(String srcFile, String sheetName) {

		try {
			FileInputStream file = new FileInputStream(new File(srcFile));

			// Get the workbook instance for XLSX file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first sheet from the workbook
			XSSFSheet sheet = workbook.getSheet(sheetName);

			int lastRow = sheet.getPhysicalNumberOfRows();
			int lastColumn = sheet.getRow(1).getPhysicalNumberOfCells();

			String data[][] = new String[lastRow][lastColumn];
			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();
			int rowNum = 0;
			// while (rowIterator.hasNext()) {
			while (rowNum < lastRow) {
				Row row = rowIterator.next();

				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				int colNum = 0;
				// while (cellIterator.hasNext()) {
				while (colNum < lastColumn) {
					Cell cell = cellIterator.next();

					String value = "";

					switch (cell.getCellType()) {
					case NUMERIC:
						value = String.valueOf((int) cell.getNumericCellValue());
						break;
					default:
						value = cell.getStringCellValue();
						break;
					}

					data[rowNum][colNum++] = value.equals("") ? "No_Data" : value;
				}
				rowNum++;
			}
			file.close();
			workbook.close();
			return data;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Create a work-sheet named 'export' and Write data
	 * @param destFile - file name to SaveAs
	 * @param dataSet - ArrayList of or ArrayList
	 */
	public void writeData(String destFile, ArrayList<ArrayList<Object>> dataSet) {
		Workbook workbook = new XSSFWorkbook();
		Sheet opSheet = workbook.createSheet("export");
		int rowIndex = -1;

		for (ArrayList<Object> dataRow : dataSet) {
			Row row = opSheet.createRow(++rowIndex);

			int cellIndex = -1;
			for (Object cellValue : dataRow) {
				String dataType = cellValue.getClass().getSimpleName(); 
				switch(dataType) {
				case "Integer":
					row.createCell(++cellIndex).setCellValue((int) cellValue);
					break;
					
				case "Double":
					row.createCell(++cellIndex).setCellValue((double) cellValue);
					break;
				
				default:
					//System.out.println(dataType);
					row.createCell(++cellIndex).setCellValue(String.valueOf(cellValue));
				}
			}
		}

		try {
			if(!(destFile.endsWith(".xlsx") || destFile.endsWith(".xlsm"))) {
				logger.warning("Excel file extension not found. Saving into .xlsx format");
				destFile = destFile + ".xlsx";
			}
			
			FileOutputStream fos = new FileOutputStream(destFile);
			workbook.write(fos);
			workbook.close();
			fos.close();
			logger.info("File saved: "+ destFile);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
