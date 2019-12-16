package util.id;

/**
 * ID生成列表生成值已经超过最大可生成值。
 * @author 
 * @create 2014-10-25
 */
public class IDExceedMaximumException extends RuntimeException
{
	private static final long serialVersionUID = 102668953585L;

	public IDExceedMaximumException() 
	{
		super();
	}

	public IDExceedMaximumException(String s) 
	{
		super(s);
	}

	public IDExceedMaximumException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public IDExceedMaximumException(Throwable cause) 
	{
		super(cause);
	}
}
