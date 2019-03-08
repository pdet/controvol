package ie.ucd.pel.engine;

import ie.ucd.pel.datastructure.MApplication;
import ie.ucd.pel.datastructure.evolution.Evolution;
import ie.ucd.pel.datastructure.evolution.Operation;
import ie.ucd.pel.datastructure.warning.IWarning;
import ie.ucd.pel.engine.crawler.CrawlerXml;
import ie.ucd.pel.engine.crawler.ICrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainChecker {

	public static void main(String[] args) {

		String[] tab = {"controvol/1113905281.xml", "controvol/1113922727.xml", "controvol/1113950428.xml", };
		ICrawler crawlerXml = new CrawlerXml();
		List<MApplication> apps = new ArrayList<MApplication>(tab.length);
		for (int i = 0 ; i < tab.length ; i++){
			apps.add(crawlerXml.getApplication("/Users/Thomas/Documents/FakeWorkspace/player/" + tab[i]));
		}
		
		ControVolEngine eng = new ControVolEngine();
		Evolution evol = eng.getEvolution(apps);
		Set<IWarning> errors = eng.check(evol);
		
		System.out.println(evol.size() + " evolution(s).");
		for (Operation o : evol){
			System.out.println(o.toString());
		}
		
		System.out.println(errors.size() + " error(s).");
		for (IWarning e : errors){
			System.out.println(e.toString());
		}
			
		/*CrawlerGit crawlerGit = new CrawlerGit("/Users/Thomas/Documents/FakeWorkspace/player/", "src/main/java/", "bin/");
		MApplication myApp = crawlerGit.getApplication();
		try {
			FileOutputStream fos = new FileOutputStream("controvol/test.xml");
			myApp.toXml(fos);
			fos.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}*/
	}

}
