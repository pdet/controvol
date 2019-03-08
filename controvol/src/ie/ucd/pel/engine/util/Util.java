package ie.ucd.pel.engine.util;


public class Util {

	public static String primitiveTypeToPrimitiveTypeObject(String typeName){
		String res = null;
		if (isJavaPrimitiveType(typeName)){
			Integer index = getIndexInArray(typeName, Cst.JAVA_PRIMITIVE_TYPES);
			if (Cst.JAVA_PRIMITIVE_TYPE_OBJECTS.length > index){
				res = Cst.JAVA_PRIMITIVE_TYPE_OBJECTS[index];
			}
		} else if (isJavaPrimitiveTypeObject(typeName)){
			res = typeName;
		}
		return res;
	}

	public static Boolean isJavaPrimitiveType(String typeName){
		return Util.isInArray(Util.removePackageName(typeName), Cst.JAVA_PRIMITIVE_TYPES);
	}

	public static Boolean isJavaPrimitiveTypeObject(String typeName){
		return Util.isInArray(Util.removePackageName(typeName), Cst.JAVA_PRIMITIVE_TYPE_OBJECTS);
	}

	private static Boolean isInArray(String typeName, String[] array){
		return !getIndexInArray(typeName, array).equals(-1);
	}

	private static Integer getIndexInArray(String typeName, String[] array){
		Integer index = -1;
		if (typeName != null){
			for (Integer i = 0 ; i < array.length ; i++){
				if (typeName.equals(array[i])){
					index = i;
				}
			}
		}
		return index;
	}
	
	public static Boolean isBiggerPrimitiveTypeObjects(String typeObject1, String typeObject2){
		Integer indexTypeObject1 = getIndexInArray(Util.removePackageName(typeObject1), Cst.JAVA_PRIMITIVE_TYPE_OBJECTS);
		Integer indexTypeObject2 = getIndexInArray(Util.removePackageName(typeObject2), Cst.JAVA_PRIMITIVE_TYPE_OBJECTS);
		return (indexTypeObject1 > indexTypeObject2); 
	}

	public static Boolean isObjectifyPrimitiveTypeObject(String typeName){
		Boolean res = false;
		Integer index = getIndexInArray(Util.removePackageName(typeName), Cst.JAVA_PRIMITIVE_TYPE_OBJECTS);
		if (index != -1){
			if (Cst.OBJECTIFY_PRIMITIVE_TYPE_OBJECTS.length > index){
				res = Cst.OBJECTIFY_PRIMITIVE_TYPE_OBJECTS[index];
			}
		}
		return res;
	}

	public static String addPackageName(String typeName){
		String res = typeName;
		if (Util.isJavaPrimitiveTypeObject(typeName)){
			if (!typeName.startsWith(Cst.JAVA_LANG_PACKAGE)){
				res = Cst.JAVA_LANG_PACKAGE + typeName;
			}
		}
		return res;
	}

	public static String removePackageName(String typeName){
		String res = typeName;
		if (typeName != null){
			if (typeName.startsWith(Cst.JAVA_LANG_PACKAGE)){
				res = typeName.replace(Cst.JAVA_LANG_PACKAGE, "");
			}
		}
		return res;
	}

	public static void main(String[] args){
		System.out.println(isJavaPrimitiveType(null));
		System.out.println(isJavaPrimitiveType(""));
		System.out.println(isJavaPrimitiveType("byt"));
		System.out.println(isJavaPrimitiveType("Byte"));
		System.out.println(isJavaPrimitiveType("byte"));
		System.out.println("===");
		System.out.println(isJavaPrimitiveTypeObject(null));
		System.out.println(isJavaPrimitiveTypeObject(""));
		System.out.println(isJavaPrimitiveTypeObject("byt"));
		System.out.println(isJavaPrimitiveTypeObject("byte"));
		System.out.println(isJavaPrimitiveTypeObject("Byte"));
		System.out.println(isJavaPrimitiveTypeObject("java.lang.Byte"));
		System.out.println("===");
		System.out.println(primitiveTypeToPrimitiveTypeObject(null));
		System.out.println(primitiveTypeToPrimitiveTypeObject("byt"));
		System.out.println(primitiveTypeToPrimitiveTypeObject("byte"));
		System.out.println(primitiveTypeToPrimitiveTypeObject("Byte"));
		System.out.println(primitiveTypeToPrimitiveTypeObject("java.lang.Byte"));
		System.out.println("===");
		System.out.println(isObjectifyPrimitiveTypeObject(null));
		System.out.println(isObjectifyPrimitiveTypeObject("Byt"));
		System.out.println(isObjectifyPrimitiveTypeObject("byte"));
		System.out.println(isObjectifyPrimitiveTypeObject("Character"));
		System.out.println(isObjectifyPrimitiveTypeObject("Byte"));
		System.out.println(isObjectifyPrimitiveTypeObject("java.lang.Byte"));
		System.out.println("===");
		System.out.println(addPackageName("Byte"));
		System.out.println(addPackageName("java.lang.Byte"));
		System.out.println(addPackageName("Hello"));
		System.out.println(addPackageName("java.lang.Hello"));
		System.out.println("===");
		System.out.println(removePackageName("Byte"));
		System.out.println(removePackageName("java.lang.Byte"));
		System.out.println(removePackageName("Hello"));
		System.out.println(removePackageName("java.lang.Hello"));
	}

}
