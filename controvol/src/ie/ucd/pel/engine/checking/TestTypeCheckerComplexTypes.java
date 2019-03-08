package ie.ucd.pel.engine.checking;

import ie.ucd.pel.engine.checking.test.A;
import ie.ucd.pel.engine.checking.test.B;
import ie.ucd.pel.engine.checking.test.C;
import ie.ucd.pel.engine.checking.test.D;
import ie.ucd.pel.engine.checking.test.E;

public class TestTypeCheckerComplexTypes {

	public static void main(String[] args) throws InterruptedException {
		TypeChecker tc = new TypeChecker();
		A objA = new A();
		B objB = new B();
		C objC = new C();
		D objD = new D();
		E objE = new E();
		Boolean val;

		Object[] objs = {objA, objB, objC, objD, objE};

		// areEquivalentComplexTypes
		for (Integer i = 0 ; i < objs.length ; i++){
			for (Integer j = 0 ; j < objs.length ; j++){
				val = tc.areEquivalentComplexTypes(objs[i], objs[j]);
				Thread.sleep(10);
				if (val){
					System.out.print(val + "\t");
				} else {
					System.err.print(val + "\t");
				}
			}
			Thread.sleep(10);
			System.out.println("");
		}
		System.out.println("===");
		
		// isCompatibleComplexTypes
		for (Integer i = 0 ; i < objs.length ; i++){
			for (Integer j = 0 ; j < objs.length ; j++){
				val = tc.isCompatibleComplexTypes(objs[i], objs[j]);
				Thread.sleep(10);
				if (val){
					System.out.print(val + "\t");
				} else {
					System.err.print(val + "\t");
				}
			}
			Thread.sleep(10);
			System.out.println("");
		}
		System.out.println("===");

		/*
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
