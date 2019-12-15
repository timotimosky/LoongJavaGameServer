package com.gline.module.load;

import org.apache.log4j.varia.StringMatchFilter;

import com.gline.ai.RoleAbstracts;
import com.gline.module.count.CountEnity;

import netBase.util.GameClient;
import util.AutoRandomID;


/**
 * 实体
 * @author djl
 * @date 20150430
 */
public class LoadAwardEntity  {
		
	public LoadAwardEntity()
	{		
		id =100;
		icon = "test";
		award = "test";
	}	
	
	public int id;
	
	public int totalDay;
	
	/**
	 * 夺宝卡״̬
	 */
	public String  award;
    
	/**
	 * 状态״̬
	 */
	public String  icon;		
		
	

}
