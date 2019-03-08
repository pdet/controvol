package ie.ucd.pel.engine.util;

public class Cst {

	// Java
	public static final String JAVA_EXTENSION = ".java";
	public static final String AT = "@";
	private static final String IMPORT = "import ";
	public static final String JAVA_LANG_PACKAGE = "java.lang.";
	
	// Objectify
	private static final String ANNOTATION_PATH = "com.googlecode.objectify.annotation.";
	
	// Access
	public static final String ACCESS_PUBLIC = "public";
	public static final String ACCESS_PROTECTED = "protected";
	public static final String ACCESS_PRIVATE = "private";
	public static final String ACCESS_PACKAGE = "";
	
	// Entity
	private static final String ENTITY = "Entity";
	public static final String ANNOTATION_ENTITY_1 = AT + ENTITY;
	public static final String ANNOTATION_ENTITY_2 = AT + ANNOTATION_PATH + ENTITY;
	public static final String IMPORT_ENTITY = IMPORT + ANNOTATION_PATH + ENTITY + ";";
	
	// Ignore
	private static final String IGNORE = "Ignore";
	public static final String ANNOTATION_IGNORE_1 = AT + IGNORE;
	public static final String ANNOTATION_IGNORE_2 = AT + ANNOTATION_PATH + IGNORE;
	public static final String IMPORT_IGNORE = IMPORT + ANNOTATION_PATH + IGNORE + ";";
	
	// AlsoLoad
	private static final String ALSOLOAD = "AlsoLoad";
	public static final String ANNOTATION_ALSOLOAD_1 = AT + ALSOLOAD;
	public static final String ANNOTATION_ALSOLOAD_2 = AT + ANNOTATION_PATH + ALSOLOAD;
	public static final String IMPORT_ALSOLOAD = IMPORT + ANNOTATION_PATH + ALSOLOAD + ";";
	
	// Types
	public static final String JAVA_PRIMITIVE_TYPE_OBJECTS[] 		= {"Void", 	"Boolean", 	"Byte", 	"Short", 	"Integer", 	"Long", 	"Float", 	"Double", 	"Character", 	"String", 	"Object"};
	public static final String JAVA_PRIMITIVE_TYPES[] 				= {"void", 	"boolean", 	"byte", 	"short", 	"int", 		"long", 	"float", 	"double", 	"char", 		null, 		null};
	public static final Boolean OBJECTIFY_PRIMITIVE_TYPE_OBJECTS[]	= {false, 	true, 		true, 		true, 		true, 		true, 		true, 		true, 		false, 			true, 		true};
	
	public static final String VOID_TYPE = JAVA_PRIMITIVE_TYPE_OBJECTS[0];
	public static final String BOOLEAN_TYPE = JAVA_PRIMITIVE_TYPE_OBJECTS[1];
	public static final String BYTE_TYPE = JAVA_PRIMITIVE_TYPE_OBJECTS[2];
	public static final String SHORT_TYPE = JAVA_PRIMITIVE_TYPE_OBJECTS[3];
	public static final String INTEGER_TYPE = JAVA_PRIMITIVE_TYPE_OBJECTS[4];
	public static final String LONG_TYPE = JAVA_PRIMITIVE_TYPE_OBJECTS[5];
	public static final String FLOAT_TYPE = JAVA_PRIMITIVE_TYPE_OBJECTS[6];
	public static final String DOUBLE_TYPE = JAVA_PRIMITIVE_TYPE_OBJECTS[7];
	public static final String CHARACTER_TYPE = JAVA_PRIMITIVE_TYPE_OBJECTS[8];
	public static final String STRING_TYPE = JAVA_PRIMITIVE_TYPE_OBJECTS[9];
	public static final String OBJECT_TYPE = JAVA_PRIMITIVE_TYPE_OBJECTS[10];
}
