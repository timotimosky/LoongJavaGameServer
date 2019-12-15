package com.gline.message;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;

import com.gline.module.person.PlayerEntity;

import netBase.Manager.MessageManager;
import netBase.util.GameClient;

/**
 * 消息发送管理类
 * @author djl
 * @date 2013.3.8
 */
public class SendManager extends MessageManager{
	
	private static final Logger log = Logger.getLogger(SendManager.class);
	
	/*带结果的发送单人消息*/
	public static void sendResult(PlayerEntity player,ChannelBuffer buffer, int moduleId, int opcode, int result, Object... objects)
	{
		buffer = encodeResult(buffer, moduleId, opcode, result, objects);
		send(player.client, buffer);
	}
	
	
	/*发送单人消息*/
	public static void send(GameClient client,ChannelBuffer buffer, int moduleId, int opcode, Object... objects)
	{
		buffer = encode(buffer, moduleId, opcode, objects);
		send(client, buffer);
	}	
	
	/* 发送多人消息 不用这个多人发送, 不然buffer会被同时用到,冲突 ，TODO：不一定*/
	protected static void sends(ConcurrentHashMap<Integer,GameClient> list, ChannelBuffer buffer, int moduleId, 
			int opcode, Object... objects)
	{	
		log.info("list长度"+list.size());
		buffer = encode(buffer, moduleId, opcode, objects);
		
		for(Map.Entry<Integer, GameClient> e: list.entrySet())
		{
			send(e.getValue(), buffer);
			
		}
	}
	
	/* 发送多人消息 不用这个多人发送, 不然buffer会被同时用到,冲突 ，TODO：不一定*/
	protected static void sendsByMap(List<GameClient> list, ChannelBuffer buffer, int moduleId, 
			int opcode, Object... objects)
	{	
		log.info("list长度"+list.size());
		buffer = encode(buffer, moduleId, opcode, objects);
		
		for(GameClient e: list)
		{
			send(e, buffer);
			
		}
	}
	
	
	
	/*带结果的发送多人消息*/
	protected static void sendsResult(Collection<PlayerEntity> list, ChannelBuffer buffer, int moduleId, int opcode, int result, Object... objects)
	{	
		
		buffer = encode(buffer, moduleId, opcode, result,objects);
		
		for(PlayerEntity player : list)
		{
			send(player.client, buffer);
		}
	}

}
