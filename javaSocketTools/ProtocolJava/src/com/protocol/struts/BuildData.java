package com.protocol.struts;

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
@XmlRootElement(name = "build")
@XmlAccessorType(XmlAccessType.NONE)
public class BuildData
{
	@XmlAttribute(name = "name")
	private String name;
	
	@XmlElement(name = "field")
	private FieldModel field;
	
	/**
	 * 解组后回调
	 * target - 解组之前的 JAXB 映射类的非 null 实例。
	 * parent - 将引用 target 的 JAXB 映射类的实例。当 target 是根元素时，该参数为 null。 
	 * @create hongxiao.z	--- 2014年5月28日
	 */
	void afterUnmarshal(Unmarshaller u, Object parent) 
	{
		 
	}

	public String getName() {
		return name;
	}
	
	/**
	 * 根据类型获取打印字符
	 * @param type
	 * @return
	 */
	public BytesModel getTypeOfModel(BytesType type)
	{
		if(field == null) return null;
		return field.getTypeOfModel(type);
	}
}
