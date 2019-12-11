package com.gline.ai.action;

import java.util.concurrent.ConcurrentHashMap;

import com.gline.ai.AI;
import com.gline.ai._FSMState;
import com.gline.module.npc.entity.NpcBaseEntity;
import com.gline.module.person.PlayerEntity;

import netBase.World;
import netBase.util.GameClient;


public class AttackState implements _FSMState 
{
	NpcBaseEntity nNpc;
	
	public AttackState(NpcBaseEntity mNpcBase)
	{
		
		nNpc = mNpcBase;
	}
	
	
	public void Reason ()
	{
		
		if(nNpc.state != 3)
		{
			return;
		}
		
		//寻找所有敌人
		//获取该场景能看到该用户的所有玩家，暂时玩家只有一个场景，就不去判断不同场景的玩家了。
		ConcurrentHashMap<Integer, GameClient> mMap  = World.newInstance().getChannelMap();
				
		//找到距离最近的玩家攻击，距离不够，则移动
		GameClient nclient;
		PlayerEntity nplayer = null;	
		
		if(System.nanoTime() - AI.getInstance().time >1000)
		{
			nNpc.state = 0;		
			AI.getInstance().time = System.nanoTime();
		}
		
	}



    //接收推送1：服务端攻击验证，发起攻击动画//
    public void DoAct()
    {
    }

    /// <summary>
    /// 接收推送2：攻击命中的特效播放请求
    /// </summary>
    public void DoHurt()
    {

    }


    /// <summary>
    /// 接收推送3：被击特效的播放、伤害的扣除
    /// </summary>
    public void DoUnHurt()
    {

    }

    /// <summary>
    /// 发送攻击请求
    /// </summary>
	public  void Act()
	{
        

	}
}
