package nogbeter.crossing;

import bezier.util.Tuple;
import nogbeter.paths.results.intersections.Intersection;
import nogbeter.points.angles.AngularInterval;
import nogbeter.points.angles.AngularIntervalFactory;
import nogbeter.points.twod.Vec;

public class GetCrossingType {

	static LineStateBeforeAndAfter singleLineState(Vec tanl, Vec tanr) {
		AngularInterval rinterval = AngularIntervalFactory.create180DegreesInterval(tanr);
		boolean inside = rinterval.isInside(tanl);
		return new LineStateBeforeAndAfter(!inside, inside);
	}

	 static LineStateBeforeAndAfter doubleLineStateL(Vec tanl,
			Vec tanr1, Vec tanr2) {
		AngularInterval rinterval = AngularIntervalFactory.createAngularIntervalSingleIfEq(tanr2,tanr1.negate());
		return new LineStateBeforeAndAfter(rinterval.isInside(tanl.negate()), rinterval.isInside(tanl));
	}

	static LineStateBeforeAndAfter doubleLineStateR(Vec tanl1, Vec tanl2,
			Vec tanr) {
		AngularInterval rinterval = AngularIntervalFactory.create180DegreesInterval(tanr);
		return new LineStateBeforeAndAfter(rinterval.isInside(tanl1.negate()), rinterval.isInside(tanl2));
	}

	static LineStateBeforeAndAfter quadLineState(Vec tanl1, Vec tanl2,
			Vec tanr1, Vec tanr2) {
		AngularInterval rinterval = AngularIntervalFactory.createAngularIntervalSingleIfEq(tanr2,tanr1.negate());
		return new LineStateBeforeAndAfter(rinterval.isInside(tanl1.negate()), rinterval.isInside(tanl2));
	}

	public static CrossingType singleType(Vec tanl, Vec tanr) {
		LineStateBeforeAndAfter ls = singleLineState(tanl, tanr);
		if(ls.leftBeforeInside != ls.leftAfterInside){
			return ls.leftAfterInside ? CrossingType.Enter : CrossingType.Exit;
		} else {
			return CrossingType.Touch;
		}
	}

	public static CrossingType doubleTypeL(Vec tanl, Vec tanr, Vec tanr2) {
		LineStateBeforeAndAfter ls = doubleLineStateL(tanl,tanr,tanr2);
		if(ls.leftBeforeInside != ls.leftAfterInside){
			return ls.leftAfterInside ? CrossingType.Enter : CrossingType.Exit;
		} else {
			if(ls.leftBeforeInside && ls.leftAfterInside){
				if(tanl.onSameLine(tanr) && tanl.onSameLine(tanr2)){
					if(tanl.sameDir(tanr)){
						return CrossingType.Parallel;
					} else {
						return CrossingType.AntiParallel;
					}
				} else {
					return CrossingType.Touch;
				}
			} else { // !ls.leftBeforeInside && !ls.leftAfterInside
				return CrossingType.Touch;
			}
		}
	}
	
	public static CrossingType doubleTypeR(Vec tanl, Vec tanl2, Vec tanr) {
		LineStateBeforeAndAfter ls = doubleLineStateR(tanl,tanl2,tanr);
		if(ls.leftBeforeInside != ls.leftAfterInside){
			return ls.leftAfterInside ? CrossingType.Enter : CrossingType.Exit;
		} else {
			if(ls.leftBeforeInside && ls.leftAfterInside){
				if(tanl.onSameLine(tanr) && tanl2.onSameLine(tanr)){
					if(tanl.sameDir(tanr)){
						return CrossingType.Parallel;
					} else {
						return CrossingType.AntiParallel;
					}
				} else {
					return CrossingType.Touch;
				}
			} else { // !ls.leftBeforeInside && !ls.leftAfterInside
				return CrossingType.Touch;
			}
		}
	}

	public static CrossingType quadTypeL(Vec tanl, Vec tanl2, Vec tanr,
			Vec tanr2) {
		LineStateBeforeAndAfter ls = quadLineState(tanl, tanl2, tanr, tanr2);
		if(ls.leftBeforeInside != ls.leftAfterInside){
			return ls.leftAfterInside ? CrossingType.Enter : CrossingType.Exit;
		} else {
			if(ls.leftBeforeInside && ls.leftAfterInside){
				if(tanl.onSameLine(tanr) && tanl2.onSameLine(tanr2) && 
						tanl.sameDir(tanr) && tanl2.sameDir(tanr2)){
						return CrossingType.Parallel;
				} else if(tanl2.onSameLine(tanr) && tanl2.onSameLine(tanr) && 
						tanl2.sameDir(tanr) && tanl2.sameDir(tanr)){
					return CrossingType.AntiParallel;
				} else {
					return CrossingType.Touch;
				}
			} else { // !ls.leftBeforeInside && !ls.leftAfterInside
				return CrossingType.Touch;
			}
		}
	}
}
