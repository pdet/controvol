package com.controvol;

import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;

import com.controvol.typechecking.TypeCheckingDelegation;

public class MyResourceDeltaVisitor implements IResourceDeltaVisitor {

	@Override
	public boolean visit(IResourceDelta delta) { 
    	TypeCheckingDelegation checking = new TypeCheckingDelegation();
    	checking.setTypeChecking("controvol");
    	checking.setIResource(delta.getResource());
    	checking.checking();
		return true; 
	}

}
