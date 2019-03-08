package com.controvol.markers;

import ie.ucd.pel.datastructure.warning.AttributeDeleted;
import ie.ucd.pel.datastructure.warning.AttributeImproperlyRenamed;
import ie.ucd.pel.datastructure.warning.IWarning;
import ie.ucd.pel.datastructure.warning.TypeWarning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.controvol.quickfix.Fix;
import com.controvol.quickfix.FixFactory;
import com.controvol.util.Cst;

public class MarkerFactory {
	
	static IWarning warning;
	//private static List<IMarker> markers;
	
	public static IMarker createWarning(IWarning warning, IProject project) {
		IMarker marker = null;
		IResource res = project.getFile(warning.getEntityLocation());
		Integer lineNb = warning.getLocation().getLineNumber();
		int severity = warning.getSeverity();
		String description = "";		
		
		JSONArray jsonArray = new JSONArray();
		
		try {
			marker = res.createMarker(Cst.MARKER_ID);
			marker.setAttribute(IMarker.LOCATION, "line " + lineNb);
			marker.setAttribute(IMarker.SEVERITY, severity);
			marker.setAttribute(IMarker.PRIORITY, severity); // priority = severity	
			marker.setAttribute(IMarker.LINE_NUMBER, lineNb);
			List<Fix> fixes = new ArrayList<Fix>();
			if (warning instanceof TypeWarning){
				TypeWarning warningRetype = (TypeWarning) warning;
				String type = warningRetype.getOperation().getAttribute().getType();
				String legacyType = warningRetype.getOperation().getLegacyAttribute().getType();
				description = "Type '" + type + "' is not compatible with '" + legacyType + "'";
				fixes = FixFactory.getCodeFixes(warningRetype);
			} else 	if (warning instanceof AttributeImproperlyRenamed){
				AttributeImproperlyRenamed warningRename = (AttributeImproperlyRenamed) warning;
				String formerName = warningRename.getOperation().getLegacyAttribute().getName();
				description = "Attribute '" + formerName + "' will be lost when loading legacy entities";
				fixes = FixFactory.getCodeFixes(warningRename);				
			} else if (warning instanceof AttributeDeleted){
				AttributeDeleted warningAttDel = (AttributeDeleted) warning;
				String name = warningAttDel.getOperation().getAttribute().getName();
				description = "Attribute '" + name + "' will be lost when loading legacy entities";
				fixes = FixFactory.getCodeFixes(warningAttDel);
			} 
			for (Fix fix : fixes){
				try {
					JSONObject jsonMarker = new JSONObject();
					jsonMarker.put(Cst.MARKER_FIX_TYPE, fix.getType());
					jsonMarker.put(Cst.MARKER_NEW_NAME_ID, fix.getNewName());
					jsonMarker.put(Cst.MARKER_OLD_NAME_ID, fix.getOldName());
					jsonMarker.put(Cst.MARKER_DESCRIPTION, fix.getMessage());
					jsonMarker.put(Cst.MARKER_INSERT_CODE_FIX, fix.getCodeToInsert());
					jsonMarker.put(Cst.MARKER_REPLACE_CODE_FIX, fix.getCodeToReplace());
					jsonMarker.put(Cst.MARKER_IS_RENAMING, fix.isRenamingOperation());
					jsonArray.put(jsonMarker);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}	
			marker.setAttribute(IMarker.MESSAGE, description);
			marker.setAttribute(Cst.MARKER_JSON_FIXES, jsonArray.toString());
			
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return marker;
	}

	
	/*
	 * returns a list of a resources markers
	 */
	public static List<IMarker> findMarkers(IResource resource) {
		try {
			return Arrays.asList(resource.findMarkers(Cst.MARKER_ID, true, IResource.DEPTH_ZERO));
		} catch (CoreException e) {
			return new ArrayList<IMarker>();
		}
	}
}
