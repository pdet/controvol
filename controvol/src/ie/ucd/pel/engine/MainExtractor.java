package ie.ucd.pel.engine;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ie.ucd.pel.datastructure.MApplication;
import ie.ucd.pel.engine.crawler.CrawlerJar;
import ie.ucd.pel.engine.crawler.ICrawler;

public class MainExtractor {

	public static void main(String[] args) {
		
		if (args.length != 3){
			System.err.println("3 arguments needed: <jarFile> <xmlFile> <version>");
		} else {

			String jarFile = args[0];
			String xmlFile = args[1];
			String version = args[2];

			ICrawler crawler = new CrawlerJar();

			MApplication app = crawler.getApplication(jarFile);
			app.setVersion(version);
			try {
				FileOutputStream fos = new FileOutputStream(xmlFile);
				app.toXml(fos);
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			

			System.out.println("JAR file exported in XML.");
			
		}

	}

}
