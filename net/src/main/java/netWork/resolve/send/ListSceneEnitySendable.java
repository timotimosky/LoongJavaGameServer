/*
package netWork.resolve.send;

import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;

import com.gline.module.fish.FishEnity;

import netWork.resolve.SendableBuffer;

*/
/**
 * 发送list<Integer>的解析
 * @author djl
 * @date 2012-10-12
 *//*

public class ListSceneEnitySendable extends SendableBuffer 
{
	@SuppressWarnings("unchecked")
	@Override
	public void write(ChannelBuffer buffer, Object arg1)
	{
		List<FishEnity> list = (List<FishEnity>)arg1;
		
		writeShort(buffer, list.size());
		for(FishEnity model : list)
		{
			writeInt(buffer, Integer.valueOf(model.mFishType.type));
			writeFloat(buffer, model.nowPos.x);
			writeFloat(buffer, model.nowPos.y);
			writeFloat(buffer, model.targetPos.x);
			writeFloat(buffer, model.targetPos.y);
			writeLong(buffer, model.EnityId);
			writeFloat(buffer, model.nowPath.time-model.nowPath.CostTime);

		
		}
		
	}
}
*/
