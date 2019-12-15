package com.protocol.view.mian.item;

import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import com.mnt.gui.base.contorls.SimpleDialogFactory;
import com.mnt.gui.base.contorls.executer.DialogExecuter;
import com.mnt.gui.base.controller.BaseController;
import com.mnt.gui.base.logger.TipDialogLogger;
import com.mnt.gui.base.util.FXMLLoaderTool;
import com.protocol.control.ItemExecuter;
import com.protocol.model.CommandModelVO;
import com.protocol.model.RowModelVO;
import com.protocol.type.DataType;
import com.protocol.type.RequestType;

/**
 * 一条命令对应的界面 
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	下午5:04:30
 * @version 1.0
 */
public class CommandView extends BaseController
{

	@FXML
	private TextField txtCommandId;
	
    @FXML
    private VBox vbMain;

	@FXML
	private ComboBox<RequestType> combCommandType;

	@FXML
	private TreeTableColumn<RowModelVO, String> tcolName;

    @FXML
    private Button btnAddListParam;

	@FXML
	private TextField txtListPackage;

	@FXML
	private TreeTableView<RowModelVO> treetvwParams;

	@FXML
	private TextField txtCommandName;

	@FXML
	private TreeTableColumn<RowModelVO, String> tcolType;

	@FXML
	private TreeTableColumn<RowModelVO, String> tcolInfo;

	@FXML
	private TreeTableColumn<RowModelVO, String> tcolSize;

	@FXML
	private TextField txtCommandInfo;

	@FXML
	private Button btnDel;

	@FXML
	private Button btnDelParam;
	
	private CommandModelVO commandModelVO;
	
	private ItemExecuter executer;
	
	public CommandView() {
		FXMLLoaderTool.load(this);
	}
	
	private TreeItem<RowModelVO> root;
	
