package com.gline.module.fish;

import com.gline.GameManager;
import com.gline.module.scene.FloatPos;
import com.gline.module.scene.Pos;

public class FishEnity  {

	public FishEnity()
	{
		mFishType = new FishType();
		EnityId = (long)GameManager.mIDList.nextId();
				//AutoRandomID.randomRoleArticleId();
		waitBornTime = System.nanoTime();
		
		nowPos = new Pos();
		targetPos = new Pos();
		nowdir = new FloatPos();
		
		mFishPath = new FishPath();
		nowPath = new Path();
		
		nodeListIndex = 0;
	}
	
	
	
	public long waitBornTime; //鱼出生更新时间开始计时
	
	public FishType mFishType; //这个是用来判断鱼是什么类型,如果是全屏炸弹,则死亡全屏死亡, 等等.以及奖励	
	
	public String picturFishType;  //显示出来是怎么样的鱼.
	
    public float timeDelay;//这个鱼群的出生延迟时间。
	
    //出生后的路径 //出生时随机一个路线id
    public FishPath mFishPath;	
	
	public int nodeListIndex; 
	
	public Path nowPath; //当前路径
	//实例ID
	public long EnityId;
	
	//当前位置
	public Pos nowPos;
	
	//上一个位置
	public Pos lastPos;
	
	//目标位置
	public Pos targetPos;
	
	//当前朝向
	public FloatPos nowdir;
	
	
	//需要记录的是，lastnode，以及到达lastnode的速度。  然后判断到达lastnode后，取下一个lastnode
	
}
