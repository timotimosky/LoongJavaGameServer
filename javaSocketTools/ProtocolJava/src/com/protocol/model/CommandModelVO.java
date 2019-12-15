package com.protocol.model;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TreeItem;

/**
 * 每个命令视图obj 
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	下午3:15:17
 * @version 1.0
 */
public class CommandModelVO {

    private SimpleStringProperty enName = new SimpleStringProperty("");
    private SimpleStringProperty cnName = new SimpleStringProperty("");
    private SimpleStringProperty value = new SimpleStringProperty("");
    private SimpleStringProperty direction = new SimpleStringProperty("");
    private List<TreeItem<RowModelVO>> rowModels;
    
    public List<TreeItem<RowModelVO>> getRowModels()
	{
		return rowModels;
	}

	public void setRowModels(List<TreeItem<RowModelVO>> rowModels)
	{
		this.rowModels = rowModels;
	}

	public void setEnName(String enName) {
        this.enName.set(enName);
    }

    public String getEnName() {
        return enName.get();
    }

    public SimpleStringProperty enNameProperty() {
        return enName;
    }

    public void setCnName(String cnName) {
        this.cnName.set(cnName);
    }

    public String getCnName() {
        return cnName.get();
    }

    public SimpleStringProperty cnNameProperty() {
        return cnName;
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

    public void setDirection(String direction) {
        this.direction.set(direction);
    }

    public String getDirection() {
        return direction.get();
    }

    public SimpleStringProperty directionProperty() {
        return direction;
    }

	@Override
	public String toString()
	{
		return "CommandModelVO [enName=" + enName + ", cnName=" + cnName + ", value=" + value + ", direction=" + direction + ", rowModels=" + rowModels + "]";
	}

}