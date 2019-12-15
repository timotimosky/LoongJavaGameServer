package com.protocol.view.mian.dialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import com.mnt.gui.base.controller.BaseController;
import com.mnt.gui.base.util.FXMLLoaderTool;

/**
 * 配置弹框界面
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	下午4:43:27
 * @version 1.0
 */
public class InfoController extends BaseController
{
	
	private final Stage stage;
    
	public InfoController(Stage stage) {
		FXMLLoaderTool.load(this);
		this.stage  = stage;
	}
	
	@Override
	public void init()
	{
		
	}
	
    @FXML
    void processClose(ActionEvent event) {
    	stage.close();
    }

}
