package com.protocol.generate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import com.protocol.model.ProtocolItemVO;
import com.protocol.model.RowModelVO;
import com.protocol.utils.DataUtil;


/**
 * 代码生成工具 
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	下午5:32:22
 * @version 1.0
 */
public class CodeGenerate
{
	private final TreeItem<ProtocolItemVO> protocolItemVO;
	private final File tmpFile;
	
	public CodeGenerate(TreeItem<ProtocolItemVO> protocolModel, File tmpFile) {
		this.protocolItemVO = protocolModel;
		this.tmpFile = tmpFile;
	}
	//声明模板
	private String declare;
	
	//命令
	private String commandModel;
	
	//注释参数
	private String params;
	
	//命令解析
	private String commandArray;
	
	//声明命令
	private String command;
	
	//命令详情
	private String commandDetail;
	
	//list命令
	private String commandList;
	
	
	/**
	 * 构建代码文件 
	 * @param filePath
	 * @create 2014年8月12日 Cico.姜彪
	 */
	public void buildFlie(File filePath) {
		String fileName = tmpFile.getName();
		fileName = fileName.substring(0, fileName.indexOf('.'));
		final File file = new File(filePath.getAbsoluteFile() + "/" + protocolItemVO.getValue().getRemark() + "." + fileName);
		final String tmpStr = readFile();
		buildCommand();
		String fileStr = tmpStr.split(DataUtil.TMPKey.SPLIT)[1];
		fileStr = buildClassName(fileStr);
		fileStr = buildCommandDeclare(fileStr);
		fileStr = buildCommandDetail(fileStr);
		writeFile(file, fileStr);
	}
	
	/**
	 * 创建命令 创建命令详情
	 * @create 2014年8月12日 Cico.姜彪
	 */
	private void buildCommand() {
		final StringBuilder commandSb = new StringBuilder();
		final StringBuilder commandDetailSb = new StringBuilder();
		final ObservableList<TreeItem<ProtocolItemVO>> commandList = protocolItemVO.getChildren();
		String declareTmp; 
		String commandModelTmp;
		for (TreeItem<ProtocolItemVO> commandModelVO : commandList)
		{
			declareTmp = declare;
			commandModelTmp = commandModel; 
			declareTmp = declareTmp.replace(DataUtil.TMPKey.CN_NAME, commandModelVO.getValue().getCommandVO().getCnName());
			declareTmp = declareTmp.replace(DataUtil.TMPKey.TYPE, commandModelVO.getValue().getCommandVO().getDirection());
			declareTmp = declareTmp.replace(DataUtil.TMPKey.EN_NAME, commandModelVO.getValue().getCommandVO().getEnName());
			declareTmp = declareTmp.replace(DataUtil.TMPKey.COMMANDVALUE, commandModelVO.getValue().getValue());
			declareTmp = declareTmp.replace(DataUtil.TMPKey.PARAMS, buildParams(commandModelVO.getValue().getCommandVO().getRowModels()));
			commandModelTmp = commandModelTmp.replace(DataUtil.TMPKey.TYPE, commandModelVO.getValue().getCommandVO().getDirection());
			commandModelTmp = commandModelTmp.replace(DataUtil.TMPKey.COMMANDVALUE, commandModelVO.getValue().getValue());
			commandModelTmp = commandModelTmp.replace(DataUtil.TMPKey.COMMAND_ARRAY, buildCommandArray(commandModelVO.getValue().getCommandVO().getRowModels()));
			commandDetailSb.append(commandModelTmp);
			commandSb.append(declareTmp);
		}
		command = commandSb.toString();
		commandDetail = commandDetailSb.toString();
	}
	
	/**
	 * 构建注释参数 
	 * @param rowModels
	 * @return
	 * @create 2014年8月13日 Cico.姜彪
	 */
	private String buildParams(List<TreeItem<RowModelVO>> rowModels) {
		final StringBuilder paramsSb = new StringBuilder();
		String paramsTmp;
		for (TreeItem<RowModelVO> treeItem : rowModels)
		{
			paramsTmp = params;
			paramsTmp = paramsTmp.replace(DataUtil.TMPKey.INFO, treeItem.getValue().getInfo());
			paramsTmp = paramsTmp.replace(DataUtil.TMPKey.NAME, treeItem.getValue().getName());
			paramsTmp = paramsTmp.replace(DataUtil.TMPKey.SIZE, treeItem.getValue().getSize());
			paramsTmp = paramsTmp.replace(DataUtil.TMPKey.TYPE, treeItem.getValue().getType());
			paramsSb.append(paramsTmp);
		}
		return paramsSb.toString();
	}
	
