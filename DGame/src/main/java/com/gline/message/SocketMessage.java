package com.gline.message;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;

import com.gline.module.person.PlayerEntity;

import netBase.Manager.MessageManager;

public class SocketMessage extends MessageManager{
	
	private static final Logger log = Logger.getLogger(SendManager.class);
	
	/*带结果的发送单人消息*/
	public static void sendResult(PlayerEntity player,ChannelBuffer buffer, int moduleId, int opcode, int result, Object... objects)
	{
		buffer = encodeResult(buffer, moduleId, opcode, result, objects);
		send(player.client, buffer);
	}

}
