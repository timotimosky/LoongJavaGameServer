package netWork.Iresolve;


import java.nio.ByteBuffer;

import netWork.model.CommandInfoModel;

import org.jboss.netty.buffer.ChannelBuffer;

public abstract interface IResolveBuffer
{
  public abstract Object read(ByteBuffer paramByteBuffer, CommandInfoModel paramCommandInfoModel);

  public abstract Object read(ChannelBuffer paramChannelBuffer, CommandInfoModel paramCommandInfoModel);

  public abstract void write(ChannelBuffer paramChannelBuffer, Object paramObject, CommandInfoModel paramCommandInfoModel);
  
  //TODO:根據自定義協議讀取
 // public abstract Object read(ChannelBuffer paramChannelBuffer);
}