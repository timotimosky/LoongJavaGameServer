package com.gline.module.fish;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import com.gline.module.scene.Pos;

//等待刷新的鱼实体
public class WaitFishEnity {

	public WaitFishEnity(float mwaitTime)
	{
		waitTime = mwaitTime;
		
	}
	
	public float waitTime;
	
	
	//需要记录的是，lastnode，以及到达lastnode的速度。  然后判断到达lastnode后，取下一个lastnode
	
}
