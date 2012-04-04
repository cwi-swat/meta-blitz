package bezier.paths.simple;

import java.util.List;

import bezier.paths.IConnectedPath;
import bezier.paths.Path;
import bezier.paths.compound.CompoundPath;
import bezier.paths.util.PathParameter;
import bezier.paths.util.TPair;
import bezier.points.Vec;

public abstract class SimplePath extends Path implements IConnectedPath{

	public final double tStart, tEnd;
	public double length; // optional
	public double lengthStart;
	
	public SimplePath(double tStart, double tEnd) {
		this.tStart = tStart;
		this.tEnd = tEnd;
	}
	
	public abstract int nrBelow(Vec p);
	
	public PathParameter convertTBackLeaf(double t, ReportType type, PathParameter lParent){
		switch(type){
		case T: return new PathParameter(lParent.connected, lParent.t + t * (tEnd - tStart) + tStart);
		case LENGTH: return new PathParameter(lParent.connected, lParent.t * (length) + lengthStart);
		}
		throw new Error(String.format("Unkown report type : %s",type));
	}
	
	protected void addDoubleResult(TPair localRes, Line other, ReportType type, 
			PathParameter lParent, PathParameter rParent, List<PathParameter> lres,List<PathParameter> rres){
		if(localRes != null){
			PathParameter llres = convertTBackLeaf(localRes.tl,type,lParent);
			PathParameter lrres = other.convertTBackLeaf(localRes.tr,type,rParent);

			lres.add(llres);
			rres.add(lrres);
		}
	}
	
	public boolean isClosed(){
		return getStartPoint().isEqError(getEndPoint());
	}
	


	@Override
	public boolean isSimple() {
		return true;
	}
	
	public boolean isConnected(){
		return true;
	}
	
	public IConnectedPath getConnected(){
		return this;
	}

	@Override
	public SimplePath getSimple() {
		return this;
	}

	@Override
	public CompoundPath getCompound() {
		throw new Error("Not compund!");
	}

	@Override
	public Vec getAt(PathParameter t) {
		return getAt(t.t);
	}

	@Override
	public Vec getTangentAt(PathParameter t) {
		return getTangentAt(t.t);
	}

	 public abstract int currentSegmentAWT(float[] coords);
}
