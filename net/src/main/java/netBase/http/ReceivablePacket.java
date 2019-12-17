/*
package netBase.http;

import java.nio.BufferUnderflowException;

import org.apache.log4j.Logger;



*/
/**
 * 接收数据包超类
 * @author djl
 * @create 2013-3-26
 *//*

public abstract class ReceivablePacket<T extends HttpConnection> extends AbstractPacketHttp  implements Runnable,Cloneable
{
	protected static Logger log = Logger.getLogger(ReceivablePacket.class); 
	
	AbstractPacketFactory<T> factory;

	AbstractPacketFactory<T> getFactory() 
	{
		return factory;
	}

	void setFactory(AbstractPacketFactory<T> factory)
	{
		this.factory = factory;
	}

	public ReceivablePacket(int opcode)
	{
		super(opcode);
	}

	T client;
	
	
	protected boolean read() throws BufferUnderflowException, RuntimeException
	{
		readImpl();
		return true;
	}
	
	
	protected abstract void readImpl() throws BufferUnderflowException, RuntimeException;
	

	@Override
	public void run()
	{
		try 
		{
			read();
		}
		catch (Exception e)
		{
			StringBuilder builder = new StringBuilder("命令[").append(this.opcode).append("]无法解析。");
			log.warn(builder.toString(), e);
			*/
/*发送错误信息*//*

			SendManager.send(getClient().getChannel(), this.opcode, new DefaultResult("内容无法解析。"));
		}
		
		try 
		{
			runImpl();
		}
		catch (Exception e)
		{
			StringBuilder builder = new StringBuilder("命令[").append(this.opcode).append("]异常。");
			log.warn(builder.toString(), e);
			*/
/*发送错误信息*//*

			SendManager.send(getClient().getChannel(), this.opcode, new DefaultResult("错误啦！"));
		} 
		finally
		{
			*/
/*错误返回*//*

			//getClient().setState(Boolean.FALSE);
		}
	}
	
	
	public abstract void runImpl() throws Exception, RuntimeException;

	
	@SuppressWarnings("unchecked")
	public ReceivablePacket<T> clonePacket()
	{
		try
		{
			return (ReceivablePacket<T>) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			return null;
		}
	}
	
	public void sendPacket(SendablePacket<T> sp)
	{
		factory.getSendable(sp);
		//SendManager.send(getClient(), sp);
	}
}
*/
