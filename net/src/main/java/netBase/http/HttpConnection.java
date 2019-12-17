/*
package netBase.http;

import netBase.http.config.HttpConfig;

import org.jboss.netty.channel.Channel;

*/
/**
 * http连接
 * @author djl
 * @date 2013-3-26
 *//*

public abstract class HttpConnection
{
	*/
/**
	 * 连接ID
	 *//*

	String id;
	
	*/
/**
	 * IP
	 *//*

	String ip;
	
	*/
/**
	 * 帐号
	 *//*

	String count;
	
	*/
/**
	 * 当前连接
	 *//*

	Channel channel;
	
	*/
/**
	 * 最后一次操作时间
	 *//*

	public long operateTime;
	
	*/
/**
	 * 状态
	 * true 该连接已经有效
	 * false 该连接还没确定有效
	 *//*

	boolean state;
	
	public HttpConnection(String id)
	{
		this.id = id;
	}
	
	void init(String ip)
	{
		this.ip = ip;
		
		this.operateTime = System.currentTimeMillis() - HttpConfig.OPERATE_GAP_TIME;
		
		this.state = Boolean.FALSE;
	}


	public String getId()
	{
		return id;
	}


	public void setId(String id) 
	{
		this.id = id;
	}


	public String getIp() 
	{
		return ip;
	}


	void setIp(String ip)
	{
		this.ip = ip;
	}


	Channel getChannel()
	{
		return channel;
	}


	void setChannel(Channel channel) 
	{
		this.channel = channel;
	}


	long getOperateTime()
	{
		return operateTime;
	}


	void setOperateTime(long operateTime) 
	{
		this.operateTime = operateTime;
	}
	
	
	public boolean isState() 
	{
		return state;
	}

	public void setState(boolean state)
	{
		this.state = state;
	}
	
	void updateoOperateTime()
	{
		this.operateTime = System.currentTimeMillis();
	}

	*/
/**
	 * 关闭连接
	 *//*

	void close()
	{
		if(channel != null)
		{
			channel.disconnect();
			
			channel.close();
		}
	}
	
	
	*/
/**
	 * 关闭连接
	 *//*

	public void shutDown()
	{
		close();
		
	}
}
*/
