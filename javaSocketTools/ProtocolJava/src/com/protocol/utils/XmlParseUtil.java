package com.protocol.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * xml 解析工具 
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	下午2:16:17
 * @version 1.0
 */
public class XmlParseUtil
{
	private final SAXReader reader = new SAXReader();
	
	private Document document; 
	private File file;
	
	
	public XmlParseUtil(File file) {
		this.file = file;
		reader.setEncoding("UTF-8");
		try
		{
			final FileInputStream in = new FileInputStream(file); 
			final Reader read = new InputStreamReader(in,"UTF-8");  
			document = reader.read(read);
			read.close();
			in.close();
		} catch (IOException | DocumentException e)
		{
			e.printStackTrace();
		}  
	}
	
	public Element getRoot() {
		return document.getRootElement();
	}
	
	public static final XmlParseUtil createXml(String name, String rootName) {
		final File file = new File(SystemConfUtil.getProtocolPath() + name);
			try
			{
				file.createNewFile();
				Document document = DocumentHelper.createDocument();
				document.addElement(rootName);
				/* 定义输出流的目的地 */
				FileWriter fw = new FileWriter(file);

				/* 定义输出格式和字符集 */
				OutputFormat format = OutputFormat.createPrettyPrint();
				format.setEncoding("UTF-8");

				// 定义用于输出xml文件的XMLWriter对象
				XMLWriter xmlWriter = new XMLWriter(fw, format);
				xmlWriter.write(document);
				xmlWriter.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		final XmlParseUtil result = new XmlParseUtil(file);
		return result;
	}
	
	public void save()
	{
		try
		{

			/* 定义输出流的目的地 */
			PrintWriter pw = new PrintWriter(file, "utf-8");
			/* 定义输出格式和字符集 */
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			// 定义用于输出xml文件的XMLWriter对象
			XMLWriter xmlWriter = new XMLWriter(pw, format);
			xmlWriter.write(document);
			xmlWriter.flush();
			xmlWriter.close();
			pw.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}
	
}
