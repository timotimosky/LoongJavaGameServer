package com.gline.module.scene;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.gline.GameManager;
import com.gline.message.SceneMessage;
import com.gline.module.person.PlayerEntity;

import netBase.util.GameClient;

/**
 * 场景逻辑类
 * @author djl
 * @date 2012-10-10
 */
public class SceneLogic
{
	private final static SceneLogic sceneLogic = new SceneLogic();
	public static final Logger log = Logger.getLogger(SceneLogic.class);	
	
	private SceneLogic(){}	
	public static SceneLogic newInstance()
	{
		return sceneLogic;
	}
	
	public static int MaxPlayerInScence = 4; //每个普通房间的最大人数。
	
	public static int MaxPlayerInJJCScence = 2; //每个JJC房间的最大人数。
	
	//无论是玩家下线,还是玩家主动离开场景,都要调用这个函数
	public static void sceneLeave(GameClient client) {	
		
		PlayerEntity mplayer = client.getPlayer();
		
		if(mplayer == null)
		{
			log.error("玩家信息为空~~~~~~");
			return;
		}
		
		long newSencePID = mplayer.currMapID;
		
		ScenceEntity newScence = GameManager.GetSence(newSencePID);		
		
		if(newScence!=null)
		{			
			for (Entry<Byte, PlayerEntity> entry : newScence.PlayerMap.entrySet())
			{
				PlayerEntity nplayer = entry.getValue();
				//不能发送给自己
				if (nplayer != null && nplayer.pid!=mplayer.pid) 
				{
					SceneMessage.GC_PLAYER_EXIT(nplayer.client,  mplayer.pid);
					log.info("广播昌吉，     其他玩家 id="+nplayer.pid);
				}
			}
			
			log.info("玩家离开当前场景，当前curScence.PlayerMap.size()="+newScence.PlayerMap.size());
			newScence.PlayerMap.remove(mplayer.towerId);

			log.info("玩家离开当前场景，删除操作，当前curScence.PlayerMap.size()="+newScence.PlayerMap.size());			
			
			//如果场景里没有玩家,则删除该场景
			if(newScence.PlayerMap.size() == 0 )
			{
				ScenceEntity RScenceEntity = GameManager.Remove(newSencePID);			
				//清空该场景
				if(RScenceEntity!=null)				
					RScenceEntity.Clear();
			}
		}	
	}
}


