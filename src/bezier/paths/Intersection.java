package bezier.paths;

import bezier.paths.simple.Line;
import bezier.points.Vec;

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
