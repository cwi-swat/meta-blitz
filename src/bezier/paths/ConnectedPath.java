package bezier.paths;

import java.util.List;

import bezier.paths.util.PathParameter;
import bezier.paths.util.TPair;
import bezier.points.Vec;
import bezier.util.STuple;

public abstract class ConnectedPath extends Path{
	
	final double tStart, tEnd;
	final int index;
	double length, lengthStart; // optional
	
	public ConnectedPath(int index,double tStart, double tEnd) {
		this.index = index;
		this.tStart = tStart;
		this.tEnd = tEnd;
	}
	
	abstract int nrBelow(Vec p);
	abstract Vec getStartPoint();
	abstract Vec getEndPoint();

	abstract ConnectedPath reverse();
	abstract ConnectedPath getWithAdjustedStartPoint(Vec newStart);
	abstract Vec getTangentAt(double t) ;
	abstract Vec getAt(double t) ;

	boolean isClosed() {
		return getStartPoint().isEqError(getEndPoint());
	}
	
	Vec getAt(PathParameter t) {
		return getAt(t.t);
	}

	

	Vec getTangentAt(PathParameter t) {
		return getTangentAt(t.t);
	}
	
	

	PathParameter convertTBack(double t){
		return new PathParameter(index,t * (tEnd - tStart) + tStart);
	}
	
	void addDoubleResult(TPair localRes, Line other, List<STuple<PathParameter>> result){
		if(localRes != null){
			PathParameter lres = convertTBack(localRes.tl);
			PathParameter rres = other.convertTBack(localRes.tr);

			result.add(new STuple<PathParameter>(lres, rres));
		}
	}
}
