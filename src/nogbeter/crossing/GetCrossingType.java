package nogbeter.crossing;

import bezier.util.Tuple;
import nogbeter.paths.results.intersections.Intersection;
import nogbeter.points.twod.Vec;

public class GetCrossingType {

	
	static CrossType singleIntersectionType(Vec tanl, Vec tanr) {
		double sign =Math.signum(tanl.dot(tanr.tanToNormal()));
		if(sign < 0){
			return CrossType.Enter;
		} else if(sign > 0){
			return CrossType.Exit;
		} else{
			return CrossType.Parallel;
		} 
	}
	
	static CrossType doubleIntersectionTypeL(Vec tanl, Vec tanr1, Vec tanr2) {
		return doubleIntersectionType(tanl, tanr1, tanr2);
	}
	
	static CrossType doubleIntersectionTypeR(Vec tanl1, Vec tanl2, Vec tanr) {
		return doubleIntersectionType(tanr, tanl1, tanl2).flip();
	}
	
	static CrossType doubleIntersectionType(Vec tanl, Vec tanr1, Vec tanr2) {
		CrossType c2 = singleIntersectionType(tanl,tanr2);
		if(c2 == CrossType.Parallel){
			return c2;
		}
		CrossType c1 = singleIntersectionType(tanl,tanr1);
		if(c1 == CrossType.Parallel){
			return c2;
		}
		if(c1 == c2){
			return c1;
		} else {
			return null; // touch
		}
	}
	
	static  CrossType quadIntersectionType(Vec tanl1, Vec tanl2, Vec tanr1, Vec tanr2) {
		CrossType c2 = doubleIntersectionType(tanl2,tanr1,tanr2);
		if(c2 == CrossType.Parallel){
			return c2;
		}
		CrossType c1 = doubleIntersectionType(tanl1,tanr1,tanr2);
		if(c1 == CrossType.Parallel){
			return c2;
		} else if(c1 == c2){
			return c1;
		} else {
			return null;
		}
	}

	public static CrossType quadIntersectionType(Vec tanl, Vec tanl2,
			Vec tanl3, Vec tanl4, Vec tanr, Vec tanr2, Vec tanr3, Vec tanr4) {
		Tuple<Vec,Vec> tanltup = getDifferent(tanl,tanl2,tanl3,tanl4);
		Tuple<Vec,Vec> tanrtup = getDifferent(tanr,tanl2,tanr3,tanr4);
		return quadIntersectionType(tanltup.l, tanltup.r, tanrtup.l, tanrtup.r);
	}

	private static Tuple<Vec, Vec> getDifferent(Vec tanl, Vec tanl2, Vec tanl3,
			Vec tanl4) {
		if(tanl.sameDir(tanl2)){
			return new Tuple<Vec, Vec>(tanl, tanl3);
		} else {
			return new Tuple<Vec, Vec>(tanl, tanl2);
		}
	}
	
}
