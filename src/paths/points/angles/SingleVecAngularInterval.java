package paths.points.angles;

import paths.points.twod.Vec;

public class SingleVecAngularInterval implements AngularInterval{

	final Vec wichNorm;
	final Vec wich;
	public SingleVecAngularInterval(Vec wich,Vec whichNorm) {
		this.wich = wich;
		this.wichNorm = whichNorm;
	}

	@Override
	public boolean isInside(Vec a) {
		return wichNorm.dot(a) == 0 && (wich.sameDir(a) || a.isEq(Vec.ZeroVec));
	}
	
	public String toString(){
		return "SingleVec(which: " + wich + ")";
	}

}
