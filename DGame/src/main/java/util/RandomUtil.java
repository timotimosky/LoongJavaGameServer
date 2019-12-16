package util;

/**
 *
 * @param           
 * @return
 * @create 2012-11-9
 */
public class RandomUtil {

	
	/*����small-big�������,small��big�������κε���ֵ����ʹ�Ǹ���Ҳһ��*/
	public static int Int(int small,int big)
	{
		/*��������*/
		Math.round(Math.random()*100); 
		if(small<=big)
		{
			return (int)Math.round(Math.random()*(big-small))+small; 
		}
		else
			return (int)Math.round(Math.random()*(small-big))+big; 	
	}
	
	/*����0-a�������*/
	public static int IntFromZero(int a)
	{
		/*����ȡ��*/	
		return (int)Math.round(Math.random()*a); 
	}
	
	/*����1-a�������*/
	public static int IntFromOne(int a)
	{
		return (int)Math.ceil(Math.random()*a); 
	}
	
	
	/*�������ֵ*/
	public static boolean Boolean()
	{
		long i = Math.round(Math.random()); 
		if(i==0)
			return false;
		else
			return true;
	}
	
}
