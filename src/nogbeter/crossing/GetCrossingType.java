package nogbeter.crossing;

import nogbeter.paths.results.intersections.Intersection;
import nogbeter.points.twod.Vec;

public class GetCrossingType {

	static CrossingType singleIntersectionType(Vec tanl, Vec tanr) {
		return singleIntersectionType(tanl.dot(tanr.tanToNormal()));
	}
	
	static CrossingType singleIntersectionType(double tanldotnormr) {
		double sign =Math.signum(tanldotnormr);
		if(sign > 0){
			return CrossingType.Enter;
		} else if(sign < 0){
			return CrossingType.Exit;
		} else{
			return CrossingType.Follow;
		}
	}
	
	static CrossingType doubleIntersectionCrossingType(Vec tanl, Vec tanr1, Vec tanr2) {
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
	
	static CrossingType quadIntersectionCrossingType(Vec tanl1, Vec tanl2, Vec tanr1, Vec tanr2) {
		CrossingType c1 = doubleIntersectionCrossingType(tanl1,tanr1,tanr2);
		CrossingType c2 = doubleIntersectionCrossingType(tanl2,tanr1,tanr2);
		
		if(c1 == CrossingType.Follow || c2 == CrossingType.Follow){
			return CrossingType.Follow;
		} else if(c1 == c2){
			return c1;
		} else {
			return null; // touches
		}
	}
	
}
