package com.protocol.control;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.mnt.gui.base.contorls.FileChooserFacotry;
import com.protocol.model.CommandModelVO;
import com.protocol.model.RowModelVO;
import com.protocol.utils.SystemConfUtil;
import com.protocol.view.cache.ItemCache;
import com.protocol.view.mian.dialog.ConfigureController;
import com.protocol.view.mian.dialog.InfoController;
import com.protocol.view.mian.item.CommandView;

/**
 * 控件构造工厂类
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	下午8:39:42
 * @version 1.0
 */
public abstract class ControlBuilderFactory
{
	private ControlBuilderFactory() {
		
	}
	
	/**
	 * 构建命令控件
	 * @param commandModelVO
	 * @return
	 * @create 2014年8月14日 Cico.姜彪
	 */
	public static final CommandView builderCommandItemByCommandModelVO(CommandModelVO commandModelVO, ItemExecuter executer, int index) {
		final CommandView result = ItemCache.addCommandView(index);
		result.build(commandModelVO, executer);
		return result;
	}
	
	private static final List<String> types = new ArrayList<String>();
	
	private static final List<String> selectTypes = new ArrayList<String>();
	
	public static List<String> getSelecttypes()
	{
		return selectTypes;
	}

	public static List<String> getTypes()
	{
		return types;
	}

	/**
	 * 构建生成文件类型的列表
	 * @param director
	 * @return
	 * @create 2014年8月14日 Cico.姜彪
	 */
	public static final VBox builderTypeSelected(File director, SelectedFileExecuter selected) {
		final VBox result = new VBox(5);
		result.setAlignment(Pos.TOP_CENTER);
		final File[] files  = director.listFiles(new FileFilter()
		{
			@Override
			public boolean accept(File pathname)
			{
				return pathname.getName().endsWith(".code.tmp");
			}
		}); 
		CheckBox checkBox = null;
		for (File file : files)
		{
			checkBox = new CheckBox();
			String fileName = file.getName();
			fileName = fileName.substring(0, fileName.indexOf('.'));
			types.add(fileName);
			checkBox.setText(fileName);
			final String type = fileName;
			checkBox.setPrefWidth(60);
			checkBox.selectedProperty().addListener((obs, oldValue, newValue) -> {
				if(newValue) {
//					selected.getSelectedFile(file);
//					SystemConfUtil.setCurrFileType(type);
					selectTypes.add(type);
				} else {
					selectTypes.remove(type);
				}
			});
			checkBox.setSelected(true);
			result.getChildren().add(checkBox);
		}
		return result;
	}
	
	/**
	 * 获取文件选择组件
	 * @return
	 * @create 2014年8月15日 Cico.姜彪
	 */
	public static final HBox buildFilePathSelector(Stage stage, String type) {
		final HBox result = new HBox(10);
		result.setPrefHeight(30);
		result.setAlignment(Pos.CENTER_LEFT);
		result.getStyleClass().add("font-14");
		
		final TextField txtFilePath = new TextField();
		txtFilePath.getStyleClass().add("text-field-beautiful");
		txtFilePath.setEditable(false);
		txtFilePath.setPromptText("请选择生成的文件路径");
		final Button button = new Button(type + "类型路径选择");
		final Button btnOpen = new Button("打开文件夹");
		btnOpen.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				 try {
					Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL "
							+ "Explorer.exe /open," + txtFilePath.getText());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		button.setPrefWidth(150);
		btnOpen.getStyleClass().add("button-beautiful");
		button.getStyleClass().add("button-beautiful");
		result.getChildren().addAll(txtFilePath, button, btnOpen);
		HBox.setHgrow(txtFilePath, Priority.ALWAYS);
		txtFilePath.setText(SystemConfUtil.getSavePath(type));
		button.setOnAction(event -> {
			final File file = FileChooserFacotry.chooserDirectorControl(stage, txtFilePath.getText());
	    	if(null != file) {
	    		txtFilePath.setText(file.getAbsolutePath());
	    		SystemConfUtil.setSavePath(type, txtFilePath.getText());
	    	}
		});
		return result;
	}
	
	/**
	 * 配置界面 
	 * @param parentStage
	 * @create 2014年8月18日 Cico.姜彪
	 */
	public static final void showConfigDialog(Stage parentStage) {
		final Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.DECORATED);
		stage.initOwner(parentStage);
		final ConfigureController result = new ConfigureController(stage);
		Scene scene = new Scene(result);
		stage.setScene(scene);
		stage.showAndWait();
	}
	
	/**
	 * 显示快捷键说明
	 * @create 2014年8月18日 Cico.姜彪
	 */
	public static final void showInfoDialog() {
		final Stage stage = new Stage();
		stage.initStyle(StageStyle.DECORATED);
		final InfoController result = new InfoController(stage);
		Scene scene = new Scene(result);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * 构建树状列表
	 * @param content
	 * @param rowModelVo
	 * @create 2014年9月22日 Cico.姜彪
	 */
	public static final VBox buildCommandRow(List<TreeItem<RowModelVO>> rowModelVo) {
		final VBox result = new VBox();
		
		
		
		return result;
	}
	
	public static final HBox buildCommamdRow() {
		final HBox result = new HBox();
		
		return result;
	}
	
	/**
	 * 构建模板选择
	 * @return
	 * @create 2014年12月18日 Cico.姜彪
	 */
	public static final List<RadioButton> getCommandTemps() {
		final List<String> tmpNames = SystemConfUtil.getCommandFileNames();
		List<RadioButton> result = new ArrayList<>(tmpNames.size());
		RadioButton radioButton;
		for (String name : tmpNames)
		{
			radioButton = new RadioButton(name);
			result.add(radioButton);
		}
		return result;
	}
}
