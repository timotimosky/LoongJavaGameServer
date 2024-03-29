package netWork.resolve.send;

import io.netty.buffer.ByteBuf;
import java.util.List;


import netWork.resolve.SendableBuffer;

/**
 * 发送list<Integer>的解析
 * @author djl
 * @date 2012-10-12
 */
public class gunlistSendable extends SendableBuffer 
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
	public void write(ByteBuf buffer, Object arg1)
	{
		List<Integer> list = (List<Integer>)arg1;
		
		writeShort(buffer, list.size());
		for(Integer model : list)
		{
			writeInt(buffer, model);	
		}
		
	}
}
