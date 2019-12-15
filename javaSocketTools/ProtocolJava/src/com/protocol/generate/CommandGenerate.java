package com.protocol.generate;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.TreeItem;

import com.mnt.gui.base.logger.TipDialogLogger;
import com.protocol.model.ProtocolItemVO;
import com.protocol.model.RowModelVO;
import com.protocol.type.DataType;
import com.protocol.type.RequestType;
import com.protocol.utils.DataUtil;
import com.protocol.utils.FileUtil;
import com.protocol.utils.SystemConfUtil;

/**
 * java端命令生成
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	下午5:37:32
 * @version 1.0
 */
public class CommandGenerate
{
	private static final String MAP_TAG = "-";
	private int i = 0;
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss");
	
	/**
	 * 基础数据类型
	 */
	private static final List<String> baseDataType = new ArrayList<String>(8);
	private static final List<String> loopNames = new ArrayList<>(9);
	static {
		baseDataType.add("java.lang.Integer");
		baseDataType.add("java.lang.Boolean");
		baseDataType.add("java.lang.Character");
		baseDataType.add("java.lang.Float");
		baseDataType.add("java.lang.Short");
		baseDataType.add("java.lang.Byte");
		baseDataType.add("java.lang.Double");
		baseDataType.add("java.lang.Long");
		baseDataType.add("java.lang.String");
		
		loopNames.add("i");
		loopNames.add("j");
		loopNames.add("k");
		loopNames.add("m");
		loopNames.add("s");
		loopNames.add("q");
		loopNames.add("x");
		loopNames.add("y");
		loopNames.add("z");
		loopNames.add("a");
		loopNames.add("b");
		loopNames.add("c");
		loopNames.add("d");
		loopNames.add("e");
		loopNames.add("f");
	}
	
	private final ProtocolItemVO protocol;
	private final String className;
	private final RequestType requestType;
	private final String fileName;
	
	public CommandGenerate(ProtocolItemVO protocol, RequestType type, String fileName) {
		this.protocol = protocol;
		className = protocol.getCommandVO().getEnName();
//		if(protocol.getCommandVO().getDirection().equals(RequestType.CLIENT.getCode())) {
//			requestType = type;
//		} else {
//			requestType =type;
//		}
		this.requestType = type;
		this.fileName = fileName;
		i = 0;
	}
	
	private String importPackage;
	private String declareParams;
	@SuppressWarnings("unused")
	private String rowData;
	private String listData;
	private String innerListData;
	private String innerBaseTypeListData;
	private String baseData;
	@SuppressWarnings("unused")
	private String listType;
	@SuppressWarnings("unused")
	private String mapType;
	private String voluationParam;
	private String baseTypeListData;
	private String baseTypeData;
	//map
	private String baseTypeMapData;
	private String innerBaseTypeMapData;
	private String baseTypeRowMapData;
	private String mapData;
	private String innerMapData;
	private String baseRowMapData;
	//array
	private String baseTypeArrayData;
	private String innerBaseTypeArrayData;
	private String baseTypeRowArrayData;
	private String arrayData;
	private String innerArrayData;
	private String baseRowArrayData;
	
	private List<String> importPackages = new ArrayList<String>(1);
	
	//根据内部类型获取模板
	private String getTempByInnerType(String type) {
		if(type.startsWith(DataType.LIST.getName())){
			return innerListData;
		} else if(type.startsWith(DataType.MAP.getName())) {
			return innerMapData;
		} else if(type.startsWith(DataType.ARRAY.getName())) {
			return innerArrayData;
		}
		return "";
	}
	
	private String getTempByBaseType(String type) {
		if(type.startsWith(DataType.LIST.getName())){
			return baseTypeListData;
		} else if(type.startsWith(DataType.MAP.getName())) {
			return baseTypeMapData;
		} else if(type.startsWith(DataType.ARRAY.getName())) {
			return baseTypeArrayData;
		}
		return "";
	}
	
	//根据类型获取模板
	private String getTempByType(String type) {
		if(type.startsWith(DataType.LIST.getName())){
			return listData;
		} else if(type.startsWith(DataType.MAP.getName())) {
			return mapData;
		} else if(type.startsWith(DataType.ARRAY.getName())) {
			return arrayData;
		}
		return "";
	}
	
	//根据类型获取一行数据模板
	private String getRowTempByType(String type) {
		if(type.startsWith(DataType.LIST.getName())){
			return baseData;
		} else if(type.startsWith(DataType.MAP.getName())) {
			return baseRowMapData;
		} else if(type.startsWith(DataType.ARRAY.getName())) {
			return baseRowArrayData;
		}
		return "";
	}
	//根据类型获取一行内部数据模板
	private String getBaseTypeRowTempByType(String type) {
		if(type.startsWith(DataType.LIST.getName())){
			return baseTypeData;
		} else if(type.startsWith(DataType.MAP.getName())) {
			return baseTypeRowMapData;
		} else if(type.startsWith(DataType.ARRAY.getName())) {
			return baseTypeRowArrayData;
		}
		return "";
	}
	
	private String getBaseTypeInnerTempByType(String type) {
		if(type.startsWith(DataType.LIST.getName())){
			return innerBaseTypeListData;
		} else if(type.startsWith(DataType.MAP.getName())) {
			return innerBaseTypeMapData;
		} else if(type.startsWith(DataType.ARRAY.getName())) {
			return innerBaseTypeArrayData;
		}
		return "";
	}
	
	public File getFile(String packageName) {
		final File codeFile = new File(className + ".java");
		String tmpString = readFile();
		tmpString = tmpString.split(DataUtil.TMPKey.SPLIT)[1];
		tmpString = tmpString.replace(DataUtil.TMPKey.CLASS_NAME, className);
		tmpString = tmpString.replace(DataUtil.TMPKey.PACKAGE_NAME, packageName);
		tmpString = tmpString.replace(DataUtil.TMPKey.INFO, protocol.getInfo());
		tmpString = tmpString.replace(DataUtil.TMPKey.PACKAGES, buildPackages());
		tmpString = tmpString.replace(DataUtil.TMPKey.DATE, dateFormat.format(new Date()));
		if(requestType == RequestType.CLIENT) {
			tmpString = buildCMParam(tmpString);
		} else {
			tmpString = buildSMParam(tmpString);
		}
		codeFile.deleteOnExit();
		FileUtil.writeFile(codeFile, tmpString);
		return codeFile;
	}
	
	/**
	 * 替换cm类型参数
	 * @param tmpString
	 * @return
	 * @create 2014年12月4日 Cico.姜彪
	 */
	private String buildCMParam(String tmpString) {
		tmpString = tmpString.replace(DataUtil.TMPKey.DECLARE_PARAMS, buildDeclareParams());
		tmpString = tmpString.replace(DataUtil.TMPKey.READ_PARAMS, buildReadParams());
		return tmpString;
	}
	
