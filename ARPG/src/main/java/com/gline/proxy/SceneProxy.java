
package com.gline.proxy;

import java.util.ArrayList;
import java.util.List;
import netWork.model.CommandInfoModel;
import netWork.model.CommandModel;

	public class SceneProxy 
	{
		/**
        *请求进入场景
        *Server
		*	场景类型 
		*	Name:SenceType		TYPE:int		SIZE:4
		*	VIP和JJC房传入密码 
		*	Name:args		TYPE:String		SIZE:20
		*	JJC房传房间ID,其他类型传空 
		*	Name:id		TYPE:long		SIZE:8
        */
        public final static int CG_EnterScene = 5001;
		/**
        *返回进入场景的初始化
        *Server
		*	场景内实体list 
		*	Name:SceneEnityList		TYPE:ListSceneEnity		SIZE:2
		*	玩家炮台ID 
		*	Name:towerid		TYPE:byte		SIZE:1
		*	0成功 1货币不足 2 人数已满 
		*	Name:error		TYPE:byte		SIZE:1
        */
        public final static int GC_EnterScene = 5002;
		/**
        *推送:有玩家进入房间
        *Server
		*	玩家ID 
		*	Name:playerID		TYPE:long		SIZE:8
		*	玩家所装备炮台类型 
		*	Name:Guntype		TYPE:short		SIZE:2
		*	玩家姓名 
		*	Name:playerName		TYPE:String		SIZE:20
		*	所在炮台 
		*	Name:towerid		TYPE:byte		SIZE:1
		*	玩家拥有金币 
		*	Name:gold		TYPE:int		SIZE:4
        */
        public final static int GC_PLAYER_Enter = 5003;
		/**
        *推送:有玩家离开房间
        *Server
		*	玩家ID 
		*	Name:playerID		TYPE:long		SIZE:8
        */
        public final static int GC_PLAYER_EXIT = 5004;
		/**
        *请求:自己离开场景
        *Server
        */
        public final static int CG_SelfLeaveSence = 5005;
		/**
        *自己离开场景
        *Server
        */
        public final static int GC_SelfLeaveSence = 5006;
		/**
        *移除一个场景物体
        *Server
		*	 
		*	Name:EnityId		TYPE:long		SIZE:8
        */
        public final static int GC_REMOVE_Obj = 5007;
		/**
        *获取所有竞技场列表
        *Client
        */
        public final static int CG_GetAllJJC = 5008;
		/**
        *获取所有竞技场列表
        *Server
		*	JJC列表 
		*	Name:JJClist		TYPE:JJClist		SIZE:2
        */
        public final static int GC_GetAllJJC = 5009;
		/**
        *创建一个JJC
        *Server
		*	名字 
		*	Name:name		TYPE:String		SIZE:20
		*	密码 
		*	Name:password		TYPE:String		SIZE:20
		*	门票 
		*	Name:price		TYPE:int		SIZE:4
        */
        public final static int CG_CreateJJc = 5010;
		/**
        *
        *Server
		*	0成功 1失败 
		*	Name:error		TYPE:int		SIZE:4
        */
        public final static int GC_CreateJJc = 5011;

		
		public static List<CommandModel> DATA_MODE()
        {
        	List<CommandModel> list = new ArrayList<CommandModel>();
            CommandModel model= null;
            CommandInfoModel [] infoArray=null;
			model = new CommandModel(5001,"Server");
			infoArray = new CommandInfoModel[]{new CommandInfoModel("SenceType", "int", 4),new CommandInfoModel("args", "String", 20),new CommandInfoModel("id", "long", 8)};
			model.setCommandInfoModel(infoArray);
			list.add(model);
			model = new CommandModel(5002,"Server");
			infoArray = new CommandInfoModel[]{new CommandInfoModel("SceneEnityList", "ListSceneEnity", 2, new CommandInfoModel[]{new CommandInfoModel("fishtype", "int", 4),new CommandInfoModel("posx", "float", 4),new CommandInfoModel("posy", "float", 4),new CommandInfoModel("targetPosX", "float", 4),new CommandInfoModel("targetPosY", "float", 4),new CommandInfoModel("entityID", "long", 8),new CommandInfoModel("remainTime", "float", 4)}),new CommandInfoModel("towerid", "byte", 1),new CommandInfoModel("error", "byte", 1)};
			model.setCommandInfoModel(infoArray);
			list.add(model);
			model = new CommandModel(5003,"Server");
			infoArray = new CommandInfoModel[]{new CommandInfoModel("playerID", "long", 8),new CommandInfoModel("Guntype", "short", 2),new CommandInfoModel("playerName", "String", 20),new CommandInfoModel("towerid", "byte", 1),new CommandInfoModel("gold", "int", 4)};
			model.setCommandInfoModel(infoArray);
			list.add(model);
			model = new CommandModel(5004,"Server");
			infoArray = new CommandInfoModel[]{new CommandInfoModel("playerID", "long", 8)};
			model.setCommandInfoModel(infoArray);
			list.add(model);
			model = new CommandModel(5005,"Server");
			infoArray = new CommandInfoModel[]{};
			model.setCommandInfoModel(infoArray);
			list.add(model);
			model = new CommandModel(5006,"Server");
			infoArray = new CommandInfoModel[]{};
			model.setCommandInfoModel(infoArray);
			list.add(model);
			model = new CommandModel(5007,"Server");
			infoArray = new CommandInfoModel[]{new CommandInfoModel("EnityId", "long", 8)};
			model.setCommandInfoModel(infoArray);
			list.add(model);
			model = new CommandModel(5008,"Client");
			infoArray = new CommandInfoModel[]{};
			model.setCommandInfoModel(infoArray);
			list.add(model);
			model = new CommandModel(5009,"Server");
			infoArray = new CommandInfoModel[]{new CommandInfoModel("JJClist", "JJClist", 2, new CommandInfoModel[]{new CommandInfoModel("name", "String", 20),new CommandInfoModel("price", "int", 4),new CommandInfoModel("id", "long", 8)})};
			model.setCommandInfoModel(infoArray);
			list.add(model);
			model = new CommandModel(5010,"Server");
			infoArray = new CommandInfoModel[]{new CommandInfoModel("name", "String", 20),new CommandInfoModel("password", "String", 20),new CommandInfoModel("price", "int", 4)};
			model.setCommandInfoModel(infoArray);
			list.add(model);
			model = new CommandModel(5011,"Server");
			infoArray = new CommandInfoModel[]{new CommandInfoModel("error", "int", 4)};
			model.setCommandInfoModel(infoArray);
			list.add(model);

        	return list;
        }
	
	}
