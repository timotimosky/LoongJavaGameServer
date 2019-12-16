package com.gline.module.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.log4j.Logger;

import com.gline.Excel.ReadExcelFishGruop;
import com.gline.Excel.ReadExcelFishPath;
import com.gline.Excel.ReadExcelFishRefresh;
import com.gline.Excel.ReadExcelFishType;
import com.gline.message.FightMessage;
import com.gline.message.SceneMessage;
import com.gline.module.fish.FishEnity;
import com.gline.module.fish.FishGroup;
import com.gline.module.fish.FishPath;
import com.gline.module.fish.FishRefresh;
import com.gline.module.person.PlayerEntity;

///每个场景一个独立的ScenceAI
public class ScenceAI {

	private static final Logger log = Logger.getLogger(ScenceAI.class);
	ScenceEntity mScenceEntity;
	
	public  Map<String, Long> FishUpdateTimeConfig;//记录每一类型的鱼,上一次的刷新时间.
	
	
	public ScenceAI(ScenceEntity tEntity) {
		WaitFishEnityList = new ArrayList<FishEnity>();
		mScenceEntity = tEntity;
		MaxFish = 60;
		NowFish = 0;
		FishUpdateTimeConfig = new HashMap<String, Long>();
		long LastUpdateTime;
		
		for (Entry<String, FishRefresh> mfishentry : ReadExcelFishRefresh.Config.entrySet()) 
		{
			
			LastUpdateTime = System.currentTimeMillis();
			FishUpdateTimeConfig.put(mfishentry.getKey(), LastUpdateTime);
		}

	}

	public void Clear()
	{
		WaitFishEnityList.clear();
	}
	
	public static final long nano = 1000000000;//(1秒)
	public static final float nanoOff = 0.000000001f;//(1秒)

	
	public int MaxFish;
	public int NowFish;
	
	public List<FishEnity> WaitFishEnityList;


	// 单独开启线程 刷新鱼群
	// 场景内部， 鱼群数量，鱼群游动路径
	public void newFishAI() {
		
		if(NowFish>=MaxFish)
		{
			//log.info("===============鱼数量过多============================="+mScenceEntity.Entityid);			
			return;
		}	
		
		if (WaitFishEnityList.size()>0) 
		{			
			//循环等待刷新的鱼儿列表，判断是否到达更新时间
			for (int i = 0; i < WaitFishEnityList.size(); i++)
			{
				FishEnity mFishEnity = WaitFishEnityList.get(i);
				if (System.nanoTime() - mFishEnity.waitBornTime > mFishEnity.timeDelay * nano) 
				{
					WaitFishEnityList.remove(i);	
					// 刷新,通知客户端
					NowFish++;
					//获取第一条路径  假设有三条路径,其实应该是三个鱼分开用三个路径		
					mFishEnity.nowPos = mFishEnity.nowPath.pList.get(0);
					mFishEnity.lastPos = mFishEnity.nowPath.pList.get(0);	
					mFishEnity.targetPos = mFishEnity.nowPath.pList.get(1);	
					mFishEnity.nowPath.lastNanotime = System.nanoTime();
					mFishEnity.nowPath.nowIndex++; //出身就发送了起点
					mScenceEntity.FishEnityMap.put(mFishEnity.EnityId, mFishEnity);
					
					//log.info(mFishEnity.EnityId+"刷新"+mFishEnity.mFishType.type);
					//log.info(mScenceEntity.Entityid+"===============刷新鱼=============================");
					//根据所在炮台位置，鱼儿的初始化坐标需要倒转 0,1不需要。 2,3需要
					for (PlayerEntity mpPlayerEntity : mScenceEntity.PlayerMap.values())
					{
						 FightMessage.GC_NewFish(mpPlayerEntity.client,mFishEnity.EnityId, Integer.valueOf(mFishEnity.mFishType.type),
								 mFishEnity.nowPos,mFishEnity.targetPos,mFishEnity.nowPath.time);		
					}			
				}
			}
		}

		//循环配置表，判断是否有鱼儿可以加入等待更新列表
		for (Entry<String, FishRefresh> mfishentry : ReadExcelFishRefresh.Config.entrySet()) 
		{
			//因为不同场景   mfish.LastUpdateTime  公用了,实际上不能公用
			
			FishRefresh mfish = mfishentry.getValue();			
			
			if (System.nanoTime() - FishUpdateTimeConfig.get(mfish.type) > mfish.interval * nano)
			{
				//更新时间
				FishUpdateTimeConfig.put(mfish.type, System.nanoTime());
				
				List<FishGroup> mFishGroupList = ReadExcelFishGruop.Config.get(mfish.type); //鱼群ID
				//随机一个鱼群组合
				int rodm = mFishGroupList.size();
				int getRodm = new Random().nextInt(rodm); // RandomUtil.Int(0,rodm-1);
				FishGroup mFishGroup = mFishGroupList.get(getRodm);

				//随机一个路线组合
				List<FishPath> mFishPaths = ReadExcelFishPath.Config.get(mFishGroup.pathGroup);
				int rodm2 = mFishPaths.size();
				int getRodm2 = new Random().nextInt(rodm2); 
				FishPath tFishPath = mFishPaths.get(getRodm2);

				// 这里将鱼群分为多个 处理下时长
				for (int i = 0; i < mFishGroup.count; i++)
				{
					FishEnity mFishEnity = new FishEnity();
					mFishEnity.mFishPath = tFishPath.Clone();
					mFishEnity.timeDelay = mFishGroup.timeDelay * (i + 1);										
					mFishEnity.mFishType = ReadExcelFishType.Config.get(mFishGroup.GroupType);
					mFishEnity.picturFishType = mFishGroup.fishSiglType;
				
					mFishEnity.nowPath = mFishEnity.mFishPath.pathList.get(0).clone();
					WaitFishEnityList.add(mFishEnity);
				}
			} 
		}
	}