	/**
	 * 替换sm类型参数
	 * @param tmpString
	 * @return
	 * @create 2014年12月4日 Cico.姜彪
	 */
	private String buildSMParam(String tmpString) {
		tmpString = tmpString.replace(DataUtil.TMPKey.DECLARE_PARAMS, buildDeclareParams());
		//构建方法参数
		tmpString = tmpString.replace(DataUtil.TMPKey.FUNCTION_PARAM, buildFunctionParam());
		//写入赋值
		tmpString = tmpString.replace(DataUtil.TMPKey.VOLUATION_PARAM, buildVoluationParam());
		//写入写值详情
		tmpString = tmpString.replace(DataUtil.TMPKey.WRITE_PARAMS, buildWriteParams());
		return tmpString;
	}
	
	/**
	 * 构建读入参数
	 * @return
	 * @create 2014年12月4日 Cico.姜彪
	 */
	private String buildReadParams() {
		final List<TreeItem<RowModelVO>> params = protocol.getCommandVO().getRowModels();
		String readParamsTmp;
		StringBuilder sb = new StringBuilder();
//		buildReadParams(sb, params);
		for (TreeItem<RowModelVO> rowParam : params)
		{
			readParamsTmp = "		";
			DataType type;
			if(rowParam.getChildren().isEmpty()) {
				type = DataType.getValueFor(rowParam.getValue().getType());
				readParamsTmp = readParamsTmp + rowParam.getValue().getName() + " = " + type.getReadBuff() + DataUtil.ENTER;
			} else {
				final String remark = rowParam.getValue().getRemark();
				if(remark == null || remark.equals("")) {
					Platform.runLater(new Runnable()
					{
						@Override
						public void run()
						{
							TipDialogLogger.error("参数: " + rowParam.getValue().getName() + " 没有指定list/map/array的包");
						}
					});
					//没有指定包名则打断
					return "";
				}
				
				//第一个类名
				String className1;
				//第二个类名
				String className2 = "";
				String packageName1 = "";
				String packageName2 = "";
				if(remark.contains(MAP_TAG)) {
					final String [] packages =  remark.split(MAP_TAG);
					className1 = parsePackageClassName(packages[0]);
					className2 = parsePackageClassName(packages[1]);
					packageName1 = packages[0];
					packageName2 = packages[1];
				} else {
					className1 = parsePackageClassName(remark);
				}
				//为Map的时候处理
				if(remark.contains(MAP_TAG)) {
					readParamsTmp = getTempByType(rowParam.getValue().getType());
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.CLASS_NAME, className1);
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.CLASS_NAME_1, className1);
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.CLASS_NAME_2, className2);
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.CLASS_JAVA_NAME, filterGeneric(lowIndex(className1)));
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.CLASS_JAVA_NAME_1, buildMapEntityCreate(lowIndex(className1), packageName1));
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.CLASS_JAVA_NAME_2, buildMapEntityCreate(lowIndex(className2), packageName2));
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.ENTITY_NAME_1, lowIndex(className1));
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.ENTITY_NAME_2, lowIndex(className2));
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.PARAM_NAME, rowParam.getValue().getName());
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.LIST_NAME, rowParam.getValue().getName());
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.LOOP_PARAM, loopNames.get(i ++));
					if(!importPackages.contains(remark)) {
						importPackages.add(remark);
					}
					readParamsTmp =  readParamsTmp.replace(DataUtil.TMPKey.READ_DETAIL, buildReadDetail(rowParam.getChildren(), className1, remark));
				} else {
					
					if(baseDataType.contains(remark)) {
						readParamsTmp = getTempByBaseType(rowParam.getValue().getType());
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.CLASS_NAME, className1);
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.CLASS_JAVA_NAME, filterGeneric(lowIndex(className1)));
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.ENTITY_NAME, className1);
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.PARAM_NAME, rowParam.getValue().getName());
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.LIST_NAME, rowParam.getValue().getName());
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.LOOP_PARAM, loopNames.get(i ++));
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.READ_DETAIL, buildBaseReadDetail(rowParam.getChildren().get(0), className1, getBaseTypeRowTempByType(rowParam.getValue().getType())));
					} else {
						readParamsTmp = getTempByType(rowParam.getValue().getType());
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.CLASS_NAME, className1);
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.CLASS_JAVA_NAME, filterGeneric(lowIndex(className1)));
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.ENTITY_NAME, className1);
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.PARAM_NAME, rowParam.getValue().getName());
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.LIST_NAME, rowParam.getValue().getName());
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.LOOP_PARAM, loopNames.get(i ++));
						if(!importPackages.contains(remark)) {
							importPackages.add(remark);
						}
//					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.READ_DETAIL, buildBaseReadDetail(rowParam.getChildren().get(0), className, baseData));
						readParamsTmp =  readParamsTmp.replace(DataUtil.TMPKey.READ_DETAIL, buildReadDetail(rowParam.getChildren(), className1, remark));
				}
				
				}
			}
			sb.append(readParamsTmp);
		}
		
		return sb.toString();
	}
	
