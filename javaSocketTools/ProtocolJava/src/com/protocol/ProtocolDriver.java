package com.protocol;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.protocol.struts.BuildData;
import com.protocol.struts.BytesModel;
import com.protocol.struts.BytesType;

/**
 * 协议驱动类
 * @auther		hongxiao.z
 * @date  2014年6月26日
 */
public class ProtocolDriver 
{
	private static Map<String, BuildData> datas = new HashMap<>();
	static void init()
	{
		File file = new File(ProtocolConfig.TEMPLATE_PATH);
		
		File[] xmls = file.listFiles();
		
		
		String postfix = "_template.xml";
		
		for (File temp : xmls) 
		{
			String fileName = temp.getName();
			
			if(fileName.length() <= postfix.length()) continue;
			
			String filePost = fileName.substring(fileName.length() - postfix.length());
			
			if(!filePost.equals(postfix)) continue;
			
			try
			{
				//创建类型上下文
				JAXBContext context = JAXBContext.newInstance(BuildData.class);
				
				//创建解组程序工具
				Unmarshaller us = context.createUnmarshaller();
				
				//解组程序， 解组过程中有Unmarshaller监听过程。解组前执行方法beforUnmarshal, 解组后执行方法afterUnmarshal
				BuildData data = (BuildData) us.unmarshal(temp);
				datas.put(data.getName(), data);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		System.out.println();
	}
	
	/**
	 * 获取字段输出流时候的字符串表示
	 * @param fieldModel
	 * @param fieldName
	 * @param name
	 * @param type
	 * @return
	 */
	static String getFieldWriteStr(BytesModel fieldModel, String fieldName, String name, BytesType type)
	{
		if(fieldModel == null) fieldModel = getNameAndTypeOfStr(name, type);
		if(fieldModel == null) return "";
		return getStr(fieldModel.getWriteStr(), fieldName, fieldModel.getType());
	}
	
	/**
	 * 获取字段输入流时候的字符串表示
	 * @param fieldModel
	 * @param fieldName
	 * @param name
	 * @param type
	 * @return
	 */
	static String getFieldReadStr(BytesModel fieldModel, String fieldName, String name, BytesType type)
	{
		if(fieldModel == null) fieldModel = getNameAndTypeOfStr(name, type);
		if(fieldModel == null) return "";
		return getStr(fieldModel.getReadStr(), fieldName, fieldModel.getType());
	}
	
	/**
	 * 获取最终输出字符串
	 * @param str		原字符
	 * @param fieldName	字段名
	 * @param type		字段类型
	 * @return
	 */
	private static String getStr(String str, String fieldName, String type)
	{
		return str.replaceAll(ProtocolConfig.FIELD_NAME, fieldName)
				.replaceAll(ProtocolConfig.FIELD_TYPE, type);
	}
	
	private static BytesModel getNameAndTypeOfStr(String name, BytesType type)
	{
		BuildData data = datas.get(name);
		if(data == null) return null;
		return data.getTypeOfModel(type);
	}
	
	public static void main(String[] args) {
		init();
		System.out.println('\000');
	}
}
