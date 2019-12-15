package com.protocol.generate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import com.protocol.model.ProtocolItemVO;
import com.protocol.utils.DataUtil;
import com.protocol.utils.FileUtil;
import com.protocol.utils.SystemConfUtil;

/**
 * Controller MODEL 
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	下午5:15:56
 * @version 1.0
 */
public class ControllerCodeGenerate
{
	
	private final TreeItem<ProtocolItemVO> protocols;
	
	public ControllerCodeGenerate(TreeItem<ProtocolItemVO> protocols) {
		this.protocols = protocols;
	}
	
	private String declare;
	public File getFile(String type) {
		final File codeFile = new File("ControllerModel." + type);
		String tmpString = readFile(type);
		tmpString = tmpString.split(DataUtil.TMPKey.SPLIT)[1];
		tmpString = tmpString.replace(DataUtil.TMPKey.CONTROLLER_DETAIL, buildControllerDetail());
		codeFile.deleteOnExit();
		FileUtil.writeFile(codeFile, tmpString);
		return codeFile;
	}
	
	private String buildControllerDetail() {
		final StringBuilder result = new StringBuilder();
		String tmpDeclare;
		ObservableList<TreeItem<ProtocolItemVO>> protocolList = protocols.getChildren();
		for (TreeItem<ProtocolItemVO> protocol : protocolList)
		{
			tmpDeclare = declare;
			tmpDeclare = tmpDeclare.replace(DataUtil.TMPKey.INFO, protocol.getValue().getCommandVO().getCnName());
			tmpDeclare = tmpDeclare.replace(DataUtil.TMPKey.NAME, protocol.getValue().getCommandVO().getEnName());
			tmpDeclare = tmpDeclare.replace(DataUtil.TMPKey.COMMANDVALUE, protocol.getValue().getCommandVO().getValue());
			result.append(tmpDeclare);
		}
		return result.toString();
	}
	
	private String readFile(String type) {
		final StringBuilder result = new StringBuilder();
		InputStreamReader read = null;
		BufferedReader bufferedReader = null;
		try
		{
			read = new InputStreamReader( new FileInputStream(SystemConfUtil.getControllerTmpByType(type)),"UTF-8");
			bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while((lineTxt = bufferedReader.readLine()) != null){
            	if(lineTxt.startsWith(DataUtil.TMPKey.DECLARE)) {
            		declare = lineTxt.replace(DataUtil.TMPKey.DECLARE, "").replace("\\n", DataUtil.ENTER);
            	} 
            	result.append(lineTxt + DataUtil.ENTER);
            }
           
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally {
			 try
			{
				 if(null != bufferedReader) {
					 bufferedReader.close();
				 }
				 
				if (null != read)
				{
					read.close();
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return result.toString();
	}
}
