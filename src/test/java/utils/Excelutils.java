package utils;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Excelutils {

	public static FileInputStream fis;
	public static FileOutputStream fos;
	public static Workbook wb;
	public static Sheet sh;
	public static Row row;
	public static Cell col;

	public static int getrowcount(String xlfilepath, String xlsheet) throws Throwable {
		fis = new FileInputStream(xlfilepath);
		wb = WorkbookFactory.create(fis);
		sh = wb.getSheet(xlsheet);
		int rowcount = sh.getLastRowNum();
		wb.close();
		fis.close();
		return rowcount;

	}

	public static int getcellcount(String xlfilepath, String xlsheet, int rownum) throws Throwable {
		fis = new FileInputStream(xlfilepath);
		wb = WorkbookFactory.create(fis);
		sh = wb.getSheet(xlsheet);
		row = sh.getRow(rownum);
		int cellcount = row.getLastCellNum();
		wb.close();
		fis.close();
		return cellcount;

	}

	public static String getcelldata(String xlfilepath, String xlsheet, int rownum, int colnum) throws Throwable {

		fis = new FileInputStream(xlfilepath);
		wb = WorkbookFactory.create(fis);
		sh = wb.getSheet(xlsheet);
		row = sh.getRow(rownum);
		col = row.getCell(colnum);
		String data;
		try {
			DataFormatter formatter = new DataFormatter();
			String celldata = formatter.formatCellValue(col);
			return celldata;

		} catch (Exception e) {

			data = "";

		}
		wb.close();
		fis.close();
		return data;

	}

	public static void setcelldata(String xlfilepath, String xlsheet, int rownum, int colnum, String data)
			throws Throwable {
		fis = new FileInputStream(xlfilepath);
		wb = WorkbookFactory.create(fis);
		sh = wb.getSheet(xlsheet);
		row = sh.getRow(rownum);
		if (row == null) {
			row = sh.createRow(rownum);
		}

		col = row.getCell(colnum);
		if (col == null) {
			col = row.createCell(colnum);
		}
		col.setCellValue(data);
		fis.close();

		fos = new FileOutputStream(xlfilepath);
		wb.write(fos);
		wb.close();
		fos.close();

	}
	public static String[][] getcelldatas(String xlfilepath, String sheetname) throws IOException {

		fis = new FileInputStream(xlfilepath);

		wb = WorkbookFactory.create(fis);

		sh = wb.getSheet(sheetname);

		int totalrow = sh.getPhysicalNumberOfRows();

		int totalcols = sh.getRow(0).getLastCellNum();

		DataFormatter formatter = new DataFormatter();

		String data[][] = new String[totalrow - 1][totalcols];

		for (int i = 1; i < totalrow; i++) {

			Row row = sh.getRow(i);
			
			if (row == null) {

				for (int j = 0; j < totalcols; j++) {

					data[i - 1][j] = "";
				}
				continue;
			}
			for (int j = 0; j < totalcols; j++) {

				Cell col = row.getCell(j);

				data[i - 1][j] = (col != null) ? formatter.formatCellValue(col) : "";

			}
		}
		fis.close();
		return data;

	}
	
	
	
	//excel write data
	public static void writecelldatas(String xlfilepath, String sheetname, int rownum, int cellnum, String data)
			throws EncryptedDocumentException, IOException {
		

		fis = new FileInputStream(xlfilepath);
		wb = WorkbookFactory.create(fis);
	    sh=wb.getSheet(sheetname);
	
	    
	    Row row= sh.getRow(rownum);
	    
	    if(row==null) {
	    	
	    
	    row=sh.createRow(rownum);
	    
	    }
	    
	

	    Cell cell=row.getCell(cellnum);
	    
	    if(cell==null) {
	    	
	    	cell=row.createCell(cellnum);
	    }
	    cell.setCellValue(data);
	    
	        
	      
	    
	    FileOutputStream fos = new FileOutputStream(xlfilepath);
	    wb.write(fos);
	    fos.close();

	    
	}
	



}
