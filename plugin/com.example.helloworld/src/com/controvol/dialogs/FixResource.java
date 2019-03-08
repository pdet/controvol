package com.controvol.dialogs;

import java.util.List;

import org.eclipse.core.resources.IResource;

public class FixResource {

	private List<Fixes> fixes;
	private IResource resource;
	

	public void setElement(IResource resource){
		this.resource = resource;
	}
	
	public void setFixes(List<Fixes> fixes){
		this.fixes=fixes;
	}
	
	public IResource getResource(){
		return resource;
	}
	
	public List<Fixes> getFixes(){
		return fixes;
	}
	
	@Override
	public String toString(){
		return resource.toString();
	}
}