package com.controvol.quickfix;

import ie.ucd.pel.datastructure.warning.AttributeDeleted;
import ie.ucd.pel.datastructure.warning.AttributeImproperlyRenamed;
import ie.ucd.pel.datastructure.warning.IWarning;
import ie.ucd.pel.datastructure.warning.TypeWarning;

import java.util.ArrayList;
import java.util.List;

import com.controvol.util.Cst;
import com.controvol.util.Util;

public class FixFactory {
	
	public FixFactory(){}
	
	public static List<Fix> getCodeFixes(IWarning warning){
		List<Fix> fixes = new ArrayList<Fix>();
		String codeToInsert;
		String codeToReplace;
		String annotation;
		String message;
		Fix fix;
		String type;
		String access;
		if (warning instanceof AttributeDeleted){
			AttributeDeleted warningAttDel = (AttributeDeleted) warning;
			// Fix 1
			annotation = ie.ucd.pel.engine.util.Cst.ANNOTATION_IGNORE_1;
			type = Util.getClassNameWithoutPackage(warningAttDel.getOperation().getAttribute().getType());
			String currentName = warningAttDel.getOperation().getAttribute().getName();
			String formerName = currentName;
			type = Util.getClassNameWithoutPackage(warningAttDel.getOperation().getAttribute().getType());
			access = warningAttDel.getOperation().getAttribute().getAccess();
			codeToInsert = Cst.TAB + annotation + Cst.LINE_BREAK;
			if (access.equals("")){
				codeToInsert += Cst.TAB;
			} else {
				codeToInsert += Cst.TAB + access + Cst.SPACE;
			}
			codeToInsert += type + Cst.SPACE + currentName + Cst.SEMI_COLON + Cst.LINE_BREAK; 
			message = "Add annotation " + annotation + " to explicitly remove attribute '" + currentName + "'";
			fix = new Fix(message, Cst.MARKER_FIX_TYPE_INSERT, currentName, formerName, codeToInsert, "", "", false);
			fixes.add(fix);
			
			// Fix 2
			type = Util.getClassNameWithoutPackage(warningAttDel.getOperation().getAttribute().getType());
			access = warningAttDel.getOperation().getAttribute().getAccess();
			if (access.equals("")){
				codeToInsert = Cst.TAB;
			} else {
				codeToInsert = Cst.TAB + access + Cst.SPACE;
			}
			codeToInsert += type + Cst.SPACE + formerName + Cst.SEMI_COLON + Cst.LINE_BREAK;
			codeToReplace = type + Cst.SPACE + currentName;  
			message = "Restore attribute '" + formerName + "'.";
			fix = new Fix(message, Cst.MARKER_FIX_TYPE_INSERT, currentName, formerName, codeToInsert, "", "", false);
			fixes.add(fix);
			
		} else if (warning instanceof AttributeImproperlyRenamed){
			AttributeImproperlyRenamed warningAttRenamed = (AttributeImproperlyRenamed) warning;
			String currentName = warningAttRenamed.getOperation().getAttribute().getName();
			String formerName = warningAttRenamed.getOperation().getLegacyAttribute().getName();

			// Fix 1
			annotation = ie.ucd.pel.engine.util.Cst.ANNOTATION_ALSOLOAD_1;
			codeToInsert = Cst.TAB + annotation + Cst.BRACKET_1 + Cst.QUOTE + formerName + Cst.QUOTE + Cst.BRACKET_2 + Cst.LINE_BREAK;
			codeToReplace = Util.getClassNameWithoutPackage(warningAttRenamed.getOperation().getAttribute().getType()); 
			codeToReplace = codeToReplace + " " + warningAttRenamed.getOperation().getLegacyAttribute() + ";";
			message = "Add annotation " + annotation + Cst.BRACKET_1 + Cst.QUOTE + formerName + Cst.QUOTE + Cst.BRACKET_2 + " to prevent data loss";
			fix = new Fix(message, Cst.MARKER_FIX_TYPE_INSERT, currentName, formerName, codeToInsert, codeToReplace, "", true);
			fixes.add(fix);
			
			// Fix 2
			annotation = ie.ucd.pel.engine.util.Cst.ANNOTATION_IGNORE_1;
			type = Util.getClassNameWithoutPackage(warningAttRenamed.getOperation().getAttribute().getType());
			access = warningAttRenamed.getOperation().getAttribute().getAccess();
			codeToInsert = Cst.TAB + annotation + Cst.LINE_BREAK;
			if (access.equals("")){
				codeToInsert += Cst.TAB;
			} else {
				codeToInsert += Cst.TAB + access + Cst.SPACE;
			}
			codeToInsert += type + Cst.SPACE + formerName + Cst.SEMI_COLON + Cst.LINE_BREAK;
			codeToReplace = Util.getClassNameWithoutPackage(warningAttRenamed.getOperation().getAttribute().getType()); 
			codeToReplace = codeToReplace + Cst.SPACE + warningAttRenamed.getOperation().getAttribute() + Cst.SEMI_COLON;
			message = "Add annotation " + annotation + " to explicitly remove attribute '" + formerName + "'";
			fix = new Fix(message, Cst.MARKER_FIX_TYPE_INSERT, currentName, formerName, codeToInsert, codeToReplace, "", true);
			fixes.add(fix);
			
			// Fix 3
			type = Util.getClassNameWithoutPackage(warningAttRenamed.getOperation().getAttribute().getType());
			codeToInsert = type + Cst.SPACE + formerName;
			codeToReplace = type + Cst.SPACE + currentName; 
			message = "Restore attribute '" + formerName + "'.";
			fix = new Fix(message, Cst.MARKER_FIX_TYPE_REPLACE, currentName, formerName, codeToInsert, codeToReplace, "", true);
			fixes.add(fix);
			
		} else if (warning instanceof TypeWarning){
			// Fix 1
			TypeWarning typeWarning = (TypeWarning) warning;
			type = Util.getClassNameWithoutPackage(typeWarning.getOperation().getAttribute().getType());
			String legacyType = Util.getClassNameWithoutPackage(typeWarning.getOperation().getLegacyAttribute().getType());
			codeToInsert = legacyType; 
			codeToReplace = type;
			String currentName = typeWarning.getOperation().getAttribute().getName();
			String formerName = currentName;
			message = "Restore type '" + legacyType + "'.";
			fix = new Fix(message, Cst.MARKER_FIX_TYPE_REPLACE, currentName, formerName, codeToInsert, codeToReplace, "", false);
			fixes.add(fix);
		}
		return fixes;
	}
	
}
