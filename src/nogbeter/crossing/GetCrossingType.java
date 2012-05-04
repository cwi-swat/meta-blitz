package nogbeter.crossing;

import bezier.util.Tuple;
import nogbeter.paths.results.intersections.Intersection;
import nogbeter.points.twod.Vec;

public class GetCrossingType {

	static CrossingType singleIntersectionType(Vec tanl, Vec tanr) {
		return singleIntersectionType(tanl.dot(tanr.tanToNormal()));
	}
	
	static CrossingType singleIntersectionType(double tanldotnormr) {
		double sign =Math.signum(tanldotnormr);
		if(sign < 0){
			return CrossingType.Enter;
		} else if(sign > 0){
			return CrossingType.Exit;
		} else{
			return CrossingType.Follow;
		}
	}
	
	static CrossingType doubleIntersectionType(Vec tanl1, Vec tanl2, Vec tanr1, Vec tanr2) {
		if(tanl1.sameDir(tanl2)){
			return doubleIntersectionType(tanl1, tanr1, tanr2);
		} else {
			return doubleIntersectionType(tanr1, tanl1, tanl2).flip();
		}
	}
	
	static CrossingType doubleIntersectionType(Vec tanl, Vec tanr1, Vec tanr2) {
		CrossingType c1 = singleIntersectionType(tanl,tanr1);
		CrossingType c2 = singleIntersectionType(tanl,tanr2);
		
		if(c1 == CrossingType.Follow || c2 == CrossingType.Follow){
			return CrossingType.Follow;
		} else if(c1 == c2){
			return c1;
		} else {
			return null; // touches
		}
	}
	
	static CrossingType quadIntersectionType(Vec tanl1, Vec tanl2, Vec tanr1, Vec tanr2) {
		CrossingType c1 = doubleIntersectionType(tanl1,tanr1,tanr2);
		CrossingType c2 = doubleIntersectionType(tanl2,tanr1,tanr2);
		
		if(c1 == CrossingType.Follow || c2 == CrossingType.Follow){
			return CrossingType.Follow;
		} else if(c1 == c2){
			return c1;
		} else {
			return null; // touches
		}
	}

	public static CrossingType quadIntersectionType(Vec tanl, Vec tanl2,
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
