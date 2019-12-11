package netBase.http.netty;

import static org.jboss.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.COOKIE;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.SET_COOKIE;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.OK;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.net.URLDecoder;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.frame.TooLongFrameException;
import org.jboss.netty.handler.codec.http.Cookie;
import org.jboss.netty.handler.codec.http.CookieDecoder;
import org.jboss.netty.handler.codec.http.CookieEncoder;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.jboss.netty.util.CharsetUtil;

public class HttpServerHandler extends SimpleChannelUpstreamHandler
{
	protected static Logger log = Logger.getLogger(HttpServerHandler.class); 
	
	
	private HttpRequest request;
	
	
	/**
	 * 接收到消息的处理
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
	{
		
		log.info("http-----------有消息到来");
		HttpRequest request = (HttpRequest)e.getMessage();
		try 
		{

			String url = URLDecoder.decode(request.getUri(),"UTF-8");
			/*解包*/
			//String data = url.substring(url.indexOf(HttpConfig.URI_SPLIT), url.length());
			
			int index = url.indexOf("/");
			int len = url.length();
			String data = url.substring(index, len);
			log.info("http-----------消息data=="+data+"...index="+index+"....len="+len);
			
			//String[] array = data.split(HttpConfig.DATA_SPLIT);
			String[] array = data.split("&");
			//String[] array = Subcontract.getPacket(url);
					
			if(array == null)
			{
				log.info("http-----------空消息");
				return;
			}
			
			log.info("http-----------有消息到来array== "+array.length);	
			/*执行包内容*/
			//ReceivablePacket<?> packet = Subcontract.execute(ctx.getChannel(), array);
				
			//if(packet == null)
				//return;
			//packet.run();
		} 
		catch (Exception e2)
		{
			
		}
		finally
		{
			//因为是HTTP，所以每次执行完，关闭链接
			if(ctx.getChannel().isOpen())
			{
				//ctx.getChannel().disconnect();
				ctx.getChannel().close().awaitUninterruptibly();
			}
		}

	}
	
	

	/**
	 * 异常处理
	 */
	@Override 
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)throws Exception
    {  
		log.error("http-----------异常");
		
        Channel ch = e.getChannel();  
        Throwable cause = e.getCause();  
        if (cause instanceof TooLongFrameException)
        {  
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);  
            return;  
        }  
        cause.printStackTrace();  
        if (ch.isConnected())
        {  
            sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);  
        }  
    } 
	
 
	/**
	 * 发送错误
	 * @param ctx
	 * @param status
	 */
    private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status)
    {  
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, status);  
        
        HttpHeaders headers = response.headers();
        
        headers.set(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
        
        response.setContent(ChannelBuffers.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));  

        ctx.getChannel().write(response).addListener(ChannelFutureListener.CLOSE);  
    } 
    
    private void writeResponse(MessageEvent e) {
        // Decide whether to close the connection or not.
        boolean keepAlive = isKeepAlive(request);

        // Build the response object.
        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
        //response.setContent(ChannelBuffers.copiedBuffer(buf.toString(), CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");

        if (keepAlive) {
            // Add 'Content-Length' header only for a keep-alive connection.
            response.headers().set(CONTENT_LENGTH, response.getContent().readableBytes());
            // Add keep alive header as per:
            // - http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
            response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }

        // Encode the cookie.
        String cookieString = request.headers().get(COOKIE);
        if (cookieString != null) {
            CookieDecoder cookieDecoder = new CookieDecoder();
            Set<Cookie> cookies = cookieDecoder.decode(cookieString);
            if (!cookies.isEmpty()) {
                // Reset the cookies if necessary.
                CookieEncoder cookieEncoder = new CookieEncoder(true);
                for (Cookie cookie : cookies) {
                    cookieEncoder.addCookie(cookie);
                    response.headers().add(SET_COOKIE, cookieEncoder.encode());
                }
            }
        } else {
            // Browser sent no cookie.  Add some.
            CookieEncoder cookieEncoder = new CookieEncoder(true);
            cookieEncoder.addCookie("key1", "value1");
            response.headers().add(SET_COOKIE, cookieEncoder.encode());
            cookieEncoder.addCookie("key2", "value2");
            response.headers().add(SET_COOKIE, cookieEncoder.encode());
        }

        // Write the response.
        ChannelFuture future = e.getChannel().write(response);

        // Close the non-keep-alive connection after the write operation is done.
        if (!keepAlive) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }
    
}