//	/**
//	 * 递归解析参数
//	 * @param sb
//	 * @param params
//	 * @create 2014年12月4日 Cico.姜彪
//	 */
//	private void buildReadParams(StringBuilder sb, List<TreeItem<RowModelVO>> params ) {
//		String readParamsTmp;
//		for (TreeItem<RowModelVO> rowParam : params)
//		{
//			readParamsTmp = "        ";
//			DataType type;
//			if(rowParam.getChildren().isEmpty()) {
//				type = DataType.getValueFor(rowParam.getValue().getType());
//				readParamsTmp = readParamsTmp + rowParam.getValue().getName() + " = " + type.getReadBuff() + DataUtil.ENTER;
//			}
//			sb.append(readParamsTmp);
//		}
//	}
	
	/**
	 * 构建写入参数
	 * @return
	 * @create 2014年12月4日 Cico.姜彪
	 */
	private String buildWriteParams() {
		final List<TreeItem<RowModelVO>> params = protocol.getCommandVO().getRowModels();
		String writeParamsTmp;
		StringBuilder sb = new StringBuilder();
		for (TreeItem<RowModelVO> rowParam : params)
		{
			writeParamsTmp = "		";
			DataType type;
			if(rowParam.getChildren().isEmpty()) {
				type = DataType.getValueFor(rowParam.getValue().getType());
				writeParamsTmp = writeParamsTmp +  type.getWriteBuff() + DataUtil.ENTER;
				writeParamsTmp = writeParamsTmp.replace(DataUtil.TMPKey.PARAM, rowParam.getValue().getName());
			} else {
				final String remark = rowParam.getValue().getRemark();
				if(remark == null || remark.equals("")) {
					Platform.runLater(new Runnable()
					{
						@Override
						public void run()
						{
							TipDialogLogger.error("参数: " + rowParam.getValue().getName() + " 没有指定list的包");
						}
					});
					//没有指定包名则打断
					return "";
				}
				//第一个类名
				final String className1;
				//第二个类名
				String className2 = "";
				if(remark.contains(MAP_TAG)) {
					final String [] packages =  remark.split(MAP_TAG);
					className1 = parsePackageClassName(packages[0]);
					className2 = parsePackageClassName(packages[1]);
				} else {
					className1 = parsePackageClassName(remark);
				}
				//XXX 写
				final String listName =  upperInde(rowParam.getValue().getName().replace("array", "").replace("list", "").replace("map", ""));
				if(baseDataType.contains(remark)) {
					writeParamsTmp = getTempByBaseType(rowParam.getValue().getType());
					writeParamsTmp = writeParamsTmp.replace(DataUtil.TMPKey.CLASS_NAME, className1);
					writeParamsTmp = writeParamsTmp.replace(DataUtil.TMPKey.CLASS_JAVA_NAME, lowIndex(className1));
					writeParamsTmp = writeParamsTmp.replace(DataUtil.TMPKey.ENTITY_NAME, className1);
					writeParamsTmp = writeParamsTmp.replace(DataUtil.TMPKey.PARAM_NAME, rowParam.getValue().getName());
					writeParamsTmp = writeParamsTmp.replace(DataUtil.TMPKey.LIST_NAME, rowParam.getValue().getName());
					if(writeParamsTmp.contains(DataUtil.TMPKey.LOOP_PARAM)) {
						writeParamsTmp = writeParamsTmp.replace(DataUtil.TMPKey.LOOP_PARAM, loopNames.get(i ++));
					}
					writeParamsTmp = writeParamsTmp.replace(DataUtil.TMPKey.WRITE_DETAIL, buildBaseWriteDetail(rowParam.getChildren().get(0), lowIndex(listName), getBaseTypeRowTempByType(rowParam.getValue().getType())));

				} else {
					writeParamsTmp = getTempByType(rowParam.getValue().getType());
					writeParamsTmp = writeParamsTmp.replace(DataUtil.TMPKey.CLASS_NAME, className1);
					writeParamsTmp = writeParamsTmp.replace(DataUtil.TMPKey.CLASS_JAVA_NAME, lowIndex(className1));
					writeParamsTmp = writeParamsTmp.replace(DataUtil.TMPKey.ENTITY_NAME, className1);
					writeParamsTmp = writeParamsTmp.replace(DataUtil.TMPKey.ENTITY_NAME_1, className1); 
					writeParamsTmp = writeParamsTmp.replace(DataUtil.TMPKey.ENTITY_NAME_2, className2); 
					writeParamsTmp = writeParamsTmp.replace(DataUtil.TMPKey.PARAM_NAME, rowParam.getValue().getName());
					writeParamsTmp = writeParamsTmp.replace(DataUtil.TMPKey.LIST_NAME, rowParam.getValue().getName());
					if(writeParamsTmp.contains(DataUtil.TMPKey.LOOP_PARAM)) {
						writeParamsTmp = writeParamsTmp.replace(DataUtil.TMPKey.LOOP_PARAM, loopNames.get(i ++));
					}
					writeParamsTmp =  writeParamsTmp.replace(DataUtil.TMPKey.WRITE_DETAIL, buildWriteDetail(rowParam.getChildren(), rowParam.getValue().getName(), remark));
					if(!importPackages.contains(remark)) {
						importPackages.add(remark);
					}
				}
			}
			sb.append(writeParamsTmp);
		}
		
		return sb.toString();
	}
	
	/**
	 * 检测和解析byte和short类型
	 * @return
	 * @create 2014年12月16日 Cico.姜彪
	 */
	private String checkAndParseByteOrShort(String type ) {
		if(type.equals("byte") || type.equals("short")) {
			return "int";
		} else {
			return type;
		}
	}
	
	/**
	 * 构建传入方法的参数
	 * @return
	 * @create 2014年12月5日 Cico.姜彪
	 */
	private String buildFunctionParam() {
		final StringBuilder detailStrBuilder = new StringBuilder();
		String functionTmp;
		final List<TreeItem<RowModelVO>> params = protocol.getCommandVO().getRowModels();
		for (TreeItem<RowModelVO> rowParam : params)
		{
			functionTmp = " #{type} #{param},";
			functionTmp = functionTmp.replace(DataUtil.TMPKey.PARAM, rowParam.getValue().getName());
			//判断是否为list
			if(rowParam.getChildren().isEmpty()) {
				functionTmp = functionTmp.replace(DataUtil.TMPKey.TYPE, checkAndParseByteOrShort(rowParam.getValue().getType()));
			} else {
				DataType type = DataType.getValueFor(rowParam.getValue().getType());
				//为list/map/array时
				final String packageName = rowParam.getValue().getRemark();
				//map
				if(packageName.contains(MAP_TAG)) {
					final String[] classTypes = packageName.split(MAP_TAG);
					final String className1 = classTypes[0].substring(classTypes[0].lastIndexOf('.') + 1);
					final String className2 = classTypes[1].substring(classTypes[1].lastIndexOf('.') + 1);
					final String map = "java.util.Map";
					if(!importPackages.contains(map)) {
						importPackages.add(map);
					}
					if(!importPackages.contains(packageName) && !baseDataType.contains(packageName)) {
						importPackages.add(packageName);
					}
					functionTmp = functionTmp.replace(DataUtil.TMPKey.TYPE, "Map<" + className1 +" ,"+ className2 + ">");

				} else {
					//list
//					final String className = packageName.substring(packageName.lastIndexOf('.') + 1);
//					final String list = "java.util.List";
//					if(!importPackages.contains(list)) {
//						importPackages.add(list);
//					}
//					if(!importPackages.contains(packageName) && !baseDataType.contains(packageName)) {
//						importPackages.add(packageName);
//					}
//					functionTmp = functionTmp.replace(DataUtil.TMPKey.TYPE, "List<" + className + ">");
					final String className = packageName.substring(packageName.lastIndexOf('.') + 1);
					if(type == DataType.LIST) {
						//list
						final String list = "java.util.List";
						if(!importPackages.contains(list)) {
							importPackages.add(list);
						}
						functionTmp = functionTmp.replace(DataUtil.TMPKey.TYPE, "List<" + className + ">");
					} else if(type == DataType.ARRAY){
						//array
						functionTmp = functionTmp.replace(DataUtil.TMPKey.TYPE, className + "[]");
					}
					
					if(!importPackages.contains(packageName) && !baseDataType.contains(packageName)) {
						importPackages.add(packageName);
					}
				}
			}
			detailStrBuilder.append(functionTmp);
	}
	return detailStrBuilder.length() == 0 ? "" : detailStrBuilder.substring(1, detailStrBuilder.length() - 1).toString();
	}
	
	/**
	 * 构建实例化参数
	 * @return
	 * @create 2014年12月5日 Cico.姜彪
	 */
	private String buildVoluationParam() {
		final StringBuilder detailStrBuilder = new StringBuilder();
		String functionTmp;
		final List<TreeItem<RowModelVO>> params = protocol.getCommandVO().getRowModels();
		for (TreeItem<RowModelVO> rowParam : params)
		{
			functionTmp = voluationParam;
			functionTmp = functionTmp.replace(DataUtil.TMPKey.PARAM, rowParam.getValue().getName());
			detailStrBuilder.append(functionTmp);
		}
		return detailStrBuilder.toString();
	}
	
	
	/**
	 * 构建方法详情
	 * @return
	 * @create 2014年8月20日 Cico.姜彪
	 */
	private String buildDeclareParams()
	{
		final StringBuilder detailStrBuilder = new StringBuilder();
		String declareTmp;
		final List<TreeItem<RowModelVO>> params = protocol.getCommandVO().getRowModels();
		for (TreeItem<RowModelVO> rowParam : params)
		{
				declareTmp = declareParams;
				declareTmp = declareTmp.replace(DataUtil.TMPKey.INFO, rowParam.getValue().getInfo());
				declareTmp = declareTmp.replace(DataUtil.TMPKey.PARAM, rowParam.getValue().getName());
				//判断是否为list
				if(rowParam.getChildren().isEmpty()) {
					declareTmp = declareTmp.replace(DataUtil.TMPKey.TYPE, checkAndParseByteOrShort(rowParam.getValue().getType()));
				} else {
					DataType type = DataType.getValueFor(rowParam.getValue().getType());
					//为list/map/array时
					final String packageName = rowParam.getValue().getRemark();
					//map
					if(packageName.contains(MAP_TAG)) {
						final String[] classTypes = packageName.split(MAP_TAG);
						final String className1 = classTypes[0].substring(classTypes[0].lastIndexOf('.') + 1);
						final String className2 = classTypes[1].substring(classTypes[1].lastIndexOf('.') + 1);
						final String map = "java.util.Map";
						if(!importPackages.contains(map)) {
							importPackages.add(map);
						}
						if(!importPackages.contains(packageName) && !baseDataType.contains(packageName)) {
							importPackages.add(packageName);
						}
						declareTmp = declareTmp.replace(DataUtil.TMPKey.TYPE, "Map<" + className1 +" ,"+ className2 + ">");

					} else {
						final String className = packageName.substring(packageName.lastIndexOf('.') + 1);
						if(type == DataType.LIST) {
							//list
							final String list = "java.util.List";
							if(!importPackages.contains(list)) {
								importPackages.add(list);
							}
							declareTmp = declareTmp.replace(DataUtil.TMPKey.TYPE, "List<" + className + ">");
						} else if(type == DataType.ARRAY){
							//array
							declareTmp = declareTmp.replace(DataUtil.TMPKey.TYPE, className + "[]");
						}
						
						if(!importPackages.contains(packageName) && !baseDataType.contains(packageName)) {
							importPackages.add(packageName);
						}
						
					}
				}
				detailStrBuilder.append(declareTmp);
		}
		return detailStrBuilder.toString();
	}

	/**
	 * 解析包名的class
	 * @param packageName
	 * @return
	 * @create 2014年12月5日 Cico.姜彪
	 */
	private String parsePackageClassName(String packageName) {
		return packageName.substring(packageName.lastIndexOf('.') + 1);
	}
	
