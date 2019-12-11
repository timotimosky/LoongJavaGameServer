package com.gline;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.gline.config.SystemConfig;
import com.gline.message.SceneMessage;
import com.gline.module.person.PlayerLogic;

import netBase.World;
import netBase.util.GameClient;


public class GameClose {

	Logger logger = Logger.getLogger(GameClose.class);

	public void close(boolean flag) throws Exception {

		logger.info("close server....................");
		SystemConfig.loginDenote = SystemConfig.loginClose;

		Thread.sleep(30 * 1000L);
		playerDownLine(true);

		Thread.sleep(10 * 1000L);
		playerDownLine(false);

	}


	private void playerDownLine(boolean sessionClose) throws Exception {

		ConcurrentHashMap<Integer, GameClient> clientList = World.newInstance().getChannelMap();
		
		while (clientList.size() > 0) 
		{			
			GameClient client;
			int index = 0;

			for (Entry<Integer, GameClient> entry : clientList.entrySet()) 
			{
				client = (GameClient) entry.getValue();		
				PlayerLogic.newInstance().downLine(client);
				index++;
			}
			logger.info("关闭游戏中....关闭玩家 index" + index);

			if (clientList.size() > 0)
				Thread.sleep(30 * 1000L);

		}
	}
}
