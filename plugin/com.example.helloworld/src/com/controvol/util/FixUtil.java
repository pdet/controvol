package com.controvol.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;


public class FixUtil {

	/**
	 * TODO: This class is yet to be done. It will help user setting up controvol
	 * The <tt>Properties</tt> object must containing the application properties
	 */
	private static Properties props;
	//final public static FixUtil instance = new FixUtil();
	private Map<String,String> propList = new TreeMap<>();
	String prop;
	
	public FixUtil(String propFolder){
		Properties prop = new Properties();
		InputStream input = null;
	 
		try {
	 
			input = new FileInputStream(propFolder+"/controvol/controvol.properties");
	 
			// load a properties file
			prop.load(input);
	 
			
			
			// get the property value and print it out
			System.out.println("Property: "+prop.getProperty("APPROACH"));
			System.out.println(prop.getProperty("ALSO_LOAD"));
			System.out.println(prop.getProperty("GIT_LOCATION"));
			
			
		    StringWriter writer = new StringWriter();
		    try {
		        prop.store(writer, "");
		    } catch (IOException e) {
		    	System.out.println("Properties loading error");
		    }

			
			
		    System.out.println("buffer "+ writer.getBuffer().toString());
			
			Enumeration eProps = prop.propertyNames();
			String teste="controvol";
			while (eProps.hasMoreElements()) { 
			    String key = (String) eProps.nextElement(); 
			    String value = prop.getProperty(key); 
			    
			    //ControVolProperty cvp = new ControVolProperty(key,value);
			    //System.out.println("ULTIMO "+cvp.equals(teste));
			    
			    System.out.println(key + " => " + new String(value) + " tentando "+String.valueOf(value)); 
			    propList.put(key, value);
			}
			
	 
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getApproach(){
		String approach="";

	
			/*
		for(String key : propList.keySet()){
			
			
			
			app2 = (String) propList.get(key);
			
			System.out.println("KEY: "+key);
			//approach=propList.get(key);
			System.out.println("VALUE: "+approach);
			
			System.out.println("chave:"+app2+ "  - compara: "+approach.equals(app2)+" approach: "+ approach);
			
			if(app2.equals("controvol")){
				System.out.println("achei: ");
			}
			
		}*/
		for(String m : propList.values()){
			System.out.println("Values (getApproahc): "+m);
		}

		
		return approach;
	}
	
	

	
	/*public  String getProperty(String property, IProject project) {
		
		System.out.println("Workbench isntance: ");
		
		String properties = controvolFolder+"/controvol/controvol.properties";
		
		System.out.println("ControVol properties: "+properties);
		
		
		
		
		return null;
	}
	

	/*private FixUtil() {
		try {
			Properties defaults = new Properties();
			InputStream is = this.getClass().getResourceAsStream("controvol/.controvol");
			System.out.println("I found the default properties' resource.");
			defaults.load(is);
			is.close();
			props = new Properties(defaults);
			if (new File("fix.properties").exists()) {
				String filename = "fix.properties";
				FileInputStream fs = new FileInputStream(filename);
				props.load(fs);
				fs.close();
			}
		} catch (IOException e) {
			System.err.println("Could not find default properties' resource.");
			System.exit(1);
		}
	}


	/**
	 * Return the value of the property whose the name is given as argument
	 *
	 * @param property  the property whose we search the value
	 * @return the value of <code>property</code> property
	 * @throws Exception if the properties file can't find
	 */
/*	private String getProperty(String property) {
		String value = props.getProperty(property);
		assert value != null : "Property " + property + " is undefined";
		return value;
	}

	public String ALSO_LOAD() {
		return this.getProperty("ALSO_LOAD");
	}

	public String getTypeChecking() {
		return this.getProperty("APPROACH");
	}*/
}
