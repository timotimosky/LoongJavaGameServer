package com.gline.module.box;

import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.gline.Excel.ReadExcelBox;
import com.gline.Excel.ReadExcelBoxrate;
import com.gline.message.ItemMessage;
import com.gline.module.person.PlayerEntity;

import engine.mongodb.PlayerDao;
import netBase.util.GameClient;
import util.RandomUtil;


public class BoxLogic {
	private BoxLogic() {}

	public static BoxLogic newInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		protected static final BoxLogic instance = new BoxLogic();
	}

	private static final Logger log = Logger.getLogger(BoxLogic.class);
	
	
	/**
	 * 开宝箱
	 * 宝箱类型
	 * 根据人物充值得到的概率
	 */
	public PlayerEntity OpenBox(GameClient client, int boxType) throws Exception {
	
		PlayerEntity mEntity = client.getPlayer();
				
		//查询宝箱
		if (!ReadExcelBox.Config.containsKey(boxType)) {
			return null;
		}
		
		boxEnity mboxEnity = ReadExcelBox.Config.get(boxType);

		//金币
		if (mboxEnity.costType==1) {
			
			if(mEntity.gold <mboxEnity.cost)
			{
				log.info("金币不足,购买失败");
				ItemMessage.GC_OpenBox(client, 0,0,1);	
				return null;
			}
			else
			{
				mEntity.gold -= mboxEnity.cost;
			}
		}
		else
		{
			if(mEntity.card <mboxEnity.cost)
			{
				log.info("夺宝卡不足,购买失败");
				ItemMessage.GC_OpenBox(client, 0,0,1);	
				return null;
			}
			else {
				mEntity.card -= mboxEnity.cost;
			}
		}
		
		//根据概率权重，得到夺宝卡奖励。	
		List<boxrate> mBoxrate = ReadExcelBoxrate.Config.get(boxType);
		
		int weightTotal = 0;
		for(boxrate mBoxrate2 : mBoxrate)
		{
			weightTotal+= mBoxrate2.weight;
		}
		int getRandom = RandomUtil.Int(0, weightTotal);
		
		int nowweight = 0;
		boxrate rewardBox = null ; //奖励
		for (int i = 0; i < mBoxrate.size(); i++) 
		{
			nowweight+=mBoxrate.get(i).weight;
			if(getRandom <= nowweight)
			{
				log.info("随机到一个奖励"+i);
				rewardBox = mBoxrate.get(i);
				break;
			}
		}
		
		if(rewardBox ==null)
		{
			log.info("随机奖励失败");
			rewardBox = mBoxrate.get(0);
		}
		
		
		//奖励
		if (rewardBox.awardType==1)
		{
			mEntity.gold += rewardBox.awardNumber;			
		}
		else
		{
			mEntity.card += rewardBox.awardNumber;
		}
		
		
		//保存玩家获得的夺宝卡信息
		PlayerDao.Update(mEntity);		
		ItemMessage.GC_OpenBox(client, rewardBox.awardType,rewardBox.awardNumber,0);		
		return mEntity;
	}
	
	
}
