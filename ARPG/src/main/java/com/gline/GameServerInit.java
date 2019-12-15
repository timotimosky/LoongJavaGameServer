package com.gline;

import org.apache.log4j.Logger;

import task.HeartBeatTask;
import task.SceneTask;
import thread.ThreadPoolManager;


/**
 * 线程管理中心
 * @author timosky
 * @date 2012-12-18
 */
public class GameServerInit  {

	public static Logger log = Logger.getLogger(GameServerInit.class);

	private final static GameServerInit gameInit = new GameServerInit();
	private GameServerInit(){}
	public  static GameServerInit getInstance(){
		return  gameInit;
	}
	
	public void init() throws Exception
	{ 
		//log.info("load XML");
		//DataHolderProcessor.process(DataManager.class);	
		//boos
		//NpcBaseEntity boos = new NpcBaseEntity();
		
		//NpcStore.npcMap.put("boos", boos);*/
			
		
		//后期优化：将所有定时任务合并，放入一个线程处理
		ThreadPoolManager.getInstance().getCachepool().submit(new HeartBeatTask());
		ThreadPoolManager.getInstance().getCachepool().submit(new SceneTask());
		
		
		//new HeartBeatTask().init();
		//new SceneTask().init();
	
		//AI此处有内存泄露?
		//ThreadPoolManager.getInstance().getFixpool().submit(new AITask());
		log.info("加载线程");
	}
	
	
	public void shutDown()
	{		
		ThreadPoolManager.getInstance().shutdown();
	}
	
	

}