//	/**
//	 * 构建注释
//	 * @param rowModels
//	 * @return
//	 * @create 2014年8月20日 Cico.姜彪
//	 */
//	private String buildNodes(List<TreeItem<RowModelVO>> rowModels)
//	{
//		StringBuilder nodeStrBuild = new StringBuilder("");
//		String nodeTmp;
//		for (TreeItem<RowModelVO> treeItem : rowModels)
//		{
//			nodeTmp = "";
//			nodeTmp = nodeTmp.replace(DataUtil.TMPKey.NAME, treeItem.getValue().getName());
//			nodeTmp = nodeTmp.replace(DataUtil.TMPKey.INFO, treeItem.getValue().getInfo());
//			nodeStrBuild.append(nodeTmp);
//		}
//		return nodeStrBuild.toString();
//	}


//	/**
//	 * 构造参数 
//	 * @param rowModels
//	 * @return
//	 * @create 2014年8月20日 Cico.姜彪
//	 */
//	private String buildParams(List<TreeItem<RowModelVO>> rowModels)
//	{
//		final StringBuilder paramsStrBuilder = new StringBuilder();
//		String paramTmp = "";
//		for (TreeItem<RowModelVO> treeItem : rowModels)
//		{
//			paramTmp = ",";
//			if(null == treeItem.getValue().getRemark() || treeItem.getValue().getRemark().equals("")) {
//				paramTmp = paramTmp + treeItem.getValue().getType();
//			} else {
//				final String packageName = treeItem.getValue().getRemark();
//				final String className = packageName.substring(packageName.lastIndexOf('.') + 1);
//				final String list = "java.util.List";
//				if(!importPackages.contains(list)) {
//					importPackages.add(list);
//				}
//				if(!importPackages.contains(packageName)) {
//					importPackages.add(packageName);
//				}
//				paramTmp = paramTmp + "List<" + className + ">";
//			}
//			paramTmp = paramTmp + " " + treeItem.getValue().getName();
//			paramsStrBuilder.append(paramTmp).append(" ");
//		}
//		return paramsStrBuilder.toString();
//	}


	/**
	 * 构建引入的包 
	 * @return
	 * @create 2014年8月19日 Cico.姜彪
	 */
	private String buildPackages() {
		final StringBuilder packagesBullder = new StringBuilder();
		String importPackageTmp = "";
		System.out.println("CommandGenerate.buildPackages()" + importPackages);
		for (String packageName : importPackages)
		{
			importPackageTmp = importPackage;
			importPackageTmp = importPackageTmp.replace(DataUtil.TMPKey.PACKAGE, packageName);
		}
		packagesBullder.append(importPackageTmp);
		return packagesBullder.toString();
	}
	
	private String readFile() {
		final StringBuilder result = new StringBuilder();
		final BufferedReader bufferedReader = SystemConfUtil.getJavaCommandBufferedReader("java", fileName, requestType);
		try
		{
			if(requestType == RequestType.CLIENT) {
				readCMFile(result, bufferedReader);
			} else {
				readSMFile(result, bufferedReader);
			}
           
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return result.toString();
	}
	
	//过滤泛型
	private String filterGeneric(String name) {
		if(name.contains("<")) {
			return name.substring(0, name.indexOf("<"));
		}
		return name;
	}
	
	
	
	//构建Map实体实例化
	private String buildMapEntityCreate(String className, String packageName) {
		if(baseDataType.contains(packageName)) {
			if(packageName.equals("java.lang.String")) {
				return "";
			} else {
				return "0";
			}
		} else {
			return "new " + className + "()";
		}
	}
	
	/**
	 * 构建接收的 list详情 
	 * @param rowModelVOs
	 * @param classJavaName
	 * @return
	 * @create 2014年8月19日 Cico.姜彪
	 */
	private String buildReadDetail(List<TreeItem<RowModelVO>> rowModelVOs, String classJavaName, String parentPackageName)
	{
		StringBuilder readBuild = new StringBuilder();
		String readParamsTmp;
		for (TreeItem<RowModelVO> rowParam : rowModelVOs)
		{
			DataType type;
			if(rowParam.getChildren().isEmpty()) {
				if(DataType.MAP == DataType.getValueFor(rowParam.getParent().getValue().getType())) {
					final String packageName = rowParam.getParent().getValue().getRemark();
					//第一个类名
					String className1 = "";
					//第二个类名
					String className2 = "";
					readParamsTmp = baseRowMapData;
					boolean isKey = false;
					if(packageName.contains(MAP_TAG)) {
						final String [] packages =  packageName.split(MAP_TAG);
						className1 = parsePackageClassName(packages[0]);
						className2 = parsePackageClassName(packages[1]);
						
						if(getKeyOrValue(rowParam.getValue().getRemark()).equals("Key")) {
							isKey = true;
							if(baseDataType.contains(packages[0])) {
								readParamsTmp = baseTypeRowMapData;
							} else {
								if(!importPackages.contains(packages[0])) {
									importPackages.add(packages[0]);
								}
							}
						} else {
							if(baseDataType.contains(packages[1])) {
								readParamsTmp = baseTypeRowMapData;
							} else {
								if(!importPackages.contains(packages[1])) {
									importPackages.add(packages[1]);
								}
							}
						}
					}
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.KEY_VALUE, getKeyOrValue(rowParam.getValue().getRemark())); 
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.TYPE, upperInde(rowParam.getValue().getType())); 
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.PARENT_NAME, lowIndex(isKey ? className1 : className2));
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.PARAM_NAME, upperInde(rowParam.getValue().getName()));
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.CLASS_JAVA_NAME, lowIndex(isKey ? className1 : className2));
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.INFO, rowParam.getValue().getInfo());
				}  else {
					readParamsTmp = getRowTempByType(rowParam.getParent().getValue().getType());
					type = DataType.getValueFor(rowParam.getValue().getType());
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.PARENT_NAME, lowIndex(parsePackageClassName(parentPackageName)));
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.PARAM_NAME, upperInde(rowParam.getValue().getName()));
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.TYPE, upperInde(type.getName()));
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.INFO, rowParam.getValue().getInfo());
				}
			} else {
				final String remark = rowParam.getValue().getRemark();
				if(remark == null || remark.equals("")) {
					Platform.runLater(new Runnable()
					{
						@Override
						public void run()
						{
							TipDialogLogger.error("参数: " + rowParam.getValue().getName() + " 没有指定list的包");
						}
					});
					//没有指定包名则打断
					return "";
				}
				
				//第一个类名
				String className1;
				//第二个类名
				String className2 = "";
				
				String packageName1 = "";
				String packageName2 = "";
				if(remark.contains(MAP_TAG)) {
					final String [] packages =  remark.split(MAP_TAG);
					className1 = parsePackageClassName(packages[0]);
					className2 = parsePackageClassName(packages[1]);
					packageName1 = packages[0];
					packageName2 = packages[1];
				} else {
					className1 = parsePackageClassName(remark);
				}
				//为Map的时候处理
				if(remark.contains(MAP_TAG)) {
					readParamsTmp = getTempByInnerType(rowParam.getValue().getType());
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.CLASS_NAME, className1);
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.CLASS_NAME_1, className1);
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.CLASS_NAME_2, className2);
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.CLASS_JAVA_NAME, filterGeneric(lowIndex(className1)));
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.CLASS_JAVA_NAME_1, buildMapEntityCreate(lowIndex(className1), packageName1));
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.CLASS_JAVA_NAME_2, buildMapEntityCreate(lowIndex(className2), packageName2));
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.ENTITY_NAME, className1);
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.ENTITY_NAME_1, lowIndex(className1));
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.ENTITY_NAME_2, lowIndex(className2));
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.PARAM_NAME, rowParam.getValue().getName());
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.PARAM_1, buildMapEntityCreate(lowIndex(className1), packageName1));
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.PARAM_2, buildMapEntityCreate(lowIndex(className2), packageName2));
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.LIST_NAME, rowParam.getValue().getName());
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.PARENT_NAME, lowIndex(parsePackageClassName(parentPackageName)));
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.UPPER_PARAM_NAME, upperInde(rowParam.getValue().getName()));
					readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.LOOP_PARAM, loopNames.get(i ++));
					if(!importPackages.contains(remark)) {
						importPackages.add(remark);
					}
					readParamsTmp =  readParamsTmp.replace(DataUtil.TMPKey.READ_DETAIL, buildReadDetail(rowParam.getChildren(), className1, remark));
				}  else {
					if(baseDataType.contains(remark)) {
						readParamsTmp = getBaseTypeInnerTempByType(rowParam.getValue().getType());
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.CLASS_NAME, className1);
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.CLASS_JAVA_NAME, filterGeneric(lowIndex(className1)));
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.LOOP_PARAM, loopNames.get(i ++));
//						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.ENTITY_NAME, className);
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.PARAM_NAME, rowParam.getValue().getName());
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.UPPER_PARAM_NAME, upperInde(rowParam.getValue().getName()));
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.LIST_NAME, rowParam.getValue().getName());
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.PARENT_NAME, lowIndex(parsePackageClassName(parentPackageName)));
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.TYPE, parsePackageClassName(rowParam.getValue().getRemark()));
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.READ_DETAIL, buildBaseReadDetail(rowParam.getChildren().get(0), className1, getBaseTypeRowTempByType(rowParam.getValue().getType())));
					} else {
						System.out.println(remark); //XXX 读
						readParamsTmp = getTempByInnerType(rowParam.getValue().getType());
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.CLASS_NAME, className1);
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.CLASS_JAVA_NAME, filterGeneric(lowIndex(className1)));
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.LOOP_PARAM, loopNames.get(i ++));
//						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.ENTITY_NAME, className);
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.UPPER_PARAM_NAME, upperInde(rowParam.getValue().getName()));
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.PARAM_NAME, rowParam.getValue().getName());
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.PARENT_NAME, lowIndex(parsePackageClassName(parentPackageName)));
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.LIST_NAME, rowParam.getValue().getName());
						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.TYPE, parsePackageClassName(rowParam.getValue().getRemark()));
						if(!importPackages.contains(remark)) {
							importPackages.add(remark);
						}
