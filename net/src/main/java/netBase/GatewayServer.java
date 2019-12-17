package netBase;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import netBase.handler.ServerDispatcherHandler;
import netBase.handler.ServerIdleHandler;
import netBase.handler.TcpCodecHandler;

public class GatewayServer extends AbstractServer {


    @Override
    public ChannelInitializer<SocketChannel> channelChannelInitializer() {
        return new GatewayChannelHandler(packetFilter);
    }


    private static class GatewayChannelHandler extends ChannelInitializer<SocketChannel> {


        @Override
        protected void initChannel(SocketChannel channel) {
            System.out.println("client channel init!");
            ChannelPipeline pipeline = channel.pipeline();

            //设置心跳时间180
            pipeline.addLast(new IdleStateHandler(0, 0, 180));
           // pipeline.addLast("encoder", new WriteEncoder());
            pipeline.addLast(new ServerIdleHandler());
            pipeline.addLast(new TcpCodecHandler());
            pipeline.addLast(new ServerDispatcherHandler());
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

