package netBase.http;


import org.apache.log4j.Logger;


/*
public class HttpClientInboundHandler extends SimpleChannelUpstreamHandler {

	private static final Logger log = Logger.getLogger(HttpClientInboundHandler.class);
	
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent msg) throws Exception {
        if (msg instanceof HttpResponse) 
        {
            HttpResponse response = (HttpResponse) msg.getMessage();
            log.info("CONTENT_TYPE:" + response.headers().get(HttpHeaders.Names.CONTENT_TYPE));
        }
        */
/*if(msg instanceof HttpContent)
        {
            HttpContent content = (HttpContent)msg;
            ByteBuf buf = content.content();
            log.infoln(buf.toString(io.netty.util.CharsetUtil.UTF_8));
            buf.release();
        }*//*

    }
}*/
