package com.gline.module.person;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;

import com.gline.message.PlayerMessage;
import com.gline.module.scene.SceneLogic;

import engine.mongodb.PlayerDao;
import netBase.AloneNetMap;
import netBase.World;
import netBase.util.GameClient;

public class PlayerLogic {

	private PlayerLogic() {
	}

	public static PlayerLogic newInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		protected static final PlayerLogic instance = new PlayerLogic();
	}

	private static final Logger log = Logger.getLogger(PlayerLogic.class);

	
	public void GC_GetLoadAward(GameClient client,int gold)
	{		
		PlayerMessage.GC_GetLoadAward(client, gold);
	}
	
	
	//玩家的下线保存数据库
	public void SavePlayer(PlayerEntity player)
	{
		player.lastDownTime = System.currentTimeMillis() / 1000;
		//将下线时间保存到数据库
		PlayerDao.Update(player);
		log.info("玩家下线 id=" + player.pid);	
	
	}
	
	/**
	 * 这个方法不能处理玩家连上socket但还没登录就断开的情况
	 * 下线 :
	 * 1.当链接中断,主动或者被动下线后,通知该场景其他人
	 * 2.通信异常时，服务端会主动关闭socket
	 * 3.玩家连上socket但还没登录就断开的情况要分开处理
	 */
	public void downLine(GameClient client)
	{
		log.info("++++++++++++++++++++执行玩家下线 id");	
		
		PlayerEntity player  = client.getPlayer();
		
		//锁定,关闭期间不允许调用player
		if (player != null) {
			synchronized (player) 
			{
				//离开当前场景
				SceneLogic.sceneLeave(client);	
				SavePlayer(player);
				
				AloneNetMap.netMap.remove(client);
		    	Channel ch = client.getChannel();
		    	if(ch!=null)
		    	{
					World.newInstance().removeConnection(ch.getId());
					if(ch.isOpen())
					{
						ch.close();	
					}
			    	ch = null;
		    	} 
		    	client.Clear();
		    	client =null;
			}
		}
		else
		{
			
			log.error("玩家没有登录,就断开socket,所以需要删除map,但不需要执行广播");	
			AloneNetMap.netMap.remove(client);		
	    	Channel ch = client.getChannel();  
	    	if(ch!=null)
	    	{
				World.newInstance().removeConnection(ch.getId());
				if(ch.isOpen())
				{
					ch.close();	
				}
		    	ch = null;
	    	} 
	    	client.Clear();
	    	client =null;
		}
		
	}

	/**
	 * 第一次登陆后，将用户实体与client绑定
	 * 
	 * @param countId
	 * @param channelId
	 * @param id
	 */
	public GameClient createClient(String countId, int channelId,PlayerEntity player)
	{
		GameClient game = World.newInstance().getChannel(channelId);
		game.setPlayer(player);

		return game;
	}

}
