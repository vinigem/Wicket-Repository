package com.vini.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.vini.exceptions.ExcelUploadException;


public class ExcelUtil {
    
    private ExcelUtil() {
        
    }
    
    /**
     * Method to extract questions from the uploaded excel file stream.
     * @param inStream
     * @return List<String> questions
     * @throws ExcelUploadException
     */
    public static List<String> extractQuestions(InputStream inStream) throws ExcelUploadException{
        List<String> questions = new ArrayList<String>();
        try {
            Workbook workbook = WorkbookFactory.create(inStream);
            Sheet sheet = workbook.getSheetAt(0);
            
            if(sheet == null){
                throw new ExcelUploadException("excel.error.invalid_sheet");
            }
            
            Iterator<Row> rowIterator = sheet.iterator();
            if(sheet.getPhysicalNumberOfRows() < 2){
                throw new ExcelUploadException("excel.error.invalid_row");
            }
            
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next(); 
                Cell cell = row.getCell(0);
                
                if(row.getRowNum() > 0 && cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING){
                    String question = cell.getStringCellValue();
                    questions.add(question);
                }
            }
            
            if(questions.isEmpty()){
                throw new ExcelUploadException("excel.error.invalid_cells");
            }
        } catch (InvalidFormatException e) {
            throw new ExcelUploadException("excel.error.invalid_format", e);
        } catch (Exception e) {
            throw new ExcelUploadException("excel.error", e);
        }
        return questions;
    }
    
}
