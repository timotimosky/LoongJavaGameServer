package netWork.resolve;


import java.nio.ByteBuffer;

public abstract class ReceivableBuffer
{
  public abstract Object read(ByteBuffer paramByteBuffer);

  protected int readByte(ByteBuffer byteBuffer)
  {
    return byteBuffer.get();
  }

  protected int readShort(ByteBuffer byteBuffer)
  {
    return byteBuffer.getShort();
  }

  protected int readInt(ByteBuffer byteBuffer)
  {
    return byteBuffer.getInt();
  }

  protected long readLong(ByteBuffer byteBuffer)
  {
    return byteBuffer.getLong();
  }

  protected float readFloat(ByteBuffer byteBuffer)
  {
    return byteBuffer.getFloat();
  }

  protected double readDouble(ByteBuffer byteBuffer)
  {
    return byteBuffer.getDouble();
  }

  protected String readString(ByteBuffer byteBuffer)
  {
    StringBuilder tb = new StringBuilder();
    char c;
    while ((c = byteBuffer.getChar()) != 0)
    {
      tb.append(c);
    }String str = tb.toString();
    return str;
  }

  protected boolean readBoolean(ByteBuffer byteBuffer)
  {
    return (byteBuffer.get() == 0 ? Boolean.FALSE : Boolean.TRUE).booleanValue();
  }
}
