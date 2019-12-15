package com.gline.Excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gline.Excel.base.ExcelManager;
import com.gline.Excel.base.ReadExcel;
import com.gline.module.fish.FishRefresh;

public class ReadExcelFishRefresh {
	
	private static final Logger log = Logger.getLogger(ReadExcelFishRefresh.class);
	
	public static  Map<String, FishRefresh> Config;
	
	public static  void readXlsx(String excelName) throws IOException 
	{
		String path =  ExcelManager.LIB_PATH + excelName + ExcelManager.OFFICE_EXCEL_2010_POSTFIX;
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);

               
        //第一个表格
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(2);
        FishRefresh mFishEntity = null;     
        Config = new HashMap<String, FishRefresh>();
        log.info("xssfSheet.getLastRowNum()=="+xssfSheet.getLastRowNum());         
        //第0行是描述,跳过...第1行是 英文描述,跳过            
        for (int rowNum = 4; rowNum <= xssfSheet.getLastRowNum(); rowNum++) 
        {
        	//第0列是ID, 其他列是属性, 以第0列为key                     	
            XSSFRow xssfRow = xssfSheet.getRow(rowNum);
            if (xssfRow != null)
            {                
            	mFishEntity = new FishRefresh();
            	mFishEntity.type = ReadExcel.getValue(xssfRow.getCell(0));
            	mFishEntity.interval = Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(1)));
            	Config.put(mFishEntity.type, mFishEntity);               
            }
        }     
    }
}
