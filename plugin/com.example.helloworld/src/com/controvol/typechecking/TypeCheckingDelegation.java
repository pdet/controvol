package com.controvol.typechecking;

import org.eclipse.core.resources.IResource;

public class TypeCheckingDelegation {
	private TypeCheckingLookup lookup = new TypeCheckingLookup();
	private TypeChecking typeChecking;
	private String approach;
	private IResource resrc;
	
	public void setTypeChecking(String approach){
		this.approach = approach;
	}
	
	public void setIResource(IResource resrc){
		this.resrc=resrc;		
	}
	
	public void checking(){
		typeChecking = lookup.getTypeChecking(approach);
		typeChecking.checking(resrc);
	}	
}
