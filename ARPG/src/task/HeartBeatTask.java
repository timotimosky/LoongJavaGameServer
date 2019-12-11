package task;

import org.apache.log4j.Logger;

import thread.ExecuteThreadEver;
import thread.ThreadPoolManager;

/**
 * 心跳连接
 * @author 
 * @date 2012-10-23
 */
public class HeartBeatTask extends ExecuteThreadEver
{
	private Logger log = Logger.getLogger(HeartBeatTask.class);
	
	void systemInfo()
	{
		Runtime run = Runtime.getRuntime();
		
		StringBuilder builder = new StringBuilder("线程[");
		builder
		.append(Thread.currentThread().getThreadGroup().activeCount())
		.append("]线程组")
		.append(Thread.currentThread().getThreadGroup().activeGroupCount())
		.append("空闲内存/总内存：[")
		.append(run.freeMemory()/(1024*1024))
		.append("MB/")
		.append(run.totalMemory()/(1024*1024))
		.append("MB] 处理器[")
		.append(run.availableProcessors())
		.append("]");
		
		log.info(builder.toString());
	}

	@Override
	public void runTurn() {
		
		//long time = System.currentTimeMillis();
		
		//for (Entry<Integer, GameClient> entry : World.newInstance().getChannelMap().entrySet()) {

			//PlayerMessage.GC_HEART_BEAT(entry.getValue(), time);
		//}
		
		/*系统开销输出*/
		systemInfo();
		
	}
	@Override
	public void sleepTurn() throws InterruptedException
	{
		Thread.sleep(180000);	
	}

	@Override
	protected void begin() {
		// TODO Auto-generated method stub
		
	}

	
	public void init()
	{
		ThreadPoolManager.getInstance().getCachepool().submit(new Thread(this));
	}
}
