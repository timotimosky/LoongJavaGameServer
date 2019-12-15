package com.protocol.model;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;

/**
 * 每个协议列表 
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	下午3:15:55
 * @version 1.0
 */
public class ProtocolModel
{
	private List<CommandModelVO> commandModels;
	
	private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty asClass = new SimpleStringProperty("");
    private SimpleStringProperty value = new SimpleStringProperty("");
    private SimpleStringProperty desc = new SimpleStringProperty("");
    private SimpleStringProperty packageName = new SimpleStringProperty("");
	
	public List<CommandModelVO> getCommandModels()
	{
		return commandModels;
	}

	public void setCommandModels(List<CommandModelVO> commandModels)
	{
		this.commandModels = commandModels;
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
	    public void setPackageName(String packageName) {
	    	this.packageName.set(packageName);
	    }
	    
	    public String getPackageName() {
	    	return packageName.get();
	    }
	    
	    public SimpleStringProperty packageNameProperty() {
	    	return packageName;
	    }

	    public void setAsClass(String asClass) {
	        this.asClass.set(asClass);
	    }

	    public String getAsClass() {
	        return asClass.get();
	    }

	    public SimpleStringProperty asClassProperty() {
	        return asClass;
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

	    public void setDesc(String desc) {
	        this.desc.set(desc);
	    }

	    public String getDesc() {
	        return desc.get();
	    }

	    public SimpleStringProperty descProperty() {
	        return desc;
	    }

	    
	@Override
	public String toString()
	{
		return "ProtocolModel [commandModels=" + commandModels + ", name=" + name + ", asClass=" + asClass + ", value=" + value + ", desc=" + desc + "]";
	}
	
}
