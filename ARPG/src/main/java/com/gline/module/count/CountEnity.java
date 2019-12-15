package com.gline.module.count;

import java.io.Serializable;

//账号实体
// 根据mongdb特性,  为了避免重复存放player实体
// CountEnity 不直接存储player实体  只存储各种映射
// mac -- playerid   
// count -- playerid
// phoneNumber -- playerid //用于玩家查询
public class CountEnity  implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	
	public CountEnity()
	{
		count = "";
		mac = "";
		password = "";
		playerPID =0L;
		phoneNumber = "";
	}
		
	public String mac; 
	/**
	 * 账号
	 */
	public String count;
	
	/**
	 * 密码
	 */
	public String password;
	
	public String phoneNumber; //电话号码
      
	/**
	 * 当前玩家使用的角色
	 */
	public long playerPID;

}
