package com.gline.controller.ALL;

import org.apache.log4j.Logger;

import com.gline.controller.ControllerModel;
import netBase.contrlBase.ClassAnn;
import netBase.contrlBase.ControllerAnnotation;
import com.gline.message.PlayerMessage;
import com.gline.proxy.UtilProxy;

import netBase.util.GameClient;

/**
 * UTILMODEL控制器
 * @author djl
 * @date 20150429
 */
@ClassAnn(key = ControllerModel.UTILMODEL)
public class ScoketController {

	private static final Logger log = Logger.getLogger(SceneController.class);

	/**
	 * 进入场景,用于客户单请求进入某场景，只返回场景相关。   如果进入战斗场景以后，则需要再调用战斗初始化，初始化队友和敌人信息
	 * @param client 玩家连接
	 */
	@ControllerAnnotation(key = UtilProxy.CG_HEART_BEAT)
	public static void GC_HEART_BEAT(GameClient client)
	{	
		PlayerMessage.GC_HEART_BEAT(client, System.currentTimeMillis());
	}

}
