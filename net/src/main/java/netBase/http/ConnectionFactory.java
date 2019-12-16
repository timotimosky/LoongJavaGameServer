package netBase.http;

/**
 * 连接工厂
 * @author djl
 * @create 2013-3-27
 */
public interface ConnectionFactory<T extends HttpConnection> 
{
	/**
	 * 创建连接
	 * @param id
	 * 						连接ID
	 * @return
	 * 						返回连接
	 * @create 2013-3-27 djl
	 */
	public T createConnection(String id);
	
	
	
}
