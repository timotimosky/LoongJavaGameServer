package com.gline.Excel.base;


import java.io.IOException;
import com.gline.Excel.ReadExcelBox;
import com.gline.Excel.ReadExcelBoxrate;
import com.gline.Excel.ReadExcelFishGruop;
import com.gline.Excel.ReadExcelFishPath;
import com.gline.Excel.ReadExcelFishRefresh;
import com.gline.Excel.ReadExcelFishType;
import com.gline.Excel.ReadExcelGun;
import com.gline.Excel.ReadExcelGunLv;
import com.gline.Excel.ReadExcelLoadAward;
import com.gline.Excel.ReadExcelPrize;
import com.gline.Excel.ReadExcelRoleLv;
import com.gline.Excel.ReadExcelShopItem;

/**
 * @author
 * @created 2014-5-21
 */
public class ExcelManager {
	
    public static final String OFFICE_EXCEL_2010_POSTFIX = ".xlsx";
    public static final String LIB_PATH = "excel/";   
  
    
	public static void ReadAllExcel() throws IOException
	{		
		//ReadExcelFishPath.readXlsx("fishpath");
		ReadExcelBoxrate.readXlsx("box");	
		ReadExcelLoadAward.readXlsx("recharge");
		ReadExcelFishType.readXlsx("fishType");
		
				ReadExcelShopItem.readXlsx1("shop");
				ReadExcelShopItem.readXlsx2("shop");
				ReadExcelShopItem.readXlsx3("shop");
				ReadExcelShopItem.readXlsx4("shop");
				
				ReadExcelRoleLv.readXlsx("roleLv");
				ReadExcelFishRefresh.readXlsx("fishpath");
				ReadExcelFishGruop.readXlsx("fishpath");
				ReadExcelFishPath.readXlsx("fishpath");				
				ReadExcelPrize.readXlsx("fishType");
				ReadExcelBox.readXlsx("box");
				ReadExcelGun.readXlsx("gun");
				ReadExcelGunLv.readXlsx("gun");
		//TODO

	}
	
}