package com.gline.module.fish;

import java.util.ArrayList;
import java.util.List;

public class FishPath {

	public FishPath()
	{
		pathList= new ArrayList<Path>();
	}
	
    /**
     *  
     */
    public String id;
    /**
     *
     */
    public String groupid; //鱼群类型。  
    /**	
     *
     */
    public List<Path> pathList;
    
    public FishPath Clone()
    {
    	FishPath mFishPath = new FishPath();
    	mFishPath.id = this.id;
    	mFishPath.groupid = this.groupid;  	
    	mFishPath.pathList = this.pathList;
    	return mFishPath;
    }
	
}
