package com.controvol.typechecking;

import ie.ucd.pel.datastructure.MApplication;
import ie.ucd.pel.datastructure.evolution.AddAttribute;
import ie.ucd.pel.datastructure.evolution.DeleteAttribute;
import ie.ucd.pel.datastructure.evolution.Evolution;
import ie.ucd.pel.datastructure.evolution.Operation;
import ie.ucd.pel.datastructure.evolution.WildRenameAttribute;
import ie.ucd.pel.datastructure.warning.IWarning;
import ie.ucd.pel.engine.ControVolEngine;
import ie.ucd.pel.engine.crawler.CrawlerGit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;

import com.controvol.markers.MarkerFactory;
import com.controvol.refactoring.RenameAttribute1;
import com.controvol.util.Cst;
import com.controvol.util.Util;

public class ControVol implements TypeChecking {

	private IProject project;
	private IResource resr;

	/* The following attribute corresponds to the current model of the application. 
	 * It will be modified every time the Java code changes. 
	 * XXX allow to have only 1 model in the environment... 
	 */
	public static MApplication currentApplication;

	@Override
	public void checking(IResource resr) {	
		visit(resr);
	}

	@Override
	public void checking(IResourceDelta delta) {
		visit(delta.getResource());
	}

	private void visit(IResource resr){
		setResource(resr);
		setProject(resr.getProject());
		visitor();
	}

	public void visitor(){
		String ext = getResource().getFileExtension();

		if (ext != null){
			if (ext.equals(Cst.JAVA_EXTENSION)){
				//System.out.println("RESOURCE ON THE FLY: "+getResource().toString());

				String entityName = Util.getResourceName(getResource());

				// This get the entire legacy history from the staging
				List<MApplication> appHistory = Util.getAppList(getProjectFullName());		

				if (ControVol.currentApplication.containsEntity(entityName)){
					//CrawlerGit c = new CrawlerGit(getProjectFullName());
					CrawlerGit c = new CrawlerGit(getProjectFullName(), Cst.SRC_FOLDER, Cst.BIN_FOLDER);
					ControVol.currentApplication = c.getApplication();

					// Compute evolutions	
					appHistory.add(ControVol.currentApplication);
					ControVolEngine eng = new ControVolEngine();
					Evolution evol = eng.getEvolution(appHistory);

					List<Operation> operationsToRemove = new ArrayList<Operation>();
					for (WildRenameAttribute operation : RenameAttribute1.renamingOperations){
						String operationEntityName = operation.getEntity().getEntityName();
						String operationAttributeName = operation.getAttribute().getName();
						String operationLegagcyAttributeName = operation.getLegacyAttribute().getName();
						String versionNb = operation.getVersion2();
						// Remove the add operations and the remove operations
						for (Operation op : evol){
							if (op instanceof AddAttribute){
								AddAttribute opAdd = (AddAttribute) op;
								if (operationEntityName.equals(opAdd.getEntity().getEntityName()) && operationAttributeName.equals(opAdd.getAttribute().getName())){
									operationsToRemove.add(op);
								}
							} else if (op instanceof DeleteAttribute){
								DeleteAttribute opDelete = (DeleteAttribute) op;
								if (operationEntityName.equals(opDelete.getEntity().getEntityName()) && operationLegagcyAttributeName.equals(opDelete.getAttribute().getName())){
									operationsToRemove.add(op);									
								}
							}
						}
						evol.removeAll(operationsToRemove);
						// Add the rename operations
						operation.getLegacyAttribute().getName();
						operation.setVersion2(versionNb);
						evol.add(operation);
					}

					Set<IWarning> warnings = eng.check(evol);
					// Create all the markers
					for (IWarning warning : warnings){
						MarkerFactory.createWarning(warning, project);
					}

					MarkerFactory.findMarkers(resr);
				} 
			} // it should never be null
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

	private IProject getProject(){
		return project;
	}

	private void setProject(IProject project){
		this.project = project;
	}

	private void setResource(IResource resr){
		this.resr = resr;
	}
	private IResource getResource(){
		return resr;
	}
}
