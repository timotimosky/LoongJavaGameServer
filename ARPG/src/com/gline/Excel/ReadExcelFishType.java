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
import com.gline.module.fish.FishType;

public class ReadExcelFishType {
	
	private static final Logger log = Logger.getLogger(ReadExcelFishType.class);
	public static  Map<String, FishType> Config;
	public static  void readXlsx(String excelName) throws IOException 
	{
		String path =  ExcelManager.LIB_PATH + excelName + ExcelManager.OFFICE_EXCEL_2010_POSTFIX;
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);

               
        //第一个表格
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        FishType mFishEntity = null;     
        Config = new HashMap<String, FishType>();
        log.info("xssfSheet.getLastRowNum()=="+xssfSheet.getLastRowNum());         
        //第0行是描述,跳过...第1行是 英文描述,跳过            
        for (int rowNum = 4; rowNum <= xssfSheet.getLastRowNum(); rowNum++) 
        {
        	//第0列是ID, 其他列是属性, 以第0列为key                     	
            XSSFRow xssfRow = xssfSheet.getRow(rowNum);
            if (xssfRow != null)
            {
            	mFishEntity = new FishType();
            	mFishEntity.type =  ReadExcel.getValue(xssfRow.getCell(0));
            	mFishEntity.name = ReadExcel.getValue(xssfRow.getCell(1));
            	
            	mFishEntity.benefitGroup = Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(2)));
            	mFishEntity.aim = Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(3)));
            	mFishEntity.autoFirePrority = Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(4)));
            	mFishEntity.goldDrop = Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(5)));               	
            	mFishEntity.catchRate = Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(6)));
            	mFishEntity.isAdd = Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(7)));
  
            	mFishEntity.zorder = Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(8)));
            	mFishEntity.icon = (ReadExcel.getValue(xssfRow.getCell(9)));
            	mFishEntity.moveAnim = Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(10)));
            	mFishEntity.deadEffect = Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(11)));
            	Config.put(mFishEntity.type, mFishEntity);               
            }
        }
    }
}
