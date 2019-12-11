package util.id;

/**
 * 当ID重复时抛出此异常
 * @author 
 * @create 2014-10-25
 */
public class DuplicateIDException extends RuntimeException 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4029843626479524346L;

	public DuplicateIDException() 
	{
		super();
	}

	public DuplicateIDException(String s) 
	{
		super(s);
	}

	public DuplicateIDException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public DuplicateIDException(Throwable cause) 
	{
		super(cause);
	}
}
