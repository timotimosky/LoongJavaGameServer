package netBase.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * 
 * @author timosky
 * @date 2013-1-8
 */ 
public class NettyServer {  

    public static void start(int port) throws Exception {  
 	
    	final ChannelFactory factory =  new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), 
        		Executors.newCachedThreadPool());  
        
        ServerBootstrap bootstrap = new ServerBootstrap (factory);  
        
        bootstrap.setPipelineFactory(new ServerPipelineFactory()); 

        bootstrap.setOption("child.tcpNoDelay", true);  
        bootstrap.setOption("child.keepAlive", true); 
        
		//bootstrap.setOption("child.reuseAddress", true);
		bootstrap.setOption("child.connectTimeoutMillis", 100);
		bootstrap.setOption("readWriteFair", true); //读写平衡
        
		//bootstrap.setOption("child.bufferFactory", HeapChannelBufferFactory.getInstance(ByteOrder.LITTLE_ENDIAN));
		//bootstrap.setOption("child.receiveBufferSize", 1048576);  //设置接收缓冲区的大小。在一定程度上可以提高吞吐量。
		
        bootstrap.bind(new InetSocketAddress(port));
       
    } 
    
} 
