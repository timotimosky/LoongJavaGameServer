
using Communication.Model;
using System;
using System.Collections;
using System.Linq;

	public class ControllerModel
    {
		 /**
		 *请求进入大地图
	     **/
		 public const int CG_MAINMAP_JOIN = 6601;
		 /**
		 *返回进入大地图
	     **/
		 public const int GC_MAINMAP_JOIN = 6602;
		 /**
		 *请求离开主地图
	     **/
		 public const int CG_MAINMAP_LEAVE = 6611;
		 /**
		 *返回离开主地图
	     **/
		 public const int GC_MAINMAP_LEAVE = 6612;
		 /**
		 *请求开启金矿
	     **/
		 public const int CG_MAINMAP_QUARTS_OPEN = 6613;
		 /**
		 *返回开启金矿
	     **/
		 public const int GC_MAINMAP_QUARTS_OPEN = 6614;
		 /**
		 *请求加速升级金矿
	     **/
		 public const int CG_MAINMAP_QUARTS_UPGRADE = 6615;
		 /**
		 *返回加速升级金矿
	     **/
		 public const int GC_MAINMAP_QUARTS_UPGRADE = 6616;
		 /**
		 *请求收取金币
	     **/
		 public const int CG_MAINMAP_QUARTS_COLLECT = 6617;
		 /**
		 *返回收取金币
	     **/
		 public const int GC_MAINMAP_QUARTS_COLLECT = 6618;
		 /**
		 *请求主将驻守
	     **/
		 public const int CG_MAINMAP_QUARTS_GARRISON = 6619;
		 /**
		 *返回主将驻守
	     **/
		 public const int GC_MAINMAP_QUARTS_GARRISON = 6620;
		 /**
		 *请求主将回营
	     **/
		 public const int CG_MAINMAP_QUARTS_DODGE = 6621;
		 /**
		 *返回主将回营
	     **/
		 public const int GC_MAINMAP_QUARTS_DODGE = 6622;
		 /**
		 *请求金矿信息
	     **/
		 public const int CG_MAINMAP_QUARTS_INFO = 6623;
		 /**
		 *返回金矿信息
	     **/
		 public const int GC_MAINMAP_QUARTS_INFO = 6624;
		 /**
		 *请求征讨兵种
	     **/
		 public const int CG_MAINMAP_CORPS_VGTK = 6631;
		 /**
		 *返回征讨兵种
	     **/
		 public const int GC_MAINMAP_CORPS_VGTK = 6632;
		 /**
		 *请求征讨失败
	     **/
		 public const int CG_MAINMAP_CORPS_FAILED = 6633;
		 /**
		 *返回征讨失败
	     **/
		 public const int GC_MAINMAP_CORPS_FAILED = 6634;
		 /**
		 *请求征讨胜利
	     **/
		 public const int CG_MAINMAP_CORPS_WIN = 6635;
		 /**
		 *返回征讨胜利
	     **/
		 public const int GC_MAINMAP_CORPS_WIN = 6636;
		 /**
		 *请求放弃再次挑战
	     **/
		 public const int CG_MAINMAP_CORPS_CANCEL = 6637;
		 /**
		 *返回放弃再次挑战
	     **/
		 public const int GC_MAINMAP_CORPS_CANCEL = 6638;
		 /**
		 *推送兵种刷新
	     **/
		 public const int GC_MAINMAP_CORPS_PUSH = 6639;
		 /**
		 *请求开始升级
	     **/
		 public const int CG_QUART_START_UPGRADE = 6640;
		 /**
		 *返回开始升级
	     **/
		 public const int GC_QUART_START_UPGRADE = 6641;
		 /**
		 *请求判断是否升级
	     **/
		 public const int CG_QUART_CHECK_UPGRADE = 6642;
		 /**
		 *返回判断是否升级
	     **/
		 public const int GC_QUART_CHECK_UPGRADE = 6643;
		 /**
		 *战斗结束返回
	     **/
		 public const int GC_MAINMAP_CORPS_BATTLEEND = 6644;

	}
}
