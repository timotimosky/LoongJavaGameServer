package Timer;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;  

//场景定时器
public class SenceTimer {

	
	
	public static Map<String, Timer> TimeMap = new HashMap<String, Timer>();
	
    public static void TimeBegin(String name,long delay, long Rate,TimerTask mTask) 
    {  
        Timer timer = new Timer();  
        timer.schedule(mTask, delay, Rate);  
    	if (!TimeMap.containsKey(name)) {
    		TimeMap.put(name, timer);
		}
    	else
    	{

    	}
    }  
  
    public void CancelTimer(String name)
    {
    	if (TimeMap.containsKey(name)) {
    		TimeMap.get(name).cancel();
		}
    	
    }
    
    
}
