package ie.ucd.pel.engine.checking;

import ie.ucd.pel.engine.checking.test.A;
import ie.ucd.pel.engine.checking.test.B;
import ie.ucd.pel.engine.checking.test.C;
import ie.ucd.pel.engine.checking.test.IC;


public class TestTypeChecker {

	public static void main(String[] args) throws InterruptedException {
		TypeChecker tc = new TypeChecker();
		Boolean val;
		Boolean a = true;
		Byte b = 0;
		Short c = 0;
		Integer d = 0;
		Long e = new Long(0);
		Float f = new Float(0);
		Double g = new Double(0);
		String h = "";
		Object i = new Object();
		A objA = new A();
		B objB = new B();
		C objC = new C();
		IC objICC = new C();

		/*
		// isCompatiblePrimitiveTypes
		String name1 = .getClass().getName();
		String name2 = .getClass().getName();
		val = tc.isParsedCorrectlyPrimitiveTypes(name1, name2);
		System.out.println(name1 + " vs " + name2 + ": " + val);
		System.out.println("===");
		
		// isParsedCorrectlyPrimitiveTypes
		String name1 = .getClass().getName();
		String name2 = .getClass().getName();
		val = tc.isParsedCorrectlyPrimitiveTypes(name1, name2);
		System.out.println(name1 + " vs " + name2 + ": " + val);
		System.out.println("===");
		
		// isBiggerPrimitiveTypes
		String name1 = .getClass().getName();
		String name2 = .getClass().getName();
		val = tc.isParsedCorrectlyPrimitiveTypes(name1, name2);
		System.out.println(name1 + " vs " + name2 + ": " + val);
		*/
	}
	
}
