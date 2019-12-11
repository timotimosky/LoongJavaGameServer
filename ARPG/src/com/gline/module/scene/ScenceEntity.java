package com.gline.module.scene;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.gline.GameManager;
import com.gline.ai.RoleAbstracts;
import com.gline.module.fish.FishEnity;
import com.gline.module.person.PlayerEntity;

import util.AutoRandomID;

/**
 * 场景实体
 *
 */
public class ScenceEntity implements Serializable , Cloneable{
	
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ScenceEntity.class);
	
	public ScenceAI mScenceAI;
	
	//实例ID
	public long Entityid;
	public String Name;
	public int price;
	
	public String password;//密码
	
	// 鱼上一次刷新的时间
	public long UpdateTime;
	// 鱼上一次移动刷新的时间
	public long MoveUpdateTime;
	
	
	public ScenceEntity()
	{
		Entityid = GameManager.mIDList.nextId();
				//AutoRandomID.randomRoleArticleId();
		FishEnityMap = new ConcurrentHashMap<Long, FishEnity>();
		//EnityMap = new ConcurrentHashMap<Long,RoleAbstracts>();
		UpdateTime = System.nanoTime();
		MoveUpdateTime = System.nanoTime();
		mScenceAI = new ScenceAI(this);
	}
	
	public void Clear()
	{
		//回收ID
		//for(long id : FishEnityMap.keySet())
		//{
			//GameManager.mIDList.recoverId((int)id);
		//}
		
		mScenceAI.Clear();
		FishEnityMap.clear();
		//EnityMap.clear();
		PlayerMap.clear();
		
	}
	
	
	/**
	 * 房间类型
	 */
	public int mapType;
	
	public ConcurrentHashMap<Long, FishEnity> FishEnityMap = new ConcurrentHashMap<Long, FishEnity>();
	
	/**
	 * 所有场景实体的map
	 */
	//public  ConcurrentHashMap<Long,RoleAbstracts> EnityMap = new ConcurrentHashMap<Long,RoleAbstracts>();
	
	/**
	 * ROLES 玩家实体  如果是保存玩家id，则不涉及多线程。  每次去从世界地图的ConcurrentHashMap取值
	 * 如果保存PlayerEntity，可能其他线程会改动PlayerEntity的值，导致取出的值不对。
	 * 应该只在世界地图维护一个 ConcurrentHashMap的 PlayerEntity，只在这里触发多线程操作。
	 */
	//private List<Integer> roles = new ArrayList<Integer>();
	
	//玩家实体  炮塔位置0,1,2,3  对 PlayerEntity
	public  ConcurrentHashMap<Byte,PlayerEntity> PlayerMap = new ConcurrentHashMap<Byte,PlayerEntity>();
		
	
	//场景的帧更新.实现帧同步
	public void update()
	{
		//if (System.nanoTime() - UpdateTime > mScenceAI.nano*0.01f) 
		{
			mScenceAI.newFishAI();	
			//UpdateTime = System.nanoTime();
		} 

		if(FishEnityMap.size()>0)
		{
			mScenceAI.FishMove();
			//MoveUpdateTime = System.nanoTime();
		}

	}
	
	//等待初始化的鱼儿
	
	 
}

