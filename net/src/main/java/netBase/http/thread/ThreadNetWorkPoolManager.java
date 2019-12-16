package netBase.http.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * 网络通信线程池
 * @author djl
 * @date 2013-1-30
 */
public class ThreadNetWorkPoolManager implements Executor
{
	/**
	 * 网络层通信的线程池
	 */
	private final Executor	generalPacketsThreadPool;
	
	
	public static ThreadNetWorkPoolManager getInstance()
	{
		return SingletonHolder.instance;
	}
	
	
	private ThreadNetWorkPoolManager()
	{
		generalPacketsThreadPool = Executors.newCachedThreadPool();
	}

	@Override
	public void execute(Runnable command) 
	{
		generalPacketsThreadPool.execute(command);
	}
	
	
	@SuppressWarnings("synthetic-access")
	private static class SingletonHolder
	{
		protected static final ThreadNetWorkPoolManager	instance	= new ThreadNetWorkPoolManager();
	}
}
