package com.gline.controller.ALL;

import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.gline.GameManager;
import com.gline.controller.ControllerModel;
import netBase.contrlBase.ClassAnn;
import netBase.contrlBase.ControllerAnnotation;
import com.gline.message.FightMessage;
import com.gline.module.fish.FishEnity;
import com.gline.module.person.PlayerEntity;
import com.gline.module.scene.ScenceEntity;
import com.gline.proxy.FightProxy;

import engine.mongodb.PlayerDao;
import netBase.World;
import netBase.util.GameClient;

/**
 * 战斗控制器 
 * @author djl
 * @date 20150429
 */
@ClassAnn(key = ControllerModel.FIGHTMODEL)
public class FightController {

	private static final Logger log = Logger.getLogger(FightController.class);
	
	 @ControllerAnnotation(key = FightProxy.CG_ChangeGun)
	public static void CG_ChangeGun(GameClient client, short newGunId) 
	 {

		 //是否已拥有该火炮
		PlayerEntity mplayer = client.getPlayer();
		
		//TODO:contains方法本身有问题
		if(!mplayer.GunList.contains((int)newGunId))
		{
/*			for(Integer eInteger :mplayer.GunList)
			{
				log.error("玩家有这类火炮"+eInteger);
			}*/
			
			log.error("玩家没有这类火炮"+newGunId);
			FightMessage.GC_ChangeGun(client, (byte)-1);
			return;
		}
		mplayer.gunType = newGunId;
		FightMessage.GC_ChangeGun(client, (byte)0);
	 }
	
	
	//进战斗初始化
	 @ControllerAnnotation(key = FightProxy.CG_FIGHT_INIT)
	public static void GC_FIGHT_INIT(GameClient client, int mapModelId ,int fightType,float x, float y) {

		PlayerEntity mplayer = client.getPlayer();
		
		//告诉玩家，当前场景内玩家、当前场景内目前鱼儿。
		
		
		//TODO：判断是否可以进入这个原型ID		
		//TODO:根据原型ID，获取地图配置	
		//TODO：由客户端分配场景，每个场景最多有10个玩家，如果超出，则建立新场景。
		
		/*ScenceEntity newScence =  GameManager.ScenceEntityMap.get(mapModelId);		
		
		mplayer.setCurrMapID(newScence.getId());*/
		
		//场景初始化
		//SceneMessage.GC_FIGHT_INIT(client, newScence.getId(),mapModelId, 1, 1005, 1006);

		//加入场景 
		//GameManager.ScenceEntityMap.put(mapModelId, newScence);
	}
	
