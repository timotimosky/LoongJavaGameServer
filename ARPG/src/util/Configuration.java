package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

/**
 * ���л���������
 * @author 
 *
 */
public class Configuration implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Properties propertie;
	private FileInputStream inputFile;
	private FileOutputStream outputFile;


	public Configuration()
	{
		propertie = new Properties();
	}

	/**
	 * �����ļ�·������������
	 * @param filePath �ļ�·��
	 */
	public Configuration(String filePath)
	{
		propertie = new Properties();
		try {
			inputFile = new FileInputStream(filePath);
			propertie.load(inputFile);
			inputFile.close();
		}
		catch (FileNotFoundException ex) 
		{
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}


	/**
	 * �������ü���ȡ���ü�ֵ
	 * @param key ���ü�
	 * @return ���ü�ֵ
	 */
	public String getValue(String key)
	{
		if(propertie.containsKey(key)){
			String value = propertie.getProperty(key);
			return value;
		}
		else 
			return "";
	}

	/**
	 * ���������ļ�·��,���ü���ȡ���ü�ֵ
	 * @param fileName �����ļ�·��
	 * @param key ���ü�
	 * @return ���ü�ֵ
	 */
	public String getValue(String fileName, String key)
	{
		try {
			String value = "";
			inputFile = new FileInputStream(fileName);
			propertie.load(inputFile);
			inputFile.close();
			if(propertie.containsKey(key)){
				value = propertie.getProperty(key);
				return value;
			}else
				return value;
		} catch (FileNotFoundException e){
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	/**
	 * ������ü�
	 */
	public void clear()
	{
		propertie.clear();
	}

	/**
	 * ���� ���ü�ֵ
	 * @param key ���ü�
	 * @param value ���ü�ֵ
	 */
	public void setValue(String key, String value)
	{
		propertie.setProperty(key, value);
	}

	/**
	 * ���������ļ�
	 * @param fileName ���������ļ�·��
	 * @param description �ļ���������
	 */
	public void saveFile(String fileName, String description)
	{
		try {
			outputFile = new FileOutputStream(fileName);
			propertie.store(outputFile, description);
			outputFile.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException ioe){
			ioe.printStackTrace();
		}
	}
}


