package com.gline.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.gline.controller.ControllerModel;
import com.gline.proxy.ItemProxy;

import netBase.util.GameClient;


/**
 *
 * @author dai'jin'long
 * @date  2016.10.25
 */
public class ItemMessage extends SendManager {


	
	/**
	 * 进入游戏返回
	 * @param player	 玩家实体
	 * @param result	结果集:返回所有数据。人物金币、钻石
	 */
	public static void GC_OpenBox(GameClient client,int awardType,int awardNumber,int error)
	{
		ChannelBuffer _buffer;
			
		_buffer = encode(createBuffer512(), ControllerModel.ITEMMODEL,ItemProxy.GC_OpenBox,awardType,awardNumber,(short)error);
	
		send(client, _buffer);
	}

	
}
