package thread;

import org.apache.log4j.Logger;

/**
 * 单一线程
 * @author djl
 * @date 2013-1-14
 */
public abstract class ExecuteThreadOnly implements Runnable
{
	private static final Logger log = Logger.getLogger(ExecuteThreadOnly.class);
	
	/*final,�������̳�*/
	public final void run()
	{
		try
		{
			setRun();
		}
		catch(Exception e)
		{
			log.warn("线程执行失败",e);
		}
	}
	
	public abstract void setRun();

}                            
