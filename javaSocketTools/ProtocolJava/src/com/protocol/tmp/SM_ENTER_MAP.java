package com.code.gameserver.network.game.sendable;

import java.nio.ByteBuffer;

import com.code.gameserver.model.gameobjects.player.Player;
import com.code.gameserver.network.game.GameClient;
import com.code.gameserver.network.game.GameServerPacket;

/**
 * 进入地图
 * @author 杜祥
 * @create 2013-11-14
 */
public class SM_ENTER_MAP extends GameServerPacket
{
	private Player player;
	
	
	public SM_ENTER_MAP(Player player)
	{
		super();
		
		this.player = player;
	}
	

	@Override
	protected void writeImpl(GameClient client, ByteBuffer buffer) 
	{
		writeShort(buffer, 0);
		writeInt(buffer, player.getMapId());
		writeShort(buffer, (short)player.getX());
		writeShort(buffer, (short)player.getY());
		writeInt(buffer, 0);
	}

}
