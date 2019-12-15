package com.protocol.type;

/**
 * 请求类型 
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	上午11:25:56
 * @version 1.0
 */
public enum RequestType
{
	SERVER("Server", "服务端请求", "SM_"),
	CLIENT("Client", "客户端请求", "CM_");
	
	private final String code;
	private final String name;
	private final String startName;
	RequestType(String code, String name, String startName) {
		this.code = code;
		this.name = name;
		this.startName = startName;
	}
	
	public static final RequestType getValueFor(String code) {
		final RequestType [] requestTypes = RequestType.values();
		for (RequestType requestType : requestTypes)
		{
			if(code.equals(requestType.code)) {
				return requestType;
			}
		}
		return SERVER;
	}

	public String getCode()
	{
		return code;
	}

	public String getName()
	{
		return name;
	}
	
	public String getStartName()
	{
		return startName;
	}

	public String toString() {
		return name;
	};
}
