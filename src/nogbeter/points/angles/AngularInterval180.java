package nogbeter.points.angles;

import nogbeter.points.twod.Vec;

class AngularInterval180 implements AngularInterval {

	// Given a dir that is the start of the interval and the vec
	// rotated 180 degs clockwise is the end
	
	final Vec dirNorm;
	
	AngularInterval180(Vec dirNorm) {
		this.dirNorm = dirNorm;
	}
	
	@Override
	public boolean isInside(Vec a) {
		return dirNorm.dot(a) >= 0;
	}

}
