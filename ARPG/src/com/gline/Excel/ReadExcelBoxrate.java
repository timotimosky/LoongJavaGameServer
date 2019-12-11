package com.gline.Excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gline.Excel.base.ExcelManager;
import com.gline.Excel.base.ReadExcel;
import com.gline.module.box.boxrate;

public class ReadExcelBoxrate {
	
	private static final Logger log = Logger.getLogger(ReadExcelBoxrate.class);
	
	public static  Map<Integer, List<boxrate>> Config;
	public static  void readXlsx(String excelName) throws IOException 
	{
		String path =  ExcelManager.LIB_PATH + excelName + ExcelManager.OFFICE_EXCEL_2010_POSTFIX;
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        
        
        boxrate mFishEntity = null;      
        Config = new HashMap<Integer, List<boxrate>>();                  
        
        	//第2个表格
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(1);
            if (xssfSheet == null)
            {
               // continue;
            }
            //每个表格
            log.info("xssfSheet.getLastRowNum()=="+xssfSheet.getLastRowNum());
            
            //第0行是描述,跳过...第1行是 英文描述,跳过            
            for (int rowNum = 4; rowNum <= xssfSheet.getLastRowNum(); rowNum++) 
            {
            	//第0列是ID, 其他列是属性, 以第0列为key                     	
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null)
                {
                	mFishEntity = new boxrate();
                	mFishEntity.id = 	Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(0)));               	
                	//相同boxid的放一起
                	mFishEntity.boxId =  Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(1)));
                	mFishEntity.weight = Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(2)));
                	mFishEntity.content = ReadExcel.getValue(xssfRow.getCell(3));
                	mFishEntity.display = Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(4)));
                	mFishEntity.type = 	 Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(5)));
                	
                	String award =  ReadExcel.getValue(xssfRow.getCell(6));
                	String newAward = award.replace("{goodsId=", "");
                	String newAward2 =newAward.replaceAll("amount=", "");
                	String newAward3 =  newAward2.replaceAll("\\}", "");
                	//log.info("===newAward3="+newAward3);
                	
                	String[] awardList = newAward3.split(",");          
                	if (awardList.length>=2) 
                	{						
                		mFishEntity.awardType = Integer.parseInt(awardList[0]);
                    	mFishEntity.awardNumber = Integer.parseInt(awardList[1]);                   	                	               	
					}
                	
                	mFishEntity.notice = Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(7)));
                	mFishEntity.icon = 	 ReadExcel.getValue(xssfRow.getCell(8));
                	
                	if(Config.containsKey(mFishEntity.boxId))
                	{
                		Config.get(mFishEntity.boxId).add(mFishEntity);
                		
                	}
                	else 
                	{
                		 List<boxrate> fishLIst = new ArrayList<boxrate>();
                		 fishLIst.add(mFishEntity);
                		 Config.put(mFishEntity.boxId,fishLIst);   
    				}
              
                }
            }
        
    }
}