	/**
	 * 
	 * @param rowModels
	 * @return
	 * @create 2014年8月13日 Cico.姜彪
	 */
	private String buildCommandArray(List<TreeItem<RowModelVO>> rowModels) {
		final StringBuilder commandArraySb = new StringBuilder("");
		String CommandArrayTmp;
		for (TreeItem<RowModelVO> treeItem : rowModels)
		{
			CommandArrayTmp = commandArray;
			CommandArrayTmp = CommandArrayTmp.replace(DataUtil.TMPKey.TYPE, treeItem.getValue().getType());
			CommandArrayTmp = CommandArrayTmp.replace(DataUtil.TMPKey.NAME, treeItem.getValue().getName());
			CommandArrayTmp = CommandArrayTmp.replace(DataUtil.TMPKey.SIZE, treeItem.getValue().getSize());
			if(treeItem.getChildren().isEmpty()) {
				CommandArrayTmp = CommandArrayTmp.replace(DataUtil.TMPKey.LIST, "");
			} else {
				final String commanListTmp = commandList.replace(DataUtil.TMPKey.COMMAND_LIST, buildCommandArray(treeItem.getChildren()));
				CommandArrayTmp = CommandArrayTmp.replace(DataUtil.TMPKey.LIST, commanListTmp);
			}
			commandArraySb.append(CommandArrayTmp).append(",");
		}
		if(rowModels.size() > 0) {
			return commandArraySb.substring(0, commandArraySb.length() - 1);
		} else {
			return commandArraySb.toString();
		}
	}
	
	
	
	/**
	 * 构建命令 
	 * @param str
	 * @return
	 * @create 2014年8月12日 Cico.姜彪
	 */
	private String buildCommandDetail(String str) {
		return str.replace(DataUtil.TMPKey.COMMANDDETAIL, commandDetail);
	}
	
	/**
	 * 构建声明
	 * @param str
	 * @return
	 * @create 2014年8月12日 Cico.姜彪
	 */
	private String buildCommandDeclare(String str) {
		return str.replace(DataUtil.TMPKey.COMMAND, command);
	}
	
	/**
	 * 替换类名
	 * @param str
	 * @return
	 * @create 2014年8月12日 Cico.姜彪
	 */
	private String buildClassName(String str) {
		return str.replace(DataUtil.TMPKey.CLASS_NAME, protocolItemVO.getValue().getRemark());
	}
	
	
	/**
	 * 写入到文件
	 * @param file
	 * @param context
	 * @create 2014年8月12日 Cico.姜彪
	 */
	private void writeFile(File file, String context) {
		BufferedWriter writer = null;
		  try
		{
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			writer.write(context);
			
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally {
			if(null != writer) {
				try
				{
					writer.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 读取文件流
	 * @return
	 * @create 2014年8月12日 Cico.姜彪
	 */
	private String readFile() {
		final StringBuilder result = new StringBuilder();
		InputStreamReader read = null;
		BufferedReader bufferedReader = null;
		try
		{
			read = new InputStreamReader( new FileInputStream(tmpFile),"UTF-8");
			bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while((lineTxt = bufferedReader.readLine()) != null){
            	if(lineTxt.startsWith(DataUtil.TMPKey.DECLARE)) {
            		declare = lineTxt.replace(DataUtil.TMPKey.DECLARE, "").replace("\\n", DataUtil.ENTER);
            	} else
            	if(lineTxt.startsWith(DataUtil.TMPKey.COMMANDMODEL)) {
            		commandModel = lineTxt.replace(DataUtil.TMPKey.COMMANDMODEL, "").replace("\\n", DataUtil.ENTER);
            	} else
            	if(lineTxt.startsWith(DataUtil.TMPKey.PARAMS_EQUAL)) {
            		params = lineTxt.replace(DataUtil.TMPKey.PARAMS_EQUAL, "").replace("\\n", DataUtil.ENTER);
            	} else
            	if(lineTxt.startsWith(DataUtil.TMPKey.COMMAND_ARRAY_EQUAL)) {
            		commandArray = lineTxt.replace(DataUtil.TMPKey.COMMAND_ARRAY_EQUAL, "").replace("\\n", DataUtil.ENTER);
            	} else
            	if(lineTxt.startsWith(DataUtil.TMPKey.COMMAND_LIST_EQUAL)) {
            		commandList = lineTxt.replace(DataUtil.TMPKey.COMMAND_LIST_EQUAL, "").replace("\\n", DataUtil.ENTER);
            	}
            	result.append(lineTxt + DataUtil.ENTER);
            }
           
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally {
			 try
			{
				 if(null != bufferedReader) {
					 bufferedReader.close();
				 }
				 
				if (null != read)
				{
					read.close();
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return result.toString();
	}
	
	
}
