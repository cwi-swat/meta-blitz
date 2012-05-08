package nogbeter.points.angles;

import nogbeter.points.twod.Vec;

public class AngularIntervalFactory {
	
	public static AngularInterval create180DegreesInterval(Vec tan){
		return new AngularInterval180(tan.tanToNormal());
	}
	
	// if the dir of start is the same is the dir of end, returns the interval consisting of only this vec
	public static AngularInterval createAngularIntervalSingleIfEq(Vec start, Vec end){
		Vec startNorm = start.tanToNormal();
		if(startNorm.dot(end) == 0){
			if(start.sameDir(end)){
				return new SingleVecAngularInterval(start,startNorm);
			} else {
				return new AngularInterval180(startNorm);
			}
		} else if(startNorm.dot(end) >= 0){
			return new TwoVecAngularIntervalLessThan180(startNorm, end);
		} else {
			return new TwoAngularVecMoreThan180(startNorm, end);
		}
	}
	
	// if the dir of start is the same is the dir of end, returns the full interval
	public static AngularInterval createAngularIntervalFullIfEq(Vec start, Vec end){
		return new TwoVecAngularIntervalLessThan180(start.tanToNormal(), end);
	}
	
}
