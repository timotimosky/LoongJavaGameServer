package com.gline.message;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.netty.buffer.ChannelBuffer;

import com.gline.NetWorkInit;
import com.gline.controller.ControllerModel;
import com.gline.module.person.PlayerEntity;
import com.gline.proxy.LoginProxy;
import com.gline.proxy.PlayerProxy;
import com.gline.proxy.ScoketProxy;
import com.gline.proxy.UtilProxy;

import netBase.util.GameClient;


/**
 * 玩家角色相关信息发送
 * @author dai'jin'long
 * @date  2013.3.9
 */
public class PlayerMessage extends SendManager {

	/**
	 * 心跳连接
	 * @param player			玩家实体
	 * @param time				当前系统时间
	 */
	public static void GC_HEART_BEAT(GameClient client,long time)
	{
		send(client, createBuffer(30), ControllerModel.UTILMODEL, UtilProxy.GC_HEART_BEAT, time);
		
	}
	
	/**
	 * 测试命令返回
	 * @param client			玩家连接
	 * @param countId			字符串数据测试
	 * @param i					Int型数据测试
	 * @param l					Long型数据测试
	 * @param b					Byte型数据测试
	 * @param s					Short型数据测试
	 * @param f					Float型数据测试
	 * @param d					Double型数据测试
	 * @param list				List型数据测试
	 * @create 2012-10-12 djl
	 */
	public static void GC_TEST(GameClient client,String countId, int i, long l, int b, int s, float f, double d)
	{
		ChannelBuffer channel = encode(createBuffer2048(), ControllerModel.PLAYERMODEL, PlayerProxy.CG_CREATE, countId, i, l, b, s, f, d);
		send(client, channel);
	}
	/**
	 * 弹出进入游戏每日奖励
	 * @param player	 玩家实体
	 * @param result	结果集:返回所有数据。人物金币、钻石
	 */
	public static void GC_LoadAward(GameClient client,int gold,int dayNumber) {
		ChannelBuffer _buffer;
			
		_buffer = encode(createBuffer128(), ControllerModel.LOGINMODEL,LoginProxy.GC_LoadAward,gold,dayNumber);
	
		send(client, _buffer);
	}
	
	/**
	 * 游戏每日奖励
	 * @param player	 玩家实体
	 * @param result	结果集:返回所有数据。人物金币、钻石
	 */
	public static void GC_GetLoadAward(GameClient client,int gold) {
		ChannelBuffer _buffer;
			
		_buffer = encode(createBuffer128(), ControllerModel.LOGINMODEL,LoginProxy.GC_GetLoadAward,gold);
	
		send(client, _buffer);
	}
	
	/**
	 * 进入游戏返回
	 * @param player	 玩家实体
	 * @param result	结果集:返回所有数据。人物金币、钻石
	 */
	public static void GC_LoginBack(GameClient client,int error, int role1,long pid,int Gold,int diamond,int allGold,int card,
			String name,int guntype,List<Integer> gunlist)  {
		ChannelBuffer _buffer;
			
		_buffer = encode(createBuffer128(), ControllerModel.LOGINMODEL,LoginProxy.GC_LOGIN,error,role1,pid,Gold,diamond,allGold,card,name,(short)guntype);
	
		//要优化为固定
		NetWorkInit.getInstance().mgunlistSendable.write(_buffer, gunlist);
		
		send(client, _buffer);
	}

	/**
	 * 创建账号返回
	 * @param client	一个连接
	 * @param result	结果集
	 * @param Player	玩家实体
	 * b
	 */
	public static void GC_CreatebyCount(GameClient client, byte ifsuccess) 
	{
		ChannelBuffer channelBuffer = encode(createBuffer128(),ControllerModel.LOGINMODEL, LoginProxy.GC_CreatebyCount,ifsuccess);
		send(client, channelBuffer);
	}
	
	
	/**
	 * 创建角色
	 * @param client	一个连接
	 * @param result	结果集
	 * @param Player	玩家实体
	 * b
	 */
	public static void GC_CREATE_GAME(GameClient client, String FishPid,String playerName,String RoleId) 
	{
		//其实encode方式,每次都强转object,效率低,直接用write更好.
		
		ChannelBuffer channelBuffer = encode(createBuffer128(),
				ControllerModel.PLAYERMODEL, PlayerProxy.GC_CREATE, FishPid,RoleId,playerName);
		send(client, channelBuffer);
	}

	
	/**
	 * 生存状态
	 * @param player	玩家实体
	 * @Date 2012-10-22代金龙
	 */
	public static void GC_LIVE_STATE(PlayerEntity player){
		//sends(player, createBuffer128(), ControllerModel.USERMODEL, PlayerProxy.GC_LIVE_STATE,
			//	player.getState().isLive() ? 0 : 1, player.getHp(), player.getMp(), player.gainHpMax(), player.gainMpMax());
	}
	
	
	/**
	 * 推送玩家战斗属性 
	 * @param player		玩家实体
	 */        
	public static void GC_PLAY_ATTR_RERUN(PlayerEntity player)
	{
/*		sends(player,createBuffer1024(),ControllerModel.USERMODEL, PlayerProxy.GC_PLAY_ATTR_RERUN,
				player.gainKfAtMin(),
				player.gainKfAtMax(),
				player.gainDgAtMin(),
				);*/
	}
	
	/**
	 * 玩家被T出游戏 或者主动下线
	 * @param player		单个玩家
	 */
	public static void CG_GS_KICK(GameClient client, int type)
	{
		send(client, createBuffer(32), ControllerModel.SOCKETMODEL, ScoketProxy.GC_GS_DOWN,(byte)type);
	}
	
	
	/**
	 * 玩家被T出游戏 或者主动下线
	 * @param player		玩家
	 */
	public static void GC_T_GAME_ALL(ConcurrentHashMap<Integer,GameClient> list, String pid)
	{
		sends(list, createBuffer(32), ControllerModel.PLAYERMODEL, PlayerProxy.GC_GS_DOWN,pid);
	}
}
