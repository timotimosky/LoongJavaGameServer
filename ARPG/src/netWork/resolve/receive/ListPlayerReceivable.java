package netWork.resolve.receive;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.gline.module.person.PlayerEntity;

import netWork.resolve.ReceivableBuffer;

/**
 * list<PlayerEntity>解析
 * @author djl
 * @data 2012-09-24
 */
public class ListPlayerReceivable extends ReceivableBuffer 
{

	@Override
	public Object read(ByteBuffer byteBuffer) 
	{
		int length = readShort(byteBuffer);
		List<PlayerEntity> playerList = new ArrayList<PlayerEntity>();
		PlayerEntity player = null;
		for(int i = 0; i < length; i++)
		{
			player = new PlayerEntity();
/*			player.setId(readLong(byteBuffer));
			player.gainFiAt().setCfsDf(readInt(byteBuffer));*/
			playerList.add(player);
		}
		
		return playerList;
	}

}
