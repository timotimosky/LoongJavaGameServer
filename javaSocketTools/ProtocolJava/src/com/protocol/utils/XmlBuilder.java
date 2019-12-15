package com.protocol.utils;

import java.util.List;

import javafx.scene.control.TreeItem;

import org.dom4j.Element;

import com.protocol.model.ProtocolItemVO;
import com.protocol.model.RowModelVO;

/**
 * 通过model生成xml文件 
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	下午3:33:36
 * @version 1.0
 */
public class XmlBuilder
{
	/**
	 * 将model转换为xml文件
	 * @param model
	 * @param file
	 * @create 2014年8月13日 Cico.姜彪
	 */
	public static final void buildModelToXml(TreeItem<ProtocolItemVO> modelTree, String fileName) {
		final XmlParseUtil parse = XmlParseUtil.createXml(fileName, DataUtil.XMLKey.MODULE);
		final Element root = parse.getRoot();
		final ProtocolItemVO model = modelTree.getValue();
		root.addAttribute(DataUtil.XMLKey.MODEL_NAME, model.getName());
		root.addAttribute(DataUtil.XMLKey.PROXY, model.getRemark());
		root.addAttribute(DataUtil.XMLKey.VALUE, model.getValue());
		root.addAttribute(DataUtil.XMLKey.INFO, model.getInfo());
		root.addAttribute(DataUtil.XMLKey.PACKAGE, model.getPackageName());
		buildCommand(root, modelTree.getChildren());
		parse.save();
	}
	
	/**
	 * 构建Command xml
	 * @param root
	 * @param commandModelVOs
	 * @create 2014年8月13日 Cico.姜彪
	 */
	private static final void buildCommand(Element root, List<TreeItem<ProtocolItemVO>> commandModelVOs) {
		Element command;
		for (TreeItem<ProtocolItemVO> commandModelVO : commandModelVOs)
		{
			command = root.addElement(DataUtil.XMLKey.COMMAND);
			command.addAttribute(DataUtil.XMLKey.CMD_NAME, commandModelVO.getValue().getCommandVO().getEnName());
			command.addAttribute(DataUtil.XMLKey.INFO, commandModelVO.getValue().getCommandVO().getCnName());
			command.addAttribute(DataUtil.XMLKey.VALUE, commandModelVO.getValue().getCommandVO().getValue());
			command.addAttribute(DataUtil.XMLKey.DIRECTION, commandModelVO.getValue().getCommandVO().getDirection());
			if(!commandModelVO.getValue().getCommandVO().getRowModels().isEmpty()) {
				buildParam(command, commandModelVO.getValue().getCommandVO().getRowModels());
			}
		}
	}
	
	/**
	 * 构建参数 xml 
	 * @param command
	 * @param rows
	 * @create 2014年8月13日 Cico.姜彪
	 */
	private static final void buildParam(Element command,  List<TreeItem<RowModelVO>> rows) {
		Element param;
		for (TreeItem<RowModelVO> treeItem : rows)
		{
			param = command.addElement(DataUtil.XMLKey.PARAM);
			param.addAttribute(DataUtil.XMLKey.NAME, treeItem.getValue().getName());
			param.addAttribute(DataUtil.XMLKey.TYPE, treeItem.getValue().getType());
			param.addAttribute(DataUtil.XMLKey.SIZE, treeItem.getValue().getSize());
			param.addAttribute(DataUtil.XMLKey.REMARK, treeItem.getValue().getRemark());
			if(treeItem.getChildren().isEmpty()) {
				param.setText(treeItem.getValue().getInfo());
			} else {
				buildParam(param,  treeItem.getChildren());
				param.addAttribute(DataUtil.XMLKey.INFO, treeItem.getValue().getInfo());
			}
		}
	}
	
	/**
	 * 协议列表保存
	 * @param protocolModels
	 * @param fileName
	 * @create 2014年8月15日 Cico.姜彪
	 */
	public static final void buildProtocolToXml(TreeItem<ProtocolItemVO> protoItem, String fileName) {
		final XmlParseUtil parse = XmlParseUtil.createXml(fileName, DataUtil.XMLKey.MODELS);
		final Element root = parse.getRoot();
		Element model;
		for (TreeItem<ProtocolItemVO> protocolModel : protoItem.getChildren())
		{
			model = root.addElement(DataUtil.XMLKey.MODULE);
			model.addAttribute(DataUtil.XMLKey.NAME, protocolModel.getValue().getName());
			model.addAttribute(DataUtil.XMLKey.PROXY, protocolModel.getValue().getRemark());
			model.addAttribute(DataUtil.XMLKey.VALUE, protocolModel.getValue().getValue());
			model.addAttribute(DataUtil.XMLKey.INFO, protocolModel.getValue().getInfo());
//			model.addAttribute(DataUtil.XMLKey.PACKAGE, protocolModel.getValue().getPackageName());
		}
		parse.save();
	}
	
}
