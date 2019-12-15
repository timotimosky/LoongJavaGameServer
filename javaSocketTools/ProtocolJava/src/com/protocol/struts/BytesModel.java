package com.protocol.struts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 协议字段模型类
 * @author 		hongxiao.z
 * @createtime	2014年6月26日
 */
@XmlRootElement(name = "bytes")
@XmlAccessorType(XmlAccessType.FIELD)
public class BytesModel 
{
	@XmlAttribute(name = "enum")
	private BytesType _enum;
	
	@XmlAttribute(name = "type")
	private String type;
	
	@XmlAttribute(name = "readStr")
	private String readStr;
	
	@XmlAttribute(name = "writeStr")
	private String writeStr;

	
	public String getReadStr() {
		return readStr;
	}

	public String getWriteStr() {
		return writeStr;
	}

	public BytesType get_enum() {
		return _enum;
	}

	public String getType() {
		return type;
	}
}
