package bezier.paths;

import nogbeter.points.twod.Vec;
import bezier.paths.simple.Line;

public final class Intersection {
	public final ApproxCurvePosition l,r;

	public Intersection(ApproxCurvePosition l, ApproxCurvePosition r) {
		this.l = l;
		this.r = r;
	}
	
	Vec getVec(){
		return l.getVec();
	}
	

}
