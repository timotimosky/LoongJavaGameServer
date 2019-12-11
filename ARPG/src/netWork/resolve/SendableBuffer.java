package netWork.resolve;

import org.jboss.netty.buffer.ChannelBuffer;

public abstract class SendableBuffer
{
  public abstract void write(ChannelBuffer paramChannelBuffer, Object paramObject);

  protected final void writeByte(ChannelBuffer buffer, int b)
  {
    buffer.writeByte(b);
  }

  protected final void writeShort(ChannelBuffer buffer, int value)
  {
    buffer.writeShort(value);
  }

  protected final void writeInt(ChannelBuffer buffer, int value)
  {
    buffer.writeInt(value);
  }

  protected final void writeLong(ChannelBuffer buffer, long value)
  {
    buffer.writeLong(value);
  }

  protected final void writeFloat(ChannelBuffer buffer, float value)
  {
    buffer.writeFloat(value);
  }

  protected final void writeDouble(ChannelBuffer buffer, double value)
  {
    buffer.writeDouble(value);
  }

  protected final void writeString(ChannelBuffer buffer, String value)
  {
    for (int i = 0; i < value.length(); i++)
    {
      buffer.writeChar(value.charAt(i));
    }
    //以0结尾也可以.旧版
    //buffer.writeChar(0);
    buffer.writeChar('\u0000');
  }

  protected final void writeBoolean(ChannelBuffer buffer, boolean value)
  {
    buffer.writeByte(value ? 1 : 0);
  }
}
