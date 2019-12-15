package com.code.gameserver.network.game.receivable;

import com.code.gameserver.model.gameobjects.player.Player;
import com.code.gameserver.network.game.GameClient.State;
import com.code.gameserver.network.game.sendable.SM_START_MOVE;
import com.code.gameserver.network.game.sendable.SM_STOP_MOVE;
import com.code.gameserver.network.game.GameClientPacket;
import com.code.gameserver.utils.PacketSendUtility;
import com.code.gameserver.world.World;

/**
 * 玩家移动
 * @author 杜祥
 * @create 2013-11-18
 */
public class CM_PLAYER_MOVE extends GameClientPacket
{
	int currentX;
	
	int currentY;
	
	int targetX;
	
	int targetY;
	
	public CM_PLAYER_MOVE(int opcode, State state, State... states) 
	{
		super(opcode, state, states);
	}

	@Override
	protected void readImpl()
	{
		currentX = readShort();
		currentY = readShort();
		targetX = readShort();
		targetY = readShort();
		int length = readShort();
		for(int i = 0; i < length; i++)
		{
			
		}
	}

	@Override
	protected void runImpl() 
	{
		
		Player player = getConnection().getPlayer();
		
/*		if(player.getMoveController().isInMove())
		{
			player.getController().onStopMove();
			player.getMoveController().moveToDestination();
		}*/
		
		float x = player.getX();
		float y = player.getY();
		
		//System.out.println("X-Y:" + x + "-" + y);
		
		if(Math.abs(x - currentX) > 1 || Math.abs(y - currentY) > 1)
		{
			player.getMoveController().setNewDirtection(x, y, 0f, (byte)0);
			player.getController().onStopMove();
			/*通知停止移动*/
			PacketSendUtility.sendCastPacket(player, new SM_STOP_MOVE(player.getObjectId(), player.getX(), player.getY()), true);
			return;
		}
		
		World.getInstance().updatePosition(player, currentX, currentY, 0, (byte)0, false);
		
		player.getMoveController().setNewDirtection(targetX, targetY, 0f, (byte)0);
		player.getMoveController().updateLastMove();
		
		player.getController().onStartMove();
		
		
		//System.out.println("开始移动:" + "x-y:" + player.getX() + "-" + player.getY() + "targetX-targetY:" + targetX + "-" + targetY);
		
		/*发送开始移动命令*/
		PacketSendUtility.sendCastPacket(player, new SM_START_MOVE(player), true);
	}

}
