package com.gline;

import java.util.concurrent.ConcurrentHashMap;

import com.gline.module.scene.ScenceEntity;

import util.id.IDList;

//整个游戏的管理
public class GameManager {
	
	//实例场景map 主线程和AI线程都在调用    普通场景
	public static ConcurrentHashMap<Long ,ScenceEntity> ScenceEntityMap = new ConcurrentHashMap<Long ,ScenceEntity>();
	
	//VIP
	public static ConcurrentHashMap<Long ,ScenceEntity> VipScenceMap = new ConcurrentHashMap<Long ,ScenceEntity>();
	
	//万炮
	public static ConcurrentHashMap<Long ,ScenceEntity> BigScenceMap = new ConcurrentHashMap<Long ,ScenceEntity>();
	
	//JJC
	public static ConcurrentHashMap<Long ,ScenceEntity> JJCScenceMap = new ConcurrentHashMap<Long ,ScenceEntity>();
	
	//Integer.MAX_VALUE太大,默认不会有这么多用户,最多的用户ID+鱼ID = 87654321 用户ID不回收,鱼ID要回收.
	public static IDList mIDList= new IDList(999654321,1);
		
	//获取到场景
	public static ScenceEntity GetSence(long id)
	{
		if(ScenceEntityMap.containsKey(id))
		{
			return ScenceEntityMap.get(id);
		}
		else if(VipScenceMap.containsKey(id))
		{
			return VipScenceMap.get(id);
		}
		else if(BigScenceMap.containsKey(id))
		{
			return BigScenceMap.get(id);
		}
		return null;
		
	}
	
	public static ScenceEntity Remove(long id)
	{
		if(ScenceEntityMap.containsKey(id))
		{
			return ScenceEntityMap.remove(id);
		}
		else if(VipScenceMap.containsKey(id))
		{
			return VipScenceMap.remove(id);
		}
		else if(BigScenceMap.containsKey(id))
		{
			return BigScenceMap.remove(id);
		}
		return null;
		
	}
	
}
