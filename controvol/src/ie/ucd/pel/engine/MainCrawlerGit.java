package ie.ucd.pel.engine;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ie.ucd.pel.datastructure.MApplication;
import ie.ucd.pel.datastructure.MAttribute;
import ie.ucd.pel.datastructure.MEntity;
import ie.ucd.pel.engine.crawler.CrawlerGit;

public class MainCrawlerGit {

	public static void main(String[] args){
			
		CrawlerGit crawlerGit = new CrawlerGit("/Users/Thomas/Documents/FakeWorkspace/player/", "src/", "bin/");
		MApplication myApp = crawlerGit.getApplication();
		for (MEntity entity : myApp.getEntities()){
			System.out.println(entity.getEntityName());
			for (MAttribute attribute : entity.getAttributes()){
				System.out.println("\t" + attribute.getName() + " " + 
						attribute.getType() + " " + 
						attribute.getLocation().getClassName() + " " + 
						attribute.getLocation().getLineNumber());
			}
		}
		
		try {
			FileOutputStream fos = new FileOutputStream("apps/test.xml");
			myApp.toXml(fos);
			fos.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
