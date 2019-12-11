package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自增ID
 */
public class AutoRandomID 
{
	/**
	 * ȡ������ƷID
	 * @return	������ƷID
	 */
	public static long  randomRoleArticleId()
	{
		try
		{
			Thread.sleep(15);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
		 Date date=new Date();
		 SimpleDateFormat sf=new SimpleDateFormat("dkmS");
		 //log.info(sf.format(date));
		 
		 return Long.parseLong(sf.format(date));
	}
}
