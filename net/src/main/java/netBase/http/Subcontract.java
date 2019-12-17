/*
package netBase.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import netBase.http.config.HttpConfig;
import netBase.http.ip.IPManager;

import org.jboss.netty.channel.Channel;

*/
/**
 * 分包处理
 * @author djl
 * @date 2013-3-26
 *//*

public final class Subcontract
{
	
	
	*/
/**
	 * 执行数据包
	 * @param array
	 * 					数据包内容
	 * @create 2013-3-27 
	 *//*

	@SuppressWarnings("unchecked")
	public static <T extends HttpConnection>ReceivablePacket<T> execute(Channel channel, String[] array)
	{
		T connection = get(array);
			
		if(connection == null)
		{
			*/
/*返回拒绝请求*//*

			errorSend(connection, array, "拒绝请求。");
			return null;
		}
			
		int opcode = getOpcode(array);
		
		if(OperationCheck(opcode, channel, connection) && !connection.isState())
		{
			connection.updateoOperateTime();
			
			connection.setChannel(channel);
			
			connection.setState(Boolean.TRUE);
			
			ReceivablePacket<T> pakcet = null;
					//(ReceivablePacket<T>) World.getReceivable(opcode);
			
			pakcet.setClient(connection);
			
			//pakcet.setBuffer(getData(array));
			return pakcet;
		}
		else{
			errorSend(connection, array, "操作过于频繁！");
		}
		return null;
	}
	
	
	*/
/**
	 * 获得包数据
	 * @param uri		
	 * 					包
	 * @return
	 * 					返回包里的数据
	 * @create 2013-3-26 
	 *//*

	public static String[] getPacket(String uri)
	{
		String data = uri.substring(uri.indexOf(HttpConfig.URI_SPLIT) + 1, uri.length());
		
		String[] array = data.split(HttpConfig.DATA_SPLIT);
		
		return array;
	}
	
	*/
/**
	 * 操作校验（验证操作是否过于频繁）
	 * @param opcode
	 * 						命令号
	 * @param channel
	 * 						本次请求
	 * @param connection
	 * 						连接
	 * @return
	 * 						返回成功与否
	 * @create 2013-3-27 
	 *//*

	static boolean OperationCheck(int opcode, Channel channel, HttpConnection connection)
	{
		if(connection.getOperateTime() + HttpConfig.OPERATE_GAP_TIME > System.currentTimeMillis())
		{
			return Boolean.FALSE;
		}
		else
		{
			return Boolean.TRUE;
		}
	}
	
	*/
/**
	 * 获得连接
	 * @param address			
	 * 							地址
	 * @param countId
	 * 							帐号
	 * @return
	 * 							返回连接（为null的话无法建立链接）
	 * @create 2013-3-27 
	 *//*

	static <T extends HttpConnection>T get(String[] array)
	{
		if(getIpValid(array).equals(""))
		{
			return null;
		}
		String ip = getIp(array);
		
		String count = getCount(array);
		
		*/
/*生成ID*//*

		String id = IPManager.toLong(ip) + count;
		
		*/
/*获得连接*//*

		T http = null;
				//World.get(id);
		
		*/
/*if(http == null)
		{
			http = World.create(id);
			
			http.init(ip);
			
			World.register(http);
		}*//*

		return http;
	}
	
	
	*/
/**
	 * 获得指定IP和指定帐号的连接
	 * @param array
	 * 					数据内容
	 * @return
	 * 					返回连接的ID
	 *//*

	public static String getIpValid(String[] array)
	{
		String ip = getIp(array);
		if(IPManager.getInstance().isIPExist(ip))
			return ip;
		else
			return "";
	}
	
	
	*/
/**
	 * 获得数据包内容里的IP
	 * @param array
	 * 					数据包
	 * @return
	 * 					返回以String表示的Ip
	 * @create 
	 * 					2013-3-26 djl
	 *//*

	static String getIp(String[] array)
	{
		if(array.length >= 3)
		{
			return array[0];
		}
		else
		{
			return "";
		}
	}
	
	*/
/**
	 * 获得数据包内容里的帐号
	 * @param array
	 * 					数据包
	 * @return
	 * 					返回帐号
	 * @create 
	 * 					2013-3-26 djl
	 *//*

	static String getCount(String[] array)
	{
		if(array.length >= 3)
		{
			return array[1];
		}
		else
		{
			return "";
		}
	}
	
	
	*/
/**
	 * 获得数据包内容里的命令号
	 * @param array
	 * 					数据包
	 * @return
	 * 					返回命令号
	 * @create 
	 * 					2013-3-26 djl
	 *//*

	static int getOpcode(String[] array)
	{
		if(array.length >= 3)
		{
			return Integer.parseInt(array[2]);
		}
		else
		{
			return 0;
		}
	}
	
	
	*/
/**
	 * 获得数据包内容里的数据内容
	 * 
	 * @param array
	 *            数据包
	 * @return 返回数据内容
	 * @create 2013-3-26 djl
	 *//*

	static String getData(String[] array)
	{
		if(array.length > 3)
		{
			String string = "";
			try 
			{
				string = URLDecoder.decode(array[3],"utf-8");
			} 
			catch (UnsupportedEncodingException e) 
			{
				e.printStackTrace();
			}
			
			return string;
		}
		else
		{
			return "";
		}
	}
	
	
	*/
/**
	 * 设置发送数据包的内容
	 * @param packet				
	 * 					包
	 * @param data
	 * 					数据
	 * @create 2013-3-28 
	 *//*

	public static <T extends HttpConnection>void setSendableData(SendablePacket<T> packet, String data)
	{
		//packet.setBuffer(data);
	}
	
	*/
/**
	 * 错误返回
	 * @param connection
	 * @param result
	 *//*

	public static void errorSend(HttpConnection connection, String[] array, String result)
	{
		errorSend(connection, getOpcode(array), result);
	}
	
	
	public static void errorSend(HttpConnection connection, int opcode, String result)
	{
		if(connection.isState())
			return;
		
		if(connection.getChannel() == null)
			return;
		
		if(!connection.getChannel().isOpen())
			return;
		
		SendManager.send(connection.getChannel(), opcode, new DefaultResult(result));
	}
}
*/
