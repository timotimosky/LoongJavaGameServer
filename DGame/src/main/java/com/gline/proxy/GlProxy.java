
package com.gline.proxy;

import java.util.ArrayList;
import java.util.List;
import netWork.model.CommandInfoModel;
import netWork.model.CommandModel;

	public class GlProxy 
	{
		/**
        *推送
        *Server
		*	当前时间 
		*	Name:time		TYPE:long		SIZE:8
        */
        public final static int GC_HEART = 100;
		/**
        *推送
        *Server
		*	账号ID 
		*	Name:accountId		TYPE:long		SIZE:8
		*	下线类型 
		*	Name:type		TYPE:byte		SIZE:1
        */
        public final static int GC_GS_DOWN = 103;
		/**
        *接收
        *Client
		*	结果 
		*	Name:result		TYPE:short		SIZE:2
		*	账号ID 
		*	Name:accountId		TYPE:long		SIZE:8
		*	下线类型 
		*	Name:type		TYPE:byte		SIZE:1
        */
        public final static int CG_GS_KICK = 105;
		/**
        *接收
        *Client
		*	结果 
		*	Name:result		TYPE:short		SIZE:2
		*	账号ID 
		*	Name:accountId		TYPE:long		SIZE:8
		*	防沉迷状态 
		*	Name:wallow		TYPE:int		SIZE:4
		*	防沉迷时间 
		*	Name:wallowTime		TYPE:long		SIZE:8
		*	最后下线时间 
		*	Name:lastDownTime		TYPE:long		SIZE:8
        */
        public final static int CG_GS_VALIDATE = 106;

		
		public static List<CommandModel> DATA_MODE()
        {
        	List<CommandModel> list = new ArrayList<CommandModel>();
            CommandModel model= null;
            CommandInfoModel [] infoArray=null;
			model = new CommandModel(100,"Server");
			infoArray = new CommandInfoModel[]{new CommandInfoModel("time", "long", 8)};
			model.setCommandInfoModel(infoArray);
			list.add(model);
			model = new CommandModel(103,"Server");
			infoArray = new CommandInfoModel[]{new CommandInfoModel("accountId", "long", 8),new CommandInfoModel("type", "byte", 1)};
			model.setCommandInfoModel(infoArray);
			list.add(model);
			model = new CommandModel(105,"Client");
			infoArray = new CommandInfoModel[]{new CommandInfoModel("result", "short", 2),new CommandInfoModel("accountId", "long", 8),new CommandInfoModel("type", "byte", 1)};
			model.setCommandInfoModel(infoArray);
			list.add(model);
			model = new CommandModel(106,"Client");
			infoArray = new CommandInfoModel[]{new CommandInfoModel("result", "short", 2),new CommandInfoModel("accountId", "long", 8),new CommandInfoModel("wallow", "int", 4),new CommandInfoModel("wallowTime", "long", 8),new CommandInfoModel("lastDownTime", "long", 8)};
			model.setCommandInfoModel(infoArray);
			list.add(model);

        	return list;
        }
	
	}
