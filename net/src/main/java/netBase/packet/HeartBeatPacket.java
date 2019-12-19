package netBase.packet;

import io.netty.buffer.ByteBuf;

public class HeartBeatPacket extends SendablePacket{


    public HeartBeatPacket()
    {
        module = 13;
        opcode = 1001;
       // buffer = new ByteBuf();
    }
}
