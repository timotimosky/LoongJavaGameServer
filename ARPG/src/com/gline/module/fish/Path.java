package com.gline.module.fish;

import java.util.ArrayList;
import java.util.List;

import com.gline.module.scene.Pos;

public class Path implements Cloneable{
	public Path()
	{
		pList = new ArrayList<Pos>();
		CostTime= 0;
		time = 0;
		nowIndex = 0;
		lastNanotime = 0;
	}
    
	public int nowIndex;
	
	public List<Pos> pList;
    
	public float time; //总时间
	
	public float CostTime; //已花费时间
	
	public long lastNanotime; //上一次更新时,获取的NanoTime;
	
	 public Path clone() 
	 {  
		 	Path o = null;  
	        try 
	        {  
	            o = (Path) super.clone();  
	        } 
	        catch (CloneNotSupportedException e) 
	        {  
	            e.printStackTrace();  
	        }  
	        return o;  
	 } 
	
}
