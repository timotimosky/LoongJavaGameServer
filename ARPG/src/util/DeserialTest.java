package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException; 
import java.io.ObjectInputStream;

import org.apache.log4j.Logger;

import netBase.netty.ServerHandler;

public class DeserialTest {

	private static final Logger log = Logger.getLogger(ServerHandler.class);
/**   * @param args   */
	public static void main(String[] args) {  
		
	// TODO Auto-generated method stub  
		Serial serial2 ;  
		try {   
			FileInputStream fis = new FileInputStream("serialTest.txt");    
			ObjectInputStream ois = new ObjectInputStream(fis);    
			serial2 = (Serial)ois.readObject();    
			ois.close();    
			log.info("Object Deserial"+serial2);   
			} 
		
		
		catch (FileNotFoundException e) {    
			// TODO Auto-generated catch block   
			e.printStackTrace();   
			} 
		catch (IOException e) 
		{    // TODO Auto-generated catch block   
			e.printStackTrace();  
			
		} catch (ClassNotFoundException e) {  
			// TODO Auto-generated catch block    
			e.printStackTrace();  
			}   
		}
		
}
