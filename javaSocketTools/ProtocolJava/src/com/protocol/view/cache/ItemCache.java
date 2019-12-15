package com.protocol.view.cache;

import java.util.ArrayList;
import java.util.List;

import com.protocol.view.mian.item.CommandView;

/**
 * 每一条命令缓存
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	下午4:53:45
 * @version 1.0
 */
public class ItemCache
{
	private static List<CommandView> commandViewCacheList;
	
	/*初始化缓存*/
	public static void init(int size) {
		commandViewCacheList = new ArrayList<>(size);
		for (int i = 0; i < size; i++)
		{
			commandViewCacheList.add(new CommandView());
		}
		
	}
	
	public static final List<CommandView> getCommandViewCacheList() {
		return commandViewCacheList;
	}
	
	/**
	 * 获取指定大小的集合
	 * @param size
	 * @return
	 * @create 2014年11月10日 Cico.姜彪
	 */
	public static final List<CommandView> getCommandViewCacheList(int size) {
		return commandViewCacheList.subList(0, size);
	}
	
	/**
	 * 添加命令
	 * @param index
	 * @return
	 * @create 2014年11月10日 Cico.姜彪
	 */
	public static final CommandView addCommandView(int index) {
		if(commandViewCacheList.size() > index) {
			return commandViewCacheList.get(index);
		} else {
			CommandView commandView = new CommandView();
			commandViewCacheList.add(commandView);
			return commandView;
		}
	}
	
}
