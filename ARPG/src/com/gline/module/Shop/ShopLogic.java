package com.gline.module.Shop;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;

import com.gline.message.PlayerMessage;
import com.gline.module.person.PlayerEntity;
import com.gline.module.scene.SceneLogic;

import engine.mongodb.PlayerDao;
import netBase.AloneNetMap;
import netBase.World;
import netBase.util.GameClient;

public class ShopLogic {

	private ShopLogic() {
	}

	public static ShopLogic newInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		protected static final ShopLogic instance = new ShopLogic();
	}

	private static final Logger log = Logger.getLogger(ShopLogic.class);

	
	public void GC_SHOP_Init(GameClient client)
	{
		
		PlayerMessage.GC_GetLoadAward(client, 10000);
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
