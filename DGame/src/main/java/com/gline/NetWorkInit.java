package com.gline;

import org.apache.log4j.Logger;

import com.gline.controller.LoadController;

import netBase.Manager.ModuleManager;
import netBase.thread.NetWorkerThread;
import netWork.resolve.send.ListSceneEnitySendable;
import netWork.resolve.send.gunlistSendable;
import netWork.resolve.send.listGoodsSendable;
import netWork.resolve.send.listJJC;

/**
 *  * 网络层初始化
 * @author 	djl
 * @date	2012-12-24
 */
public class NetWorkInit
{
	private static final Logger log = Logger.getLogger(NetWorkInit.class);
	private NetWorkInit(){}
	private final static NetWorkInit init = new NetWorkInit();	
	public static NetWorkInit getInstance()
	{
		return init;
	}
	
	
	public void init() throws Exception
	{
		log.info("--------->>>>>>>>>initController");
		//LoadController.load();
		log.info("--------->>>>>>>>>initResolve");
		initResolve();
		
/*		initController();
		log.info(">>>>>>>>>>>>>>>>>>初始化控制器");
		initResolve();
		log.info(">>>>>>>>>>>>>>>>>>初始化命令解析");
		//初始化服务项
		NettyServerInit.serverInit(NetWorkConfig.ADDRESS_SERVER_GAME, new ServerPipelineFactory());
		log.info(">>>>>>>>>>>>>>>>>>初始化网络层服务");*/
	
	}
	
	
	public listGoodsSendable  mlistGoodsSendable;
	public gunlistSendable  mgunlistSendable;
	public ListSceneEnitySendable mListSceneEnitySendable;	
	public listJJC  mpublic;
	
	/**
	 * 注册协议解析
	 * 所有单独的特殊结构的解析，都注册在这里
	 */
	public void initResolve()
	{
		mlistGoodsSendable = new listGoodsSendable();
		mgunlistSendable = new gunlistSendable();
		mListSceneEnitySendable = new ListSceneEnitySendable();
		mpublic = new listJJC();
		log.info("--------->>>>>>>>>init--------Resolve");
		//recieve
		//BufferManager.getInstance().registerReceivalbe("listInteger", new ListPlayerReceivable());
		//BufferManager.getInstance().registerReceivalbe("listnode", new ListNodeReceivable());
		//BufferManager.getInstance().registerReceivalbe("listpath", new ListPathReceivable());
		
		//send
		//BufferManager.getInstance().registerSendable("listGoods", new listGoodsSendable());

	}
	
	
	public void shutDown()
	{
		ModuleManager.getInstance().shutdown();
	}
}