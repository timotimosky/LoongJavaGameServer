/*
package netBase.http.ip;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;



*/
/**
 * IP管理类
 * @date 2013-3-26
 * @create djl
 *//*

public abstract class AbstractIPManager<K,V>
{
	protected Logger log = Logger.getLogger(getClass());
	
	*/
/**
	 * IPMAP
	 *//*

	protected Map<K, V> map;
	
	public AbstractIPManager()
	{
		map = Collections.synchronizedMap(new HashMap<K, V>());
	}
	
	
	*/
/**
	 * 增加一个Ip信息
	 * @param ip
	 * @param value
	 *//*

	public abstract void register(K k, V v);
	
	
	
	*/
/**
	 * 注销一个IP信息
	 *//*

	public abstract void unregister(K k);
	
	
	*/
/**
	 * 获得一个IP信息
	 *//*

	public abstract V get(K k);
	
	
	
	*/
/**
	 * 关闭
	 *//*

	public void shutDown()
	{
		map.clear();
	}

	
	
	*/
/**
	 * 将string类型的IP4转换成long类型的
	 * @param ips			ip地址
	 * @return			返回long类型的adress
	 * @create 2013-3-26 djl
	 *//*

	public static long toLong(String ips)
	{
		return toLong(toByteArray(ips));
	}
	
	
	*/
/**
	 * 将IP4形式的Byte[]转换成long型IP4
	 * 
	 * @param bytes Ip4形式的Byte数组
	 * @return 返回long表示的IP4地址
	 *//*

	protected static long toLong(byte[] bytes)
	{
		long result = 0;
		result += (bytes[3] & 0xFF);
		result += ((bytes[2] & 0xFF) << 8);
		result += ((bytes[1] & 0xFF) << 16);
		result += (bytes[0] << 24);
		return result & 0xFFFFFFFFL;
	}

	*/
/**
	 * 将long型的IP4地址转换成数组
	 * 
	 * @param ip地址
	 * @return 数组
	 *//*

	protected static byte[] toBytes(long val)
	{
		byte[] result = new byte[4];
		result[3] = (byte) (val & 0xFF);
		result[2] = (byte) ((val >> 8) & 0xFF);
		result[1] = (byte) ((val >> 16) & 0xFF);
		result[0] = (byte) ((val >> 24) & 0xFF);
		return result;
	}

	*/
/**
	 * 将IP4形式的地址转换成byte[]
	 * 
	 * @param 地址
	 * @return 返回数组
	 * @create 2013-3-26  djl
	 *//*

	protected static byte[] toByteArray(String address)
	{
		byte[] result = new byte[4];
		String[] strings = address.split("\\.");
		for (int i = 0, n = strings.length; i < n; i++)
		{
			result[i] = (byte) Integer.parseInt(strings[i]);
		}

		return result;
	}
	
}
*/
