package com.protocol.launcher;

import java.nio.file.WatchEvent.Kind;

import com.mnt.gui.base.controller.BaseController;
import com.mnt.gui.base.launcher.BaseLauncher;
import com.mnt.gui.base.style.WindowStyle;
import com.mnt.gui.base.util.FXMLLoaderTool;
import com.protocol.listener.FileListenerManager;
import com.protocol.listener.IFileListener;
import com.protocol.utils.BuilderTreeRoot;
import com.protocol.utils.SystemConfUtil;
import com.protocol.view.mian.ProtocolView;

/**
 * 协议创建工具启动类
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	下午8:27:27
 * @version 1.0
 */
public class ProtocolBuilderLauncher extends BaseLauncher
{
	
	public static boolean isChange = false;

	@Override
	protected BaseController getRoot()
	{
		return FXMLLoaderTool.load(ProtocolView.class);
	}

	@Override
	protected String getTitle()
	{
		return "协议生成工具";
	}
	
	@Override
	protected void loadSource()
	{
		setWindowStyle(WindowStyle.WINDOW_DEFAULT);
		BuilderTreeRoot.buildRoot();
		//添加文件监控
		FileListenerManager.listenerFileChange(SystemConfUtil.getProtocolPath(), new IFileListener()
		{
			@Override
			public void fileChange(String path, Kind<?> eventType)
			{
				//System.out.println(path);
				if(!isChange) {
					BuilderTreeRoot.buildRoot();
				}
			}
		});
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
