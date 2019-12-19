package netBase;


import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;
import netWork.model.CommandInfoModel;

public abstract interface IResolveBuffer
{
  public abstract Object read(ByteBuffer paramByteBuffer, CommandInfoModel paramCommandInfoModel);

  public abstract Object read(ByteBuf paramChannelBuffer, CommandInfoModel paramCommandInfoModel);

  public abstract void write(ByteBuf paramChannelBuffer, Object paramObject, CommandInfoModel paramCommandInfoModel);
  
  //TODO:根據自定義協議讀取
 // public abstract Object read(ChannelBuffer paramChannelBuffer);
}