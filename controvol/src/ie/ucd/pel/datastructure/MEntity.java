package ie.ucd.pel.datastructure;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

public class MEntity implements Comparable<MEntity> {

	protected String entityName;
	protected Set<MAttribute> attributes = new TreeSet<MAttribute>();
	protected String superEntityName;
	protected String location = "";
	protected Boolean isEntity = true; // Used to deal with inheritance

	public MEntity(String entityName, String location){
		this.entityName = entityName;
		this.location = location;
		if (!this.location.endsWith(File.separator)){
			this.location += File.separator;
		}
	}

	public MEntity(String entityName, String location, String superEntityName){
		this.entityName = entityName;
		this.location = location;
		this.superEntityName = superEntityName;
		if (!this.location.endsWith(File.separator)){
			this.location += File.separator;
		}
	}

	public String getEntityName(){
		return this.entityName;
	}

	public String getSuperEntityName(){
		return this.superEntityName;
	}

	public void setSuperEntity(String superEntityName) {
		this.superEntityName = superEntityName;
	}
	
	public String getLocation(){
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public Set<MAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<MAttribute> attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(MAttribute attribute){
		this.attributes.add(attribute);
	}
	
	public void addAttributes(Set<MAttribute> attributes){
		this.attributes.addAll(attributes);
	}
	
	public Boolean isEntity(){
		return this.isEntity;
	}
	
	public void setIsEntity(Boolean isEntity){
		this.isEntity = isEntity;
	}
	
	public Boolean containsAttribute(String attName){
		return (getAttribute(attName) != null);
	}

	public MAttribute getAttribute(String attName){
		MAttribute attDesc = null;
		for (MAttribute attDescAux : this.getAttributes()){
			if (attDescAux.getName().equals(attName)){
				attDesc = attDescAux;
			}
			for (String formerName : attDescAux.getFormerNames()){
				if (formerName.equals(attName)){
					attDesc = attDescAux;
				}
			}
		}
		return attDesc;
	}

	@Override
	public int compareTo(MEntity o) {
		return this.getEntityName().compareTo(o.getEntityName());
	}

	public String toString(){
		String str = this.getEntityName();
		for (MAttribute attDesc : this.getAttributes()){
			str += "\n\t"+attDesc.toString();
		}
		return str;
	}

}
