package com.gline.module.person.model;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * �ȼ���Ӧ�����࣬ӳ��Xml���ӽڵ�
 * @author �����
 * @Date 2012-9-26
 */

@XmlRootElement(name = "level")
@XmlAccessorType(XmlAccessType.NONE)
public class LvAddModel
{
	
	@XmlAttribute(name = "lv")
	private int lv;
	@XmlAttribute(name = "lv_exp")
	private  int lvExp;

	public int getLv() {
		return lv;
	}

	public  int getLvExp() {
		return lvExp;
	}	

}
