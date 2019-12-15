package com.protocol.view.mian;

import java.io.File;
import java.util.ArrayList;
import java.util.List;










import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;










import com.mnt.gui.base.buildfactory.FunctionExpandFactory;
import com.mnt.gui.base.buildfactory.expand.FileDragOutHandle;
import com.mnt.gui.base.concurrent.ServiceTask;
import com.mnt.gui.base.contorls.SimpleDialogFactory;
import com.mnt.gui.base.contorls.executer.DialogExecuter;
import com.mnt.gui.base.controller.BaseController;
import com.mnt.gui.base.logger.TipDialogLogger;
import com.protocol.control.ControlBuilderFactory;
import com.protocol.control.ItemExecuter;
import com.protocol.generate.CodeGenerate;
import com.protocol.generate.CommandGenerate;
import com.protocol.generate.ControllerCodeGenerate;
import com.protocol.launcher.ProtocolBuilderLauncher;
import com.protocol.model.CommandModelVO;
import com.protocol.model.ProtocolItemVO;
import com.protocol.model.RowModelVO;
import com.protocol.type.RequestType;
import com.protocol.utils.BuilderTreeRoot;
import com.protocol.utils.SystemConfUtil;
import com.protocol.utils.XmlBuilder;
import com.protocol.view.mian.item.CommandView;

/**
 * 协议主界面
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	下午2:45:33
 * @version 1.0
 */
public class ProtocolView extends BaseController
{
	@FXML
    private Button btnSaveFile;

    @FXML
    private TextField txtModuleName;

    @FXML
    private TextField txtModuleClass;

    @FXML
    private TextField txtModuleId;
    
    @FXML
    private TextField txtPackageName;

    @FXML
    private VBox vbFileSavePath;

    @FXML
    private VBox vbBuildType;

    @FXML
    private VBox vbTable;
    
    @FXML
    private VBox vbTemp;

    @FXML
    private Button btnDelModel;

    @FXML
    private TreeView<ProtocolItemVO> treeProto;

    @FXML
    private ScrollPane spTable;

    @FXML
    private Button btnSaveAllFile;
    
	@FXML
	private CheckBox cbGeneralAll;

    @FXML
    private TextField txtModuleDesc;
    
