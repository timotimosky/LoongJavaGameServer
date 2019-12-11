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
import com.gline.module.gun.GunEnity;

public class ReadExcelGun {
	
	private static final Logger log = Logger.getLogger(ReadExcelGun.class);
	
	public static  Map<Integer, GunEnity> Config;
	public static  void readXlsx(String excelName) throws IOException 
	{
		String path =  ExcelManager.LIB_PATH + excelName + ExcelManager.OFFICE_EXCEL_2010_POSTFIX;
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        
      
        GunEnity mFishEntity = null;      
        Config= new HashMap<Integer, GunEnity>();                  
        
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(1);
            
            //第0行是描述,跳过...第1行是 英文描述,跳过            
            for (int rowNum = 4; rowNum <= xssfSheet.getLastRowNum(); rowNum++) 
            {
            	//第0列是ID, 其他列是属性, 以第0列为key                     	
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null)
                {              	
                	mFishEntity = new GunEnity();
                	mFishEntity.type = 	Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(0)));
                	mFishEntity.name =  ReadExcel.getValue(xssfRow.getCell(1));
                	mFishEntity.fireInterval = Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(2)));
                	mFishEntity.cp = Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(3)));               	
                	mFishEntity.icon = ReadExcel.getValue(xssfRow.getCell(4));                	
                	
                	Config.put(mFishEntity.type, mFishEntity);                    
                }
            }
        
    }
}
