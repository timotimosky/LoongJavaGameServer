package thread;

/**
 *
 * @author
 * @date 2013-1-14
 */
public abstract class ExecuteThreadEver implements Runnable
{
	protected volatile boolean _isAlive = true;

	public final void run()
	{
		try
		{
			//begin();
			while (this._isAlive)
			{
				try
				{	
					runTurn();
					sleepTurn();
				}
				catch (Exception e)
				{
					System.out.print("=========================e！！！"+e);
					e.printStackTrace();
				}
				finally
				{
					//this._isAlive =false;
					//System.out.print("==========this._isAlive =false===============e！！！");
				}
			}
		}
		finally
		{
			onFinally();
		}
	}

	protected abstract void begin();
	
	protected abstract void runTurn();

	protected abstract void sleepTurn()throws InterruptedException;

	protected void onFinally()
	{
	}
}