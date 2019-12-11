package util;

import java.io.FileNotFoundException; 
import java.io.FileOutputStream; 
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

import netBase.netty.ServerHandler;

public class SerialTest {

	private static final Logger log = Logger.getLogger(ServerHandler.class);
/**   * @param args   */ 
	public static void main(String[] args) 
	{  
	// TODO Auto-generated method stub  
		Serial serial1 = new Serial(1,"song");   
		log.info("Object Serial"+serial1);     
		try {    
			FileOutputStream fos = new FileOutputStream("serialTest.txt");   
			ObjectOutputStream oos = new ObjectOutputStream(fos);   
			oos.writeObject(serial1);    
			oos.flush();    
			oos.close();        
			} 
		catch (FileNotFoundException e) 
			{    
			// TODO Auto-generated catch block   
			e.printStackTrace();   
			} 
			catch (IOException e) 
			{   
				// TODO Auto-generated catch block  
				e.printStackTrace();   
			} 
	} 
}

