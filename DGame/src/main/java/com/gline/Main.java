package com.gline;
import org.apache.log4j.Logger;

import com.gline.Excel.base.ExcelManager;
import com.gline.log.LogFactory;

import engine.mongodb.test;
import netBase.netty.NettyServer;
import netBase.thread.NetWorkerThread;
import thread.ThreadUncaughtExceptionHandler;

/**
 * @author djl
 * @date 2012-12-19
 * 载入的lib包中： poi-ooxml-3.8-beta3-20110606 用于创建及读取excel数据
 */
public class Main {

	public static Logger log = Logger.getLogger(Main.class);
	
	public static void main(String[] args) throws Exception 
	{      
		try 
		{     

			Thread.setDefaultUncaughtExceptionHandler(new ThreadUncaughtExceptionHandler());
			
			LogFactory.init();
				
			// long nano = 1000000000;//(1秒)
			 //float nanoOff = 0.000000001f;//(1秒)
			
			//log.info("nano========"+nano*nanoOff);
			
			//log.info("test EXCEL");
			ExcelManager.ReadAllExcel();
			
			log.info("--------->>>>>>>>>Init GameServer");
		    GameServerInit.getInstance().init();	

		    log.info("--------->>>>>>>>>Init NetWork");
			NetWorkInit.getInstance().init();	
			
			 log.info("--------->>>>>>>>>HttpServer");
			//HttpServer server = new HttpServer();
			//log.info("Http Server listening on 8844 ...");
		   // server.init(8844);
			
			log.info("--------->>>>>>>>>Test Mongodb");
			test.testPut();
			test.testSelect();
			//new NpcController().trains(1);
			
			//log.info("test XML data  LV_DATAΪ"+DataManager.LV_DATA.get(1).getLvExp());
		
			System.setProperty("user.timezone", "GMT +08"); 
			log.info("--------->>>>>>>>>set timezone GMT +08");		
			
			log.info("--------->>>>>>>>>Game Start!"); 
			NettyServer.start(10045);
			log.info("--------->>>>>>>>>22222222222222222222"); 
			new NetWorkerThread().run();
			log.info("--------->>>>>>>>>3333333333333333333333333"); 
			    
		} 
		catch (Exception e) 
		{           
			log.info("exception happens",e);         
			System.exit(-1);       
		}  
	}

}

