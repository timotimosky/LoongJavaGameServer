package com.protocol.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * 协议树VO 
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	上午11:50:11
 * @version 1.0
 */
public class ProtocolItemVO
{
	private SimpleStringProperty name = new SimpleStringProperty("");
	private SimpleStringProperty value = new SimpleStringProperty("");
	private SimpleStringProperty info = new SimpleStringProperty("");
	private SimpleStringProperty remark = new SimpleStringProperty("");
	private SimpleStringProperty packageName = new SimpleStringProperty("");
	private boolean proxy;
	private CommandModelVO commandVO;
	
	public boolean isProxy()
	{
		return proxy;
	}

	public void setProxy(boolean proxy)
	{
		this.proxy = proxy;
	}
	
	public CommandModelVO getCommandVO()
	{
		return commandVO;
	}

	public void setCommandVO(CommandModelVO commandVO)
	{
		this.commandVO = commandVO;
	}

	public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setRemark(String remark) {
        this.remark.set(remark);
    }

    public String getRemark() {
        return remark.get();
    }

    public SimpleStringProperty remarkProperty() {
        return remark;
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public String getValue() {
        return value.get();
    }

    public SimpleStringProperty valueProperty() {
        return value;
    }

    public void setInfo(String info) {
        this.info.set(info);
    }

    public String getInfo() {
        return info.get();
    }

    public SimpleStringProperty infoProperty() {
        return info;
    }

	@Override
	public String toString()
	{
		return info.get();
	}
	
	public void setPackageName(String packageName) {
    	this.packageName.set(packageName);
    }
    
    public String getPackageName() {
    	return packageName.get();
    }
    
    public SimpleStringProperty packageNameProperty() {
    	return packageName;
    }

    
}
