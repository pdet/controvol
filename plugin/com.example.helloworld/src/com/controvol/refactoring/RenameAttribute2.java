package com.controvol.refactoring;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.TextEditGroup;

import com.controvol.util.Cst;

/**
 * This class detects when an attribute is renamed, and add an AlsoLoad annotation above the attribute. 
 * It is needed to do that, as otherwise this change would be seen as 'delete' + 'add' instead of 'rename'. 
 * (In this case, the Builder is not enough.)
 * @author Thomas
 *
 */
public class RenameAttribute2 extends org.eclipse.ltk.core.refactoring.participants.RenameParticipant {

	IField field;

	protected boolean initialize(Object element) {
		this.field = (IField) element;
		return true;
	}

	public String getName() {
		String fieldKey = this.field.getKey();
		String fieldName = fieldKey.substring(fieldKey.lastIndexOf(Cst.PACKAGE_SEPARATOR) + 1); // FIXME Dirty code
		return fieldName;
	}

	public RefactoringStatus checkConditions(IProgressMonitor pm, CheckConditionsContext context) throws OperationCanceledException {
		return new RefactoringStatus();
	}

	public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		TextFileChange change;
		String oldName = getName();

		IJavaElement fieldClass = this.field.getParent();
		IFile fFile = (IFile) fieldClass.getUnderlyingResource();
		change = new TextFileChange(oldName, fFile);
		change.setEdit(new MultiTextEdit());

		ITextFileBufferManager manager = FileBuffers.getTextFileBufferManager();
		manager.connect(fFile.getFullPath(), LocationKind.IFILE, null);
		try {
			ITextFileBuffer textFileBuffer = manager.getTextFileBuffer(fFile.getFullPath(), LocationKind.IFILE);
			String code = textFileBuffer.getDocument().get();

			String[] lines = code.split(Cst.LINE_BREAK);
			Boolean found = false;
			Integer offset = 0;
			for (Integer i = 0 ; (i < lines.length) && (!found) ; i++){
				String line = lines[i];
				// These conditions only cover simple cases
				// FIXME Use regular expressions? retrieve the line number
				Boolean cond1 = line.endsWith(Cst.SPACE + getName());
				Boolean cond2 = line.endsWith(Cst.TAB + getName());

				Boolean cond3 = line.contains(Cst.SPACE + getName() + Cst.SPACE);
				Boolean cond4 = line.contains(Cst.SPACE + getName() + Cst.SPACE);
				Boolean cond5 = line.contains(Cst.SPACE + getName() + Cst.TAB);
				Boolean cond6 = line.contains(Cst.SPACE + getName() + Cst.TAB);
				Boolean cond7 = line.contains(Cst.SPACE + getName() + Cst.SEMI_COLON);
				Boolean cond8 = line.contains(Cst.SPACE + getName() + Cst.SEMI_COLON);

				Boolean cond9 =  line.contains(Cst.TAB + getName() + Cst.SPACE);
				Boolean cond10 = line.contains(Cst.TAB + getName() + Cst.SPACE);
				Boolean cond11 = line.contains(Cst.TAB + getName() + Cst.TAB);
				Boolean cond12 = line.contains(Cst.TAB + getName() + Cst.TAB);
				Boolean cond13 = line.contains(Cst.TAB + getName() + Cst.SEMI_COLON);
				Boolean cond14 = line.contains(Cst.TAB + getName() + Cst.SEMI_COLON);

				found = cond1 || cond2 || cond3 || cond4 || cond5 || cond6 || cond7 || cond8 || cond9 || cond10 || cond11 || cond12 || cond13 || cond14;

				if (found){
					// It assumes that: 
					// - the annotation AlsoLoad does not already exist
					String annotation = ie.ucd.pel.engine.util.Cst.ANNOTATION_ALSOLOAD_1 + Cst.BRACKET_1 + Cst.QUOTE + oldName + Cst.QUOTE + Cst.BRACKET_2;
					String annotationLine = "";
					Integer nbTabs = getNumberOfStartingTabs(line);
					for (Integer j = 0 ; j < nbTabs ; j++){
						annotationLine += Cst.TAB;
					}
					annotationLine += annotation + Cst.LINE_BREAK;
					InsertEdit insertEdit = new InsertEdit(offset, annotationLine);
					change.addEdit(insertEdit);
					change.addTextEditGroup(new TextEditGroup(Cst.MSG_CHANGE, insertEdit));
				}
				offset += line.length() + 1;
			}
		} finally {
			manager.disconnect(fFile.getFullPath(), LocationKind.IFILE, null);
		}
		return change;
	}

	public Integer getNumberOfStartingTabs(String str){
		Integer nb = 0;
		String strAux = str;
		while (strAux.startsWith(Cst.TAB)){
			nb++;
			strAux = strAux.replaceFirst(Cst.TAB, "");
		}
		return nb;
	}

}