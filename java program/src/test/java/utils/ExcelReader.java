package utils;

import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.params.provider.Arguments;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ExcelReader {
        public static Object[][] getTestData (String filePath, String sheetName){
            Object[][] data = null;
            try (FileInputStream fis = new FileInputStream(filePath);
                 Workbook workbook = WorkbookFactory.create(fis)) {

                Sheet sheet = workbook.getSheet(sheetName);
                int rowCount = sheet.getLastRowNum();
                int colCount = sheet.getRow(0).getLastCellNum();
                data = new Object[rowCount][colCount];
                DataFormatter formatter = new DataFormatter();

                for (int i = 1; i <= rowCount; i++) { // Skip header
                    Row row = sheet.getRow(i);
                    for (int j = 0; j < colCount; j++) {
                        data[i - 1][j] = formatter.formatCellValue(row.getCell(j));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }
    }