package com.gline.message;

import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;

import com.gline.NetWorkInit;
import com.gline.controller.ControllerModel;
import com.gline.module.fish.FishEnity;
import com.gline.module.scene.JJC;
import com.gline.module.scene.Pos;
import com.gline.proxy.FightProxy;
import com.gline.proxy.SceneProxy;

import netBase.util.GameClient;
import netWork.resolve.send.ListSceneEnitySendable;

/**
 * 场景发送消息
 * @author 
 * @date 2012-10-10
 */
public class SceneMessage extends SendManager 
{
	
	
	public static void GC_CreateJJc(GameClient client, int error)	
	{			
		ChannelBuffer _buffer;
		
		_buffer = encode(createBuffer(32), ControllerModel.SCENEMODEL,SceneProxy.GC_CreateJJc, error);		
		send(client, _buffer);
	}
	
	
	public static void GC_WIN(GameClient client, int battle_id ,int win_camp)	
	{			
		ChannelBuffer _buffer;
		
		_buffer = encode(createBuffer(128), ControllerModel.FIGHTMODEL,FightProxy.GC_WIN, battle_id,win_camp);		
		send(client, _buffer);
	}
	
	
	public static void GC_GetAllJJC(GameClient client, List<JJC> jjc)	
	{
		ChannelBuffer _buffer;	
		_buffer = encode(createBuffer(4096), ControllerModel.SCENEMODEL,SceneProxy.GC_GetAllJJC);
		NetWorkInit.getInstance().mpublic.write(_buffer, jjc);
		send(client, _buffer);
	}
	//进入场景,返回场景初始化信息
	public static void GC_EnterSence(GameClient client, List<FishEnity> mlist,int towerid,int error)	
	{			
		ChannelBuffer _buffer;	
		_buffer = encode(createBuffer(2048), ControllerModel.SCENEMODEL,SceneProxy.GC_EnterScene);
		NetWorkInit.getInstance().mListSceneEnitySendable.write(_buffer, mlist);
	
		_buffer.writeByte((byte)towerid);
		_buffer.writeByte((byte)error);
		send(client, _buffer);
	}
	
	//移除一个场景物体
	public static void GC_REMOVE_Obj(GameClient client, long EnityId)
	{			
		ChannelBuffer _buffer;
		
		_buffer = encode(createBuffer(128), ControllerModel.SCENEMODEL,SceneProxy.GC_REMOVE_Obj, EnityId);
		
		send(client, _buffer);
	}
	
	
	//鱼都是客户端本地刷新.
	public static void GC_ADD_Obj(GameClient client, String name , int hp)	
	{			
		ChannelBuffer _buffer;
		
	//	_buffer = encode(createBuffer(60), ControllerModel.SCENEMODEL,SceneProxy.GC_ADD_Obj, 
			//	name,System.currentTimeMillis(),x,  y ,hp);
		
		//send(client, _buffer);
	}
	
	/**
	 * 通知有人退出场景
	 * @param playerList		需要通知的玩家集合
	 * @param playerId			离开的玩家ID
	 * @create 
	 */
	public static void GC_PLAYER_EXIT(GameClient  client, long playerId)
	{
		send(client, createBuffer128(), ControllerModel.SCENEMODEL, SceneProxy.GC_PLAYER_EXIT, playerId);
	}
	
	
	public static void GC_SelfLeaveSence(GameClient client)
	{
		send(client, createBuffer128(), ControllerModel.SCENEMODEL, SceneProxy.GC_SelfLeaveSence);
	}
	
	/**
	 * 通知有人进入场景
	 * @param playerList		需要通知的玩家实体集合
	 * @param player			进入场景的玩家实体
	 * @create 2012-10-11 djl
	 */
	public static void GC_PlAYER_ENTER(GameClient client, long playerId,short Guntype , String name,byte towerid,int gold)
	{
		send(client, createBuffer128(), ControllerModel.SCENEMODEL, SceneProxy.GC_PLAYER_Enter, playerId,Guntype,name,towerid,gold);
	}
	
	public static void GC_FishMoveTarget(GameClient client, long fishEnityId , Pos targetPos, float costTime)
	{
		send(client, createBuffer128(), ControllerModel.FIGHTMODEL, FightProxy.GC_FishMoveTarget, fishEnityId,targetPos.x,targetPos.y,costTime);
	}
	
	
	/**
	 * 通知场景玩家增加NPC
	 * @param playerList			玩家列表
	 * @param npc					npc实体
	 * @create 2012-11-28 djl
	 */
/*	public static void GC_ADD_NPC(Collection<PlayerEntity> playerList, NpcEntity npc)
	{
		sends(playerList, createBuffer(60), ControllerModel.SCENEMODEL, SceneProxy.GC_ADD_NPC, npc.getId(),
				npc.getBaseId(), npc.getHp(), npc.gainMpMax(), npc.getMp(), npc.gainMpMax(), npc.getX(), npc.getY());
	}*/
	
	/**
	 * 怪物停止移动
	 * @param playerList			玩家列表
	 * @param npc					NPC实体
	 * @create 2012-11-29 djl
	 *//*
	public static void GC_STOP_MOVE(Collection<PlayerEntity> playerList, NpcEntity npc)
	{
		sends(playerList, createBuffer(50), ControllerModel.SCENEMODEL, SceneProxy.GC_STOP_MOVE, npc.getId(), npc.getX(), npc.getY());
	}
	
	*//**
	 * NPC死亡
	 * @param playerList			玩家列表
	 * @param id					NPCID
	 * @create 2012-11-29 djl
	 *//*
	public static void GC_NPC_DIE(Collection<PlayerEntity> playerList, long id)
	{
		sends(playerList, createBuffer(50), ControllerModel.SCENEMODEL, SceneProxy.GC_NPC_DIE, id);
	}*/
}
