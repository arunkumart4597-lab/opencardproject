package utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    private final String path;

    public ExcelUtils(String path) {
        this.path = path;
    }

    // Returns the last row index (header row is index 0, so this equals the data row count)
    public int getRowCount(String sheetName) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {
            return workbook.getSheet(sheetName).getLastRowNum();
        }
    }

    public int getCellCount(String sheetName, int rowNum) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {
            return workbook.getSheet(sheetName).getRow(rowNum).getLastCellNum();
        }
    }

    public String getCellData(String sheetName, int rowNum, int colNum) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {
            Row row = workbook.getSheet(sheetName).getRow(rowNum);
            if (row == null) return "";
            Cell cell = row.getCell(colNum);
            if (cell == null) return "";
            return new DataFormatter().formatCellValue(cell);
        }
    }
}