package bezier.paths;

import nogbeter.points.twod.Vec;
import bezier.paths.simple.SimplePath;
import bezier.paths.util.PathParameter;

public class ApproxCurvePosition {
	public final SimplePath approx;
	public final double t;
	public final PathParameter actual;
	public ApproxCurvePosition(SimplePath approx, double t, PathParameter actual) {
		this.approx = approx;
		this.t = t;
		this.actual = actual;
	}
	
	PathParameter translateBack(){
		return approx.getSimple().convertTBack(t, actual);
	}
	
	Vec getVec(){
		if(t == 0){
			return approx.getStartPoint();
		} else if(t == 1){
			return approx.getEndPoint();
		} else {
			return approx.getAt(t);
		}
	}

	public Vec getTan() {
		return approx.getTangentAt(t);
	}
}
