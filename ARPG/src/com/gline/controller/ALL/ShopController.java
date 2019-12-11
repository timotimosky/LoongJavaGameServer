package com.gline.controller.ALL;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gline.Excel.ReadExcelShopItem;
import com.gline.controller.ControllerModel;
import com.gline.controller.contrlBase.ClassAnn;
import com.gline.controller.contrlBase.ControllerAnnotation;
import com.gline.message.ShopMessage;
import com.gline.module.Shop.ShopItemEnity;
import com.gline.module.Shop.shopBag;
import com.gline.module.Shop.shopBullet;
import com.gline.module.Shop.shopGold;
import com.gline.module.Shop.shopGun;
import com.gline.module.person.PlayerEntity;
import com.gline.proxy.ShopProxy;

import engine.mongodb.PlayerDao;
import netBase.util.GameClient;

/**
 * 玩家控制器：必须用static方法
 * @author timoSky
 * @Data 2013-3-8
 */
@ClassAnn(key = ControllerModel.SHOPMODEL)
public class ShopController {
	private static final Logger log = Logger.getLogger(ShopController.class);
	
	/**
	 * 商城初始化
	 */
	@ControllerAnnotation(key = ShopProxy.CG_SHOP_Init)
	public static void CG_SHOP_Init(GameClient client)throws Exception
	{		
		log.info("初始问问");
		 List<ShopItemEnity> mlist = new ArrayList<ShopItemEnity>(); 
		 mlist.add(new ShopItemEnity());
		 mlist.add(new ShopItemEnity());
		 
		 
		ShopMessage.GC_SHOP_Init(client, mlist);
	} 
	
	
	//TODO: 兑换模块单独做
	@ControllerAnnotation(key = ShopProxy.CG_Exchange)
	public static void CG_Exchange(GameClient client,int PrizeId)throws Exception
	{	
		//根据相应id 扣取夺宝卡
		
		//TODO：告诉后台。
		short error = 0;
		
		ShopMessage.GC_Exchange(client,error,PrizeId);
	}
	/**
	 * 购买商品id
	 * 1， 多宝卡不足
	 * 2，金币不足
	 * 3.需要充值
	 * 4.购买失败
	 * 5.道具不存在
	 */
	@ControllerAnnotation(key = ShopProxy.CG_SHOP_BUY)
	public static void CG_SHOP_BUY(GameClient client,int goodsId)throws Exception
	{	
		//查询商品价格 四种商品
		//public static  Map<Integer, shopGold> shopGold;
		//public static  Map<Integer, shopGun> shopGun;
		//public static  Map<Integer, shopBullet>  ShopBulletConfig;	
		//public static  Map<Integer, shopBag> ShopBagConfig;
		short error =0;
		PlayerEntity mPlayerEntity = client.getPlayer();
			
		//如果购买成功，给玩家增加道具
		if(ReadExcelShopItem.shopGold.containsKey(goodsId))
		{
			shopGold mShopGold = ReadExcelShopItem.shopGold.get(goodsId);
			if (mPlayerEntity.Rmb<mShopGold.price) {
				error=2;
			}
			else
			{
				mPlayerEntity.Rmb -=mShopGold.price;				
				mPlayerEntity.ItemList.add(mShopGold.id);
			}
			log.info("购买商品   水晶商城error=="+error);
		}
		//火炮
		else if(ReadExcelShopItem.shopGun.containsKey(goodsId))
		{
			shopGun mShopGold = ReadExcelShopItem.shopGun.get(goodsId);
/*			if (mPlayerEntity.Rmb<mShopGold.price) {
				error= 2;
			}
			else*/
			{			
				mPlayerEntity.Rmb -=mShopGold.price;				
				mPlayerEntity.GunList.add((int)mShopGold.goodsId);
/*				for(Integer eInteger :mPlayerEntity.GunList)
				{
					log.error("玩家有这类火炮"+eInteger);
				}*/
			}
			log.info("购买商品   火炮 error=="+error  +"mShopGold.goodsId==="+mShopGold.goodsId +"goodsId=="+goodsId);
		}
		else if(ReadExcelShopItem.ShopBulletConfig.containsKey(goodsId))
		{
			shopBullet mShopGold = ReadExcelShopItem.ShopBulletConfig.get(goodsId);
			if (mPlayerEntity.Rmb<mShopGold.price) {
				error=2;
			}
			else
			{
				mPlayerEntity.Rmb -=mShopGold.price;				
				mPlayerEntity.ItemList.add(mShopGold.id);
			}
			log.info("购买商品   子弹 error=="+error);
		}
		else if(ReadExcelShopItem.ShopBagConfig.containsKey(goodsId))
		{
			shopBag mShopGold = ReadExcelShopItem.ShopBagConfig.get(goodsId);
			if (mPlayerEntity.Rmb<mShopGold.price) 
			{
				error=2;
			}
			else
			{
				mPlayerEntity.Rmb -=mShopGold.price;				
				mPlayerEntity.ItemList.add(mShopGold.id);
			}
			log.info("购买商品  福袋 bag  error=="+error);
		}		
		else 			
		{
			error=5;
		}
		log.info("购买商品 error=="+error);
		ShopMessage.GC_SHOP_BUY(client,goodsId,error);
		

		if (error==0) {
			//保存数据库
			PlayerDao.Update(mPlayerEntity);
		}
	} 
}
