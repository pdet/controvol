package ie.ucd.pel.engine.crawler;

import ie.ucd.pel.datastructure.MAttribute;
import ie.ucd.pel.datastructure.MLocation;
import ie.ucd.pel.engine.util.Cst;
import ie.ucd.pel.engine.util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.jdt.core.dom.Modifier;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

/**
 * This visitor is used to extract the attributes of a given class. 
 * @author Thomas
 *
 */
public class ControVolASTVisitor2 extends ASTVisitor {

	protected CompilationUnit cu;

	protected Map<String, String> imports = new HashMap<String, String>();
	protected Set<MAttribute> attributes = new TreeSet<MAttribute>();
	protected String packageName = "";
	protected String currentClassName = "";

	public ControVolASTVisitor2(CompilationUnit cu, String srcFolderName){
		super();
		this.cu = cu;
	}

	public Set<MAttribute> getAttributes(){
		return this.attributes;
	}

	public boolean visit(PackageDeclaration node){
		this.packageName = node.getName().toString();
		return true;
	}

	// Visit import
	public boolean visit(ImportDeclaration node){
		String fullTypeName = node.getName().getFullyQualifiedName();
		// FIXME Dirty code...
		String typeName = fullTypeName.substring(fullTypeName.lastIndexOf(".")+1, fullTypeName.length());
		this.imports.put(typeName, fullTypeName);
		return true;
	} 

	public boolean visit(TypeDeclaration node){
		this.currentClassName = node.getName().toString();
		return true;
	}

	// Visit attributes
	public boolean visit(FieldDeclaration node) {
		String nodeStr = node.toString();
		// FIXME Dirty code...
		String[] legacyNames = {};
		if (nodeStr.startsWith(Cst.ANNOTATION_ALSOLOAD_1)){
			String legacyNamesAux = nodeStr.substring(nodeStr.indexOf("(\"")+2, nodeStr.indexOf("\")")); 
			legacyNames = legacyNamesAux.split(",");
			for (int i = 0 ; i < legacyNames.length ; i++){
				legacyNames[i] = legacyNames[i].replace(" ", "");
			}
		}
		Type typeAux = node.getType();
		String access = getAccess(node);
		int lineNumber = this.cu.getLineNumber(node.getStartPosition());
		MLocation location = new MLocation(this.packageName + "." + this.currentClassName, lineNumber);
		
		@SuppressWarnings("unchecked")
		List<VariableDeclarationFragment> fragments = node.fragments();
		for (VariableDeclarationFragment fragment : fragments){
			String name = fragment.getName().toString();
			String className = getFullName(typeAux.toString());
			MAttribute attribute = new MAttribute(name, className, location, access); 
			Boolean isDeleted = nodeStr.startsWith(Cst.ANNOTATION_IGNORE_1);
			attribute.setIsDeleted(isDeleted);
			for (int i = 0 ; i < legacyNames.length ; i++){
				attribute.addFormerName(legacyNames[i]);
			}
			this.attributes.add(attribute);
		}
		return false; 
	}
	
	private String getAccess(FieldDeclaration node){
		String access = Cst.ACCESS_PACKAGE;
		List<?> modifiers = node.modifiers();
		for (Object modifierAux : modifiers){
			if (modifierAux instanceof Modifier){
				Modifier modifier = (Modifier) modifierAux;
				if (modifier.isPublic()){
					access = Cst.ACCESS_PUBLIC;
				} else if (modifier.isProtected()){
					access = Cst.ACCESS_PROTECTED;
				} else if (modifier.isPrivate()){
					access = Cst.ACCESS_PRIVATE;
				} 
			}
		}
		return access;
	}

	private String getFullName(String typeName){
		String fullTypeName = typeName;
		if (this.imports.containsKey(typeName)){
			fullTypeName = imports.get(typeName);
		} else if ((!Util.isJavaPrimitiveType(typeName)) && (!Util.isJavaPrimitiveTypeObject(typeName))){
			fullTypeName = this.packageName + "." + typeName;
		} else {
			// FIXME The conversion made here could be confusing... (int => java.lang.Integer)
			typeName = Util.primitiveTypeToPrimitiveTypeObject(typeName);
			fullTypeName = Cst.JAVA_LANG_PACKAGE + typeName;
		}
		return fullTypeName;
	}

}
