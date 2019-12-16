package netBase.http.ip;



/**
 * IP管理
 * @author djl
 * @create 2013-3-26
 */
public class IPManager extends AbstractIPManager<Long, IPInfo>
{
	private static class SingleInstance
	{
		public static final IPManager instance = new IPManager();
	}
	
	private IPManager()
	{
	}
	
	public static IPManager getInstance()
	{
		return SingleInstance.instance;
	}
	
	/**
	 * 创建一个IP信息
	 * @param ip				IP地址
	 * @param numMax			最大数量限制
	 * @return					
	 * 				返回IPinfo类
	 * @create 2013-3-26 djl
	 */
	public static IPInfo createIPInfo(String ip, int numMax)
	{
		return new IPInfo(ip, numMax);
	}
	
	
	@Override
	public void register(Long k, IPInfo v) 
	{
		IPInfo oldInfo = this.map.get(k);
		
		if(oldInfo != null)
		{
			StringBuilder builder = new StringBuilder("IP地址[").append(oldInfo.getIp()).append("]最大数量限制[");
			builder.append(oldInfo.getNumMax()).append("]已经存在。");
			//log.warn(builder.toString(), new IPAlreadyRegisteredException());
			return;
		}
		this.map.put(k, v);
		
		StringBuilder builder = new StringBuilder("IP地址[").append(v.getIp()).append("]最大数量限制[");
		builder.append(v.getNumMax()).append("]注册成功！");
		log.info(builder.toString());
	}

	@Override
	public void unregister(Long k)
	{
		this.map.remove(k);
	}

	
	@Override
	public IPInfo get(Long k) 
	{
		return this.map.get(k);
	}

	/**
	 * 判断此IP是否存在
	 * @param adress		地址
	 * @return
	 * 				false 不存在
	 * 				true 存在
	 * @create 2013-3-27 djl
	 */
	public boolean isIPExist(String adress)
	{
		if(this.map.containsKey(toLong(adress)))
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}
}
