package com.analogyx.schemer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.analogyx.util.ResourceLoader;

/**
 * Loads file objects in various formats based on a specified path.
 */
public class SchemerFileLoader {
	
	private static final String LOG_CATEGORY = "SchemerFileLoader";

	protected Logger log = LoggerFactory.getLogger(SchemerFileLoader.class);
	
	public SchemerFileLoader() {
	}

    public BufferedReader findResourceAsBufferedReader(String name) throws Exception {
		log.debug("findResourceAsBufferedReader: "+name);
		return new BufferedReader( findResourceAsInputStreamReader(name));
	}
	
    public InputStreamReader findResourceAsInputStreamReader(String name) throws Exception {
		log.debug("findResourceAsInputStreamReader: "+name);
		return new InputStreamReader( findResourceAsInputStream(name));
	}
	
    public FileInputStream findResourceAsInputStream(String name) throws Exception {
		log.debug("findResourceAsInputStream: "+name);
		return ResourceLoader.getFileInputStream(findResource(name));
	}
	
    public File findResourceAsFile(String file) throws Exception {
		log.debug("findResourceAsFile: "+file);
		return ResourceLoader.getFile(findResource(file));
	}
	
    public String findResource(String file) throws Exception  {
		log.debug("findResource: "+file);
		String resultantpath = null;
        URL url = ResourceLoader.getResource(ClassLoader.getSystemClassLoader(), file);
		if( (ResourceLoader.getFile(file)).exists() ) {
			log.debug("Found " + file + " as it was requested.");
			resultantpath = file;
		} else if( url == null ) {
			log.debug("Could not open url to file, simply returning the file.");
			resultantpath = file;
		} else if( (ResourceLoader.getFile(url.getFile())).exists() ) {
			log.debug("Using url getFile syntax.");
			resultantpath = url.getFile(); 
		} else if( (ResourceLoader.getFile(url.getPath())).exists() ) {
			log.debug("Using url getPath syntax.");
			resultantpath = url.getPath();
		} else {
			log.error(file + " resolved to URL: " + url + ", cannot represent as file. Please check that your classpath is correct.");
			if (log.isDebugEnabled()) {
				log.debug("Can not find resource: "+file+" , URL from classloader: " + url + ", classpath: " + System.getProperty("java.class.path"));
			}
			throw new FileNotFoundException("File not found: "+file+", please check that your classpath is correct.");
		}
		log.debug("Returning, "+resultantpath+", for file.");
		return resultantpath;
	}
    
}