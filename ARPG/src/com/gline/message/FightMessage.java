package com.gline.message;

import com.gline.controller.ControllerModel;
import com.gline.module.scene.Pos;
import com.gline.proxy.FightProxy;

import netBase.util.GameClient;

/**
 * 战斗消息发送
 * @author djl
 * @date 2015-4-30
 */
public class FightMessage extends SendManager
{
	
	/** 
	 * @create 20150429
	 */
	public static void GC_NewFish(GameClient mGameClient, long fishpId,int fishtype,Pos nowPos,Pos nowDir,float costtime)
	{
		send(mGameClient, createBuffer128(), ControllerModel.FIGHTMODEL, FightProxy.GC_NewFish, fishpId,fishtype,
				nowPos.x,nowPos.y,nowDir.x,nowDir.y,costtime);
	}
	
	public static void GC_ChangeGun(GameClient mGameClient, byte error)
	{
		send(mGameClient, createBuffer(40), ControllerModel.FIGHTMODEL, FightProxy.GC_ChangeGun, error);
	}
	
	//接收鱼死亡  金币奖励 夺宝卡奖励 
	public static void GC_FishDead(GameClient mGameClient, long fishid,int gold,int treasure,long pid)
	{
		send(mGameClient, createBuffer(80), ControllerModel.FIGHTMODEL, FightProxy.GC_FishDead, fishid,gold,treasure,pid);
	}
	
	
	/**
	 * 单体技能推送信息
	 * @param playerList		玩家集合
	 * @param playerId			施放技能者的ID
	 * @param type				施放技能者的类型
	 * @param skillId			技能ID
	 * @param targetId			目标ID
	 * @param Ttype				目标类型
	 * @param harm				伤害值
	 * @param miss				是否命中
	 */
	public static void GC_Shot(GameClient player, int type,float dirX, float dirY,long id)
	{
		send(player, createBuffer(30), ControllerModel.FIGHTMODEL, FightProxy.GC_Shot,type,dirX,dirY,id);
	}
	

}
