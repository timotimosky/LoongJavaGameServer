package com.gline.log;


import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *   以上配置是每天产生一个备份文件。其中备份文件的名字叫backup.log。
    具体的效果是这样：当天的日志信息记录在backup.log文件中，前一天的记录在名称为   backup.log.yyyy-mm-dd 的文件中。
 * log4j
 * @author timosky
 * @date 2013-1-11
 */
public class LogFactory {

	    public static void init()  throws Exception
	    {	  
	    	//不用默认配置,读取自己的配置
	        //BasicConfigurator.configure();
	        Properties props = new Properties();
	        props.load(new FileInputStream(System.getProperty("user.dir")+"/properties/log4j.properties"));
	        
	        String driver =props.getProperty("log4j.rootLogger");

	        Logger logger = Logger.getLogger(LogFactory.class);
	        logger.info("This is an driver."+driver);
	        logger.setLevel(Level.INFO);
	        // This request will be disabled since Level.DEBUG < Level.INFO.
	        logger.debug("This is debug.");
	        // These requests will be enabled.
	        logger.info("This is an info.");
	        logger.warn("This is a warning.");
	        logger.error("This is an error.");
	        logger.fatal("This is a fatal error.");
	        
	        return;
	    }
	

}
