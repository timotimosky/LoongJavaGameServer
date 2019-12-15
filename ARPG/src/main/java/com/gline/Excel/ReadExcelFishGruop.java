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
import com.gline.module.fish.FishGroup;

public class ReadExcelFishGruop {
	
	private static final Logger log = Logger.getLogger(ReadExcelFishGruop.class);
	
	//为了随机方便， 建立一个嵌套数组 将同一鱼儿id的放进一个list里
	public static  Map<String,List<FishGroup>> Config;
	public static  void readXlsx(String excelName) throws IOException 
	{
		String path =  ExcelManager.LIB_PATH + excelName + ExcelManager.OFFICE_EXCEL_2010_POSTFIX;
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);

               
        //第一个表格
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        FishGroup mFishEntity = null;  
        //鱼群类型/ 鱼群
        Config = new HashMap<String, List<FishGroup>>(); 
        log.info("xssfSheet.getLastRowNum()=="+xssfSheet.getLastRowNum());         
        //第0行是描述,跳过...第1行是 英文描述,跳过            
        for (int rowNum = 4; rowNum <= xssfSheet.getLastRowNum(); rowNum++) 
        {
        	//第0列是ID, 其他列是属性, 以第0列为key                     	
            XSSFRow xssfRow = xssfSheet.getRow(rowNum);
            if (xssfRow != null)
            {
            	mFishEntity = new FishGroup();
            	mFishEntity.groupId = ReadExcel.getValue(xssfRow.getCell(0));
            	mFishEntity.bornType = ReadExcel.getValue(xssfRow.getCell(1));//这个没啥用,只用于鱼生成.
            	mFishEntity.pathGroup = ReadExcel.getValue(xssfRow.getCell(2));
            	mFishEntity.count = Integer.valueOf(ReadExcel.getValue(xssfRow.getCell(3)));

            	//鱼种类型、数量、出生间隔、旋转角度           	
            	String bornRuleList  =ReadExcel.getValue(xssfRow.getCell(4));
            	
            	//先进行{ 如果有两个,则说明后面是鱼ID
            	String[] liString = bornRuleList.split("\\''|\\{\\{|\\,|\\}\\}");	          	

            	mFishEntity.GroupType = liString[1];
            	mFishEntity.fishSiglType = mFishEntity.GroupType; 
            	
            	mFishEntity.GroupType = liString[1];
            	
            	mFishEntity.number = Integer.valueOf(liString[2]);  //这个没啥用
            	mFishEntity.timeDelay = Float.valueOf(liString[3]);
            	mFishEntity.Roule = liString[4];
            	//log.info("mFishEntity.groupId="+mFishEntity.grouptype+"==mFishEntity.fishType="+mFishEntity.fishType);
            	//说明有鱼id
            	if (liString.length>5) {
            		for (int j=5;j<liString.length-1;j++)
            		{
                		//mFishEntity.fishTypeIdlist = new ArrayList<String>();
                		//mFishEntity.fishTypeIdlist.add(liString[j].replaceAll("\\{", ""));
                		mFishEntity.fishSiglType = liString[j].replaceAll("\\{", "");
                		//log.info("mFishEntity.groupId="+mFishEntity.groupId+"==="+liString[j].replaceAll("\\{", ""));
					}            		
				}
      
            	if(Config.containsKey(mFishEntity.bornType))
            	{
            		Config.get(mFishEntity.bornType).add(mFishEntity);
            		
            	}
            	else {
            		 List<FishGroup> fishLIst = new ArrayList<FishGroup>();
            		 fishLIst.add(mFishEntity);
            		Config.put(mFishEntity.bornType,fishLIst);   
				}         
            }
        }
    }
}
