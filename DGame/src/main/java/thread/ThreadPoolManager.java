package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

/**
 * ��Ϸ�߼�����ʹ�õ��̳߳أ�ͨ�Ų�ʹ�������̳߳أ�
 * @author ������ 
 * @date 2013-1-11
 */
public class ThreadPoolManager {

	private static final Logger log = Logger.getLogger(ThreadPoolManager.class);
	
	private  ThreadPoolManager(){}
	private final static ThreadPoolManager singtol = new ThreadPoolManager();
	public static ThreadPoolManager getInstance()
	{
		return singtol;
	}
	
	
	/**
	 *1.ָ������ӵ����������ִ���ӳ�������̳߳�
	 */
	private final static  ExecutorService schedulepool = Executors.newScheduledThreadPool(6);
	
	/**
	 *2.�̳߳أ����л��湦��
	 */
	private final static  ExecutorService cachePool = Executors.newCachedThreadPool();
	
	/**
	 *3.ָ���������̳߳�
	 */
	private final static  ExecutorService fixPool = Executors.newFixedThreadPool(6);
	
	/**
	 *4.�������̳߳�
	 */
	private final static  ExecutorService singlePool = Executors.newSingleThreadExecutor();
	
	/**
	 *�ر��̳߳�
	 */
	public void shutdown()
	{
		cachePool.shutdown();
		schedulepool.shutdown();
		fixPool.shutdown();
		singlePool.shutdown();
		log.info("线程池关闭");
	}
	
	
	public ExecutorService getCachepool() {
		return cachePool;
	}

	public  ExecutorService getFixpool() {
		return fixPool;
	}

	public  ExecutorService getSinglepool() {
		return singlePool;
	}
	
	public  ExecutorService getSchedulepool() {
		return schedulepool;
	}


}
