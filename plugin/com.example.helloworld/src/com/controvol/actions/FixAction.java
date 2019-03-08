package com.controvol.actions;

import ie.ucd.pel.engine.crawler.CrawlerGit;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionDelegate;

import com.controvol.typechecking.ControVol;
import com.controvol.util.ControVolSetup;
import com.controvol.util.Cst;
import com.controvol.util.Util;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class FixAction extends ActionDelegate {

	/**
	 * The constructor.
	 */
	public FixAction() {}

	/**
	 * This action spits the XML for the current version in the data dictionary
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		MessageDialog dialog = new MessageDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
				Cst.CONTROVOL, 
				null, 
				Cst.MSG_QUESTION_REGISTER, 
				MessageDialog.QUESTION, 
				new String[] {Cst.MSG_NO, Cst.MSG_YES}, 1);
		int result = dialog.open();

		if (result == 1){ 
			IProject project = ControVolSetup.getCurrentProject();
			String projectLocation = project.getLocation().toString();
			if (ControVol.currentApplication == null){
				CrawlerGit crawler = new CrawlerGit(projectLocation, Cst.SRC_FOLDER, Cst.BIN_FOLDER);
				ControVol.currentApplication = crawler.getApplication();
			} 
			Util.exportToXml(ControVol.currentApplication, project);
		}
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {}	

}