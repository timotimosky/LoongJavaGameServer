package com.protocol.control;

import com.protocol.model.CommandModelVO;
import com.protocol.view.mian.item.CommandView;

/**
 * item 操作接口 
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	上午11:09:38
 * @version 1.0
 */
public interface ItemExecuter
{
	/**
	 * 删除命令接口
	 * @param commandModelVO
	 * @param commandItemController
	 * @create 2014年8月15日 Cico.姜彪
	 */
	void delCommand(CommandModelVO commandModelVO, CommandView commandView);
}
