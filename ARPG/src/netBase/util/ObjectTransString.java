package netBase.util;


import java.io.StringWriter;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * 对象转换json
 * @author 
 *
 */
public class ObjectTransString 
{
	private static ObjectMapper obj = new ObjectMapper();
	/**
	 * 对象转换成json
	 * @return
	 */
	public static String objectTransString(Object object)
	{
		
		StringWriter sw = new StringWriter();
		
		try 
		{
			obj.writeValue(sw, object);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return sw.toString();
	}
}
