package com.gline.ai;

import java.io.Serializable;
import java.util.List;

import com.gline.module.person.RoleStatus;



/**
 *  玩家超类
 */
public abstract class RoleAbstracts implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public enum newEnityType
	{
		Player(0),
		Obj(1),
		Npc(2);
		
		private int value = 0;

	    private newEnityType(int value) {    //    必须是private的，否则编译错误
	        this.value = value;
	    }

	    public static newEnityType valueOf(int value) {    //    手写的从int到enum的转换函数
	        switch (value) {
	        case 0:
	            return Player;
	        case 1:
	            return Obj;
	        case 2:
	            return Npc;
	        default:
	            return null;
	        }
	    }

	    public int value() {
	        return this.value;
	    }
		
	}
	
	public newEnityType mnewEnityType;
	
	public String name;
	
	public int level;
	
	//状态， 0为正常，1为死亡。
	public int RoleState;

	public long currMapID;//当前所在地图
	
	public List<Integer> selectedRoleList=null;
	
	public int currSelectRoleId;//״̬
	
	public RoleStatus roleStatus = new RoleStatus();
	
	

}
