package com.protocol.listener;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 文件监控
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	下午5:55:19
 * @version 1.0
 */
public class FileListenerManager
{
	private static FileSystem fileSystem = FileSystems.getDefault();  
	private static WatchService watcher;
	private static Path myDir;
	
	static {
		try
		{
			watcher = fileSystem.newWatchService();
			Thread fileLister = new Thread("fileListener") {
				@Override
				public void run()
				{
					while(true){  
			            WatchKey key = null;
						try
						{
							key = watcher.take();
						} catch (InterruptedException e1)
						{
							e1.printStackTrace();
						}  
			            for(WatchEvent<?> event : key.pollEvents()){  
			                Kind<?> kind = event.kind();  
			                  
			                if(kind == StandardWatchEventKinds.OVERFLOW){//事件可能lost or discarded  
			                    continue;  
			                }  
			                  
			                @SuppressWarnings("unchecked")
							WatchEvent<Path> e = (WatchEvent<Path>)event;  
			                Path fileName = e.context(); 
			               // fileName.toAbsolutePath().toString();
			                
//			                System.out.println(myDir.toString());
			                if(fileListenerMap.containsKey(myDir.toString())) {
			                	fileListenerMap.get(myDir.toString()).fileChange(myDir.resolve(fileName).toString(),  kind);
			                }
			               ;
//			                System.out.printf("Event %s has happened,which fileName is %s%n"  
//			                        ,kind.name(),  myDir.resolve(fileName).toFile()); 
			            }  
			            
			            if(!key.reset()){  
			                break;  
			            }  
			        }  
				}
			};
			fileLister.setDaemon(true);
			fileLister.start();
		} catch (IOException e)
		{
			e.printStackTrace();
		} 
		
		
	}
	//文件监控系统
	private static final Map<String, IFileListener> fileListenerMap = new HashMap<>();
	
	//已经监听的文件夹路径
	private static final List<String> directorPathList = new ArrayList<>();
	
	/**
	 * 监听文件改变
	 * @param filePath
	 * @param listener
	 * @create 2014年12月12日 Cico.姜彪
	 */
	public static final void listenerFileChange(String filePath, IFileListener listener) {
		
		File file = new File(filePath);
		final String fileDirectorPath; 
		if(file.isFile()) {
			fileDirectorPath = file.getParentFile().getAbsolutePath();
			file = file.getParentFile();
			return;
		} else {
			fileDirectorPath = file.getAbsolutePath();
		}
		if(directorPathList.contains(fileDirectorPath)) {
			if(!fileListenerMap.containsKey(filePath)) {
				fileListenerMap.put(filePath, listener);
			}
			return;
		}
		//不存在则添加
		if(!fileListenerMap.containsKey(fileDirectorPath)) {
			fileListenerMap.put(fileDirectorPath, listener);
		}
		directorPathList.add(fileDirectorPath);
		try
		{
			myDir = fileSystem.getPath(fileDirectorPath);
			myDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
			//System.out.println("FileListenerManager.listenerFileChange()" + myDir.toString());
		} catch (IOException e)
		{
			e.printStackTrace();
		} 
		
		
	}
}
