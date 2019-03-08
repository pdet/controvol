package com.controvol;

import ie.ucd.pel.datastructure.evolution.Operation;
import ie.ucd.pel.engine.crawler.CrawlerGit;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.controvol.typechecking.ControVol;
import com.controvol.util.Cst;

public class Builder extends IncrementalProjectBuilder { 

	protected Set<Operation> errors = new TreeSet<Operation>();

	@SuppressWarnings("rawtypes")
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor) {
		
		String projectName = this.getProjectFullName();		
		
		CrawlerGit crawlerGit = new CrawlerGit(projectName, Cst.SRC_FOLDER, Cst.BIN_FOLDER); 

		ControVol.currentApplication = crawlerGit.getApplication();
		if (kind == IncrementalProjectBuilder.FULL_BUILD) {
			fullBuild();
		} else {
			IResourceDelta delta = getDelta(getProject());		
			if ((delta == null) || (ControVol.currentApplication == null)) {
				fullBuild();
			} else {
				incrementalBuild(delta, monitor);
			}
		}
		return null;
	}   

	private void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor) {
		MyResourceDeltaVisitor visitor = new MyResourceDeltaVisitor();
		try {
			delta.accept(visitor);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private void fullBuild(){
		MyResourceVisitor visitor = new MyResourceVisitor();
		try {
			getProject().accept(visitor);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private String getProjectLocation(){
		return getProject().getWorkspace().getRoot().getLocation().toString();
	}
	
	private String getProjectName(){
		return getProject().getName();
	}
	
	private String getProjectFullName(){
		return getProjectLocation() + File.separator + getProjectName() + File.separator;
	}
	
} 