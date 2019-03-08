package com.controvol.util;

import ie.ucd.pel.datastructure.MApplication;
import ie.ucd.pel.engine.crawler.CrawlerXml;
import ie.ucd.pel.engine.crawler.ICrawler;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.controvol.crawler.MyLegacyList;

public class Util {

	public static String getResourceFolderName(IResource resrc){
		/* It was like it in the previous version
		 * IResource currentResrc = resrc;
		IResource parent = resrc.getParent();
		while (parent.getType() != 4){
			currentResrc = parent;
			parent = currentResrc.getParent();		
		}
		//return currentResrc.getName(); */
		String folderName = Cst.SRC_FOLDER.replaceAll(File.separator, Cst.DOT);
		return folderName;
	}

	public static String getResourceName(IResource resrc){
		String relativePath = resrc.getProjectRelativePath().toString();
		String folderName = getResourceFolderName(resrc);
		return Util.getResourceName(relativePath, folderName);
	}
	
	public static String getResourceName(String relativePath, String folderName){
		String nameResrc = "";
		nameResrc = relativePath.replace(File.separator, Cst.PACKAGE_SEPARATOR);
		nameResrc = nameResrc.replaceFirst(folderName, "");
		nameResrc = nameResrc.substring(0, nameResrc.lastIndexOf(Cst.DOT + Cst.JAVA_EXTENSION));
		return nameResrc;
	}	
	
	public static String getNextVersionNb(){
		Integer time =  new Integer((int) (System.currentTimeMillis() % Integer.MAX_VALUE));
		return String.valueOf(time);
	}
	
	public static String getLastVersionNb(String projectFullName){
		// XXX Costly but easy to write ;-)
		String versionNb = null;
		List<MApplication> appList = Util.getAppList(projectFullName);
		versionNb = appList.get(appList.size() - 1).getVersion();
		return versionNb;
	}
	
	public static void exportToXml(MApplication app, IProject project){
		String version = Util.getNextVersionNb();
		app.setVersion(version);
		
		String projectLocation = project.getWorkspace().getRoot().getLocation().toString();
		String projectName = project.getName();
		
		try {
			String fileName = projectLocation + File.separator + projectName + File.separator + Cst.REP_PLUGIN + File.separator + version + Cst.DOT + Cst.XML_EXTENSION;
			OutputStream os = new DataOutputStream(new FileOutputStream(fileName));
			app.toXml(os);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<MApplication> getAppList(String projectFullName){
		MyLegacyList appList = new MyLegacyList();
		List<String> legacyFiles  = appList.getLegacyXML(projectFullName + Cst.REP_PLUGIN + File.separator);
		ICrawler crawlerXml = new CrawlerXml();
		List<MApplication> apps = new ArrayList<MApplication>(legacyFiles.size());
		for (String file : legacyFiles){
			apps.add(crawlerXml.getApplication(file));
		}
		return apps;
	}
	
	/**
	 * 
	 * @param classNameWithPackage
	 * @return the class name without the package description. 
	 * For instance, "java.lang.String" will return "String" and 
	 * "String" will return "String"
	 */
	public static String getClassNameWithoutPackage(String classNameWithPackage){
		return classNameWithPackage.substring(classNameWithPackage.lastIndexOf(Cst.DOT) + 1);
	}
}
