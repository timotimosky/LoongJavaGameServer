package com.gline.controller.ALL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.gline.GameManager;
import com.gline.controller.ControllerModel;
import com.gline.controller.contrlBase.ClassAnn;
import com.gline.controller.contrlBase.ControllerAnnotation;
import com.gline.message.SceneMessage;
import com.gline.module.fish.FishEnity;
import com.gline.module.person.PlayerEntity;
import com.gline.module.scene.JJC;
import com.gline.module.scene.ScenceEntity;
import com.gline.module.scene.SceneLogic;
import com.gline.proxy.SceneProxy;

import netBase.util.GameClient;

/**
 * 场景控制器  每个房间最多四个人。 找不到空余房间，则自动创建一个房间
 * @author djl
 * @date 20150429
 */
@ClassAnn(key = ControllerModel.SCENEMODEL)
public class SceneController {

	private static final Logger log = Logger.getLogger(SceneController.class);
	
	
	@ControllerAnnotation(key = SceneProxy.CG_CreateJJc)
	public static void CG_CreateJJc(GameClient client,String Name,String password,int price)
	{	
		int error = 0;
		//查询，场景的玩家
		PlayerEntity mplayer = client.getPlayer();	
		
		if(mplayer.gold < price)
		{
			log.info("金币不够1000  不能进入JJC房");
			error = 1;
			SceneMessage.GC_CreateJJc(client,error);	
			return;
		}	
		
		
		ScenceEntity mScenceEntity = new ScenceEntity();
		
		//String args,int price,String Name
		mScenceEntity.Name =Name  ;
		mScenceEntity.password = password;
		mScenceEntity.price = price;
		
		GameManager.JJCScenceMap.put(mScenceEntity.Entityid, mScenceEntity);
	
		mplayer.gold -= price;
		SceneMessage.GC_CreateJJc(client,error);
	}
	
