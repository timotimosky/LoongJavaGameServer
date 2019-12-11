package netWork.resolve.send;

import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;

import com.gline.module.scene.JJC;

import netWork.resolve.SendableBuffer;

/**
 * 发送list<Integer>的解析
 * @author djl
 * @date 2012-10-12
 */
public class listJJC extends SendableBuffer 
{
	@SuppressWarnings("unchecked")
	@Override
	public void write(ChannelBuffer buffer, Object arg1)
	{

		List<JJC> list = (List<JJC>)arg1;
		 
		writeShort(buffer, list.size());
		for(JJC model : list)
		{
			writeString(buffer, model.name);
			writeInt(buffer, model.price);
			writeLong(buffer, model.id);
		
		}
		
	}
}
