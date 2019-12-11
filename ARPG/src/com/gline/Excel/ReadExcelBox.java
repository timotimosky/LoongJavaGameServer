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
import com.gline.module.box.boxEnity;
import com.gline.module.box.boxrate;

public class ReadExcelBox {
	
	private static final Logger log = Logger.getLogger(ReadExcelBox.class);
	
	public static  Map<Integer, boxEnity> Config;
	public static  void readXlsx(String excelName) throws IOException 
	{
		String path =  ExcelManager.LIB_PATH + excelName + ExcelManager.OFFICE_EXCEL_2010_POSTFIX;
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        
      
        boxEnity mFishEntity = null;      
        Config= new HashMap<Integer, boxEnity>();                  
        
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            if (xssfSheet == null)
            {
               // continue;
            }
            //每个表格
            //log.info("xssfSheet.getLastRowNum()=="+xssfSheet.getLastRowNum());
            
            //第0行是描述,跳过...第1行是 英文描述,跳过            
            for (int rowNum = 4; rowNum <= xssfSheet.getLastRowNum(); rowNum++) 
            {
            	//第0列是ID, 其他列是属性, 以第0列为key                     	
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null)
                {              	
                	mFishEntity = new boxEnity();
                	mFishEntity.id = 	Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(0)));
                	mFishEntity.name =  ReadExcel.getValue(xssfRow.getCell(1));
                	mFishEntity.desc = ReadExcel.getValue(xssfRow.getCell(2));
                	mFishEntity.icon = ReadExcel.getValue(xssfRow.getCell(3));
                	
                	String costS = ReadExcel.getValue(xssfRow.getCell(4));
                	//costS.replaceAll("\\{|\\}", "");
                	//String award =  ReadExcel.getValue(xssfRow.getCell(6));
                	String newAward = costS.replace("{goodsId=", "");
                	String newAward2 =newAward.replaceAll("amount=", "");
                	String newAward3 =  newAward2.replaceAll("\\}", "");
                	//log.info("===newAward3="+newAward3);
                	
                	
                	String[] liString = newAward3.split("\\,");
                	if (liString.length>=2) 
                	{						
                		mFishEntity.costType = Integer.parseInt(liString[0]);
                    	mFishEntity.cost = Integer.parseInt(liString[1]);                   	                	               	
					}
                	mFishEntity.rank = 	 Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(5)));
                	
                	Config.put(mFishEntity.id, mFishEntity);                    
                }
            }
        
    }
}
