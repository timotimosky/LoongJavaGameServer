package netWork.resolve;

import io.netty.buffer.ByteBuf;

public abstract class SendableBuffer
{
  public abstract void write(ByteBuf paramChannelBuffer, Object paramObject);

  protected final void writeByte(ByteBuf buffer, int b)
  {
    buffer.writeByte(b);
  }

  protected final void writeShort(ByteBuf buffer, int value)
  {
    buffer.writeShort(value);
  }

  protected final void writeInt(ByteBuf buffer, int value)
  {
    buffer.writeInt(value);
  }

  protected final void writeLong(ByteBuf buffer, long value)
  {
    buffer.writeLong(value);
  }

  protected final void writeFloat(ByteBuf buffer, float value)
  {
    buffer.writeFloat(value);
  }

  protected final void writeDouble(ByteBuf buffer, double value)
  {
    buffer.writeDouble(value);
  }

  protected final void writeString(ByteBuf buffer, String value)
  {
    for (int i = 0; i < value.length(); i++)
    {
      buffer.writeChar(value.charAt(i));
    }
    //以0结尾也可以.旧版
    //buffer.writeChar(0);
    buffer.writeChar('\u0000');
  }

  protected final void writeBoolean(ByteBuf buffer, boolean value)
  {
    buffer.writeByte(value ? 1 : 0);
  }
}
