package com.protocol.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 文件工具 
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	下午3:54:07
 * @version 1.0
 */
public abstract class FileUtil
{
	private FileUtil() {
		
	}
	
	/**
	 * 文件复制工具 
	 * @param oldFile
	 * @param newFile
	 * @throws IOException
	 * @create 2014年8月19日 Cico.姜彪
	 */
	public static final void copyFile(File oldFile, File newFile){
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(oldFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(newFile));
            // 缓冲数组
            byte[] b = new byte[1024 * 2];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } catch (IOException e){
        	e.printStackTrace();
        }finally {
            // 关闭流
				try
				{ 
					if (inBuff != null) {
						inBuff.close();
					}
					if (outBuff != null) {
						outBuff.close();
					}
				} catch (IOException e)
				{
					e.printStackTrace();
				}
            
        }
	}
	
	/**
	 * 写入到文件
	 * @param file
	 * @param context
	 * @create 2014年8月12日 Cico.姜彪
	 */
	public static final void writeFile(File file, String context) {
		BufferedWriter writer = null;
		  try
		{
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			writer.write(context);
			
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally {
			if(null != writer) {
				try
				{
					writer.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获取文件夹下的xml文件 
	 * @param file
	 * @return
	 * @create 2014年9月22日 Cico.姜彪
	 */
	public static final File [] getDirectorXmlFileCount(File file) {
		if(file.isDirectory()) {
			return file.listFiles(new FileFilter()
			{
				
				@Override
				public boolean accept(File pathname)
				{
					return pathname.getName().endsWith(".xml");
				}
			});
		} else {
			return new File[0];
		}
	}
	
}
