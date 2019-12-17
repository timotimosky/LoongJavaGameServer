/*
package netBase.http.thread;

import java.util.Iterator;

import netBase.World;
import netBase.http.HttpConnection;
import netBase.http.config.HttpConfig;
import thread.ExecuteThreadOnly;

*/
/**
 * 工作任务
 * @author djl
 * @date 2013-3-27
 *//*

public class WorkerThread extends ExecuteThreadOnly 
{
	
	@Override
	public void setRun()
	{
		
		for(;;)
		{
			try 
			{
				Thread.sleep(60000);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			Iterator<HttpConnection> it =  null;
					
					//World.values().iterator();
			
			long time = System.currentTimeMillis();
			
			while(it.hasNext())
			{
				HttpConnection http = (HttpConnection) it.next();
				
				long expiredTime = 0;
				
				if(http.isState())
				{
					expiredTime = http.operateTime + HttpConfig.CONNECTION_ONLINE_TIME;
				}
				else
				{
					expiredTime = http.operateTime + HttpConfig.CONNECTION_DOWN_TIME;
				}
				*/
/*判断是否过期*//*

				if(time > expiredTime)
				{
					http.shutDown();
					
					//World.unregister(http.getId());
				}
			}
		}
	}

}
*/
