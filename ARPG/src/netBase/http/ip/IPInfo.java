package netBase.http.ip;

/**
 * IP信息
 * @author djl
 * @date 2013-3-26
 */
public class IPInfo
{
	/**
	 * IP地址
	 */
	String ip;
	
	
	/**
	 * 数量
	 */
	int num;
	
	
	/**
	 * 最大数量
	 */
	int numMax;
	
	
	IPInfo(String ip, int numMax)
	{
		this.ip = ip;
		
		this.numMax = numMax;
	}


	public String getIp()
	{
		return ip;
	}


	void setIp(String ip) 
	{
		this.ip = ip;
	}


	public int getNum() 
	{
		return num;
	}


	void setNum(int num) 
	{
		this.num = num;
	}

	
	public int getNumMax() 
	{
		return numMax;
	}

	public void setNumMax(int numMax) 
	{
		this.numMax = numMax;
	}
	
	
	

}
