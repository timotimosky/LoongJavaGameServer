package com.protocol.type;

/**
 * 数据类型枚举
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	下午4:26:01
 * @version 1.0
 */
public enum DataType
{
	BYTE("byte", 1, "Byte", "readByte();", "writeByte(buffer, #{param});"),
	SHORT("short", 2, "Short", "readShort();", "writeShort(buffer, #{param});"),
	INT("int", 4, "Integer", "readInt();", "writeInt(buffer, #{param});"),
	LONG("long",8,"Long", "readLong();", "writeLong(buffer, #{param});"),
	FLOAT("float", 4,"Float", "readFloat();", "writeFloat(buffer, #{param});"),
	DOUBLE("double", 8,"Double", "readDouble();", "writeDouble(buffer, #{param});"),
	STRING("String", 20,"String", "readString();", "writeString(buffer, #{param});"),
	LIST("list", 2,"List", "readShort();", "writeShort(buffer, #{param});"),
	MAP("map", 2,"Map", "readShort();", "writeShort(buffer, #{param});"),
	ARRAY("array", 2,"array", "readShort();", "writeShort(buffer, #{param});"),
	;
	
	public String getLongName()
	{
		return longName;
	}
	private final String name;
	private final int size;
	private final String longName;
	private final String readBuff;
	private final String writeBuff;
	private DataType(String name, int size, String longName, String readBuff, String writeBuff) {
		this.name = name;
		this.size = size;
		this.longName = longName;
		this.writeBuff = writeBuff;
		this.readBuff = readBuff;
	}
	public String getName()
	{
		return name;
	}
	public int getSize()
	{
		return size;
	}
	
	public static final DataType getValueFor(String name) {
		final DataType [] dataTypes = DataType.values();
		for (DataType DataType : dataTypes)
		{
			if(name.equals(DataType.name) || name.startsWith(DataType.name)) {
				return DataType;
			}
		}
		return LIST;
	}

	
	@Override
	public String toString()
	{
		return name;
	}
	public String getReadBuff()
	{
		return readBuff;
	}
	public String getWriteBuff()
	{
		return writeBuff;
	}
	
}
