package com.protocol.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TreeItem;

import org.dom4j.Element;

import com.protocol.model.CommandModelVO;
import com.protocol.model.ProtocolItemVO;
import com.protocol.model.RowModelVO;

/**
 * 协议模型构建工具 
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	下午3:50:47
 * @version 1.0
 */
public class ModelBuilder
{
	
	/**
	 * 构建协议模型 
	 * @param file
	 * @return
	 * @create 2014年8月12日 Cico.姜彪
	 */
	@SuppressWarnings("unchecked")
	public static final TreeItem<ProtocolItemVO> buildXmlToModel(File file) {
		final TreeItem<ProtocolItemVO> result = new TreeItem<>();
		final ProtocolItemVO resultValue = new ProtocolItemVO();
		result.setValue(resultValue);
		final XmlParseUtil parseUtil = new XmlParseUtil(file);
		final Element root = parseUtil.getRoot();
		final String name = root.attributeValue(DataUtil.XMLKey.MODEL_NAME);
		final String proxy = root.attributeValue(DataUtil.XMLKey.PROXY);
		final String value = root.attributeValue(DataUtil.XMLKey.VALUE);
		final String info = root.attributeValue(DataUtil.XMLKey.INFO);
		final String packageName = root.attributeValue(DataUtil.XMLKey.PACKAGE);
		resultValue.setRemark(proxy);
		resultValue.setInfo(info);
		resultValue.setValue(value);
		resultValue.setName(name);
		resultValue.setProxy(true);
		resultValue.setPackageName(packageName);
		//获取所有子节点
		final List<Element> commandElements = root.elements(DataUtil.XMLKey.COMMAND);
		final List<TreeItem<ProtocolItemVO>> commandModels = new ArrayList<>(commandElements.size());
		for (Element element : commandElements)
		{
				commandModels.add(buildChildrenMenu(element));
		}
		result.getChildren().addAll(commandModels);
		return result;
	}
	
	
	private static TreeItem<ProtocolItemVO> buildChildrenMenu(Element root) {
		final TreeItem<ProtocolItemVO> result = new TreeItem<>();
		final ProtocolItemVO resultValue = new ProtocolItemVO();
		result.setValue(resultValue);
		final String name = root.attributeValue(DataUtil.XMLKey.NAME);
		final String proxy = root.attributeValue(DataUtil.XMLKey.PROXY);
		final String value = root.attributeValue(DataUtil.XMLKey.VALUE);
		final String info = root.attributeValue(DataUtil.XMLKey.INFO);
//		final String packageName = root.attributeValue(DataUtil.XMLKey.PACKAGE);
		resultValue.setRemark(proxy);
		resultValue.setInfo(info);
		resultValue.setValue(value);
		resultValue.setName(name);
//		resultValue.setPackageName(packageName);
		resultValue.setProxy(false);
		final CommandModelVO commandModel = buildCommandModelVO(root);
		resultValue.infoProperty().bindBidirectional(commandModel.cnNameProperty());
		resultValue.setCommandVO(commandModel);;
		return result;
	}
	
	/**
	 * 构型模型里面的命令
	 * @param command
	 * @return
	 * @create 2014年8月12日 Cico.姜彪
	 */
	@SuppressWarnings("unchecked")
	private static final CommandModelVO buildCommandModelVO(Element command) {
		final CommandModelVO result = new CommandModelVO();
		final String enName = command.attributeValue(DataUtil.XMLKey.CMD_NAME);
		final String cnName = command.attributeValue(DataUtil.XMLKey.INFO);
		final String value = command.attributeValue(DataUtil.XMLKey.VALUE);
		final String direction = command.attributeValue(DataUtil.XMLKey.DIRECTION);
		result.setCnName(cnName);
		result.setDirection(direction);
		result.setEnName(enName);
		result.setValue(value);
		final List<Element> rowElements = command.elements(DataUtil.XMLKey.PARAM);
		final List<TreeItem<RowModelVO>> rowModels = new ArrayList<>(rowElements.size());
		result.setRowModels(rowModels);
		for (Element element : rowElements)
		{
			rowModels.add(buildRowModelVO(element));
		}
		return result;
	}
	
	/**
	 * 构建每一行数据
	 * @param row
	 * @return
	 * @create 2014年8月12日 Cico.姜彪
	 */
	@SuppressWarnings("unchecked")
	private static final TreeItem<RowModelVO> buildRowModelVO(Element row) {
		final TreeItem<RowModelVO> result = new TreeItem<RowModelVO>();
		result.setExpanded(true);
		final RowModelVO rowModelVO = new RowModelVO();
		final List<Element> rowElements = row.elements(DataUtil.XMLKey.PARAM);
		final String name = row.attributeValue(DataUtil.XMLKey.NAME);
		final String type = row.attributeValue(DataUtil.XMLKey.TYPE);
		final String size = row.attributeValue(DataUtil.XMLKey.SIZE);
		final String remark = row.attributeValue(DataUtil.XMLKey.REMARK);
		final String text;
		if(rowElements.size() > 0) {
			buildRowModelChildren(result, row);
			text = row.attributeValue(DataUtil.XMLKey.INFO);
		} else {
			text = row.getTextTrim();
		}
		rowModelVO.setInfo(text);
		rowModelVO.setName(name);
		rowModelVO.setType(type);
		rowModelVO.setSize(size);
		rowModelVO.setRemark(remark);
		result.setValue(rowModelVO);
		return result;
	}
	
	/**
	 * 构建子元素
	 * @param rowModel
	 * @param row
	 * @create 2014年8月12日 Cico.姜彪
	 */
	@SuppressWarnings("unchecked")
	private static final void buildRowModelChildren(TreeItem<RowModelVO> rowModel, Element row) {
		final List<Element> rowElements = row.elements(DataUtil.XMLKey.PARAM);
		final List<TreeItem<RowModelVO>> childrens = new ArrayList<TreeItem<RowModelVO>>(rowElements.size());
		for (Element element : rowElements)
		{
			final TreeItem<RowModelVO> result = new TreeItem<RowModelVO>();
			result.setExpanded(true);
			final RowModelVO rowModelVO = new RowModelVO();
			final String name = element.attributeValue(DataUtil.XMLKey.NAME);
			final String type = element.attributeValue(DataUtil.XMLKey.TYPE);
			final String size = element.attributeValue(DataUtil.XMLKey.SIZE);
			final String remark = element.attributeValue(DataUtil.XMLKey.REMARK);
			final String text;
			if(!element.elements(DataUtil.XMLKey.PARAM).isEmpty()) {
				buildRowModelChildren(result, element);
				text = element.attributeValue(DataUtil.XMLKey.INFO);
			} else {
				text = element.getTextTrim();
			}
			rowModelVO.setInfo(text);
			rowModelVO.setName(name);
			rowModelVO.setType(type);
			rowModelVO.setSize(size);
			rowModelVO.setRemark(remark);
			result.setValue(rowModelVO);
			childrens.add(result);
		}
		rowModel.getChildren().addAll(childrens);
	}
	
}
