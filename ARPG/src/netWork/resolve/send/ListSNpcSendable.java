package netWork.resolve.send;

import java.util.Collection;

import org.jboss.netty.buffer.ChannelBuffer;

import com.gline.module.npc.entity.NpcEntity;

import netWork.resolve.SendableBuffer;

/**
 * 场景NPC返回信息
 * @author djl
 * @date 2012-10-12
 */
public class ListSNpcSendable extends SendableBuffer
{
	@Override
	public void write(ChannelBuffer buffer, Object arg1) 
	{
		@SuppressWarnings("unchecked")
		Collection<NpcEntity> list = (Collection<NpcEntity>) arg1;
		writeShort(buffer, list.size());
		for(NpcEntity entity : list)
		{
/*			writeLong(buffer, entity.getId());
			writeInt(buffer, entity.getBaseId());
			writeInt(buffer, entity.getHp());
			writeInt(buffer, entity.gainHpMax());
			writeInt(buffer, entity.getMp());
			writeInt(buffer, entity.getMpMax());
			writeShort(buffer, entity.getX());
			writeShort(buffer, entity.getY());*/
		}
	}

}