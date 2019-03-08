package com.controvol.refactoring;

import ie.ucd.pel.datastructure.MApplication;
import ie.ucd.pel.datastructure.MAttribute;
import ie.ucd.pel.datastructure.MEntity;
import ie.ucd.pel.datastructure.evolution.WildRenameAttribute;
import ie.ucd.pel.engine.crawler.CrawlerGit;

import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.text.edits.MultiTextEdit;

import com.controvol.typechecking.ControVol;
import com.controvol.util.Cst;
import com.controvol.util.Util;

/**
 * This class detects when an attribute is renamed and triggers the ControVol engine. 
 * It is needed to do that, as otherwise this change would be seen as 'delete' + 'add' instead of 'rename'. 
 * (In this case, the Builder is not enough.)
 * @author Thomas
 *
 */
public class RenameAttribute1 extends org.eclipse.ltk.core.refactoring.participants.RenameParticipant {
	
	protected IField field;
	protected String newName;
	protected String oldName;
	protected String entityName;
	// This list should be emptied when the application is registered as a release (?)
	// Elements from this list can be removed when the @AlsoLoad annotation is added.
	public static Set<WildRenameAttribute> renamingOperations = new TreeSet<WildRenameAttribute>();

	protected boolean initialize(Object element) {
		this.field = (IField) element;
		this.oldName = this.field.getElementName();
		this.newName = this.getArguments().getNewName();
		IJavaElement fieldClass = this.field.getParent();
		try {
			IResource resrc = (IResource) fieldClass.getUnderlyingResource();
			this.entityName = Util.getResourceName(resrc);
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	public String getName() {
		return this.oldName;
	}

	public RefactoringStatus checkConditions(IProgressMonitor pm, CheckConditionsContext context) throws OperationCanceledException {
		return new RefactoringStatus();
	}
	
	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
			
		TextFileChange change;
			
		IJavaElement fieldClass = this.field.getParent();
		IFile fFile = (IFile) fieldClass.getUnderlyingResource();
		change = new TextFileChange(this.oldName, fFile);
		change.setEdit(new MultiTextEdit());
		
		ControVol.currentApplication = getCurrentApplication(this.field.getResource()); 		
		MEntity entity = ControVol.currentApplication.getEntity(entityName);		
		
		if (entity.containsAttribute(this.oldName)){
			MAttribute oldAttribute = entity.getAttribute(this.oldName);
			MAttribute newAttribute = new MAttribute(this.newName, oldAttribute.getType(), oldAttribute.getLocation()); // type and location didn't change
			// Should verify that the operation is not in it already
			RenameAttribute1.renamingOperations.add(new WildRenameAttribute(entity, newAttribute, oldAttribute, null, Util.getLastVersionNb(ControVol.currentApplication.getLocation())));
		} // if the attribute didn't exist in the current application model, it doesn't have to be updated

		return change;
	}
	
	public MApplication getCurrentApplication(IResource res){
		String projectFullName = res.getProject().getLocation().toString();
		CrawlerGit c = new CrawlerGit(projectFullName, Cst.SRC_FOLDER, Cst.BIN_FOLDER);
		return c.getApplication();
	}
}