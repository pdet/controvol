package com.controvol.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.ui.packageview.PackageFragmentRootContainer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

// FIXME I don't think the methods in this class are perfectly okay (Thomas)

public class ControVolSetup {

	private File file;
	private IPath path;
	private IProject project;

	public ControVolSetup(IResource res){
		this.path = ResourcesPlugin.getWorkspace().getRoot().getLocation();
		this.project = res.getProject();	
	}

	public ControVolSetup(IProject project){
		this.path = ResourcesPlugin.getWorkspace().getRoot().getLocation();
		this.project = project;
	}

	public File getControVolFolder(){		
		file = new File(this.getProjectFolder() + "controvol");
		if (!file.exists()) {
			if (!file.mkdir()) {
				System.out.println("Folder is NOT created!");
			}
		}
		return file;
	}


	/**
	 * Code from external resource
	 * @return
	 */
	public static IProject getCurrentProject(){
		IProject project = null;
		IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench != null){
			IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
			if (window != null){
				ISelectionService selectionService = window.getSelectionService(); 
				if (selectionService != null){
					ISelection selection = selectionService.getSelection();
					if (selection instanceof IStructuredSelection) {
						Object element = ((IStructuredSelection)selection).getFirstElement();    
						if (element instanceof IResource) {  
							project = ((IResource)element).getProject();
						} else if (element instanceof PackageFragmentRootContainer) {    
							IJavaProject jProject = ((PackageFragmentRootContainer)element).getJavaProject();    
							project = jProject.getProject();    
						} else if (element instanceof IJavaElement) {    
							IJavaProject jProject= ((IJavaElement)element).getJavaProject();    
							project = jProject.getProject();    
						}    
					}
				}
			}
		}
		return project;
	}

	public void generateProperties(File controVolFolder) {
		DataOutputStream os;
		try {
			os = new DataOutputStream(new FileOutputStream(controVolFolder.getPath() + File.separator + "controvol.properties"));
			os.writeChars("GIT_LOCATION=/your_location/path \n");
			os.writeChars("ALSO_LOAD=Add @AlsoLoad to rename \n");
			os.writeChars("IGNORE=Add @Ignore to remore \n");
			os.writeChars("APPROACH=controvol \n");
			os.close();
		} catch (IOException e) {
			System.out.println(this.toString()+e);
		}
	}

	public  String getProjectFolder(){
		String projectName = project.getName();
		String projectPath = path.toString();
		String folder = projectPath + File.separator + projectName + File.separator;
		return folder;
	}

}
