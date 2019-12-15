package com.gline.module.fish;

import java.util.List;

public class FishGroup {

	public FishGroup()
	{		
		LastUpdateTime = System.currentTimeMillis();
	}
	
	public FishGroup(float mtimeDelay)
	{		
		timeDelay = mtimeDelay;
		LastUpdateTime = System.currentTimeMillis();
	}
	
    public long LastUpdateTime; //这种鱼群上一次出生更新时间。 每个鱼群有自己的单独计时。
	
    //出生后的路径
    public FishPath tFishPath;
    
    
    
    /**
     *  
     */
    public String groupId;
    /**
     *这个没啥用,只用于鱼生成
     */
    public String bornType; 
    /**	
     *
     */
    public String pathGroup;
    /**
     * 
     */
    public int count;

    //解析 
    public String fishSiglType; //鱼群里单个鱼的类型ID  
    
    //解析 
    public String GroupType; //鱼群ID   单个鱼的鱼群ID=fishtype    群体鱼比如三元四喜有单独的这个ID
    
    public int number;
    public float timeDelay;
    public String Roule; //角度
    
    public List<String> fishTypeIdlist; //暂不处理
      
    
	
}
