package GameClient.main;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import GameClient.netty.ClientPipelineFactory;

/**
 * ����Nttey�ͻ���
 * @author ������
 * @date 2013-1-19
 */ 
public class NettyClient {  

	private static final Logger log = Logger.getLogger(NettyClient.class);
    public static List<Channel> channelList = new ArrayList<Channel>();
	
    public void start(String host,int port) throws Exception {   

        ChannelFactory factory =new NioClientSocketChannelFactory (Executors.newCachedThreadPool(), Executors.newCachedThreadPool());  

        ClientBootstrap bootstrap = new ClientBootstrap (factory);   

        bootstrap.setPipelineFactory(new ClientPipelineFactory()); 
        /*socketChannel*/
        bootstrap.setOption("tcpNoDelay" , true);  
        bootstrap.setOption("keepAlive", true);  
        
        
        int clientCount = 2;
                
         for (int i=0; i<clientCount; i++) {
        	 	  ChannelFuture cf =  bootstrap.connect (new InetSocketAddress(host, port)); 
        	       channelList.add(cf.getChannel());
               }
        
        
        log.info("-----------------NettyClient---Open");
        
        
        for(int i=0; i<100; i++)
        {
        	clientTest(channelList.get(0) );
          //  clientTest2(channelList.get(1) );
//        	channelList.get(0).write("client1 "+i);
//        	channelList.get(1).write("client2 "+i);
        }
        
        
        
        //��������
 //       Channel x =e.getChannel();
//      while(true)
//       {
//    	  try {  
//               Thread.sleep(1000*5);  
//               clientTest(x);
//               
//           } catch (Exception ex) {  
//               ex.printStackTrace();  
//           } ;
//       }
      
        
    }  
    

    
    //test  int short short  ...
    public static void clientTest(Channel x ) throws Exception
    {     	
    	
        ChannelBuffer buf  = ChannelBuffers.buffer(128);
        
        buf.writeInt(9); 
        buf.writeShort(7);
        buf.writeShort(1001);
        
        buf.writeByte(1);
        buf.writeInt(123456); 
        
        
        x.write(buf); 
        log.info("NettyClient send---getShort---");
       // log.info("NettyClient send---getShort---"+ buf.getShort(0)+"getByte----"+buf.getByte(2)+" getShort------"
        	//	+buf.getShort(3)+"---getInt-----"+buf.getInt(5));
        
       // buf.clear();
        
    }  
    
  //test2
    public static void clientTest2(Channel x ) throws Exception
    {     	
    	
        ChannelBuffer buf  = ChannelBuffers.buffer(9);

        buf.writeShort(7);
        buf.writeByte(1);
        buf.writeShort(1001);
        buf.writeInt(123456); 
        
        
        x.write(buf); 
        log.info("NettyClient send22222---getShort---"+ buf.getShort(0)+"getByte----"+buf.getByte(2)+" getShort------"
        		+buf.getShort(3)+"---getInt-----"+buf.getInt(5));
        
       // buf.clear();
        
    }  
} 