	public static FloatPos getNormalize(float OffsetX, float OffsetY, double dis) {
		FloatPos newPos = new FloatPos();
		if (dis == 0) {
			return newPos;
		}

		newPos.x = (float) (OffsetX / dis);
		newPos.y = (float) (OffsetY / dis);

		return newPos;
	}

	public static double getDistance(Pos point1, Pos point2) {
		double distance = Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2);

		return Math.pow(distance, 0.5);

	}

	public  double getDistance(int x, int y,  int tx, int ty){
		double _x = Math.abs(x -tx);
		double _y = Math.abs(y - ty);
		return Math.sqrt(_x*_x+_y*_y);
	}
	
	//鱼群游动,每0.5秒同步所有客户端的NPC位置，告诉所有客户端，0.5秒以后该NCP应该所在的位置
	//所有鱼群使用统一的移动更新计时
	public void FishMove() 
	{				
		//log.info("OffsetTime=="+OffsetTime);
		// 广播 鱼实例ID、原型id、出生位置、运动路线		
		for (FishEnity mfish : mScenceEntity.FishEnityMap.values())
		{			
			//单独计时
			float OffsetTime = (System.nanoTime() -mfish.nowPath.lastNanotime)*nanoOff;	
			mfish.nowPath.lastNanotime = System.nanoTime();
			
			mfish.nowPath.CostTime += OffsetTime;

			//到达目标位置
			if (mfish.nowPath.CostTime >= mfish.nowPath.time)
			{
				//如果是终点,说明到下一个path了
				if(mfish.nowPath.nowIndex >= mfish.nowPath.pList.size()-1)
				{
					if(mfish.nodeListIndex < mfish.mFishPath.pathList.size()-1)
					{
						mfish.nodeListIndex++;
						mfish.nowPath = mfish.mFishPath.pathList.get(mfish.nodeListIndex);
					}
					else
					{
						FishDestroy(mfish);
						//log.info(mfish.EnityId+"游动完毕"+" 数量=="+NowFish+"CostTime="+mfish.nowPath.CostTime+" time="+mfish.nowPath.time);
					}				
				}
				else //通知客户端，鱼儿的目标点更新, 每一个点只通知一次
				{
					mfish.nowPath.CostTime =0;
					mfish.nowPos.x = mfish.targetPos.x;
					mfish.nowPos.y = mfish.targetPos.y;
					
					mfish.nowPath.nowIndex++;
					mfish.lastPos.x = mfish.targetPos.x;
					mfish.lastPos.y = mfish.targetPos.y;
					
					mfish.targetPos = mfish.nowPath.pList.get(mfish.nowPath.nowIndex);
					
					if (getDistance(mfish.nowPos.x,mfish.nowPos.y,mfish.targetPos.x,mfish.targetPos.y)<20) {
						log.info(mfish.EnityId+"警告!!!  nowx"+mfish.nowPos.x+"  nowY"+mfish.nowPos.y+
								"  tX"+mfish.targetPos.x+"  tY"+mfish.targetPos.y);
						
						mfish.targetPos = mfish.nowPath.pList.get(mfish.nowPath.nowIndex);
					}
					
					
					// 计算矢量
					int OffsetX = mfish.targetPos.x - mfish.lastPos.x;
					int OffsetY = mfish.targetPos.y - mfish.lastPos.y;
					// 计算距离
					double dis = getDistance(mfish.nowPos, mfish.targetPos);

					//保存单位矢量朝向   矢量化
					FloatPos result = getNormalize(OffsetX, OffsetY, dis);
					//计算速度
					double normalSpeed = dis/mfish.nowPath.time;
					
					mfish.nowdir = new FloatPos((float)(result.x * normalSpeed), (float)(result.y* normalSpeed));
					//根据所在炮台位置，鱼儿的初始化坐标需要倒转 0,1不需要。 2,3需要
					for (PlayerEntity mpPlayerEntity : mScenceEntity.PlayerMap.values()) 
					{
						SceneMessage.GC_FishMoveTarget(mpPlayerEntity.client, mfish.EnityId, mfish.targetPos,mfish.nowPath.time);					 
					}
					//log.info(mfish.EnityId+String.format("改变目标 {%d,%d}",mfish.targetPos.x,mfish.targetPos.y));
				}
				continue;
			}
			
			// 根据 速度、方向、倾斜度 计算出 xy下一步位置
			mfish.nowPos.x += mfish.nowdir.x * OffsetTime ;
			mfish.nowPos.y += mfish.nowdir.y * OffsetTime;

		}
	}

	// 鱼儿被打死
	public void FishDie(long id) {
		if (mScenceEntity.FishEnityMap.containsKey(id)) 
		{
			//FishEnity mfish = mScenceEntity.FishEnityMap.get(id);
			mScenceEntity.FishEnityMap.remove(id);
			//log.error("====打死");
			NowFish--;
			//根据该鱼儿的类型，获得击杀奖励
			//回收ID
			//GameManager.mIDList.recoverId((int)id);
		}
		else 
		{
			log.error("销毁====鱼ID不存在,出现异常");
		}
	}

	// 鱼儿被销毁（离开屏幕后）
	public void FishDestroy(FishEnity mfish) {
		
		//发送鱼消失命令
		for (PlayerEntity mpPlayerEntity : mScenceEntity.PlayerMap.values()) 
		{
			SceneMessage.GC_REMOVE_Obj(mpPlayerEntity.client, mfish.EnityId);					 
		}
		
		//log.error("鱼儿被销毁 id="+mfish.EnityId);
		if (!mScenceEntity.FishEnityMap.containsKey(mfish.EnityId)) 
		{
			log.error("销毁====鱼ID不存在,出现异常");
			return;
		}
		
		//回收ID
		//GameManager.mIDList.recoverId((int)(mfish.EnityId));
		
		mScenceEntity.FishEnityMap.remove(mfish.EnityId);
		NowFish--;
		

	}
}
