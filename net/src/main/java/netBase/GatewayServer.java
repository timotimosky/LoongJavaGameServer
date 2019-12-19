package netBase;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import netBase.packet.AbstractPacket;
import netBase.core.AbstractServer;
import netBase.handler.GatewayDispatcherHandler;
import netBase.handler.ServerIdleHandler;
import netBase.handler.TcpCodecHandler;

import java.util.function.BiFunction;

public class GatewayServer extends AbstractServer {

    //action
    private BiFunction<Session, AbstractPacket, Boolean> packetFilter;


    public GatewayServer(String host,int port) {
        super(host,port);
    }

    @Override
    public ChannelInitializer<SocketChannel> channelChannelInitializer() {
        return new GatewayChannelHandler(packetFilter);
    }


    private static class GatewayChannelHandler extends ChannelInitializer<SocketChannel> {

        private BiFunction<Session, AbstractPacket, Boolean> packetFilter;

        public GatewayChannelHandler(BiFunction<Session, AbstractPacket, Boolean> packetFilter) {
            this.packetFilter = packetFilter;
        }

        @Override
        protected void initChannel(SocketChannel channel) {
            System.out.println("client channel init!");
            ChannelPipeline pipeline = channel.pipeline();

            //设置心跳时间180
            pipeline.addLast(new IdleStateHandler(0, 0, 180));
           // pipeline.addLast("encoder", new WriteEncoder());
            pipeline.addLast(new ServerIdleHandler());
            pipeline.addLast(new TcpCodecHandler());
           // pipeline.addLast(new ServerDispatcherHandler());
            pipeline.addLast(new GatewayDispatcherHandler(packetFilter));
        }

        /*@Override
        protected void initChannel(Channel channel) throws Exception {
            System.out.println("client channel init!");
            ChannelPipeline pipeline = channel.pipeline();
            pipeline.addLast("StringDecoder",new StringDecoder());
            pipeline.addLast("StringEncoder",new StringEncoder());
            pipeline.addLast("ClientHandler",new ClientHandler());
        }*/

    }
}

