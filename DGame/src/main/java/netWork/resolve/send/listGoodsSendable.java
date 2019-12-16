package netWork.resolve.send;

import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;

import com.gline.module.Shop.ShopItemEnity;

import netWork.resolve.SendableBuffer;

/**
 * 发送list<Integer>的解析
 * @author djl
 * @date 2012-10-12
 */
public class listGoodsSendable extends SendableBuffer 
{

/*	@SuppressWarnings("unchecked")
	@Override
	public void write(ChannelBuffer buffer, Object obj)
	{
		List<Integer> list = (List<Integer>) obj;
		writeShort(buffer, (short) list.size());
		for(int i : list)
		{
			writeInt(buffer, list.get(i));
		}
	}*/
	@SuppressWarnings("unchecked")
	@Override
	public void write(ChannelBuffer buffer, Object arg1)
	{

		List<ShopItemEnity> list = (List<ShopItemEnity>)arg1;
		
		writeShort(buffer, list.size());
		for(ShopItemEnity model : list)
		{
			writeInt(buffer, model.id);
			writeString(buffer, model.icon);
			writeByte(buffer, model.buyType);
			writeInt(buffer, model.buyCount);
			writeString(buffer, model.info1);
			writeString(buffer, model.info2);
			writeByte(buffer, model.consumeType);
			writeInt(buffer, model.consumeCount);
		
		}
		
	}
}
