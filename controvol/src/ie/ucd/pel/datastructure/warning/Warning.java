package ie.ucd.pel.datastructure.warning;

import ie.ucd.pel.datastructure.evolution.Operation;

import java.io.File;

public abstract class Warning<O extends Operation> implements IWarning {
	
	protected O op;
	
	public Warning(O op){
		this.op = op;
	}
	
	@Override
	public O getOperation() {
		return op;
	}

	public String getEntityLocation() {
		String location =  op.getEntity().getLocation();
		String name = op.getEntity().getEntityName();
		name = name.replaceAll("\\.", File.separator);
		return location + name + ".java";
	}

	@Override
	public String getLegacyVersion() {
		return op.getVersion1();
	}

	@Override
	public String getCurrentVersion() {
		return op.getVersion2();
	}

	public abstract int getSeverity();
	
	@Override
	public int compareTo(IWarning o) {
		int res = 0;
		if (o instanceof Warning){
			Warning<?> oAux = (Warning<?>) o;
			res = this.op.compareTo(oAux.op);
		}
		
		return res;
	}
	
	public Boolean isRenamingOperation(){
		return false;
	}

}
