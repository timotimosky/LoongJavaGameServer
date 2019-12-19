package com.gline.controller.ALL;


import org.apache.log4j.Logger;

import com.gline.controller.ControllerModel;
import netBase.contrlBase.ClassAnn;
import netBase.contrlBase.ControllerAnnotation;
import com.gline.message.PlayerMessage;

import netBase.util.GameClient;

/**
 * 玩家控制器：必须用static方法
 * @author timoSky
 * @Data 2013-3-8
 */
@ClassAnn(key = ControllerModel.PLAYERMODEL)
public class PlayerController {
	
	private static final Logger log = Logger.getLogger(PlayerController.class);

	
	/**
	 * 测试命令
	 * @param client			玩家连接
	 * @param countId			字符串数据测试
	 * @param i					Int型数据测试
	 * @param l					Long型数据测试
	 * @param b					Byte型数据测试
	 * @param s					Short型数据测试
	 * @param f					Float型数据测试
	 * @param d					Double型数据测试
	 * @param list				List型数据测试
	 * @create 2012-10-12 djl
	 */
	@ControllerAnnotation(key = 111111)
	public static void playerTest(GameClient client,String countId, int i, long l, byte b, short s, float f, double d)
	{
		PlayerMessage.GC_TEST(client, countId, i, l, b, s, f, d);
	}
	
	/**
	 * 进入游戏后获取数据命令（背包，好友，身上装备）
	 * @param client	一个连接
	 */
/*	@ControllerAnnotation(key = PlayerProxy.CG_REQUEST_DATA)
	public static void enterGameSData(GameClient client)
	{
		进入游戏后获取数据命令 修改命令 
		PlayerLogic.newInstance().enterGameSData(client.getPlayer());
	
	}*/
	
	
}
