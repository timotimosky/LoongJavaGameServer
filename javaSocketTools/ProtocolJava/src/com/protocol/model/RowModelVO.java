package com.protocol.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * 每一行数据模型
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	下午2:55:03
 * @version 1.0
 */
public class RowModelVO {
    private final SimpleStringProperty name = new SimpleStringProperty("");
    private final SimpleStringProperty type = new SimpleStringProperty("int");
    private final SimpleStringProperty size = new SimpleStringProperty("4");
    private final SimpleStringProperty info = new SimpleStringProperty("");
    private final SimpleStringProperty remark = new SimpleStringProperty("");
    

    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setSize(String size) {
        this.size.set(size);
    }

    public String getSize() {
        return size.get();
    }

    public SimpleStringProperty sizeProperty() {
        return size;
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

    public void setRemark(String remark) {
        this.remark.set(remark);
    }

    public String getRemark() {
        return remark.get();
    }

    public SimpleStringProperty remarkProperty() {
        return remark;
    }

	@Override
	public String toString()
	{
		return "RowModeVO [name=" + name + ", type=" + type + ", size=" + size + ", info=" + info + ", remark=" + remark + "]";
	}

}