package com.controvol.util;

public class Cst {

	public static final String REP_PLUGIN = "controvol";
	public static final String CONTROVOL = "ControVol";
	
	// CONSTANTS USED IN THE JAVA CODE AND IN plugin.xml
	public static final String PLUGIN_ID = "com.controvol";
	public static final String BUILDER_ID = PLUGIN_ID + Cst.DOT + "builder";
	public static final String NATURE_ID = PLUGIN_ID + Cst.DOT + "nature";
	public static final String ANNOTATION_ID = PLUGIN_ID + Cst.DOT + "annotation";
	
	public static final String MARKER_ID = PLUGIN_ID + Cst.DOT + "marker";
	public static final String MARKER_ICON_ID = "icons/sample.gif";
	public static final String MARKER_INSERT_CODE_FIX = "insertCodeFix";
	public static final String MARKER_REPLACE_CODE_FIX = "replaceCodeFix";
	public static final String MARKER_DELETE_CODE_FIX = "deleteCodeFix";
	public static final String MARKER_FIX_TYPE = "fixType"; // MARKER_FIX_TYPE_INSERT: insert, MARKER_FIX_TYPE_REPLACE: replace, MARKER_FIX_TYPE_DELETE: delete
	public static final Integer MARKER_FIX_TYPE_INSERT = 0; 
	public static final Integer MARKER_FIX_TYPE_REPLACE = 1; 
	public static final Integer MARKER_FIX_TYPE_DELETE = 2; 
	public static final String MARKER_DESCRIPTION = "description";
	public static final String MARKER_OLD_NAME_ID = "oldName";
	public static final String MARKER_NEW_NAME_ID = "newName";
	public static final String MARKER_IS_RENAMING = "isRenaming";
	public static final String MARKER_JSON_FIXES = "jsonFixes";
	public static final String RESTORE_MARKER_ID = PLUGIN_ID + Cst.DOT + "restoreMarker";
	
	public static final String SRC_FOLDER = "src/main/java/"; // Must end with File.separator
	public static final String BIN_FOLDER = "bin/"; //"target/castles-1.0-SNAPSHOT/WEB-INF/classes/"; // Must end with File.separator
	
	// USUAL CONSTANTS
	public static final String PACKAGE_SEPARATOR = Cst.DOT;
	public static final String LINE_BREAK = "\n";
	public static final String TAB = "\t";
	public static final String SEMI_COLON = ";";
	public static final String DOT = ".";
	public static final String SPACE = " ";
	public static final String BRACKET_1 = "(";
	public static final String BRACKET_2 = ")";
	public static final String QUOTE = "\"";
	
	// EXTENSION
	public static final String JAVA_EXTENSION = "java";
	public static final String XML_EXTENSION = "xml";
	
	// MESSAGES
	public static final String MSG_YES = "Yes";
	public static final String MSG_NO = "No";
	public static final String MSG_CHANGE = "Automatic ControVol change";
	public static final String MSG_QUESTION_REGISTER = "Register the current version of the application as a new release?";
	
}
