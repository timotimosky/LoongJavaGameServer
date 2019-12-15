package com.protocol.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

	private Properties properties = new Properties();
	private File file;

	public PropertiesUtil(File file) {
		
		try {
			this.file = file;
			properties.load(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String get(String key) {
		return properties.getProperty(key);
	}

	public void putValue(String key, String value) {
		properties.put(key, value);
		save();
	}

	public final boolean containsKey(String key) {
		return properties.containsKey(key);
	}

	private synchronized void save() {
		try {
			properties.store(new FileOutputStream(file), "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
