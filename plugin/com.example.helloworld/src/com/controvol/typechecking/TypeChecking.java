package com.controvol.typechecking;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;

public interface TypeChecking {

	public void checking(IResource resrc);
	public void checking(IResourceDelta resrc);

}
