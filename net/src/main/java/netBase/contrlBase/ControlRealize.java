package netBase.contrlBase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import io.netty.buffer.ByteBuf;
import netBase.Manager.ModuleManager;
import netBase.packet.ReceivablePacket;
import netBase.Session;

/**
 * @author timoSky
 * @Data 2012-12-20
 */
public class ControlRealize {
	
    public static void ControllerWait(Session mmo, ReceivablePacket pack)
    		throws IllegalAccessException, IllegalArgumentException,InvocationTargetException
    {

		ByteBuf buff = pack.getBuffer();
        
    	//log.error("model"+model+"order"+order);   	
        	
    	Class<?> clazz = ModuleManager.getInstance().get(pack.getModule());
   	
    	Method[] methods = clazz.getDeclaredMethods();     	
    	
		List<Object> obj= new ArrayList<Object>(); 

		//log.info("222222222222222222"+clazz.getName()); 
    	for(Method method : methods)
    	{
    		//log.info("333333333333"+method.getName()); 

            if (method.isAnnotationPresent(ControllerAnnotation.class)) 
            {  
            	ControllerAnnotation annotation = method.getAnnotation(ControllerAnnotation.class);

            	if(annotation.key()==pack.getOpcode())
            	{
            		Type[] paramTypeList = method.getGenericParameterTypes();
            		
	               	int n = 0;
	               		               	
	               	for (Type paramType : paramTypeList) 
	               	{	               		
	            		if(paramType == null || paramType instanceof ParameterizedType)
	            		{
	            				break;
	            		}
	            		else if(paramType == Integer.TYPE)
	                	{
	            			int a = buff.readInt();
	            			obj.add(a);
	            			n++;
	                	}
	                	
	            		else if(paramType ==  Byte.TYPE)
	            	    {
	            			obj.add(buff.readByte());
	            			n++;            	    		
	            	    }
	            		    	
	            		else if(paramType ==  String.class)
	            		{	            										
	            			StringBuffer stringbuff = new StringBuffer();
	            			char c =  buff.readChar();
	            			while(c!=0)
	            			{
	            				stringbuff.append(c);
	            				c = buff.readChar();
	            			}
	            			
	            			obj.add(stringbuff.toString());
	            			n++;
	            		}	
	            		    	
	            		else if(paramType == Short.TYPE)
	            	    {
	            			obj.add(buff.readShort());
	            			n++;
	            	    }
	            	    	
	            	    else if(paramType == Long.TYPE)
	            	    {
	            	    	long tes = buff.readLong();
	            	    	obj.add(tes);
	            	    	//log.info("tes============"+tes);
	            			n++;
	            	   	}
	            		    	          	
	                	else if (paramType ==Double.TYPE)
	                	{
	                		obj.add(buff.readDouble());
	            			n++;                		
	                	}
	                	
	                	else if (paramType ==Float.TYPE)
	                	{
	                		float nnn = buff.readFloat();
	                		obj.add(nnn);
	            			n++;
	                	}
	            		
	            		//TODO:這裡應該處理其他類型的參數，復合參數	            		
	            		//TODO:但是这里，会反射GameClient，要注意区分
	            		//TODO:工具的类型，用其他类来做
	                	else
	                	{
	                		//TODO：:如果传输的是对象，则要在前面加上对象的length （4个字节）
	                		//Object m = ((ReceivableBuffer)BufferManager.getReceivable(paramType.toString())).read(
	                			//	buff.toByteBuffer(0, buff.capacity()));
	                		//obj.add(m);
	                		//n++;
	                	}
	                }
	               	 
	               	int lenth = n+1;
	               	
	                Object[] newobj = new Object[lenth];           
	                
	                newobj[0] =  mmo;
	                
	                for(int i=1; i<lenth; i++)
	                {
	                	 newobj[i] = obj.get(i-1);
	                }
	                
	               	method.setAccessible(true); 
	             	method.invoke(clazz,newobj);
	             	return;
	              }
	               	
            	}
            }          
    	}  	    	 
    }

