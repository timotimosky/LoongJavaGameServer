package netWork.resolve.send;

import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;

import com.gline.module.person.PlayerEntity;

import netWork.resolve.SendableBuffer;

/**
 * List<PlayerEntity>发送解析
 * @author djl
 * @date 2012-09-24
 */
public class ListPlayerSendable extends SendableBuffer
{

	@SuppressWarnings("unchecked")
	@Override
	public void write(ChannelBuffer buffer, Object value) 
	{
		List<PlayerEntity> playerList = (List<PlayerEntity>) value;
		
		writeShort(buffer, (short)playerList.size());
		PlayerEntity player = null;
		for(int i = 0; i < playerList.size() ; i++)
		{
			player = playerList.get(i);
/*			writeInt(buffer, player.getBindCoins());
			writeLong(buffer, player.getId());
			writeFloat(buffer, player.gainFiAt().getmF());*/
		}
	}

}
