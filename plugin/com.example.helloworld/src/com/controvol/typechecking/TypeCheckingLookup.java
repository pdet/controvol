package com.controvol.typechecking;


public class TypeCheckingLookup {
	
	public TypeChecking getTypeChecking(String approach){
		if(approach.equalsIgnoreCase("controvol")){
			return new ControVol();
		}
		else
			return new Machiavelli();
	}
}
