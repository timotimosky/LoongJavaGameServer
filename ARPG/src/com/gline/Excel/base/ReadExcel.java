package com.gline.Excel.base;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;

/**
 * @author 
 * @created 2014-5-20
 */
public class ReadExcel {
    
	private static final Logger log = Logger.getLogger(ReadExcel.class);

    /// 数字 0 布尔值4  其余string
    @SuppressWarnings("static-access")
    public static String getValue(XSSFCell xssfRow) {
    	
    	if (xssfRow ==null) {
			return "0";
		}
    	
        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) 
        {
        	//log.info("是布尔值"+xssfRow.getBooleanCellValue());
            return String.valueOf(xssfRow.getBooleanCellValue()).trim();
        } 
        else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) 
        {        	
        	DecimalFormat df = new DecimalFormat("0");
        	String value = df.format(xssfRow.getNumericCellValue()); 
        	//log.info("是数字"+xssfRow.getNumericCellValue());
           // return String.valueOf(xssfRow.getNumericCellValue()).trim();
        	value = subZeroAndDot(value);
            return value;
        } 
        else if(xssfRow.getCellType() == xssfRow.CELL_TYPE_STRING)
        {
        	//log.info("======================是string "+xssfRow.getStringCellValue());
            return String.valueOf(xssfRow.getStringCellValue());
        }
        else if(xssfRow.getCellType() == xssfRow.CELL_TYPE_BLANK)//代表空白单元格
        {
        	//log.info("======================是BLANK "+xssfRow.getStringCellValue());
            return "0";
        }
        else if(xssfRow.getCellType() == xssfRow.CELL_TYPE_ERROR)//表示在单元的误差值
        {
            return "0";
        }    
        else if(xssfRow.getCellType() == xssfRow.CELL_TYPE_FORMULA)//表示一个单元格公式的结果
        {
            return "0";
        } 
        else 
        {
        	//log.info("====不知道是什么类型 "+xssfRow.getStringCellValue());
            return String.valueOf(xssfRow.getStringCellValue());
        }
    }
    
    /** * 使用正则表达式去掉多余的.与 
     *  * @param s 
        * @return 
        */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".0") > 0) {
            // 去掉多余的 
            s = s.replaceAll("0+?$", ""); 
           // 如果最后一位是.则去掉
            s = s.replaceAll("[.]$", "");
        } 
       return s;
    }
}