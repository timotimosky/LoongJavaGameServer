package com.protocol.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.scene.control.TreeItem;

import com.protocol.model.ProtocolItemVO;
import com.protocol.view.cache.ItemCache;

public class BuilderTreeRoot
{

	private final static TreeItem<ProtocolItemVO> root = new TreeItem<>();
	static {
		final ProtocolItemVO rootVO = new ProtocolItemVO();
		rootVO.setInfo("全部协议");
    	root.setValue(rootVO);
    	root.setExpanded(true);
	}
	
	/*最大协议数*/
	private static int maxSize;
	
	public static final TreeItem<ProtocolItemVO> getTreeRoot() {
		return root;
	}
	
	public static final void buildRoot() {
    	root.getChildren().clear();
    	root.getChildren().addAll(buildProtocolMenu());
	}
	
	/**
	 * 构建树目录
	 * @return
	 * @create 2014年9月22日 Cico.姜彪
	 */
	private static List<TreeItem<ProtocolItemVO>> buildProtocolMenu() {
		final File[] files = FileUtil.getDirectorXmlFileCount(new File(SystemConfUtil.getProtocolPath()));
		final List<TreeItem<ProtocolItemVO>> result = new ArrayList<>(files.length);
		TreeItem<ProtocolItemVO> vo;
		for (File file : files)
		{
			vo = ModelBuilder.buildXmlToModel(file);
			if(maxSize < vo.getChildren().size()) {
				maxSize = vo.getChildren().size();
			}
			result.add(vo);
		}
		//排序
		Collections.sort(result, new Comparator<TreeItem<ProtocolItemVO>>()
		{
			@Override
			public int compare(TreeItem<ProtocolItemVO> o1, TreeItem<ProtocolItemVO> o2)
			{
				return o1.getValue().getValue().compareTo(o2.getValue().getValue());
			}
		});
		initCache();
		return result;
	}
	
	/**
	 * 初始化缓存
	 * @create 2014年11月10日 Cico.姜彪
	 */
	private static void initCache() {
		ItemCache.init(maxSize);
	}
	
	
}
