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
import com.gline.module.fish.FishPath;
import com.gline.module.fish.Path;
import com.gline.module.scene.Pos;

public class ReadExcelFishPath {
	
	private static final Logger log = Logger.getLogger(ReadExcelFishPath.class);
	
	//为了随机方便， 建立一个嵌套数组 将同一路径id的放进一个list里
	public static  Map<String,List<FishPath>> Config;
	public static  void readXlsx(String excelName) throws IOException 
	{
		String path =  ExcelManager.LIB_PATH + excelName + ExcelManager.OFFICE_EXCEL_2010_POSTFIX;
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
               
        //第一个表格
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(1);
        FishPath mFishPath = null;  
        //鱼群类型
        Config = new HashMap<String, List<FishPath>>(); 
        log.info("xssfSheet.getLastRowNum()=="+xssfSheet.getLastRowNum());         
        //第0行是描述,跳过...第1行是 英文描述,跳过            
        for (int rowNum = 4; rowNum <= xssfSheet.getLastRowNum(); rowNum++) 
        {
        	//第0列是ID, 其他列是属性, 以第0列为key                     	
            XSSFRow xssfRow = xssfSheet.getRow(rowNum);
            if (xssfRow != null)
            {
            	mFishPath = new FishPath();
            	mFishPath.id = ReadExcel.getValue(xssfRow.getCell(0));
            	mFishPath.groupid = ReadExcel.getValue(xssfRow.getCell(1));
            	//解析
            	String pathlist =  ReadExcel.getValue(xssfRow.getCell(2));
            	//log.info("=========================pathlist=="+pathlist); 
            	//先进行{ 如果有两个,则说明后面是鱼ID
            	//if(bornRuleList.equals("0"))
            		//continue;
            	String[] liString = pathlist.split("\\}\\,|\\}\\}");
            	
            	//List<FishPath> mList = new ArrayList<FishPath>();
            	
            	List<Path> cachePathList = new ArrayList<Path>();
            	
            	//路径,一行就是一个路径. 
            	for (String liStringa : liString) 
            	{         
            		//一条路径
            		String[] pathOne =  liStringa.split("\\,");
            		
            		//mPath.beginX = pathOne
            		List<String> poStrings = new ArrayList<String>();
            		float time =0;
            		
            		//每个小节点
            		for(String pathn : pathOne)
            		{
            			if (pathn=="" || pathn==null) {
							continue;
						}
            			if (pathn.startsWith("time=")) {
            				time = Float.valueOf(pathn.replaceAll("time=", ""));
            				continue;
						}
            			poStrings.add(pathn.replaceAll("\\{", ""));
            		}
            		
            		Pos mPos = null;
            		
            		Path mPathn = new Path();
            		for(int j=0;j<poStrings.size();j++)
            		{
            			if (j%2==0) 
            			{
							mPos = new Pos();
							mPos.x = Integer.valueOf(poStrings.get(j));
						}
            			else
            			{
            				//log.info("poStrings.get(j)=="+poStrings.get(j));
            				mPos.y = Integer.valueOf(poStrings.get(j));
            				mPathn.pList.add(mPos);
            			}
            		}
            		mPathn.time = time;
            		cachePathList.add(mPathn);
            	}          
            	
            	
            	FishPath newFishPath = new FishPath(); 

            	if(cachePathList.size()>=2)
            	{
                	//取上一个的终点,和下一个的起点,合并...
                	for(int x=0; x<cachePathList.size()-1;x++)
                	{
                		//构建中间节点
                    	Path startPah = cachePathList.get(x);
                    	Path endPath = cachePathList.get(x+1);
                    	Pos startPos = startPah.pList.get(startPah.pList.size()-1);
                    	Pos endPos = endPath.pList.get(0);                	
                    	Path newPath = new Path();
                    	newPath.pList.add(startPos);
                    	newPath.pList.add(endPos);
                    	newPath.time = 2;
                    	
                    	newFishPath.pathList.add(startPah);
                    	newFishPath.pathList.add(newPath);
                    	
                    	if(x == cachePathList.size()-2)
                    		newFishPath.pathList.add(endPath);
                	}
            		
            	}
            	else {
            		newFishPath.pathList = cachePathList;
				}

            
            	
            	if(Config.containsKey(mFishPath.groupid))
            	{
            		Config.get(mFishPath.groupid).add(newFishPath);
            		
            	}
            	else 
            	{
            		 List<FishPath> fishLIst = new ArrayList<FishPath>();
            		 fishLIst.add(newFishPath);
            		Config.put(mFishPath.groupid,fishLIst);   
				}

            }
        }
       /* for(List<FishPath> aString : Config.values())
        {
        	for(FishPath mFishPath2 :aString)
        	{
        		for( Path mPath :  mFishPath2.pathList)
        		{
        			log.info("tieme=="+mPath.time);
        			for(Pos mPos :  mPath.pList)
        				log.info("mPos.x=="+mPos.x + "   mPos.y=="+mPos.y);
        		}
        		log.info("=============================================");
        	}

        }*/
    }
}
