package bezier.paths;

import java.util.List;

import bezier.paths.leaf.Line;
import bezier.paths.util.PathParameter;
import bezier.paths.util.TPair;
import bezier.points.Vec;
import bezier.util.STuple;

public abstract class ConnectedPath extends Path{
	
	public final double tStart, tEnd;
	public final int index;
	public double length; // optional
	public double lengthStart;
	
	public ConnectedPath(int index,double tStart, double tEnd) {
		this.index = index;
		this.tStart = tStart;
		this.tEnd = tEnd;
	}
	
	public abstract int nrBelow(Vec p);
	public abstract Vec getStartPoint();
	public abstract Vec getEndPoint();

	public abstract ConnectedPath reverse();
	public abstract ConnectedPath getWithAdjustedStartPoint(Vec newStart);
	public abstract Vec getTangentAt(double t) ;
	public abstract Vec getAt(double t) ;

	public boolean isClosed() {
		return getStartPoint().isEqError(getEndPoint());
	}
	
	public Vec getAt(PathParameter t) {
		return getAt(t.t);
	}

	public Vec getTangentAt(PathParameter t) {
		return getTangentAt(t.t);
	}
	
	public PathParameter convertTBack(double t, ReportType type){
		switch(type){
		case T: return new PathParameter(index,t * (tEnd - tStart) + tStart);
		case LENGTH: return new PathParameter(index,t * (length) + lengthStart);
		}
		throw new Error(String.format("Unkown report type : %s",type));
	}
	
	protected void addDoubleResult(TPair localRes, Line other, ReportType type,List<STuple<PathParameter>> result){
		if(localRes != null){
			PathParameter lres = convertTBack(localRes.tl,type);
			PathParameter rres = other.convertTBack(localRes.tr,type);

			result.add(new STuple<PathParameter>(lres, rres));
		}
	}
}