//						readParamsTmp = readParamsTmp.replace(DataUtil.TMPKey.READ_DETAIL, buildBaseReadDetail(rowParam.getChildren().get(0), className, baseData));
						readParamsTmp =  readParamsTmp.replace(DataUtil.TMPKey.READ_DETAIL, buildReadDetail(rowParam.getChildren(), className1, remark));
					}
				
				}
			}
			readBuild.append(readParamsTmp);
		}
		
		return readBuild.toString();
	}
	
	
	/**
	 * 构建写入详情
	 * @return
	 * @create 2014年8月14日 Cico.姜彪
	 */
	private String buildWriteDetail(List<TreeItem<RowModelVO>> rowModelVOs, String paramName, String parentPackageName) {
		StringBuilder writeDetailBuild = new StringBuilder();
		String writeDataTmp;
		for (TreeItem<RowModelVO> vo : rowModelVOs)
		{
			if(vo.getChildren().isEmpty()) {
				writeDataTmp = getRowTempByType(vo.getParent().getValue().getType());
				//为map时
				if(DataType.MAP == DataType.getValueFor(vo.getParent().getValue().getType())) {
					final String packageName = vo.getParent().getValue().getRemark();
					//第一个类名
					@SuppressWarnings("unused")
					String className1 = "";
					//第二个类名
					@SuppressWarnings("unused")
					String className2 = "";
					if(packageName.contains(MAP_TAG)) {
						final String [] packages =  packageName.split(MAP_TAG);
						className1 = parsePackageClassName(packages[0]);
						className2 = parsePackageClassName(packages[1]);
						if(getKeyOrValue(vo.getValue().getRemark()).equals("Key")) {
							if(baseDataType.contains(packages[0])) {
								writeDataTmp = baseTypeRowMapData;
							} else {
								if(!importPackages.contains(packages[0])) {
									importPackages.add(packages[0]);
								}
							}
						} else {
							if(baseDataType.contains(packages[1])) {
								writeDataTmp = baseTypeRowMapData;
							} else {
								if(!importPackages.contains(packages[1])) {
									importPackages.add(packages[1]);
								}
							}
						}
					}
					
					writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.KEY_VALUE, getKeyOrValue(vo.getValue().getRemark())); 
					writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.TYPE, upperInde(vo.getValue().getType())); 
					writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.PARENT_NAME, lowIndex(paramName));
					writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.PARAM_NAME, upperInde(vo.getValue().getName()));
					writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.INFO, vo.getValue().getInfo());
				} else {
					writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.TYPE, upperInde(vo.getValue().getType())); 
					writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.PARENT_NAME, lowIndex(paramName));
					writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.PARAM_NAME, upperInde(vo.getValue().getName()));
					writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.INFO, vo.getValue().getInfo());
					if(writeDataTmp.contains(DataUtil.TMPKey.LOOP_PARAM)) {
						writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.LOOP_PARAM, loopNames.get(i - 1));
					}
				}
			} else {
				final String packageName = vo.getValue().getRemark();
				//第一个类名
				final String className1;
				//第二个类名
				String className2 = "";
				if(packageName.contains(MAP_TAG)) {
					final String [] packages =  packageName.split(MAP_TAG);
					className1 = parsePackageClassName(packages[0]);
					className2 = parsePackageClassName(packages[1]);
				} else {
					className1 = parsePackageClassName(packageName);
				}
//				final String className = parsePackageClassName(packageName) ;
				if(null == packageName || packageName.equals("")) {
					writeDataTmp = "";
					Platform.runLater(()-> {
						TipDialogLogger.error("对应的list/map没有设置对应的包!");
					});
					return "";
				} else {
					final String listName =  upperInde(vo.getValue().getName().replace("array", "").replace("list", "").replace("map", ""));
					if(baseDataType.contains(packageName)) {
						writeDataTmp = getBaseTypeInnerTempByType(vo.getValue().getType());
//						writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.CLASS_JAVA_NAME, classJavaName);
						writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.ENTITY_NAME, parsePackageClassName(packageName)); 
						writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.PARAM_NAME, vo.getValue().getName());
						writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.PARENT_NAME, lowIndex(paramName));
						writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.UPPER_PARAM_NAME, upperInde(vo.getValue().getName()));
						if(writeDataTmp.contains(DataUtil.TMPKey.PARENT_LOOP_PARAM)) {
							writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.PARENT_LOOP_PARAM, loopNames.get(i - 1));
						}
						if(writeDataTmp.contains(DataUtil.TMPKey.LOOP_PARAM)) {
							writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.LOOP_PARAM, loopNames.get(i ++));
						}
						writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.WRITE_DETAIL, buildBaseWriteDetail(vo.getChildren().get(0), lowIndex(listName), getBaseTypeRowTempByType(vo.getValue().getType())));
					} else {
						writeDataTmp = getTempByInnerType(vo.getValue().getType());
//						writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.CLASS_JAVA_NAME, classJavaName);
						writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.ENTITY_NAME, parsePackageClassName(packageName)); 
						writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.ENTITY_NAME_1, className1); 
						writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.ENTITY_NAME_2, className2); 
						writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.LIST_NAME, lowIndex(listName));
						writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.PARENT_NAME, lowIndex(paramName));
						writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.PARAM_NAME, vo.getValue().getName());
						writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.UPPER_PARAM_NAME, upperInde(vo.getValue().getName()));
						if(!importPackages.contains(packageName)) {
							importPackages.add(packageName);
						}
						if(writeDataTmp.contains(DataUtil.TMPKey.PARENT_LOOP_PARAM)) {
							writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.PARENT_LOOP_PARAM, loopNames.get(i - 1));
						}
						if(writeDataTmp.contains(DataUtil.TMPKey.LOOP_PARAM)) {
							writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.LOOP_PARAM, loopNames.get(i ++));
						}
