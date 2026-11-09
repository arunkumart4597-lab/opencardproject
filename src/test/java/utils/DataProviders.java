package utils;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

    private static final String SHEET = "Sheet1";

    @DataProvider(name = "LoginData")
    public static String[][] getLoginData() throws IOException {

        String path = System.getProperty("user.dir") + "/testdata/Opencarttestdata.xlsx";
        ExcelUtils xl = new ExcelUtils(path);

        int totalRows = xl.getRowCount(SHEET);
        int totalCols = xl.getCellCount(SHEET, 1);

        String[][] data = new String[totalRows][totalCols];

        for (int i = 1; i <= totalRows; i++) {
            for (int j = 0; j < totalCols; j++) {
                data[i - 1][j] = xl.getCellData(SHEET, i, j);
            }
        }
        return data;
    }
}