package bezier.paths.simple;

import java.util.List;

import bezier.paths.ConnectedPath;
import bezier.paths.Path;
import bezier.paths.compound.ICompoundPath;
import bezier.paths.util.PathParameter;
import bezier.paths.util.TPair;
import bezier.points.Vec;

public abstract class SimplePath extends ConnectedPath{

	public final double tStart, tEnd;
	public double length; // optional
	public double lengthStart;
	
	public SimplePath(double tStart, double tEnd) {
		this.tStart = tStart;
		this.tEnd = tEnd;
	}
	

	public PathParameter convertTBack(double t, PathParameter lParent){
		return new PathParameter(lParent.connected, lParent.t + t * (tEnd - tStart) + tStart);
	}
	
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
	
	@Override
	public boolean isSimple() {
		return true;
	}
	
	public boolean isConnected(){
		return true;
	}

	@Override
	public SimplePath getSimple() {
		return this;
	}

	@Override
	public ICompoundPath getCompound() {
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
	 
	@Override
	public Path getSubPath(PathParameter start, PathParameter end) {
		return getSubPath(start.t, end.t);
	}
	
	public Path getPath(){
		return this;
	}
}
