package nogbeter.crossing;

import bezier.util.Tuple;
import nogbeter.paths.results.intersections.Intersection;
import nogbeter.points.angles.AngularInterval;
import nogbeter.points.angles.AngularIntervalFactory;
import nogbeter.points.twod.Vec;

public class GetCrossingType {

	public static LineStateBeforeAndAfter singleCrossingType(Vec tanl, Vec tanr) {
		AngularInterval rinterval = AngularIntervalFactory.create180DegreesInterval(tanr);
		boolean inside = rinterval.isInside(tanl);
		return new LineStateBeforeAndAfter(!inside, inside);
	}

	public static LineStateBeforeAndAfter doubleIntersectionTypeL(Vec tanl,
			Vec tanr1, Vec tanr2) {
		AngularInterval rinterval = AngularIntervalFactory.createAngularIntervalSingleIfEq(tanr2,tanr1.negate());
		return new LineStateBeforeAndAfter(rinterval.isInside(tanl.negate()), rinterval.isInside(tanl));
	}

	public static LineStateBeforeAndAfter doubleIntersectionTypeR(Vec tanl1, Vec tanl2,
			Vec tanr) {
		AngularInterval rinterval = AngularIntervalFactory.create180DegreesInterval(tanr);
		return new LineStateBeforeAndAfter(rinterval.isInside(tanl1.negate()), rinterval.isInside(tanl2));
	}

	public static LineStateBeforeAndAfter quadIntersectionType(Vec tanl1, Vec tanl2,
			Vec tanr1, Vec tanr2) {
		AngularInterval rinterval = AngularIntervalFactory.createAngularIntervalSingleIfEq(tanr2,tanr1.negate());
		return new LineStateBeforeAndAfter(rinterval.isInside(tanl1.negate()), rinterval.isInside(tanl2));
	}
}
