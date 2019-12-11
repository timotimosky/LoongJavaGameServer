package com.gline.module.count;

import java.awt.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.gline.message.PlayerMessage;
import com.gline.module.person.PlayerEntity;
import com.gline.module.person.PlayerLogic;

import engine.mongodb.CountDao;
import engine.mongodb.PlayerDao;
import netBase.World;
import netBase.util.GameClient;

public class CountLogic {
	
	private CountLogic() {}

	public static CountLogic newInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		protected static final CountLogic instance = new CountLogic();
	}

	private static final Logger log = Logger.getLogger(CountLogic.class);
	
	
	/**
	 * 绑定账号
	 */
	public PlayerEntity BindMacByCount(GameClient client, String mac) throws Exception {
	
		return null;
	}
	
	
	/**
	 * 快速创建账号并登陆,根据MAC值
	 */
	public PlayerEntity loadCount(GameClient client, String mac) throws Exception {
				
		//通过账号查询：该账号拥有的角色、每个角色的等级，供客户端显示，供玩家选择角色进入
		PlayerEntity mPlayerEntity = null;
		CountEnity mCountEnity = CountDao.SelectCount("mac", mac);
		if (mCountEnity == null) 
		{
			log.error("玩家mac不存在,是第一次登录，自动注册账号密码 mac="+mac);
		    mCountEnity = new CountEnity();
		    mCountEnity.mac = mac;
		    
		    mPlayerEntity = new PlayerEntity();	    
		    mCountEnity.playerPID = mPlayerEntity.pid;		    	    
		    CountDao.Put(mCountEnity);
		    PlayerDao.Put(mPlayerEntity);
			
		}
		else {
			log.error("玩家mac存在,获取玩家信息");
	
			mPlayerEntity = PlayerDao.SelectPlayerByPid(mCountEnity.playerPID);
		}
	
		//绑定 
		client.setPlayer(mPlayerEntity);
		mPlayerEntity.client = client;
		mPlayerEntity.mCountEnity = mCountEnity;
		
		PlayerMessage.GC_LoginBack(client,0,mPlayerEntity.level,mPlayerEntity.pid,mPlayerEntity.gold,mPlayerEntity.Rmb,mPlayerEntity.allGold,mPlayerEntity.card,mPlayerEntity.name
				,mPlayerEntity.gunType,new ArrayList<Integer>());
		log.error("玩家登陆成功,mPlayerEntity.gold="+mPlayerEntity.gold+"mPlayerEntity.diamond="+mPlayerEntity.Rmb+
		"mPlayerEntity.card="+mPlayerEntity.card);
		
		//TODO:登录成功后,发放奖励
		//PlayerMessage.GC_LoadAward(client,1000,2);
		
		//log.error("测试玩家进入和离开战场");
		//SceneMessage.GC_PlAYER_ENTER(client, 345345364364L, "测试玩家进入");
		//SceneMessage.GC_PLAYER_EXIT(client, 345345364364L);
		//SceneController.CG_SelfLeaveSence(client, 345345364364L);
		
		
		return mPlayerEntity;		

	}
	

	/**
	 * 绑定手机号
	 */
	public void CG_BindPhoneNumber(GameClient client, String PhoneNumber, int code) throws Exception 
	{
		PlayerEntity mPlayerEntity = client.getPlayer();
		
		//判断验证码是否正确  TODO 暂时不做发送短信功能
		
		
		mPlayerEntity.mCountEnity.phoneNumber = PhoneNumber;		
		CountDao.Put(mPlayerEntity.mCountEnity);
			
	}
	
	/**
	 * 账号注册
	 */
	public void CG_CreatebyCount(GameClient client, String count,String password) throws Exception 
	{
		//TODO:注册账号密码
		//通过账号查询：该账号拥有的角色、每个角色的等级，供客户端显示，供玩家选择角色进入
		CountEnity mCountEnity = CountDao.SelectCount("count", count);
		if (mCountEnity != null) 
		{
			log.error("该账号已存在，请更换账号重新注册！");
			PlayerMessage.GC_CreatebyCount(client, (byte)1);
			return;		
		}
		
		PlayerEntity mPlayerEntity = new PlayerEntity();	    	    	    		
		
		mCountEnity = new CountEnity();
	    mCountEnity.playerPID = mPlayerEntity.pid;	
		mCountEnity.count = count;
		mCountEnity.password = password;
		//新建一个角色
		
		//保存
		CountDao.Put(mCountEnity);	
	    PlayerDao.Put(mPlayerEntity);
		PlayerMessage.GC_CreatebyCount(client, (byte)0);
		log.error("账号注册成功");
	}
	
	
	
	/**
	 * 账号登录
	 */
	public PlayerEntity loadCount(GameClient client, String count,String password) throws Exception {
		
		//通过账号查询：该账号拥有的角色、每个角色的等级，供客户端显示，供玩家选择角色进入
				
				CountEnity mCountEnity = CountDao.SelectCount("count",count);
				if (mCountEnity == null) 
				{
					PlayerMessage.GC_LoginBack(client,1,0,0,0,0,0,0,"",0,new ArrayList<Integer>());
					log.error("玩家账号不存在");
					return null;					
				}
				if (!mCountEnity.password.equals(password)) 
				{
					PlayerMessage.GC_LoginBack(client,2,0,0,0,0,0,0,"",0,new ArrayList<Integer>());
					log.error("密码错误 mCountEnity.password="+mCountEnity.password+" password="+password);
					return null;					
				}
				
					
				log.error("玩家账号密码存在,获取玩家信息+"+mCountEnity.playerPID);			
				PlayerEntity mPlayerEntity = PlayerDao.SelectPlayerByPid(mCountEnity.playerPID);
				if (mPlayerEntity == null) {
					mPlayerEntity = new PlayerEntity();
					mCountEnity.playerPID = mPlayerEntity.pid;
					log.error("一个新角色+"+mCountEnity.playerPID);	
					//保存账号信息
					CountDao.UpdateByPID(mCountEnity);
				}
				else 
				{
					//如果玩家已存在,则T之前的玩家下线
					for (GameClient mClient: World.newInstance().getChannelMap().values()) 
					{
						//有的clietn处于刚登录还没绑定player的时候
						if (mClient.getPlayer() ==null) {
							continue;
						}
						
						if (mClient.getPlayer().pid == mCountEnity.playerPID) {
							
							log.error("玩家已登录, T下线");	
							PlayerMessage.CG_GS_KICK(mClient,1);
							PlayerLogic.newInstance().downLine(mClient);						
						}
					}
				}
				
				//绑定 
				client.setPlayer(mPlayerEntity);
				mPlayerEntity.client = client;
				mPlayerEntity.mCountEnity = mCountEnity;
				
										
				PlayerMessage.GC_LoginBack(client,0,mPlayerEntity.level,mPlayerEntity.pid,mPlayerEntity.gold,mPlayerEntity.Rmb,
						mPlayerEntity.allGold ,mPlayerEntity.card,mPlayerEntity.name
						,mPlayerEntity.gunType,mPlayerEntity.GunList);
				log.error("玩家登陆成功,mPlayerEntity.pid="+mPlayerEntity.pid+"mPlayerEntity.diamond="+mPlayerEntity.Rmb+
				"mPlayerEntity.card="+mPlayerEntity.card);
				
				
				//TODO:判断登录是否领取
				//登录成功后,发放奖励
				PlayerMessage.GC_LoadAward(client,1000,2);	
				return mPlayerEntity;								
	}
	
	
}
