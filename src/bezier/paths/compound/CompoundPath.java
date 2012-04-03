package bezier.paths.compound;

import java.util.ArrayList;
import java.util.List;

import bezier.paths.Path;
import bezier.paths.Path.ReportType;
import bezier.paths.simple.SimplePath;
import bezier.paths.util.PathParameter;
import bezier.points.Vec;

public abstract class CompoundPath extends Path {


	@Override
	public boolean isSimple() {
		return false;
	}

	@Override
	public SimplePath getSimple() {
		throw new Error("Not simple!");
	}
	
	@Override
	public CompoundPath getCompound() {
		return this;
	}


	public void convertBackCompounds(List<PathParameter> lps){
		for(int i = 0 ; i < lps.size() ; i++){
			lps.set(i,convertBackCompound(lps.get(i)));
		}
	}
	
	public abstract PathParameter convertBackCompound(PathParameter p);
	
	public abstract boolean isCompoundLeaf();
	public abstract boolean isInside(Vec p);
	
}