//						final String listItemClassName = packageName.substring(packageName.lastIndexOf('.') + 1);
						writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.WRITE_DETAIL, buildWriteDetail(vo.getChildren(), lowIndex(listName), packageName));
					}
					
				}
			}
			writeDetailBuild.append(writeDataTmp + DataUtil.ENTER);
		}
		return writeDetailBuild.toString();
	}
	
	//返回map是键还是值
	private String getKeyOrValue(String remark)
	{
		if(remark.toLowerCase().equals("key")) {
			return "Key";
		}
		return "Value";
	}

	private String buildBaseWriteDetail(TreeItem<RowModelVO> rowModelVOs, String classJavaName, String baseData) {
		String writeDataTmp = baseData;
		writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.TYPE, upperInde(rowModelVOs.getValue().getType())); 
		writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.LIST_NAME, classJavaName);
		writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.PARAM_NAME, upperInde(rowModelVOs.getValue().getName()));
		writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.INFO, rowModelVOs.getValue().getInfo());
		writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.PARENT_NAME, classJavaName);
		writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.KEY_VALUE, getKeyOrValue(rowModelVOs.getValue().getRemark())); 
		if(writeDataTmp.contains(DataUtil.TMPKey.LOOP_PARAM)) {
			writeDataTmp = writeDataTmp.replace(DataUtil.TMPKey.LOOP_PARAM, loopNames.get(i - 1));
		}
		return writeDataTmp;
	}
	
	private String buildBaseReadDetail(TreeItem<RowModelVO> rowModelVOs, String classJavaName, String baseData) {
		String readDataTmp = baseData;
		DataType type = DataType.getValueFor(rowModelVOs.getValue().getType());
		readDataTmp = readDataTmp.replace(DataUtil.TMPKey.TYPE, upperInde(rowModelVOs.getValue().getType())); 
		readDataTmp = readDataTmp.replace(DataUtil.TMPKey.BASE_TYPE, type.getName()); 
		readDataTmp = readDataTmp.replace(DataUtil.TMPKey.LIST_NAME, classJavaName);
		readDataTmp = readDataTmp.replace(DataUtil.TMPKey.PARAM_NAME, classJavaName);
		readDataTmp = readDataTmp.replace(DataUtil.TMPKey.INFO, rowModelVOs.getValue().getInfo());
//		readDataTmp = readDataTmp.replace(DataUtil.TMPKey.TYPE, upperInde(rowModelVOs.getValue().getType()));
		return readDataTmp;
	}
	
	/**
	 * 读入cm文件
	 * @param result
	 * @param bufferedReader
	 * @throws IOException
	 * @create 2014年12月3日 Cico.姜彪
	 */
	private void readCMFile(StringBuilder result, BufferedReader bufferedReader) throws IOException {
		 String lineTxt = null;
         while((lineTxt = bufferedReader.readLine()) != null) {
         	if(lineTxt.startsWith(DataUtil.TMPKey.IMPORT_PACKAGE_EQUAL)) {
         		importPackage = lineTxt.replace(DataUtil.TMPKey.IMPORT_PACKAGE_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	} else if(lineTxt.startsWith(DataUtil.TMPKey.DECLARE_PARAMS_EQUAL)) {
         		declareParams = lineTxt.replace(DataUtil.TMPKey.DECLARE_PARAMS_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	} else if(lineTxt.startsWith(DataUtil.TMPKey.LIST_DATA_EQUAL)) {
         		listData = lineTxt.replace(DataUtil.TMPKey.LIST_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	} else if(lineTxt.startsWith(DataUtil.TMPKey.ROW_DATA_EQUAL)) {
         		rowData = lineTxt.replace(DataUtil.TMPKey.ROW_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	} else if(lineTxt.startsWith(DataUtil.TMPKey.BASE_DATA_EQUAL)) {
         		baseData = lineTxt.replace(DataUtil.TMPKey.BASE_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	} else if(lineTxt.startsWith(DataUtil.TMPKey.BASE_TYPE_LIST_DATA_EQUAL)) {
         		baseTypeListData = lineTxt.replace(DataUtil.TMPKey.BASE_TYPE_LIST_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	} 
         	else if(lineTxt.startsWith(DataUtil.TMPKey.BASE_TYPE_DATA_EQUAL)) {
         		baseTypeData = lineTxt.replace(DataUtil.TMPKey.BASE_TYPE_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.INNER_LIST_DATA_EQUAL)) {
         		innerListData = lineTxt.replace(DataUtil.TMPKey.INNER_LIST_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.INNER_BASE_LIST_DATA_EQUAL)) {
         		innerBaseTypeListData = lineTxt.replace(DataUtil.TMPKey.INNER_BASE_LIST_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.MAP_TYPE_EQUAL)) {
         		mapType = lineTxt.replace(DataUtil.TMPKey.MAP_TYPE_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.LIST_TYPE_EQUAL)) {
         		listType = lineTxt.replace(DataUtil.TMPKey.LIST_TYPE_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	//Map
         	else if(lineTxt.startsWith(DataUtil.TMPKey.BASE_TYPE_MAP_DATA_EQUAL)) {
         		baseTypeMapData = lineTxt.replace(DataUtil.TMPKey.BASE_TYPE_MAP_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.INNER_BASE_TYPE_MAP_DATA_EQUAL)) {
         		innerBaseTypeMapData = lineTxt.replace(DataUtil.TMPKey.INNER_BASE_TYPE_MAP_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.BASE_TYPE_ROW_MAP_DATA_EQUAL)) {
         		baseTypeRowMapData = lineTxt.replace(DataUtil.TMPKey.BASE_TYPE_ROW_MAP_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.MAP_DATA_EQUAL)) {
         		mapData = lineTxt.replace(DataUtil.TMPKey.MAP_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.INNER_MAP_DATA_EQUAL)) {
         		innerMapData = lineTxt.replace(DataUtil.TMPKey.INNER_MAP_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.BASE_ROW_MAP_DATA_EQUAL)) {
         		baseRowMapData = lineTxt.replace(DataUtil.TMPKey.BASE_ROW_MAP_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	//Array
         	else if(lineTxt.startsWith(DataUtil.TMPKey.BASE_TYPE_ARRAY_DATA_EQUAL)) {
         		baseTypeArrayData = lineTxt.replace(DataUtil.TMPKey.BASE_TYPE_ARRAY_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.INNER_BASE_TYPE_ARRAY_DATA_EQUAL)) {
         		innerBaseTypeArrayData = lineTxt.replace(DataUtil.TMPKey.INNER_BASE_TYPE_ARRAY_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.BASE_TYPE_ROW_ARRAY_DATA_EQUAL)) {
         		baseTypeRowArrayData = lineTxt.replace(DataUtil.TMPKey.BASE_TYPE_ROW_ARRAY_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.ARRAY_DATA_EQUAL)) {
         		arrayData = lineTxt.replace(DataUtil.TMPKey.ARRAY_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.INNER_ARRAY_DATA_EQUAL)) {
         		innerArrayData = lineTxt.replace(DataUtil.TMPKey.INNER_ARRAY_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.BASE_ROW_ARRAY_DATA_EQUAL)) {
         		baseRowArrayData = lineTxt.replace(DataUtil.TMPKey.BASE_ROW_ARRAY_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	result.append(lineTxt + DataUtil.ENTER);
         }
	}
	
	/**
	 * 读入fm文件
	 * @param result
	 * @param bufferedReader
	 * @throws IOException
	 * @create 2014年12月3日 Cico.姜彪
	 */
	private void readSMFile(StringBuilder result, BufferedReader bufferedReader)  throws IOException {
		 String lineTxt = null;
         while((lineTxt = bufferedReader.readLine()) != null) {
         	if(lineTxt.startsWith(DataUtil.TMPKey.IMPORT_PACKAGE_EQUAL)) {
         		importPackage = lineTxt.replace(DataUtil.TMPKey.IMPORT_PACKAGE_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	} else if(lineTxt.startsWith(DataUtil.TMPKey.DECLARE_PARAMS_EQUAL)) {
         		declareParams = lineTxt.replace(DataUtil.TMPKey.DECLARE_PARAMS_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	} else if(lineTxt.startsWith(DataUtil.TMPKey.LIST_DATA_EQUAL)) {
         		listData = lineTxt.replace(DataUtil.TMPKey.LIST_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	} else if(lineTxt.startsWith(DataUtil.TMPKey.ROW_DATA_EQUAL)) {
         		rowData = lineTxt.replace(DataUtil.TMPKey.ROW_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	} else if(lineTxt.startsWith(DataUtil.TMPKey.BASE_DATA_EQUAL)) {
         		baseData = lineTxt.replace(DataUtil.TMPKey.BASE_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	} else if(lineTxt.startsWith(DataUtil.TMPKey.VOLUATION_PARAM_EQUAL)) {
         		voluationParam = lineTxt.replace(DataUtil.TMPKey.VOLUATION_PARAM_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	} else if(lineTxt.startsWith(DataUtil.TMPKey.BASE_TYPE_LIST_DATA_EQUAL)) {
         		baseTypeListData = lineTxt.replace(DataUtil.TMPKey.BASE_TYPE_LIST_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.BASE_TYPE_DATA_EQUAL)) {
         		baseTypeData = lineTxt.replace(DataUtil.TMPKey.BASE_TYPE_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.INNER_LIST_DATA_EQUAL)) {
         		innerListData = lineTxt.replace(DataUtil.TMPKey.INNER_LIST_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.INNER_BASE_LIST_DATA_EQUAL)) {
         		innerBaseTypeListData = lineTxt.replace(DataUtil.TMPKey.INNER_BASE_LIST_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.MAP_TYPE_EQUAL)) {
         		mapType = lineTxt.replace(DataUtil.TMPKey.MAP_TYPE_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.LIST_TYPE_EQUAL)) {
         		listType = lineTxt.replace(DataUtil.TMPKey.LIST_TYPE_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	//Map
         	else if(lineTxt.startsWith(DataUtil.TMPKey.BASE_TYPE_MAP_DATA_EQUAL)) {
         		baseTypeMapData = lineTxt.replace(DataUtil.TMPKey.BASE_TYPE_MAP_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.INNER_BASE_TYPE_MAP_DATA_EQUAL)) {
         		innerBaseTypeMapData = lineTxt.replace(DataUtil.TMPKey.INNER_BASE_TYPE_MAP_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.BASE_TYPE_ROW_MAP_DATA_EQUAL)) {
         		baseTypeRowMapData = lineTxt.replace(DataUtil.TMPKey.BASE_TYPE_ROW_MAP_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.MAP_DATA_EQUAL)) {
         		mapData = lineTxt.replace(DataUtil.TMPKey.MAP_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.INNER_MAP_DATA_EQUAL)) {
         		innerMapData = lineTxt.replace(DataUtil.TMPKey.INNER_MAP_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.BASE_ROW_MAP_DATA_EQUAL)) {
         		baseRowMapData = lineTxt.replace(DataUtil.TMPKey.BASE_ROW_MAP_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	//Array
         	else if(lineTxt.startsWith(DataUtil.TMPKey.BASE_TYPE_ARRAY_DATA_EQUAL)) {
         		baseTypeArrayData = lineTxt.replace(DataUtil.TMPKey.BASE_TYPE_ARRAY_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.INNER_BASE_TYPE_ARRAY_DATA_EQUAL)) {
         		innerBaseTypeArrayData = lineTxt.replace(DataUtil.TMPKey.INNER_BASE_TYPE_ARRAY_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.BASE_TYPE_ROW_ARRAY_DATA_EQUAL)) {
         		baseTypeRowArrayData = lineTxt.replace(DataUtil.TMPKey.BASE_TYPE_ROW_ARRAY_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.ARRAY_DATA_EQUAL)) {
         		arrayData = lineTxt.replace(DataUtil.TMPKey.ARRAY_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.INNER_ARRAY_DATA_EQUAL)) {
         		innerArrayData = lineTxt.replace(DataUtil.TMPKey.INNER_ARRAY_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	else if(lineTxt.startsWith(DataUtil.TMPKey.BASE_ROW_ARRAY_DATA_EQUAL)) {
         		baseRowArrayData = lineTxt.replace(DataUtil.TMPKey.BASE_ROW_ARRAY_DATA_EQUAL, "").replace("\\n", DataUtil.ENTER);
         	}
         	result.append(lineTxt + DataUtil.ENTER);
         }
	}
	
	/**
	 * 将首字母小写 
	 * @param str
	 * @return
	 * @create 2014年8月14日 Cico.姜彪
	 */
	private String lowIndex(String str) {
		final char[] chars = str.toCharArray();
		chars[0] = Character.toLowerCase(chars[0]);
		return new String(chars);
	}
	
	/**
	 * 将首字母大写 
	 * @param str
	 * @return
	 * @create 2014年8月14日 Cico.姜彪
	 */
	private String upperInde(String str) {
		final char[] chars = str.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		return new String(chars);
	}
}