	@ControllerAnnotation(key = SceneProxy.CG_GetAllJJC)
	public static void CG_GetAllJJC(GameClient client)
	{			
		ConcurrentHashMap<Long ,ScenceEntity> tJJCScenceMap  = GameManager.JJCScenceMap;
		List<JJC> list =  new ArrayList<JJC>();
		//判断是否有空房间
		for(Entry<Long , ScenceEntity> entry :tJJCScenceMap.entrySet())
		{
			ScenceEntity tEntity = entry.getValue();
			JJC mJjc = new JJC(tEntity.Name,tEntity.price,tEntity.Entityid);
			list.add(mJjc);
		}	
		SceneMessage.GC_GetAllJJC(client,list);
	}
	
	
	/**
	 * 进入场景,用于客户单请求进入某场景，只返回场景相关。   如果进入战斗场景以后，则需要再调用战斗初始化，初始化队友和敌人信息
	 * 传1,代表普通场 2代表开VIP房 3代表万炮厂  4 JJC
	 * @param client 玩家连接
	 */
	@ControllerAnnotation(key = SceneProxy.CG_EnterScene)
	public static void GC_EnterSence(GameClient client,int SenceType,String args,long id)
	{	
		int error = 0;
		long  newSencePID = 0;
		byte towerId = 0; //玩家占据的炮塔位置
		//查询，场景的玩家
		PlayerEntity mplayer = client.getPlayer();	
		
		ConcurrentHashMap<Long ,ScenceEntity> mScence;
		
		int MaxPlayer = SceneLogic.MaxPlayerInScence;
		
		if(SenceType==1)
			mScence =   GameManager.ScenceEntityMap;
		else if(SenceType==2)
		{
			if(mplayer.gold <10000)
			{
				log.info("金币不够1W 不能进入VIP房");
				error = 1;
				SceneMessage.GC_EnterSence(client,new ArrayList<FishEnity>(),0,error);	
				return;
			}			
			mScence =  GameManager.VipScenceMap;
		}
		else if(SenceType==3)
		{
			if(mplayer.Rmb <1000)
			{
				log.info("充值不够1000  不能进入万炮房");
				error = 1;
				SceneMessage.GC_EnterSence(client,new ArrayList<FishEnity>(),0,error);	
				return;
			}						
			mScence =  GameManager.BigScenceMap;
		}
		else if(SenceType==4)
		{
			mScence =  GameManager.JJCScenceMap;
			//查询是否有这个JJC
			if(!mScence.containsKey(id))
			{
				return;
			}
			
			ScenceEntity mScenceEntity = mScence.get(id);
			//查询该JJC的门票
			
			if(mplayer.gold < mScenceEntity.price)
			{
				log.info("金币不够  不能进入JJC房");
				error = 1;
				SceneMessage.GC_EnterSence(client,new ArrayList<FishEnity>(),0,error);	
				return;
			}	
			
			if (mScenceEntity.PlayerMap.size() >= MaxPlayer)
			{
				log.info("人数满了  不能进入JJC房");
				error = 2;
				SceneMessage.GC_EnterSence(client,new ArrayList<FishEnity>(),0,error);	
				return;
			}					
			
			MaxPlayer = SceneLogic.MaxPlayerInJJCScence;
			for(int i=0; i<MaxPlayer; i++)
				{
					if (!mScenceEntity.PlayerMap.containsKey((byte)i)) 
					{
						log.info("获得炮台位置 towerId=="+i);						
						towerId = (byte)i;
						break;
					}						
				}	
		
			SceneMessage.GC_EnterSence(client,new ArrayList<FishEnity>(),0,error);	
			return;
		}
		else
		{
			log.info("场景类型不存在  "+SenceType);
			error = 2;
			SceneMessage.GC_EnterSence(client,new ArrayList<FishEnity>(),0,error);	
			return;
		}	


		log.info("目前房间数,="+mScence.size());
	
		if(mScence.isEmpty() || mScence.size()==0)
		{
			ScenceEntity mScenceEntity = new ScenceEntity();
			mScence.put(mScenceEntity.Entityid, mScenceEntity);
			newSencePID = mScenceEntity.Entityid;
			log.info("目前没有房间,创建"+newSencePID);
		}
		else
		{
			//判断是否有空房间
			for(Entry<Long , ScenceEntity> entry :mScence.entrySet())
			{
				log.info("entry.getValue().PlayerMap.size()=="+entry.getValue().PlayerMap.size());
				ConcurrentHashMap<Byte,PlayerEntity> PlayerMap = entry.getValue().PlayerMap;
				
				if (PlayerMap.size() < MaxPlayer)
				{
					for(int i=0; i<MaxPlayer; i++)
					{
						if (!PlayerMap.containsKey((byte)i)) 
						{
							log.info("获得炮台位置 towerId=="+i);
							newSencePID = entry.getKey();	
							towerId = (byte)i;
							break;
						}						
					}
				}			
			}
		}
		
		mplayer.towerId = towerId;
					
		//创建一个新房间
		if (newSencePID == 0) 
		{		
			ScenceEntity mScenceEntity = new ScenceEntity();
			mScence.put(mScenceEntity.Entityid, mScenceEntity);
			newSencePID = mScenceEntity.Entityid;			
			//玩家进入场景初始化
			InitFight(client,mScenceEntity,mplayer.towerId);
			log.info("房间已满,创建" + newSencePID);
		}
		else //进入已有房间
		{		
			ScenceEntity newScence = mScence.get(newSencePID);
			
			//玩家进入场景初始化
			InitFight(client,newScence,mplayer.towerId);
			
			for (Entry<Byte, PlayerEntity> entry : newScence.PlayerMap.entrySet())
			{
				//告诉新进入
				PlayerEntity nplayer = entry.getValue();
				if (nplayer != null && nplayer.pid != mplayer.pid) 
				{
					SceneMessage.GC_PlAYER_ENTER(nplayer.client,  mplayer.pid, mplayer.gunType, mplayer.name,mplayer.towerId,mplayer.gold);
					SceneMessage.GC_PlAYER_ENTER(client, nplayer.pid,nplayer.gunType,nplayer.name,nplayer.towerId,nplayer.gold);
					log.info("其他玩家 id="+nplayer.pid);
				}
			}
		}	

		mScence.get(newSencePID).PlayerMap.put(mplayer.towerId, mplayer);		
		mplayer.currMapID = newSencePID;
		log.info("玩家数量"+mScence.get(newSencePID).PlayerMap.size() +"  炮塔位置"+towerId);

	}
	
	
	
	
	public static void InitFight(GameClient client,ScenceEntity newScence,int towerid)
	{		
		List<FishEnity> mlist = new ArrayList<>();
		//鱼儿list
		for (Entry<Long, FishEnity> entry : newScence.FishEnityMap.entrySet()) 
		{
			mlist.add(entry.getValue());
		}

		SceneMessage.GC_EnterSence(client,mlist,towerid,0);		
	}
	
	
	/**
	 * 请求:自己离开场景
	 */
	@ControllerAnnotation(key = SceneProxy.CG_SelfLeaveSence)
	public static void sceneLeave(GameClient client) {	
		log.error("玩家请求:自己离开场景");
		SceneLogic.sceneLeave(client);	
		//通知自己
		SceneMessage.GC_SelfLeaveSence(client);
	}

	
	//@ControllerAnnotation(key = SceneProxy.CG_DELETE_Obj)
	public static void CG_DELETE_Obj(GameClient clien,String  ObjeNAME)
	{
		//SceneMessage.GC_DELETE_Obj(clien, ObjeNAME);
		log.info("离开场景 。。。广播...名称 " + ObjeNAME);
	}
}


