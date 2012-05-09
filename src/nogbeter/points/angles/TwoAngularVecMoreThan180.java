package nogbeter.points.angles;

import nogbeter.points.twod.Vec;

public class TwoAngularVecMoreThan180 implements AngularInterval {
	// given a start vec (rotated 90 degs clockwise for efficiency) and an end vec, the angular interval is
	// the clockwise angular interval between these vecs
	final Vec startNorm;
	final Vec end;
	
	public TwoAngularVecMoreThan180(Vec startNorm, Vec end) {
		this.startNorm = startNorm;
		this.end = end;
	}
	
	public boolean isInside(Vec a){
		return startNorm.dot(a) >= 0 || end.tanToAntiNormal().dot(a) >= 0;

	}
	
	public String toString(){
		return "TwoVec>180(begin: " + startNorm + "end: " + end + ")";
	}

}

