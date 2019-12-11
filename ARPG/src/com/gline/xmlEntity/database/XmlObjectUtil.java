package com.gline.xmlEntity.database;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


/**
 * ʹ��JAXB��XML���ݰ󶨣�ת��Ϊjava��
 * @author ������
 * @date 2012-1-7
 */
public class XmlObjectUtil
{

	/**
	 * XMLתjava
	 */
  public static  Object XmlToJavaBean(String str, Class<?> clazz)  throws Exception{
	  
    JAXBContext jc = JAXBContext.newInstance(new Class[] { clazz });
    Unmarshaller un = jc.createUnmarshaller();
    Object t = un.unmarshal(new File(str));
    return t;
  }

	/**
	 * XMLתjava
	 */
  public static <T> Object XmlToJavaBean(File file, Class<T> clazz) throws Exception {
	  
    JAXBContext jc = JAXBContext.newInstance(new Class[] { clazz });
    Unmarshaller un = jc.createUnmarshaller();
    Object t = un.unmarshal(file);
    return t;
  }

	/**
	 * JavaתXML
	 */
  public static void JavaBeanToXml(String file, Object obj) throws Exception{
	  
    JAXBContext jc = JAXBContext.newInstance(new Class[] { obj.getClass() });
    Marshaller ms = jc.createMarshaller();
    ms.setProperty("jaxb.formatted.output", Boolean.TRUE);
    FileOutputStream stream = new FileOutputStream(file);
    ms.marshal(obj, stream);
  }
}
