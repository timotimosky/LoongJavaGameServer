package netBase.thread;

import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import netBase.AloneNetMap;
import netBase.packet.ReceivablePacket;
import netBase.Session;
import netBase.NetLink;

import org.apache.log4j.Logger;


/**
 * 
 */
public class NetWorkerThread extends Thread 
{	
	
	public static Logger log = Logger.getLogger(NetWorkerThread.class);
	
	private boolean process = true; 
	
	private final int corePoolSize = 20; 
	
	private final int keepAliveTime = 1;
	
	private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(); 
	
	private ThreadPoolExecutor threadPool = null; 
	
	public NetWorkerThread()
	{		
		threadPool = new ThreadPoolExecutor(corePoolSize,Integer.MAX_VALUE,keepAliveTime,
				TimeUnit.SECONDS,queue,new ThreadPoolExecutor.AbortPolicy());
	}
	
	/**
	 * true 
	 * @return
	 */
	public boolean isProcess() 
	{
		return process;
	}

	/**
	 *　控制整个通信层是否可以取消息ֹ
	 * @param process
	 */
	public void setProcess(boolean process) 
	{
		this.process = process;		
	}
	
	/**
	 * 
	 */
	public void shutdownExecutor()
	{
		threadPool.shutdown();
	}

	public void run()
	{	
		while(this.process)
		{
			try
			{
				Thread.sleep(1);
				action();
			}
			catch(Exception e)
			{
				e.printStackTrace();				
			}
/*			final()
			{
				
			}*/
		}		
	}
	
	
	//这里考虑用synchronized(this) 获取包，然后wait释放 然后传递包 出去， 然后notify重新获得锁。	
	/**
	 * 开启线程池，执行消息包
	 * @param netLink
	 */
	private void action()
	{	
		for(Entry<Session,NetLink> e : AloneNetMap.netMap.entrySet())
		{		    
		    NetLink netLink = e.getValue();
		    
			ReceivablePacket pack = netLink.getNetPack();
				
			if(null != pack)
			{
				/**
				 * 执行
				 */				
				threadPool.execute(new BusinessRunable(pack));				
			} 
						
		}
	}
	

}
