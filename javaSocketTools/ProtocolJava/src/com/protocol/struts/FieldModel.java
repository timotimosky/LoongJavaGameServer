package com.protocol.struts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 协议字段配置类
 * @author 		hongxiao.z
 * @createtime	2014年6月26日
 */
@XmlRootElement(name = "field")
@XmlAccessorType(XmlAccessType.FIELD)
public class FieldModel 
{
	@XmlAttribute(name = "statement")
	private String statement;
	
	@XmlAttribute(name = "method_set")
	private String methodSet;
	
	@XmlAttribute(name = "method_get")
	private String methodGet;
	
	@XmlElement(name = "bytes")
	private List<BytesModel> models;
	
	//数据解析存储
	private Map<BytesType, BytesModel> strs = new HashMap<>();
	
	/**
	 * 解组后回调
	 * target - 解组之前的 JAXB 映射类的非 null 实例。
	 * parent - 将引用 target 的 JAXB 映射类的实例。当 target 是根元素时，该参数为 null。 
	 * @create hongxiao.z	--- 2014年5月28日
	 */
	void afterUnmarshal(Unmarshaller u, Object parent) 
	{
		for (BytesModel model : this.models) 
		{
			strs.put(model.get_enum(), model);
		}
		
		this.models = null;
	}

	public String getMethodSet() {
		return methodSet;
	}

	public String getMethodGet() {
		return methodGet;
	}

	/**
	 * 根据类型获取模版
	 * @param type
	 * @return
	 */
	BytesModel getTypeOfModel(BytesType type)
	{
		return strs.get(type);
	}

	public String getStatement() {
		return statement;
	}
	
}
