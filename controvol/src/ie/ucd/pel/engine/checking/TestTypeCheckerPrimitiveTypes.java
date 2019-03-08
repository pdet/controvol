package ie.ucd.pel.engine.checking;

import ie.ucd.pel.datastructure.MAttribute;
import ie.ucd.pel.datastructure.evolution.RetypeAttribute;
import ie.ucd.pel.datastructure.warning.CastApproximation;
import ie.ucd.pel.datastructure.warning.IWarning;
import ie.ucd.pel.datastructure.warning.IncompatibleTypes;
import ie.ucd.pel.datastructure.warning.PrecisionLoss;
import ie.ucd.pel.engine.ControVolEngine;


public class TestTypeCheckerPrimitiveTypes {

	public static void main(String[] args) throws InterruptedException {
		TypeChecker tc = new TypeChecker();
		String[] types1 = {"Boolean", "Byte", "Short", "Integer", "Long", "Float", "Double", "String"};
		String[] types2 = {"Boolean", "Byte", "Short", "Integer", "Long", "Float", "Double", "String", "Object"};
		Boolean val;
		
		// areEquivalentPrimitiveTypes
		for (Integer i = 0 ; i < types1.length ; i++){
			for (Integer j = 0 ; j < types2.length ; j++){
				val = tc.areEquivalentPrimitiveTypes(types1[i], types2[j]);
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

		// isCompatiblePrimitiveTypes
		for (Integer i = 0 ; i < types1.length ; i++){
			for (Integer j = 0 ; j < types2.length ; j++){
				val = tc.isCompatiblePrimitiveTypes(types1[i], types2[j]);
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

		// isParsedCorrectlyPrimitiveTypes
		for (Integer i = 0 ; i < types1.length ; i++){
			for (Integer j = 0 ; j < types2.length ; j++){
				val = tc.isParsedCorrectlyPrimitiveTypes(types1[i], types2[j]);
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

		// isBiggerPrimitiveTypes
		for (Integer i = 0 ; i < types1.length ; i++){
			for (Integer j = 0 ; j < types2.length ; j++){
				val = tc.isBiggerPrimitiveTypes(types1[i], types2[j]);
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

		System.out.println("===");
		Boolean a = true;
		Byte b = 0;
		Short c = 0;
		Integer d = 0;
		Long e = new Long(0);
		Float f = new Float(0);
		Double g = new Double(0);
		String h = "";
		Object i = new Object();
		Object[] typeObjects1 = {a, b, c, d, e, f, g, h};
		Object[] typeObjects2 = {a, b, c, d, e, f, g, h, i};

		// areEquivalentPrimitiveTypes
		for (Integer j = 0 ; j < typeObjects1.length ; j++){
			for (Integer k = 0 ; k < typeObjects2.length ; k++){
				String name1 = typeObjects1[j].getClass().getName();
				String name2 = typeObjects2[k].getClass().getName();
				val = tc.areEquivalentPrimitiveTypes(name1, name2);
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

		// isCompatiblePrimitiveTypes
		for (Integer j = 0 ; j < typeObjects1.length ; j++){
			for (Integer k = 0 ; k < typeObjects2.length ; k++){
				String name1 = typeObjects1[j].getClass().getName();
				String name2 = typeObjects2[k].getClass().getName();
				val = tc.isCompatiblePrimitiveTypes(name1, name2);
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

		// isBiggerPrimitiveTypes
		for (Integer j = 0 ; j < typeObjects1.length ; j++){
			for (Integer k = 0 ; k < typeObjects2.length ; k++){
				String name1 = typeObjects1[j].getClass().getName();
				String name2 = typeObjects2[k].getClass().getName();
				val = tc.isBiggerPrimitiveTypes(name1, name2);
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
		
		// isParsedCorrectlyPrimitiveTypes
		for (Integer j = 0 ; j < typeObjects1.length ; j++){
			for (Integer k = 0 ; k < typeObjects2.length ; k++){
				String name1 = typeObjects1[j].getClass().getName();
				String name2 = typeObjects2[k].getClass().getName();
				val = tc.isParsedCorrectlyPrimitiveTypes(name1, name2);
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
		System.out.println("===");

		// Warnings
		ControVolEngine engine = new ControVolEngine();
		for (Integer j = 0 ; j < types1.length ; j++){
			for (Integer k = 0 ; k < types2.length ; k++){
				RetypeAttribute retype = new RetypeAttribute(null, new MAttribute("att", types2[k], ""), new MAttribute("att", types1[j], ""), "0", "1");
				IWarning warning = engine.getTypeWarning(retype);
				Thread.sleep(10);
				if (warning instanceof IncompatibleTypes){
					System.err.print("IC\t");
				} else if (warning instanceof PrecisionLoss){
					System.err.print("PL\t");
				} else if (warning instanceof CastApproximation){
					System.err.print("CA\t");
				} else {
					System.out.print("--\t");
				}
			}
			Thread.sleep(10);
			System.out.println("");
		}
		
		System.out.println("===");
		
		/*ControVolEngine engine = new ControVolEngine();
		RetypeAttribute retype = new RetypeAttribute(null, new MAttribute("att", "String", ""), new MAttribute("att", "Float", ""), "0", "1");
		IWarning warning = engine.getTypeWarning(retype);
		Thread.sleep(10);
		if (warning instanceof IncompatibleTypes){
			System.err.print("IC\t");
		} else if (warning instanceof PrecisionLoss){
			System.err.print("PL\t");
		} else if (warning instanceof CastApproximation){
			System.err.print("CA\t");
		} else {
			System.out.print("--\t");
		}*/
		
	}

}