    private final InvalidationListener fristClick = new InvalidationListener()
	{
		
		@Override
		public void invalidated(Observable obs)
		{
			final TreeItem<ProtocolItemVO> selected = treeProto.getSelectionModel().getSelectedItem();
			if(null == treeProto.getSelectionModel().getSelectedItem()) {
				return ;
			}
			
			if(!selected.getParent().equals(BuilderTreeRoot.getTreeRoot())) {
				/*为子元素时*/
				currSelectVO = selected.getParent();
				selectCommandVO(selected.getParent().getValue(), selected.getValue().getCommandVO(), selected.getParent().getChildren());
			} else {
				/*为模块名时*/
				currSelectVO = selected;
				selectProtocolItemVO(selected.getValue(), selected.getChildren().size(), selected.getChildren());
			}
			treeProto.getSelectionModel().selectedItemProperty().removeListener(fristClick);
			treeProto.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<ProtocolItemVO>>()
					{
						@Override
						public void changed(ObservableValue<? extends TreeItem<ProtocolItemVO>> obs, TreeItem<ProtocolItemVO> oldValue, TreeItem<ProtocolItemVO> newValue)
						{
							if(null == newValue) {
								return ;
							}
							if(!newValue.getParent().equals(BuilderTreeRoot.getTreeRoot())) {
								currSelectVO = newValue.getParent();
								/*为子元素时*/
								selectCommandVO(newValue.getParent().getValue(), newValue.getValue().getCommandVO(), newValue.getParent().getChildren());
							} else {
								currSelectVO = newValue;
								/*为模块名时*/
								selectProtocolItemVO(newValue.getValue(), newValue.getChildren().size(), newValue.getChildren());
							}
						}
					});
		}
	};
	
	private ToggleGroup toggleGroup = new ToggleGroup();
	
	/**
	 * 加载模板
	 * @create 2014年12月18日 Cico.姜彪
	 */
    private void loadTmps() {
    	List<RadioButton> rbTmps = ControlBuilderFactory.getCommandTemps();
    	for (RadioButton radioButton : rbTmps)
		{
    		radioButton.setToggleGroup(toggleGroup);
    		vbTemp.getChildren().add(radioButton);
		}
    	if(!toggleGroup.getToggles().isEmpty()) {
    		toggleGroup.getToggles().get(0).setSelected(true);
    	}
    }
    
    /**
     * 获取生成代码名称
     * @return
     * @create 2014年12月18日 Cico.姜彪
     */
    private String getGeneralFileName() {
    	RadioButton radio = (RadioButton)toggleGroup.getSelectedToggle();
    	return radio.getText();
    }
	
	
    @Override
    public void init()
    {
    	loadTmps();
    	
    	treeProto.setCellFactory(new Callback<TreeView<ProtocolItemVO>, TreeCell<ProtocolItemVO>>()
		{
			
			@Override
			public TreeCell<ProtocolItemVO> call(TreeView<ProtocolItemVO> param)
			{
				return new TreeCell<ProtocolItemVO>(){
					@Override
					protected void updateItem(ProtocolItemVO item, boolean empty)
					{
						textProperty().unbind();
						if(empty) {
							setText("");
						} else {
//							item.infoProperty().unbind();
							textProperty().bind(item.infoProperty());
						}
						super.updateItem(item, empty);
					}
				};
			}
		});
		new ServiceTask<VBox>()
		{
			@Override
			protected void executeAfterSucceeded(VBox value)
			{
				vbBuildType.getChildren().add(value);
			}

			@Override
			protected void executeFailed(Throwable error)
			{
				error.printStackTrace();
			}

			@Override
			protected VBox call() throws Exception
			{
				final File director = new File(SystemConfUtil.getTmpPath());
				return ControlBuilderFactory.builderTypeSelected(director, null);
			}
		}.start();
    	treeProto.setRoot(BuilderTreeRoot.getTreeRoot());
    	treeProto.getRoot().getChildren().addListener(new ListChangeListener<TreeItem<ProtocolItemVO>>()
		{

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends TreeItem<ProtocolItemVO>> c)
			{
				Platform.runLater(new Runnable()
				{
					
					@Override
					public void run()
					{
						vbTable.getChildren().clear();
					}
				});
				
			}
		});
    	treeProto.getSelectionModel().selectedItemProperty().addListener(fristClick);
    	
    	treeProto.setOnMousePressed(event ->{
    		if(null != currSelectVO) {
    			final ControllerCodeGenerate controllerCodeGenerate = new ControllerCodeGenerate(currSelectVO);
    			
    			FunctionExpandFactory.buildDragFileOut(treeProto, true, new FileDragOutHandle<TreeView<ProtocolItemVO>>()
    					{
    				@Override
    				public void fileCreate(TreeView<ProtocolItemVO> view, File... file)
    				{
    					
    				}
    					}, getFlieNames(controllerCodeGenerate));
    		}
		});
		vbTable.setOnMousePressed(event -> {
			if(null == currSelectVO || currProtocolItemVO == null) {
				return;
			}
			RequestType requestType;
			if(event.isAltDown()) {
				requestType = RequestType.CLIENT;
			} else {
				requestType = RequestType.SERVER;
			}
			
			if(event.isControlDown()) {
				final ObservableList<TreeItem<ProtocolItemVO>> protocols = currSelectVO.getChildren();
//				System.out.println("1");
				String [] files;
				if(!cbGeneralAll.isSelected()) {
					//如果不是生成全部
					final ObservableList<TreeItem<ProtocolItemVO>> protocolsTemp = FXCollections.observableArrayList(); 
					for (TreeItem<ProtocolItemVO> protocol : protocols)
					{
						if(treeProto.getSelectionModel().getSelectedItem().getValue().getCommandVO().getDirection().equals(protocol.getValue().getCommandVO().getDirection())) {
							protocolsTemp.add(protocol);
						}
					}
					 files = new String[protocolsTemp.size()];
					 
					 for(int i = 0; i < protocolsTemp.size(); i ++) {
							final CommandGenerate commandGenerate = new CommandGenerate(protocolsTemp.get(i).getValue(), protocolsTemp.get(i).getValue().getCommandVO().getDirection().equals(RequestType.CLIENT.getCode()) ? RequestType.CLIENT : RequestType.SERVER, getGeneralFileName());
							files[i] = commandGenerate.getFile(txtPackageName.getText()).getAbsolutePath();
						}
				} else {
					files = new String[protocols.size()];
					for(int i = 0; i < protocols.size(); i ++) {
						final CommandGenerate commandGenerate = new CommandGenerate(protocols.get(i).getValue(), requestType, getGeneralFileName());
						files[i] = commandGenerate.getFile(txtPackageName.getText()).getAbsolutePath();
					}
				}
				
				FunctionExpandFactory.buildDragFileOut(vbTable, true, new FileDragOutHandle<VBox>()
						{
							@Override
							public void fileCreate(VBox vb, File... file)
							{
								
							}
				}, files); 
			} else {
//				if(CommandView.currChoiceItem.get() != null) {
//					currSelectVO.getChildren().stream().filter(treeItem ->  treeItem.getValue().getCommandVO().getEnName().equals(CommandView.currChoiceItem.get().getKey()))
//					.forEach(treeItem -> {
//						treeProto.getSelectionModel().select(treeItem);
//					});
//				}
				if(treeProto.getSelectionModel().getSelectedItem().getValue().getCommandVO().getDirection().equals(RequestType.CLIENT.getCode())) {
					requestType = RequestType.CLIENT;
					if(event.isAltDown()) {
						requestType = RequestType.SERVER;
					}
				} else {
					requestType = RequestType.SERVER;
					if(event.isAltDown()) {
						requestType = RequestType.CLIENT;
					}
				}
				
				final CommandGenerate commandGenerate = new CommandGenerate(treeProto.getSelectionModel().getSelectedItem().getValue(), requestType, getGeneralFileName());
				FunctionExpandFactory.buildDragFileOut(vbTable, true, new FileDragOutHandle<VBox>()
						{
							@Override
							public void fileCreate(VBox vb, File... file)
							{
							}
				}, commandGenerate.getFile(txtPackageName.getText()).getAbsolutePath()); 
//				System.out.println("2");
			}
			
//			new ServiceTask<Void>()
//			{
//				@Override
//				protected void executeAfterSucceeded(Void arg0)
//				{
//					
//					
//				}
//
//				@Override
//				protected void executeFailed(Throwable arg0)
//				{
//					
//				}
//
//				@Override
//				protected Void call() throws Exception
//				{
//					Thread.sleep(300);
//					return null;
//				}
//			}.start();
		});
		
		CommandView.currChoiceItem.addListener(new ChangeListener<CommandView>()
		{
			@Override
			public void changed(ObservableValue<? extends CommandView> observable, CommandView oldValue, CommandView newValue)
			{
				if(null != oldValue) {
					oldValue.removeSelected();
				}
				currSelectVO.getChildren().stream().filter(treeItem ->  treeItem.getValue().getCommandVO().getEnName().equals(newValue.getKey()))
				.forEach(treeItem -> {
					treeProto.getSelectionModel().select(treeItem);
				});
				newValue.selected();
			}
		});
    	super.init();
    }
    
    private ProtocolItemVO currProtocolItemVO;
    private TreeItem<ProtocolItemVO> currSelectVO;
    
    /**
     * 选中一个模块名
     * @param protocolItemVO
     * @create 2014年11月11日 Cico.姜彪
     */
    private void selectProtocolItemVO(ProtocolItemVO protocolItemVO, int size, List<TreeItem<ProtocolItemVO>> childrens) {
    	if(null != currProtocolItemVO) {
			txtModuleClass.textProperty().unbindBidirectional(currProtocolItemVO.remarkProperty());
			txtModuleDesc.textProperty().unbindBidirectional(currProtocolItemVO.infoProperty());
			txtModuleId.textProperty().unbindBidirectional(currProtocolItemVO.valueProperty());
			txtModuleName.textProperty().unbindBidirectional(currProtocolItemVO.nameProperty());
			txtPackageName.textProperty().unbindBidirectional(currProtocolItemVO.packageNameProperty());
			
		}
    	
    	currProtocolItemVO = protocolItemVO;
    	txtModuleClass.textProperty().bindBidirectional(protocolItemVO.remarkProperty());
		txtModuleDesc.textProperty().bindBidirectional(protocolItemVO.infoProperty());
		txtModuleId.textProperty().bindBidirectional(protocolItemVO.valueProperty());
		txtModuleName.textProperty().bindBidirectional(protocolItemVO.nameProperty());
		txtPackageName.textProperty().bindBidirectional(protocolItemVO.packageNameProperty());
//		System.out.println(protocolItemVO.packageNameProperty().get() + "1");
//		System.out.println("选中子父节点");
		if(vbTable.getChildren().size() > childrens.size()) {
			vbTable.getChildren().remove(childrens.size(), vbTable.getChildren().size());
		}
		//获取树节点
		TreeItem<ProtocolItemVO> itemTemp = null;
		for (TreeItem<ProtocolItemVO> treeItem : childrens)
		{
			if(treeItem.getValue() == protocolItemVO) {
				itemTemp = treeItem;
			}
		}
		final TreeItem<ProtocolItemVO> item = itemTemp;
//		System.out.println(childrens.size());
		for (int i = 0; i < childrens.size(); i++)
		{
			if(vbTable.getChildren().size() > i) {
				((CommandView)vbTable.getChildren().get(i)).build(childrens.get(i).getValue().getCommandVO(), new ItemExecuter()
				{
					@Override
					public void delCommand(CommandModelVO commandModelVO, CommandView commandView)
					{
						currSelectVO.getChildren().remove(item);
						vbTable.getChildren().remove(commandView);
					}
				});
			} else {
				CommandView commandView = ControlBuilderFactory.builderCommandItemByCommandModelVO(childrens.get(i).getValue().getCommandVO(), new ItemExecuter()
				{
					@Override
					public void delCommand(CommandModelVO commandModelVO, CommandView commandView)
					{
						currSelectVO.getChildren().remove(item);
						vbTable.getChildren().remove(commandView);
					}
				}, i);
				vbTable.getChildren().add(commandView);
			}
		}
    }
    
    /**
     * 选中一个命令
     * @param commandVO
     * @create 2014年11月11日 Cico.姜彪
     */
    private void selectCommandVO(ProtocolItemVO protocolItemVO, CommandModelVO commandVO, List<TreeItem<ProtocolItemVO>> childrens) {
    	if(protocolItemVO != currProtocolItemVO) {
    		selectProtocolItemVO(protocolItemVO, childrens.size(), childrens);
    	} 
//    		System.out.println("选中子元素");
    		vbTable.getChildren().forEach(node -> {
				final CommandView commandItem = (CommandView) node;
				if(!commandItem.getKey().equals(RequestType.CLIENT.getStartName()) && !commandItem.getKey().equals(RequestType.SERVER.getStartName())) {
					if(commandItem.getKey().equals(commandVO.getEnName())) {
						spTable.setVvalue((commandItem.localToParent(0 , 0).getY() + 100) / vbTable.getHeight());
						CommandView.currChoiceItem.set(commandItem);
						return;
					}
				}
			});
    	
    }
    

    @FXML
    void processCombSave(KeyEvent event) {
    	final CommandView commandView = CommandView.currChoiceItem.get();
    	if(event.isControlDown()) { //Ctrl
    		if(event.getCode() == KeyCode.S) {
        		if(event.isShiftDown()) {
        			processSaveAllFile(null);//保存全部
        		} else {
            		processSaveFile(null);//保存单个
        		}
        	}
    		if(event.getCode() == KeyCode.N) {
    			if(event.isShiftDown()) { //Shift
        			processAddModel(null);//新建一个模块
        		} else {
        			if(!btnSaveFile.isDisable()) {
            			processAddCommand(null);//新建一条命令
            		}
        		}
    		}
    		if(event.getCode() == KeyCode.DELETE) {
    			if(event.isShiftDown()) {
    				processDelModel(null); //删除一个模块
    			} else {
    				if(null != commandView) {
    					commandView.processDel(null);//删除一个选中的命令
        			}
    			}
    		}
    	} else {
    		if(event.getCode() == KeyCode.DELETE) {
        		commandView.processDelParam(null); //删除选中一个参数
        	}
    	}
    	if(event.isAltDown()) {//Alt
    		if(event.getCode() == KeyCode.DOWN) {
    			if(null != commandView) {
    				if(event.isControlDown()) {
    					commandView.processAddListParam(null);//添加一个list参数
    				} else {
    					commandView.processAddParam(null); //选中的命令添加一个参数
    				}
    			}
    		}
    	}
    }

    @FXML
    void processSaveFile(ActionEvent event) {
    	if(ControlBuilderFactory.getSelecttypes().isEmpty()) {
    		TipDialogLogger.error("请选择一个生成类型");
    		return ;
    	}
    	if(null != currSelectVO) {
    		ProtocolBuilderLauncher.isChange = true;
    		btnSaveFile.setDisable(true);
    		new ServiceTask<Void>()
			{
				@Override
				protected void executeAfterSucceeded(Void arg0)
				{
					TipDialogLogger.successed("代码保存成功");
		    		btnSaveFile.setDisable(false);
		    		ProtocolBuilderLauncher.isChange = false;
				}

				@Override
				protected void executeFailed(Throwable error)
				{
					error.printStackTrace();
					TipDialogLogger.error("代码保存失败");
		    		btnSaveFile.setDisable(false);
		    		ProtocolBuilderLauncher.isChange = false;
				}

				@Override
				protected Void call() throws Exception
				{
					XmlBuilder.buildModelToXml(currSelectVO, currSelectVO.getValue().getRemark() + ".xml");
					ControlBuilderFactory.getSelecttypes().forEach(type ->{
						savaCode(currSelectVO, type);
					});
//					savaController();
					return null;
				}
			}.start();
    	} else {
    		TipDialogLogger.error("请选择一个协议模块!");
    	}
    }
    
    private void savaCode(TreeItem<ProtocolItemVO> protocol, String type) {
    	new CodeGenerate(protocol, SystemConfUtil.getCodeTmpByType(type)).buildFlie(SystemConfUtil.getCurrSavaDirector(type));
    }
    
    @SuppressWarnings("unused")
	private void savaController() {
    	XmlBuilder.buildProtocolToXml(currSelectVO, SystemConfUtil.getControllerModelFileName());
    }

    @FXML
    void processSaveAllFile(ActionEvent event) {
    	ProtocolBuilderLauncher.isChange = true;
		btnSaveAllFile.setDisable(true);
		new ServiceTask<Void>()
		{
			@Override
			protected void executeAfterSucceeded(Void arg0)
			{
				TipDialogLogger.successed("代码保存成功");
				btnSaveAllFile.setDisable(false);
	    		ProtocolBuilderLauncher.isChange = false;
			}

			@Override
			protected void executeFailed(Throwable error)
			{
				error.printStackTrace();
				TipDialogLogger.error("代码保存失败");
				btnSaveAllFile.setDisable(false);
				ProtocolBuilderLauncher.isChange = false;
			}

			@Override
			protected Void call() throws Exception
			{
				treeProto.getRoot().getChildren().forEach(item -> {
					XmlBuilder.buildModelToXml(item, item.getValue().getRemark() + ".xml");
					ControlBuilderFactory.getSelecttypes().forEach(type ->{
						savaCode(item, type);
					});
				});
				return null;
			}
		}.start();
    }

    @FXML
    void processConfig(ActionEvent event) {
    	ControlBuilderFactory.showConfigDialog(getStage());
    }

    @FXML
    void processInfo(ActionEvent event) {
    	ControlBuilderFactory.showInfoDialog();
    }

    @FXML
    void processAddModel(ActionEvent event) {
    	final TreeItem<ProtocolItemVO> addItem = new TreeItem<ProtocolItemVO>();
    	final ProtocolItemVO protocolItemVO = new ProtocolItemVO();
    	addItem.setValue(protocolItemVO);
    	if(!BuilderTreeRoot.getTreeRoot().getChildren().isEmpty()) {
    		final TreeItem<ProtocolItemVO> lastItem = BuilderTreeRoot.getTreeRoot().getChildren().get(BuilderTreeRoot.getTreeRoot().getChildren().size() - 1);
    		final int lastValue = Integer.parseInt(lastItem.getValue().getValue());
    		protocolItemVO.setValue(String.valueOf(lastValue + 1));
    	}
    	BuilderTreeRoot.getTreeRoot().getChildren().add(addItem);
    }

    @FXML
    void processDelModel(ActionEvent event) {
    	SimpleDialogFactory.showSimpleDialog(getStage(), "是否删除", "是否删除模块 " + currSelectVO.getValue().getInfo(), new DialogExecuter() {
			@Override
			public void processConfirm() {
				currSelectVO.getParent().getChildren().remove(currSelectVO);
				TipDialogLogger.successed("删除模块成功！");
			}
			@Override
			public void processCancel() {
			}
		});
    }

    @FXML
    void processSaveModel(ActionEvent event) {

    }

    @FXML
    void processAddCommand(ActionEvent event) {
		if(currSelectVO == null) {
			TipDialogLogger.error("逗比,请选择模块！");
		} else {
			final CommandModelVO commandModelVO = new CommandModelVO();
			final ProtocolItemVO protocolItemVO = new ProtocolItemVO();
			protocolItemVO.setCommandVO(commandModelVO);
			commandModelVO.setRowModels(new ArrayList<TreeItem<RowModelVO>>());
			if(!currSelectVO.getChildren().isEmpty()) {
				String value = currSelectVO.getChildren().get(currSelectVO.getChildren().size() - 1).getValue().getCommandVO().getValue();
				commandModelVO.setValue(String.valueOf(Integer.parseInt(value) + 1));
			}
			final TreeItem<ProtocolItemVO> item = new TreeItem<>(protocolItemVO);
			CommandView commandView = ControlBuilderFactory.builderCommandItemByCommandModelVO(commandModelVO, new ItemExecuter()
			{
				@Override
				public void delCommand(CommandModelVO commandModelVO, CommandView commandView)
				{
					currSelectVO.getChildren().remove(item);
					vbTable.getChildren().remove(commandView);
				}
			}, currSelectVO.getChildren().size());
			protocolItemVO.infoProperty().bindBidirectional(commandModelVO.cnNameProperty());
			currSelectVO.getChildren().add(item);
			vbTable.getChildren().add(commandView);
			if(currSelectVO.getChildren().size() > 1) {
				new ServiceTask<Void>()
				{
					@Override
					protected void executeAfterSucceeded(Void v)
					{
						treeProto.getSelectionModel().select(item);
						spTable.setVvalue(Double.MAX_VALUE);
					}

					@Override
					protected void executeFailed(Throwable error)
					{
						
					}

					@Override
					protected Void call() throws Exception
					{
						Thread.sleep(100);
						return null;
					}
				}.start();
			}
		}
	}

    
	private String[] getFlieNames(ControllerCodeGenerate controllerCodeGenerate) {
		final List<String> types = ControlBuilderFactory.getSelecttypes();
		final String[] result = new String[types.size()];
		for (int i = 0; i < types.size(); i++)
		{
			result[i] = controllerCodeGenerate.getFile(types.get(i)).getAbsolutePath();
		}
		return result;
	}
}
