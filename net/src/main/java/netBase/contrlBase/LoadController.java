package netBase.contrlBase;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import netBase.Manager.ModuleManager;



/**
 * 加载控制器对象
 */
public class LoadController
{
	static Logger log = Logger.getLogger(LoadController.class);
	
	/**
	 * 加载默认包下的所有控制器
	 */
	public static void load () throws Exception
	{
		load("com.gline.controller.ALL");
	}
	
	/**
	 * 加载指定包下的所有控制器
	 * @param packageName	包名
	 */
	public static void load (String packageName) throws Exception
	{
		List<Class<?>> classes = PackageClassUtil.getClass(packageName);
		Map<Integer, Class<?>> loadClzMap = new HashMap<Integer, Class<?>>();
		
		for(Class<?> clazz : classes)
		{
			loadClazz(clazz, loadClzMap);
		}
	}
	
	/**
	 * 加载单个class
	 * @param clazz
	 */
	private static boolean loadClazz (Class<?> clazz, Map<Integer, Class<?>> loadClzMap) throws Exception
	{
		/*if(log.isDebugEnabled())
			log.debug("加载类: " + clazz.getSimpleName());*/
		
		final int modifiers = clazz.getModifiers();

		if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers))
		{
			log.warn(new StringBuilder("加载控制器失败,类[").append(clazz.getName()).append("]不能为抽象类或接口！！").toString());
			return false;
		}
		
		if (!Modifier.isPublic(modifiers))
		{
			log.warn(new StringBuilder("加载控制器失败,类[").append(clazz.getName()).append("]必须public！！").toString());
			return false;
		}
		
		if (!clazz.isAnnotationPresent(ClassAnn.class))
		{
			log.warn(new StringBuilder("加载控制器失败,类[").append(clazz.getName()).append("]缺少ClassAnn注解！！").toString());
			return false;
		}
		
		ClassAnn ann = clazz.getAnnotation(ClassAnn.class);
		if (ann == null)
		{
			log.warn(new StringBuilder("加载控制器失败,类[").append(clazz.getName()).append("]注解 不存在！！").toString());
			return false;
		}
		
		if (loadClzMap.containsKey(ann.key()))
		{
			log.warn(new StringBuilder("加载控制器失败,key["+ ann.key() +"]已经存在类["+ loadClzMap.get(ann.key()).getSimpleName() +
					"],当前类[").append(clazz.getSimpleName()).append("]不可重复注册！！").toString());
			return false;
		}
		
		loadClzMap.put(ann.key(), clazz);
		ModuleManager.getInstance().register(ann.key(), clazz);
		return true;
	}
	
}
