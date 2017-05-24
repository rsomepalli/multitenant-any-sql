package com.analogyx.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;

public interface ResourceLoader {
	static URL getResource(ClassLoader classLoader, String file){
		return classLoader.getResource(file);
	}
	
	static File getFile(String file){
		File f = new File(file);
		return f;
	}
	
	static FileInputStream getFileInputStream(String file) throws FileNotFoundException{
		File f = new File(file);
		FileInputStream fi = new FileInputStream(f);
		return fi;
	}
	
	static FileOutputStream getFileOutputStream(String fileName) throws FileNotFoundException{
		return new FileOutputStream(fileName);
	}
}