	//发射炮弹
	@ControllerAnnotation(key = FightProxy.CG_Shot)
	public static void CG_Shot(GameClient client ,int type,float dirX, float dirY) 
	{
		
		PlayerEntity mplayer = client.getPlayer();	
		//广播
		ScenceEntity newScence = GameManager.GetSence(mplayer.currMapID);
		
		if(newScence.PlayerMap.size()<=0)
		{
			log.error("异常! 场景中没有玩家"+mplayer.pid);
			return;
		}
		
		
		for (Entry<Byte, PlayerEntity> entry : newScence.PlayerMap.entrySet())
		{
			PlayerEntity nplayer = entry.getValue();
			if (nplayer != null && nplayer.pid != mplayer.pid) 
			{
				FightMessage.GC_Shot(nplayer.client,  type, dirX,  dirY, mplayer.pid);
				//log.info("发射炮弹 广播场景，其他玩家 id="+nplayer.pid);
			}
		}
	}
	 
	
	//被攻击者类型，0为玩家，1为物体，2为怪物
	@ControllerAnnotation(key = FightProxy.CG_HitFish)
	public static void CG_HitFish(GameClient client, long targetId,short gunType) {
		
		PlayerEntity clientPlayerEnity = client.getPlayer();
		
		//获取玩家所在场景
		long mapID = clientPlayerEnity.currMapID;
		ScenceEntity thisScenceEntity = GameManager.GetSence(mapID);
		
		if(thisScenceEntity==null)
		{
			log.warn("找不到该场景，ID为"+targetId);
			return;
		}
		
		FishEnity targetRoleAbstracts = thisScenceEntity.FishEnityMap.get(targetId);		
		if(targetRoleAbstracts == null)
		{		
			log.warn("找不到该实体，ID为"+targetId);
			return;
		}
		
		clientPlayerEnity.gunLv = gunType;
		
		if(clientPlayerEnity.gold  < clientPlayerEnity.gunLv)
		{
			log.warn("金币不足");
			return;
		}
			
		//扣除炮弹金币
		clientPlayerEnity.gold  -= clientPlayerEnity.gunLv;
		clientPlayerEnity.costGold += clientPlayerEnity.gunLv;
		
		// 计算死亡概率	
		int dead = targetRoleAbstracts.mFishType.catchRate;		
		Random ra =new Random();
		int nowDead = ra.nextInt(1000000);		
		if(nowDead>dead)
		{		
			//log.info("鱼不死亡 nowDead="+nowDead+" dead="+dead);
			//保存数据库
			PlayerDao.Update(clientPlayerEnity);
			return;
		}
		else {
			//log.info("鱼死亡 nowDead="+nowDead+" dead="+dead);
		}
				
		//log.info("鱼死亡 FishID="+targetRoleAbstracts.mFishType.type +"金币掉落赔率=="+targetRoleAbstracts.mFishType.goldDrop);
		
		//根据目前所用子弹的倍数 X 该鱼的倍数, 即获得金币
		int GetGlod = targetRoleAbstracts.mFishType.goldDrop*clientPlayerEnity.gunLv;
		
		int card =0;
		
		//夺宝卡数量: 满5W金币, 获得夺宝卡数量为100之30概率, 满5W5 100之60, 满6W 必得一张.
		if(clientPlayerEnity.costGold > 60000) //一定获得
		{
			card = 1;
		}
		else if(clientPlayerEnity.costGold > 55000)
		{
			int cardRomdom = ra.nextInt(100);
			if(cardRomdom<=60)
			{
				card = 1;
			}
			
		}
		else if(clientPlayerEnity.costGold > 50000)
		{
			int cardRomdom = ra.nextInt(100);
			if(cardRomdom<=30)
			{
				card = 1;
			}
		}
		
		//如果获得夺宝卡，则costGlod清0
		if(card>0)
		{
			clientPlayerEnity.costGold =0;
		}		
		
		//如果鱼死亡,通知场景内所有玩家   TODO:其实这里鱼也不一定同步了,捕鱼可能做的假同步, 大家分别打自己的鱼...
		for (Entry<Byte, PlayerEntity> entry : thisScenceEntity.PlayerMap.entrySet()) 
		{
			PlayerEntity nplayer = entry.getValue();
			if (nplayer != null) 
			{
				FightMessage.GC_FishDead(nplayer.client, targetId, GetGlod, card,clientPlayerEnity.pid);
				
			}
		}
		
		//是否是JJC 
		//if(JJCScenceMap)
		
		
		//调用鱼死亡的删除   因为存在ID回收,所以先通知客户端,再回收ID,避免客户端出现重复ID
		thisScenceEntity.mScenceAI.FishDie(targetId);		
		//玩家金币/夺宝卡保存  玩家本场已花费金币保存
		clientPlayerEnity.gold += GetGlod;
		clientPlayerEnity.card += card; 
		
		clientPlayerEnity.allGold += GetGlod;
		//log.info("鱼死亡 targetId=="+targetId);
		//保存数据库
		PlayerDao.Update(clientPlayerEnity);
	}

	public static GameClient getPlayerById(long targetId) {

		// TODO：以后单独建立PID管理map，现在先从mMap里获取
		ConcurrentHashMap<Integer, GameClient> mMap = World.newInstance()
				.getChannelMap();
		for (Entry<Integer, GameClient> entry : mMap.entrySet()) {

			GameClient m = entry.getValue();
			log.info("m.getPlayer().getPId()玩家ID为" + m.getPlayer().pid);
			/*if (m.getPlayer().getRoleId() == targetId) {
				return m;
			}*/

		}

		log.info("找不到该玩家，玩家ID为" + targetId);
		return null;
	}

	
}
