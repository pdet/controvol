package com.controvol.crawler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyLegacyList {

	public List<String> getLegacyXML(String source){
		
		File folder = new File(source);
		File[] listOfFiles = folder.listFiles();
		List<String> xmlFiles= new ArrayList<String>();
				
		for (File file : listOfFiles) {
			if (file.isFile()) {		
				String legacy = file.getName();
				if(legacy.endsWith(".xml")){
					String fileToCheck = new String(source+legacy);
					xmlFiles.add(fileToCheck);
				}
			}
		}
		return xmlFiles;
	}
}