	@Override
	public void init()
	{
		combCommandType.setItems(FXCollections.observableArrayList(RequestType.values()));
		combCommandType.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) ->{
			commandModelVO.setDirection(newValue.getCode());
			if(txtCommandName.getText().equals("") || txtCommandName.getText().equals(RequestType.CLIENT.getStartName()) || txtCommandName.getText().equals(RequestType.SERVER.getStartName())) {
				switch(newValue) {
				case CLIENT:
					txtCommandName.setText(RequestType.CLIENT.getStartName());
					break;
				case SERVER:
					txtCommandName.setText(RequestType.SERVER.getStartName());
					break;
				default:
					break;
				}
			}
		});
		tcolName.setCellValueFactory(param -> param.getValue().getValue().nameProperty());
		tcolType.setCellValueFactory(param -> param.getValue().getValue().typeProperty());
		tcolInfo.setCellValueFactory(param -> param.getValue().getValue().infoProperty());
		tcolSize.setCellValueFactory(param -> param.getValue().getValue().sizeProperty());
		tcolName.setCellFactory(param -> {
			return new TreeTableCell<RowModelVO, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					if(empty) {
						setText("");
						setGraphic(null);
					} else {
						final TextField txt = new TextField(item);
						txt.getStyleClass().add("table-text-filed");
						txt.setOnAction(event -> {
							getTreeTableRow().getItem().setName(txt.getText());
						});
						txt.focusedProperty().addListener((obs, oldValue, newValue) -> {
							if(newValue) {
								treetvwParams.getSelectionModel().select(getTreeTableRow().getTreeItem());
							} else {
								getTreeTableRow().getItem().setName(txt.getText());
							}
						});
						setGraphic(txt);
					}
					super.updateItem(item, empty);
				}
			};
		});
		tcolType.setCellFactory(param -> {
			
			return new TreeTableCell<RowModelVO, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					if(empty) {
						setText("");
						setGraphic(null);
					} else {
						final HBox hb = new HBox();
						final TextField txt = new TextField(item);
						final CheckBox cbKey = new CheckBox("key");
						txt.getStyleClass().add("table-text-filed");
						final ComboBox<DataType> cb = new ComboBox<DataType>();
						cb.getStyleClass().add("table-text-filed");
						cb.setItems(FXCollections.observableArrayList(DataType.values()));
						cb.getSelectionModel().select(DataType.getValueFor(item));
						cb.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
							if(null != newValue) {
								getTreeTableRow().getItem().setType(newValue.getName());
								getTreeTableRow().getItem().setSize(String.valueOf(newValue.getSize()));
								if(DataType.getValueFor(item) == DataType.LIST || DataType.getValueFor(item) == DataType.MAP || DataType.getValueFor(item) == DataType.ARRAY) {
									txt.setVisible(true);
								} else {
									txt.setVisible(false);
								}
								//if()
							}
						});
						txt.setText(item);
						if(DataType.getValueFor(item) == DataType.LIST || DataType.getValueFor(item) == DataType.MAP || DataType.getValueFor(item) == DataType.ARRAY) {
							txt.setVisible(true);
						} else {
							txt.setVisible(false);
						}
						
						if(checkParentIsMap(getTreeTableRow().getItem())) {
							cbKey.setVisible(true);
						} else {
							cbKey.setVisible(false);
						}
						
						if(null != getTreeTableRow().getItem().getRemark() && getTreeTableRow().getItem().getRemark().toLowerCase().equals("key")) {
							cbKey.setSelected(true);
						} else {
							cbKey.setSelected(false);
						}
						//map添加key
						cbKey.selectedProperty().addListener((obs, oldValue, newValue) -> {
							if(newValue) {
								getTreeTableRow().getItem().setRemark("key");
							} else {
								getTreeTableRow().getItem().setRemark("");
							}
						});
						
						
						txt.setOnAction(event -> {
							getTreeTableRow().getItem().setType(txt.getText());
						});
						txt.focusedProperty().addListener((obs, oldValue, newValue) -> {
							if(newValue) {
								treetvwParams.getSelectionModel().select(getTreeTableRow().getTreeItem());
							} else {
								getTreeTableRow().getItem().setType(txt.getText());
							}
						});
						
						
						hb.setAlignment(Pos.CENTER_LEFT);
						hb.setSpacing(5);
						hb.getChildren().addAll(cb, txt, cbKey);
						setGraphic(hb);
					}
					super.updateItem(item, empty);
				
				}
			};
		});
		tcolInfo.setCellFactory(param -> {
			return new TreeTableCell<RowModelVO, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					if(empty) {
						setText("");
						setGraphic(null);
					} else {
						final TextField txt = new TextField(item);
						txt.getStyleClass().add("table-text-filed");
						txt.setOnAction(event -> {
							getTreeTableRow().getItem().setInfo(txt.getText());
						});
						txt.focusedProperty().addListener((obs, oldValue, newValue) -> {
							if(newValue) {
								treetvwParams.getSelectionModel().select(getTreeTableRow().getTreeItem());
							} else {
								getTreeTableRow().getItem().setInfo(txt.getText());
							}
						});
						setGraphic(txt);
					}
					super.updateItem(item, empty);
				}
			};
		});
		root = new TreeItem<RowModelVO>(new RowModelVO());
		
		treetvwParams.setRoot(root);
		root.setExpanded(true);
		
		treetvwParams.getSelectionModel().selectedItemProperty().addListener((obs, oldvalue, newValue) -> {
			if(null != oldvalue) {
				txtListPackage.textProperty().unbindBidirectional(oldvalue.getValue().remarkProperty());
			}
			if(null != newValue) {
				currRowModel.set(newValue);
				processSelectedCommand(null);
				txtListPackage.textProperty().bindBidirectional(newValue.getValue().remarkProperty());
			}
		});
		btnAddListParam.setDisable(true);
		txtListPackage.setDisable(true);
		//初始不让删除参数
		btnDelParam.setDisable(true);
		currRowModel.addListener((obs, oldValue, newValue) -> {
			if(null != newValue) {
				btnDelParam.setDisable(false);
				final DataType type = DataType.getValueFor(newValue.getValue().getType());
				if(type == DataType.LIST || type == DataType.MAP || type == DataType.ARRAY) {
					btnAddListParam.setDisable(false);
					txtListPackage.setDisable(false);
				} else {
					btnAddListParam.setDisable(true);
					txtListPackage.setDisable(true);
				}
			} else {
				btnDelParam.setDisable(true);
			}
		});
		super.init();
	}
	
	/**
	 * 判断Vo的父节点是否为Map
	 * @param rowVO
	 * @return
	 * @create 2014年12月11日 Cico.姜彪
	 */
	private boolean checkParentIsMap(RowModelVO rowVO) {
		return checkParentIsMap(root.getChildren(), rowVO);
	}
	
	private boolean checkParentIsMap(ObservableList<TreeItem<RowModelVO>> childrens, RowModelVO rowVO) {
		for (TreeItem<RowModelVO> treeItem : childrens)
		{
			if(treeItem.getValue().equals(rowVO)) {
//				System.out.println(treeItem.getParent().getValue().getType()); //TODO
				if(DataType.MAP == DataType.getValueFor(treeItem.getParent().getValue().getType())) {
					return true;
				} else {
					return false;
				}
			}
			if(!treeItem.getChildren().isEmpty()) {
				return checkParentIsMap(treeItem.getChildren(), rowVO);
			}
			
		}
		return false;
	}
	
	private SimpleObjectProperty<TreeItem<RowModelVO>> currRowModel = new SimpleObjectProperty<TreeItem<RowModelVO>>();
	
	@FXML
	public void processDel(ActionEvent event)
	{
		SimpleDialogFactory.showSimpleDialog(getStage(), "是否删除", "是否删除命令" + commandModelVO.getEnName(), new DialogExecuter() {
			@Override
			public void processConfirm() {
				executer.delCommand(commandModelVO, getCommandItemController());
				TipDialogLogger.successed("删除命令成功！");
			}
			@Override
			public void processCancel() {
			}
		});
	}

	private CommandView getCommandItemController()
	{
		return this;
	}
	
	@FXML
	public void processAddParam(ActionEvent event)
	{
		final TreeItem<RowModelVO> selectItem = treetvwParams.getSelectionModel().getSelectedItem();
		final TreeItem<RowModelVO> newParam = new TreeItem<RowModelVO>(new RowModelVO());
		if(null != selectItem && null != selectItem.getParent()) {
			final List<TreeItem<RowModelVO>> currParent = selectItem.getParent().getChildren();
			int currSelectIndex = currParent.indexOf(selectItem);
			currParent.add(currSelectIndex + 1, newParam);
			final DataType type = DataType.getValueFor(selectItem.getParent().getValue().getType());
			if(type == DataType.LIST || type == DataType.MAP || type == DataType.ARRAY) {
				
			} else {
				commandModelVO.getRowModels().add(currSelectIndex + 1, newParam);
			}
		} else {
			commandModelVO.getRowModels().add(newParam);
			root.getChildren().add(newParam);
		}
		treetvwParams.getSelectionModel().select(newParam);
	}

	@FXML
	public void processAddListParam(ActionEvent event)
	{
		final DataType type = DataType.getValueFor(currRowModel.get().getValue().getType());
		if(type == DataType.LIST || type == DataType.MAP || type == DataType.ARRAY) {
			final TreeItem<RowModelVO> newParam = new TreeItem<RowModelVO>(new RowModelVO());
			currRowModel.get().setExpanded(true);
			currRowModel.get().getChildren().add(newParam);
		} else {
			TipDialogLogger.error("当前类型不是list/map/array 不允许添加子参数");
		}
	}

	@FXML
	public void processDelParam(ActionEvent event)
	{
		if(root.getChildren().isEmpty()) {
			TipDialogLogger.error("没有参数可以删除！");
			return;
		}
		SimpleDialogFactory.showSimpleDialog(getStage(), "是否删除", "是否删除参数" + currRowModel.getName(), new DialogExecuter() {
			@Override
			public void processConfirm() {
				if(null != currRowModel.get()) {
					final int index = treetvwParams.getSelectionModel().getSelectedIndex();
					currRowModel.get().getParent().getChildren().remove(currRowModel.get());
					commandModelVO.getRowModels().remove(currRowModel.get());
					treetvwParams.getSelectionModel().select(index - 1);
					TipDialogLogger.successed("删除参数成功！");
				} else {
					TipDialogLogger.error("请选择一个参数！");
				}
			}
			@Override
			public void processCancel() {
			}
		});
	}
	
	/**
	 * 当前选择的模块
	 */
	public static final SimpleObjectProperty<CommandView> currChoiceItem = new SimpleObjectProperty<>(null);
	
    @FXML
    public void processSelectedCommand(MouseEvent event) {
    	currChoiceItem.set(this);
//    	System.out.println("选中了模块");
    }

    /**
     * 获取组件索引
     * @return
     * @create 2014年8月15日 Cico.姜彪
     */
	public String getKey()
	{
		return commandModelVO.getEnName();
	}
	
	public void selected() {
		vbMain.getStyleClass().add("bg-selected");
	}
	
	public void removeSelected() {
		vbMain.getStyleClass().remove("bg-selected");
	}
	
	/**
	 * 构建命令
	 * @param commandModelVO
	 * @param executer
	 * @create 2014年11月10日 Cico.姜彪
	 */
	public final void build(CommandModelVO commandModelVO, ItemExecuter executer) {
		this.executer = executer;
		if(null != this.commandModelVO)
		{
			txtCommandId.textProperty().unbindBidirectional(this.commandModelVO.valueProperty());
			txtCommandName.textProperty().unbindBidirectional(this.commandModelVO.enNameProperty());
			txtCommandInfo.textProperty().unbindBidirectional(this.commandModelVO.cnNameProperty());
			
			root.getChildren().clear();
		}
		this.commandModelVO = commandModelVO;
		root.getChildren().addAll(commandModelVO.getRowModels());
		txtCommandId.textProperty().bindBidirectional(commandModelVO.valueProperty());
		txtCommandName.textProperty().bindBidirectional(commandModelVO.enNameProperty());
		txtCommandInfo.textProperty().bindBidirectional(commandModelVO.cnNameProperty());
		if(commandModelVO.getDirection().equals("")) {
			combCommandType.getSelectionModel().selectFirst();
		} else {
			combCommandType.getSelectionModel().select(RequestType.getValueFor(commandModelVO.getDirection()));
		}
	}
    
}
