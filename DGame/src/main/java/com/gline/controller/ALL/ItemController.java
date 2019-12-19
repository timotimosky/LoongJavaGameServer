package com.gline.controller.ALL;

import org.apache.log4j.Logger;

import com.gline.controller.ControllerModel;
import netBase.contrlBase.ClassAnn;
import netBase.contrlBase.ControllerAnnotation;
import com.gline.module.box.BoxLogic;
import com.gline.proxy.ItemProxy;

import netBase.util.GameClient;

/**
 * 玩家控制器：必须用static方法
 * @author timoSky
 * @Data 2013-3-8
 */
@ClassAnn(key = ControllerModel.ITEMMODEL)
public class ItemController {
	private static final Logger log = Logger.getLogger(ItemController.class);
	
	/**
	 * 开启宝箱
	 */
	@ControllerAnnotation(key = ItemProxy.CG_OpenBox)
	public static void OpenBox(GameClient client, int boxType)throws Exception
	{
		BoxLogic.newInstance().OpenBox(client, boxType);
	}
}
