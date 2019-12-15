package com.protocol.listener;

import java.nio.file.WatchEvent.Kind;


/**
 * 文件发生改变监听
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	下午8:54:24
 * @version 1.0
 */
public interface IFileListener
{
	/**
	 * 文件修改的时候触发   
	 * @param path 修改文件的路径
	 * @param eventType 文件修改类型  ENTRY_CREATE 创建 ENTRY_DELETE 删除 ENTRY_MODIFY 修改
	 * @create 2014年12月12日 Cico.姜彪
	 */
	void fileChange(String path, Kind<?> eventType);
}
