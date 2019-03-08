package ie.ucd.pel.engine.crawler;

import ie.ucd.pel.datastructure.MApplication;
import ie.ucd.pel.datastructure.MAttribute;
import ie.ucd.pel.datastructure.MEntity;
import ie.ucd.pel.engine.util.Cst;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

import com.maximilian_boehm.gitaccess.access.GTAccessFactory;
import com.maximilian_boehm.gitaccess.access.struct.GTHistory;

public class CrawlerGit implements ICrawler {

	public static URLClassLoader classLoader;

	private String projectLocation;
	private String srcFolder;
	private String binFolder;

	public CrawlerGit(String projectLocation, String srcFolder, String binFolder){
		this.projectLocation = projectLocation;
		if (!this.projectLocation.endsWith(File.separator)){
			this.projectLocation += File.separator;
		}
		this.srcFolder = srcFolder;
		if (!this.srcFolder.endsWith(File.separator)){
			this.srcFolder += File.separator;
		}
		this.binFolder = binFolder;
		if (!this.binFolder.endsWith(File.separator)){
			this.binFolder += File.separator;
		}
		init();
	}

	private void init(){
		File fBinFolder = new File(projectLocation + binFolder);
		try {
			URL urlRoot = fBinFolder.toURI().toURL();
			CrawlerGit.classLoader = URLClassLoader.newInstance(new URL[] { urlRoot });
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public MApplication getApplication(){
		return this.getApplication(null);
	}

	public MApplication getApplication(String revision){
		MApplication app = new MApplication(projectLocation, revision);

		Map<String, String> hierarchy = getHierarchy(revision);
		hierarchy = eliminateExternalClasses(hierarchy);

		Map<String, MEntity> classesEntities = new HashMap<String, MEntity>();

		File fSrcFolder = new File(projectLocation + srcFolder);

		Set<String> classesToProcess = new TreeSet<String>();
		classesToProcess.addAll(hierarchy.keySet());
		classesToProcess.addAll(hierarchy.values());
		List<String> sortedClasses = sortClasses(hierarchy);
		sortedClasses = eliminateEmptyClasses(sortedClasses);
		for (String currentClass : sortedClasses){
			File srcFile = new File(getFileNameFromClassName(projectLocation + srcFolder + currentClass));
			File srcFileRevision = srcFile;
			if (revision != null){
				srcFileRevision = this.getFile(srcFile, revision);
			}
			MEntity entity = getEntity(srcFileRevision, fSrcFolder, srcFolder, currentClass);
			if (hierarchy.containsKey(currentClass)){
				String superClassName = hierarchy.get(currentClass);
				if (!superClassName.equals("")){
					MEntity superEntity = classesEntities.get(superClassName);
					entity.addAttributes(superEntity.getAttributes());
				}
			}
			if (entity.isEntity()){
				app.addEntity(entity);
			}
			classesEntities.put(currentClass, entity);
		}
		return app;
	}

	private List<String> sortClasses(Map<String, String> hierarchy){
		List<String> sortedClasses = new ArrayList<String>();
		Set<String> classesToProcess = new TreeSet<String>();
		classesToProcess.addAll(hierarchy.keySet());
		classesToProcess.addAll(hierarchy.values());
		while (sortedClasses.size() != classesToProcess.size()){
			for (String currentClass : classesToProcess){
				if (!sortedClasses.contains(currentClass)){
					if (hierarchy.containsKey(currentClass)){
						String superClassName = hierarchy.get(currentClass);
						if (sortedClasses.contains(superClassName)){
							sortedClasses.add(currentClass);
						}
					} else {
						sortedClasses.add(currentClass);
					}
				}
			}

		}
		return sortedClasses;
	}

	private List<String> eliminateEmptyClasses(List<String> classes){
		List<String> res = new ArrayList<String>();
		for (String clas : classes){
			if (!clas.equals("")){
				res.add(clas);
			}
		}
		return res;
	}

	private MEntity getEntity(File srcFile, File srcFolder2, String srcFolder, String className){
		MEntity entity = new MEntity(className, srcFolder);
		entity.setIsEntity(false);
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		String sourceCode = CrawlerGit.extractSourceCode(srcFile);
		try {
			Class<?> clas = Class.forName(className, false, CrawlerGit.classLoader);
			Boolean cond1 = sourceCode.contains(Cst.ANNOTATION_ENTITY_1);
			Boolean cond2 = sourceCode.contains(Cst.ANNOTATION_ENTITY_2);
			Boolean cond3 = sourceCode.contains(Cst.IMPORT_ENTITY);
			Boolean isEntity = (cond1) || (cond2 && cond3);
			Boolean isAbstract = Modifier.isAbstract(clas.getModifiers());
			Boolean isInterface = clas.isInterface();
			Boolean isEnum = clas.isEnum();
			if (!isInterface && !isEnum){
				parser.setSource(sourceCode.toCharArray());
				parser.setKind(ASTParser.K_COMPILATION_UNIT);
				final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
				ControVolASTVisitor2 visitor = new ControVolASTVisitor2(cu, srcFolder2.getAbsolutePath());
				cu.accept(visitor);
				entity = new MEntity(clas.getName(), srcFolder);
				entity.addAttributes(visitor.getAttributes());
				entity.setSuperEntity(clas.getSuperclass().getName());
				if (isEntity && !isAbstract){
					entity.setIsEntity(true);
				} else {
					entity.setIsEntity(false);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return entity;
	}

	public Map<String, String> getHierarchy(String revision){
		Map<String, String> hierarchy = new HashMap<String, String>();
		File fSrcFolder = new File(projectLocation + srcFolder);
		if (fSrcFolder.isDirectory()){
			Set<File> srcFiles = getFiles(fSrcFolder);
			for (File srcFile : srcFiles){
				String className = this.getClassNameFromFile(srcFile);
				File srcFileRevision = srcFile;
				if (revision != null){
					srcFileRevision = this.getFile(srcFile, revision);
				}
				String superClassName = getSuperClassName(srcFileRevision, fSrcFolder, className); 
				if (superClassName == null){
					superClassName = "";
				}
				hierarchy.put(className, superClassName);
			}
		}
		return hierarchy;
	}

	private Map<String, String> eliminateExternalClasses(Map<String, String> hierarchy){
		Map<String, String> newHierarchie = new HashMap<String, String>();
		for (String className : hierarchy.keySet()){
			Boolean keepClassName = true;
			List<String> superClasses = getSuperClasses(className, hierarchy);
			for (String superClassName : superClasses){
				keepClassName = keepClassName && isInProject(superClassName);
			}
			if (keepClassName){
				newHierarchie.put(className, hierarchy.get(className));
			}
		}
		return newHierarchie;
	}

	public Boolean isInProject(String className){
		File f = new File(projectLocation + srcFolder + getFileNameFromClassName(className));
		return f.exists();
	}

	private List<String> getSuperClasses(String className, Map<String, String> hierarchy){
		List<String> superClasses = new ArrayList<String>();
		String key = className;
		while (hierarchy.containsKey(key) && !hierarchy.get(key).equals("")){
			superClasses.add(hierarchy.get(key));
			key = hierarchy.get(key);
		}
		return superClasses;
	}

	private String getSuperClassName(File srcFile, File srcFolder2, String className){
		String superClassName = null;
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		String sourceCode = CrawlerGit.extractSourceCode(srcFile);
		parser.setSource(sourceCode.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		ControVolASTVisitor1 cvVisitor = new ControVolASTVisitor1(cu);
		cu.accept(cvVisitor);
		superClassName = cvVisitor.superClassName; 
		return superClassName;
	}

	private Set<File> getFiles(File root) {
		Set<File> files = new TreeSet<File>();
		File[] list = root.listFiles();
		if (list != null){
			for (File f : list) {
				if (f.isDirectory()) {
					files.addAll(getFiles(f));
				} else if (f.getName().endsWith(Cst.JAVA_EXTENSION)){
					files.add(f);
				}
			}
		}
		return files;
	}

	private static String extractSourceCode(File file) {
		String sourceCode = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				sourceCode += line + "\n";
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sourceCode;
	}

	public File getFile(File file, String revision){
		File currentFile = file;
		Calendar limit = this.getRevisionTime(revision);
		try {
			GTHistory history = GTAccessFactory.getHome().getGitHistoryOfFile(file);
			Integer cnt = 0;
			Calendar currentTime = history.getHistoryFiles().get(cnt).getCommitDate();
			while ((currentTime.compareTo(limit) > 0) && (cnt < history.getHistoryFiles().size())){
				currentFile = history.getHistoryFiles().get(cnt).getFile();
				currentTime = history.getHistoryFiles().get(cnt).getCommitDate();
				cnt++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentFile;
	}

	private String getClassNameFromFile(File file){
		return file.getAbsolutePath().replace(projectLocation+this.srcFolder, "").replace("/", ".").replace(Cst.JAVA_EXTENSION, "");
	}

	private String getFileNameFromClassName(String className){
		return className.replace(".", "/") + Cst.JAVA_EXTENSION;
	}

	private Calendar getRevisionTime(String revision){
		Calendar t = Calendar.getInstance();
		File gitDir = new File(projectLocation);
		try {
			Git git = Git.open(gitDir);
			Repository repo = git.getRepository();
			ObjectId commitId = repo.resolve(revision);
			RevWalk walk = new RevWalk(repo);
			RevCommit commit = walk.parseCommit(commitId);
			t.setTime(commit.getCommitterIdent().getWhen());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}

	//********************
	//** TEST IN A MAIN **
	//********************

	public static void main(String[] args) {

		String projectLocation = "/Users/Thomas/Documents/FakeWorkspace/castles/";

		CrawlerGit c = new CrawlerGit(projectLocation, "src/main/java", "target/castles-1.0-SNAPSHOT/WEB-INF/classes");

		MApplication app = c.getApplication();
		System.out.println("# entities: " + app.getEntities().size());

		for (MEntity ent : app.getEntities()){
			System.out.println(ent.getEntityName() + " (" + ent.getLocation() + ")");
			for (MAttribute att : ent.getAttributes()){
				System.out.println("\t" + att.getName());
			}
		}

	}

}