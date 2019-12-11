package com.gline.controller.ALL;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.gline.message.SceneMessage;
import com.gline.module.npc.entity.NpcBaseEntity;
import com.gline.module.npc.entity.NpcEntity;

import engine.mybatis.dao.NpcDaoService;
import netBase.World;
import netBase.util.GameClient;

/**
 * NPC控制器
 * @author DaiJinLong
 */
public class NpcController{	
	
	private static final Logger log = Logger.getLogger(NpcController.class);

	public void trains(int id) throws Exception
	{		
		NpcEntity n  = new NpcDaoService().get(id);
		log.info("测试数据库："+"newid为"+n.newid);
		
		log.info("测试数据库："+"npcid为"+n.getNpc_id());
	}
	
	//刷新怪物，这里应该赋临时ID，然后加入map
	public static void AddObj(NpcBaseEntity mNpc)
	{
		ConcurrentHashMap<Integer, GameClient> mMap  = World.newInstance().getChannelMap();
		GameClient nclient;
		for (Entry<Integer, GameClient> entry : mMap.entrySet()) {

			nclient = (GameClient) entry.getValue();
			SceneMessage.GC_ADD_Obj(nclient,  mNpc.AssetName, mNpc.getInitHp());
		}
	}
	


}
