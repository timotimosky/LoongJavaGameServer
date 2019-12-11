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
import com.gline.module.Shop.shopBag;
import com.gline.module.Shop.shopBullet;
import com.gline.module.Shop.shopGold;
import com.gline.module.Shop.shopGun;
import com.gline.module.fish.FishRefresh;

public class ReadExcelShopItem {
	
	private static final Logger log = Logger.getLogger(ReadExcelShopItem.class);
	
	
	public static  Map<Integer, shopGold> shopGold;
	public static  Map<Integer, shopGun> shopGun;
	public static  Map<Integer, shopBullet>  ShopBulletConfig;	
	public static  Map<Integer, shopBag> ShopBagConfig;
	
	
	public static  void readXlsx1(String excelName) throws IOException 
	{
		String path =  ExcelManager.LIB_PATH + excelName + ExcelManager.OFFICE_EXCEL_2010_POSTFIX;
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);    
        shopGold mFishEntity = null;      
        shopGold = new HashMap<Integer, shopGold>(); 
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        //第0行是描述,跳过...第1行是 英文描述,跳过            
        for (int rowNum = 4; rowNum <= xssfSheet.getLastRowNum(); rowNum++) 
        {
        	//第0列是ID, 其他列是属性, 以第0列为key                     	
            XSSFRow xssfRow = xssfSheet.getRow(rowNum);
            if (xssfRow != null)
            {           	
            	mFishEntity = new shopGold();
            	if (xssfRow.getCell(0) == null) {
					//log.info("该行为空");
					continue;
				}            	
            	mFishEntity.id = 	Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(0)));
            	mFishEntity.name =  ReadExcel.getValue(xssfRow.getCell(1));
            	mFishEntity.goods = ReadExcel.getValue(xssfRow.getCell(2));
            	mFishEntity.firstBuy = ReadExcel.getValue(xssfRow.getCell(3));
            	mFishEntity.desc2 = ReadExcel.getValue(xssfRow.getCell(4));
            	mFishEntity.price =  Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(5)));
            	mFishEntity.icon = ReadExcel.getValue(xssfRow.getCell(6));
            	mFishEntity.rank = 	 Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(7)));
            	mFishEntity.goodId = ReadExcel.getValue(xssfRow.getCell(8));
            	mFishEntity.treasure = 	 ReadExcel.getValue(xssfRow.getCell(9));
            	
            	shopGold.put(mFishEntity.id, mFishEntity);                    
            }
        }      
    }
	
	public static  void readXlsx2(String excelName) throws IOException 
	{
		String path =  ExcelManager.LIB_PATH + excelName + ExcelManager.OFFICE_EXCEL_2010_POSTFIX;
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        
        /***************************************************************************/
        shopGun mFishEntity = null;      
        shopGun = new HashMap<Integer, shopGun>(); 
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(1);
        //第0行是描述,跳过...第1行是 英文描述,跳过            
        for (int rowNum = 4; rowNum <= xssfSheet.getLastRowNum(); rowNum++) 
        {
        	//第0列是ID, 其他列是属性, 以第0列为key                     	
            XSSFRow xssfRow = xssfSheet.getRow(rowNum);
            if (xssfRow != null)
            {                     	
            	mFishEntity = new shopGun();
            	mFishEntity.id = 	Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(0)));
            	mFishEntity.name =  ReadExcel.getValue(xssfRow.getCell(1));
            	String goods = ReadExcel.getValue(xssfRow.getCell(2));
            	
            	String goods12 = goods.replaceAll("\\{\\{gunId=|\\}\\}", "");
            	mFishEntity.goodsId = Short.parseShort(goods12);
            	mFishEntity.desc2 = ReadExcel.getValue(xssfRow.getCell(3));    
            	mFishEntity.price =  Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(4)));
            	mFishEntity.icon = ReadExcel.getValue(xssfRow.getCell(5));
            	mFishEntity.rank = 	Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(6)));
            	mFishEntity.resId = 	 Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(7)));
            	mFishEntity.type = Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(8)));
            	mFishEntity.time = 	 Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(9)));
            	
            	shopGun.put(mFishEntity.id, mFishEntity);                    
            }
        }      
    }
	
	public static void  readXlsx3(String excelName) throws IOException 
	{
		String path =  ExcelManager.LIB_PATH + excelName + ExcelManager.OFFICE_EXCEL_2010_POSTFIX;
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
       
        shopBullet mFishEntity = null;      
        ShopBulletConfig = new HashMap<Integer, shopBullet>(); 
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(2);    
        for (int rowNum = 4; rowNum <= xssfSheet.getLastRowNum(); rowNum++) 
        {                  	
            XSSFRow xssfRow = xssfSheet.getRow(rowNum);
            if (xssfRow != null)
            {     
            	
            	mFishEntity = new shopBullet();
            	mFishEntity.id = 	Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(0)));
            	mFishEntity.name =  ReadExcel.getValue(xssfRow.getCell(1));
            	mFishEntity.goods = ReadExcel.getValue(xssfRow.getCell(2));
            	//mFishEntity.desc1 = ReadExcel.getValue(xssfRow.getCell(3));
            	mFishEntity.desc2 = ReadExcel.getValue(xssfRow.getCell(3));    
            	mFishEntity.price =  Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(4)));
            	mFishEntity.icon = ReadExcel.getValue(xssfRow.getCell(5));
            	mFishEntity.rank = 	Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(6)));
            	mFishEntity.type = Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(7)));
            	mFishEntity.resId = 	 Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(8)));
            	
            	ShopBulletConfig.put(mFishEntity.id, mFishEntity);                    
            }
        }      
    }
	
	public static  void readXlsx4(String excelName) throws IOException 
	{
		String path =  ExcelManager.LIB_PATH + excelName + ExcelManager.OFFICE_EXCEL_2010_POSTFIX;
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
       
        shopBag mFishEntity = null;      
        ShopBagConfig = new HashMap<Integer, shopBag>(); 
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(3);    
        for (int rowNum = 4; rowNum <= xssfSheet.getLastRowNum(); rowNum++) 
        {                  	
            XSSFRow xssfRow = xssfSheet.getRow(rowNum);
            if (xssfRow != null)
            {               
            	mFishEntity = new shopBag();
            	mFishEntity.id = 	Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(0)));
            	mFishEntity.name =  ReadExcel.getValue(xssfRow.getCell(1));
            	mFishEntity.goods = ReadExcel.getValue(xssfRow.getCell(2));
            	mFishEntity.desc1 = ReadExcel.getValue(xssfRow.getCell(3));
            	mFishEntity.desc2 = ReadExcel.getValue(xssfRow.getCell(4));    
            	mFishEntity.price =  Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(5)));
            	mFishEntity.icon = ReadExcel.getValue(xssfRow.getCell(6));
            	mFishEntity.rank = 	Integer.parseInt(ReadExcel.getValue(xssfRow.getCell(7)));
           	
            	ShopBagConfig.put(mFishEntity.id, mFishEntity);                    
            }
        }      
    }
}
