package netBase.http;

import java.io.UnsupportedEncodingException;

import netBase.util.ObjectTransString;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.DynamicChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;

/**
 * 发送消息管理类
 * @author 
 * @date 2013-3-27
 */
class SendManager
{
	private static Logger log = Logger.getLogger(SendManager.class);
	
	
	/**
	 * 发送消息
	 * @param connection
	 * 							连接
	 * @param sp
	 * 							数据包
	 * @create 2013-3-27 
	 */
	static <T extends HttpConnection>void send(T connection, SendablePacket<T> sp)
	{
		sp.write(connection);
		
		String data = "";
		///= getData(sp.getOpcode(), sp.getBuffer());
		
		send(connection, data);
	}
	
	
	/**
	 * 发送消息
	 * @param connection		
	 * 							连接
	 * @param data
	 * 							数据内容
	 * @create 2013-3-27 
	 */
	static void send(HttpConnection connection, String data)
	{
		if(connection.getChannel() == null)
			return;
		
		if(!connection.getChannel().isOpen())
			return;
		
		byte[] _byte = getBytes(data);
		
		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		
		ChannelBuffer buffer = new DynamicChannelBuffer(_byte.length);
		
		HttpHeaders headers = response.headers();
	        
	    headers.set(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
		
		buffer.writeBytes(_byte);
		
		response.setContent(buffer);
		
		connection.getChannel().write(response);
		
		connection.close();
	}
	
	/**
	 * 获得指定数据内容的byte数组表示形式
	 * @param data
	 * 					数据内容
	 * @return
	 * 					返回byte[]
	 * @create 2013-
	 */
	private static byte[] getBytes(String data)
	{
		byte[] _data = null;
		try 
		{
			_data = data.getBytes("UTF-8");
		} 
		catch (UnsupportedEncodingException e) 
		{
			StringBuilder builder = new StringBuilder("消息解析出错[").append(data).append("]");
			log.info(builder.toString(), e);
		}
		finally
		{
			if(_data == null)
			{
				_data = new byte[0];
			}
		}
		
		return _data;
	}
	
	/**
	 * 获得数据内容
	 * @param opcode
	 * 						命令号
	 * @param data
	 * 						内容
	 * @return
	 * 						返回数据内容
	 * @create 2013-3-27
	 */
	private static String getData(int opcode, String _buffer)
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(opcode).append("|").append(_buffer);
		
		return builder.toString();
	}
	
	/**
	 * 发送消息
	 * @param channel
	 * 						连接
	 * @param opcode
	 * 						命令号
	 * @param result
	 * 						结果集
	 * @create 2013-3-27 
	 */
	static void send(Channel channel, int opcode, Result result)
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(opcode).append("|").append(ObjectTransString.objectTransString(result));
		
		byte[] _byte = getBytes(builder.toString());
		
		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		
		ChannelBuffer buffer = new DynamicChannelBuffer(_byte.length);
		
		HttpHeaders headers = response.headers();
	        
	    headers.set(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
		
		buffer.writeBytes(_byte);
		
		response.setContent(buffer);
		
		channel.write(response);
		
		//channel.disconnect();
		
		channel.close().awaitUninterruptibly();
	}
}
