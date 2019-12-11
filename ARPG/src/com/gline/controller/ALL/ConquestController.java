package com.gline.controller.ALL;

import org.apache.log4j.Logger;

import com.gline.controller.ControllerModel;
import com.gline.controller.contrlBase.ClassAnn;
import com.gline.controller.contrlBase.ControllerAnnotation;

import netBase.util.GameClient;


/**
 * 匹配控制器：必须用static方法
 * @author timoSky
 * @Data 2015-3-8
 */
@ClassAnn(key = ControllerModel.ConquestMODEL)
public class ConquestController {
	
	private static final Logger log = Logger.getLogger(ConquestController.class);
	
	/**
	 *  匹配
	 */
	@ControllerAnnotation(key = 6002)
	public static void TankerLoad(GameClient client, String name, int type)throws Exception
	{				
		//ConquestMessage.GC_CONQUEST_INFO(client, 1, 2, 3);
		log.info("匹配");
	}  
}
