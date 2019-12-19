package netWork.resolve.send;

import java.nio.ByteBuffer;
import java.util.List;

import io.netty.buffer.ByteBuf;
import netWork.resolve.SendableBuffer;

/**
 * 发送list<Integer>的解析
 * @author djl
 * @date 2012-10-12
 */
public class ListIntSendable extends SendableBuffer 
{

	@SuppressWarnings("unchecked")
	@Override
	public void write(ByteBuf buffer, Object obj)
	{
		List<Integer> list = (List<Integer>) obj;
		writeShort(buffer, (short) list.size());
		for(int i : list)
		{
			writeInt(buffer, list.get(i));
		}
	}

}
