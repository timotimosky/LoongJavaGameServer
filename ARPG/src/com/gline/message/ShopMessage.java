package com.gline.message;


import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;

import com.gline.NetWorkInit;
import com.gline.controller.ControllerModel;
import com.gline.module.Shop.ShopItemEnity;
import com.gline.proxy.ShopProxy;

import netBase.Manager.BufferManager;
import netBase.util.GameClient;
import netWork.resolve.send.listGoodsSendable;

/**
 * 场景发送消息
 * @author 
 * @date 2012-10-10
 */
public class ShopMessage extends SendManager 
{
	
	public static void GC_SHOP_Init(GameClient client, List<ShopItemEnity> mlist)	
	{
			
		ChannelBuffer _buffer;	
		
		_buffer = encode(createBuffer(256), ControllerModel.SHOPMODEL,ShopProxy.GC_SHOP_Init);
		
		
		//要优化为固定
		NetWorkInit.getInstance().mlistGoodsSendable.write(_buffer, mlist);
		
		send(client, _buffer);
	}
	
	public static void GC_SHOP_BUY(GameClient client, int goodsId,short error)	
	{
			
		ChannelBuffer _buffer;		
		_buffer = encode(createBuffer(128), ControllerModel.SHOPMODEL,ShopProxy.GC_SHOP_BUY, goodsId,error);			
		send(client, _buffer);
	}
	
	public static void GC_Exchange(GameClient client, short error,int goodsId)	
	{
			
		ChannelBuffer _buffer;		
		_buffer = encode(createBuffer(128), ControllerModel.SHOPMODEL,ShopProxy.GC_Exchange,error, goodsId);			
		send(client, _buffer);
	}
}
