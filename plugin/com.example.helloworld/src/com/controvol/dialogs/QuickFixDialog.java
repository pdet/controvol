package com.controvol.dialogs;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.TwoPaneElementSelector;

public class QuickFixDialog   {
	//IWorkbenchWindow window;
	TwoPaneElementSelector dialog;
	Messages msg;
	
	public QuickFixDialog(Shell shell){
		//this.window=window;
		//creating the dialog box
		ILabelProvider elementRenderer = new ArrayLabelProvider(0);
		ILabelProvider qualifierRenderer = new ArrayLabelProvider(1);
		dialog = new TwoPaneElementSelector(shell, elementRenderer, qualifierRenderer);
	}
	

	public void open(){

		// Setting the dialog box
		dialog.setUpperListLabel("Select a fix:");
		dialog.setLowerListLabel("Resource:");
		dialog.setTitle("Quick fix");
		dialog.setMessage("Select the fix for attribute:");
		dialog.setElements(msg.getMsg().toArray());
		dialog.setMultipleSelection(true);
		dialog.setSize(20, 8);


		// returning the selected objects
		if (dialog.open()==Window.OK) {
			Object[] result = dialog.getResult();
			for (int i = 0; i < result.length; i++) {
				String[] ss = (String[])result[i];
				System.out.println(ss[i]);
			}
		}
	}

	static class ArrayLabelProvider extends LabelProvider{
		private int index;
		ArrayLabelProvider(int index){
			this.index = index;
		}
		public String getText(Object element) {
			return ((String[]) element)[index].toString();
		}
	}
	
	public void setMessage(Messages msg){
		this.msg = msg;
	}
}
