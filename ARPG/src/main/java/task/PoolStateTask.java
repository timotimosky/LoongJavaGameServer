package task;

import org.apache.log4j.Logger;

import thread.ExecuteThreadOnly;

/**
 * 线程池状态输出
 * @author 
 * @date 2012-11-15
 */
public class PoolStateTask extends ExecuteThreadOnly
{
	private Logger log = Logger.getLogger(PoolStateTask.class);

	@Override
	public void setRun()
	{
		/*List<String> list = ThreadPoolManager.getInstance().getStats();
		
		for(String str : list)
		{
			log.info(str);
		}*/
	}

	
}
