package Utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelUtil {
    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;

    // Method to load the Excel file
    public static void loadExcel(String filePath) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
        workbook = new XSSFWorkbook(fileInputStream);
    }

    // Get the number of rows in a sheet
    public static int getRowCount(int sheetIndex) {
        sheet = workbook.getSheetAt(sheetIndex);
        return sheet.getPhysicalNumberOfRows();
    }
   

    // Get cell data by row and column index
    public static String getCellData(int sheetIndex, int rowNum, int colNum) {
        sheet = workbook.getSheetAt(sheetIndex);
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            return ""; // Return empty string if row is null
        }
        Cell cell = row.getCell(colNum);
        if (cell == null) {
            return ""; // Return empty string if cell is null
        }

        // Check the cell type and return the appropriate value
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
            	if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    return dateFormat.format(date); // Convert to desired date format
                } else {
                	DecimalFormat decimalFormat = new DecimalFormat("#"); // Change pattern as needed
                    return decimalFormat.format(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula(); // Handle formula cells if necessary
            default:
                return ""; // Return an empty string for unsupported types
        }
    }
 // Method to close the workbook
    public static void closeWorkbook() throws IOException {
        if (workbook != null) {
            workbook.close(); // Close the workbook to release resources
        }
    }
}
