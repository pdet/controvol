package com.controvol;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;

import com.controvol.typechecking.TypeCheckingDelegation;

public class MyResourceVisitor implements IResourceVisitor {
	
	@Override
    public boolean visit(IResource res) {
    	TypeCheckingDelegation checking = new TypeCheckingDelegation();
    	checking.setTypeChecking("controvol");
    	checking.setIResource(res);
    	checking.checking();
    	return true;
    }

}
