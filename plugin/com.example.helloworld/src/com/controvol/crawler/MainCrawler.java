package com.controvol.crawler;

import ie.ucd.pel.datastructure.MApplication;
import ie.ucd.pel.engine.crawler.CrawlerGit;
import ie.ucd.pel.engine.crawler.ICrawler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.controvol.util.Cst;

public class MainCrawler {
	
	public static void main(String[] args) {

		// Should be read from a conf file
		String versions = Cst.REP_PLUGIN + File.separator + "versions.txt";
		
		List<MApplication> apps = new ArrayList<MApplication>();
		
		try {
			File fRevisions = new File(versions);
			BufferedReader br = new BufferedReader(new FileReader(fRevisions));
			String line;
			while ((line = br.readLine()) != null) {
				String[] splitLine = line.split(" ");
				ICrawler crawler = new CrawlerGit("/Users/Thomas/Documents/FakeWorkspace/player/", "src/", "bin/");
				MApplication app = crawler.getApplication(splitLine[1]);
				app.setVersion(splitLine[0]);
				apps.add(app);
			}
			br.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String fileName;
		for (Integer i=0 ; i < apps.size() ; i++){
			MApplication app = apps.get(i);
			fileName = "app-" + (apps.size()-i) + ".xml"; // TODO Remove the constants
			try {
				OutputStream os = new DataOutputStream(new FileOutputStream(Cst.REP_PLUGIN + File.separator + fileName));
				app.toXml(os);
				os.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(fileName + " created.");
		}

	}

}
