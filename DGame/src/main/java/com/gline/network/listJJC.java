package com.gline.network;

import java.util.List;

import io.netty.buffer.ByteBuf;

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
	public void write(ByteBuf buffer, Object arg1)
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
