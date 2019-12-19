package com.gline.controller.ALL;

import org.apache.log4j.Logger;

import com.gline.controller.ControllerModel;
import netBase.contrlBase.ClassAnn;
import netBase.contrlBase.ControllerAnnotation;
import com.gline.module.count.CountLogic;
import com.gline.module.person.PlayerLogic;
import com.gline.proxy.LoginProxy;

import netBase.util.GameClient;

/**
 * 登陆控制器
 * @author timoSky
 * @Data 2015-4-28
 */
@ClassAnn(key = ControllerModel.LOGINMODEL)
public class LoginController {
	
	private static final Logger log = Logger.getLogger(LoginController.class);
	
	
	/**
	 * 快速登录/游客登录
	 */
	@ControllerAnnotation(key = LoginProxy.CG_Create)
	public static void CG_Create(GameClient client, String mac)throws Exception
	{			
		CountLogic.newInstance().loadCount(client, mac);	
	} 
	
	/**
	 * 玩家账号注册
	 */
	@ControllerAnnotation(key = LoginProxy.CG_CreatebyCount)
	public static void CG_CreatebyCount(GameClient client, String count, String password)throws Exception
	{		
		log.info("玩家账号注册：账号"+count+"密码"+password);
		
		CountLogic.newInstance().CG_CreatebyCount(client, count, password);	
	}
	
	/**
	 * 玩家账号登陆
	 */
	@ControllerAnnotation(key = LoginProxy.CG_LOGIN)
	public static void playerLoad(GameClient client, String count, String password)throws Exception
	{		
		log.info("登陆控制器----玩家登陆：账号"+count+"密码"+password);
		
		CountLogic.newInstance().loadCount(client, count, password);	
	}  
	
	/**
	 * 请求获得每日奖励
	 */
	@ControllerAnnotation(key = LoginProxy.CG_GetLoadAward)
	public static void CG_GetLoadAward(GameClient client)throws Exception
	{		
		log.info("登陆控制器----请求获得每日奖励");
		
		client.getPlayer().gold += 10000;
		PlayerLogic.newInstance().GC_GetLoadAward(client,10000);
	} 
	
	/**
	 * 绑定手机号码
	 */
	@ControllerAnnotation(key = LoginProxy.CG_BindPhoneNumber)
	public static void playerLoad(GameClient client, String PhoneNumber, int code)throws Exception
	{		
		log.info("登陆控制器----手机号"+PhoneNumber+"验证码"+code);
		
		CountLogic.newInstance().CG_BindPhoneNumber(client, PhoneNumber, code);	
	}  

	
}
