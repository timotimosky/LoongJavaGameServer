package thread;

import java.lang.Thread.UncaughtExceptionHandler;

import org.apache.log4j.Logger;


/**
 * 异常捕捉
 * @author timosky
 * @date 2013-1-14
 */
public class ThreadUncaughtExceptionHandler implements UncaughtExceptionHandler 
{
	private static final Logger log = Logger.getLogger(ThreadUncaughtExceptionHandler.class);
	
	/*异常捕捉*/	  
	public void uncaughtException(Thread t, Throwable e)
	 {
		  log.error("异常捕捉: " + t.getName() + " 异常" + e, e);
		  if ((e instanceof OutOfMemoryError))
		  {
		     log.error("内存异常!");
		   }
	 }
	

}
