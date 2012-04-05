package bezier.paths.compound;

import bezier.paths.Path;
import bezier.paths.simple.SimplePath;
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
	
	public abstract boolean isInside(Vec p);
	
}
