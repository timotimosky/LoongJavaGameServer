package com.protocol.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;

import com.protocol.type.RequestType;

public class SystemConfUtil
{
	public static final String getProtocolPath() {
		return System.getProperty("user.dir") + "/protocol/";
	}
	
	public static final String getControllerModelPath() {
		return getProtocolPath() + getControllerModelFileName();
	}
	
	public static final String getControllerModelFileName() {
		return "ControllerModel.xml";
	}
	
	public static final String getTmpPath() {
		return System.getProperty("user.dir") + "/tmp";
	}
	
	/**
	 * 当前代码文件保存路径
	 */
//	private static final SimpleObjectProperty<File> currSavaDirector = new SimpleObjectProperty<File>(new File("D:/test/xml/xml2/")); 
	
//	public static final void setCurrSavaDirector(File file) {
//		currSavaDirector.set(file);
//	}
	
	public static File getCurrSavaDirector(String type) {
		return new File(getSavePath(type));
	}
	
	/**
	 * 获取代码模板文件 
	 * @param type
	 * @return
	 * @create 2014年8月15日 Cico.姜彪
	 */
	public static File getCodeTmpByType(String type) {
		return new File(System.getProperty("user.dir") + "/tmp/" + type + ".code.tmp");
	}
	
	public static File getControllerTmpByType(String type) {
		return new File(System.getProperty("user.dir") + "/tmp/" + type + ".controller.tmp");
	}
	
	public static File getJavaCommandCMFile() {
		return new File(System.getProperty("user.dir") + "/tmp/java.command_cm.tmp");
	}
	
	public static File getJavaCommandSMFile() {
		return new File(System.getProperty("user.dir") + "/tmp/java.command_sm.tmp");
	}
	
	/**
	 * 获取代码模板列表
	 * @return
	 * @create 2014年12月18日 Cico.姜彪
	 */
	public static final List<String> getCommandFileNames() {
		List<String> result = new ArrayList<>();
		File fileDir = new File(System.getProperty("user.dir") + "/tmp/command");
		if(fileDir.isDirectory()) {
			File[] files = fileDir.listFiles(new FileFilter()
			{
				@Override
				public boolean accept(File pathname)
				{
					return pathname.getName().endsWith("_r.tmp") || pathname.getName().endsWith("_w.tmp");
				}
			});
			String name;
			for (File file : files)
			{
				name = file.getName();
				name = name.substring(name.indexOf(".") + 1);
				name = name.split("_")[0];
				if(!result.contains(name)) {
					result.add(name);
				}
			}
		}
		return result;
	}
	
	public static final File getCommandFileBy(String codeType, String fileName, RequestType type) {
		String fileTypeName;
		if(type == RequestType.CLIENT) {
			fileTypeName = "_r.tmp";
		} else {
			fileTypeName = "_w.tmp";
		}
		
		return new File(System.getProperty("user.dir") + "/tmp/command/" + codeType + "." + fileName + fileTypeName);
	}
	
	public static BufferedReader getJavaCommandBufferedReader(String codeType, String fileName, RequestType requestType) {
		InputStreamReader read = null;
		try
		{
			if(requestType == RequestType.CLIENT) {
				read = new InputStreamReader( new FileInputStream(SystemConfUtil.getCommandFileBy(codeType, fileName, requestType)),"UTF-8");
				return new BufferedReader(read);
			} else if(requestType == RequestType.SERVER) {
				read = new InputStreamReader( new FileInputStream(SystemConfUtil.getCommandFileBy(codeType, fileName, requestType)),"UTF-8");
				return new BufferedReader(read);
			}
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	/*当前转换的类型*/
	private static final SimpleStringProperty currFileType = new SimpleStringProperty("java");
	
	public static final String getCurrFileType() {
		return currFileType.get();
	}
	
	public static final void setCurrFileType(String type) {
		currFileType.set(type);
	}
	
	public static final SimpleStringProperty currFileTypeProperty() {
		return currFileType;
	}
	
	private final static PropertiesUtil propertiesUtil = new PropertiesUtil(new File(System.getProperty("user.dir") + "/conf/system.conf"));
	
	public static final String getSavePath(String type) {
		return propertiesUtil.get("path." + type);
	}
	
	public static final void setSavePath(String type, String path) {
		propertiesUtil.putValue("path." + type, path);
	}
}
