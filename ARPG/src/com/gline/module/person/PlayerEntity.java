package com.gline.module.person;


import java.util.ArrayList;
import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.impl.PublicImpl;

import com.gline.ai.RoleAbstracts;
import com.gline.module.count.CountEnity;

import netBase.util.GameClient;
import util.AutoRandomID;


/**
 * 玩家实体
 * @author djl
 * @date 20150430
 */
public class PlayerEntity  extends RoleAbstracts {

	private static final long serialVersionUID = 1L;	
	
	public PlayerEntity()
	{		
		pid = AutoRandomID.randomRoleArticleId();
		gold =5000;
		Rmb = 0;
		exp= 100;
		card = 0;
		name = "游客";
		level = 1;
		ItemList = new ArrayList<>();
		costGold = 0;
		//每个玩家默认给一个最低端的炮
		gunType = 1;
		allGold =0;
		//倍率默认是10， 玩家每次+ -倍率，要向服务端请求 ，或者每次开炮，向服务端请求
		gunLv =10;
		towerId = 1;
		GunList = new ArrayList<>(); 
		
		//初始火炮
		GunList.add(1);
		
		score = 0;
	}	
	
	public long pid;
	
	public transient CountEnity mCountEnity;
	
	public List<Integer> GunList; //玩家拥有的火炮类型
	
	public List<Integer> ItemList; //玩家拥有的道具列表
	
	//玩家目前使用的炮塔ID
	public byte towerId;
	
	//玩家目前使用的炮塔倍率
	public short gunLv;
	
	//玩家目前装备的炮塔Type
	public short gunType;
	
	
	//记录玩家在当前场景所获得的金币,用于JJC..
	public int score;
	
	/**
	 * 夺宝卡״̬
	 */
	public int  card;
    
	//目前战斗已花费金币
	public int  costGold;
	
	public int  allGold;//累计获得金币
	
	/**
	 * 状态״̬
	 */
	public boolean  state;
	
	
	/**
	 * 当前经验值
	 */
	public int exp;
	
	/**
	 * 金币
	 */
	public int gold;
	
	/**
	 * 已充值的RMB
	 */
	public int AllRmb;
	
	/**
	 * 当前拥有未消费的
	 */
	public int Rmb;
	
		
	/**
	 * 上一次上线时间
	 */
	public long lastOnTime;
	
	/**
	 * 上一次下线时间
	 */
	public long lastDownTime;
	
	/**
	 * 创建号的时间
	 */
	public long createTime;
	
	/**
	 * 在线时长
	 */
	public long onlineTime;

	/**
	 * 链接
	 */
	public  transient GameClient client;

	/**
	 * 上一次缓存的时间
	 */
	public int cachetime = 0;
		
	

}
