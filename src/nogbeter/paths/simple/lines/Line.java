package nogbeter.paths.simple.lines;

import java.util.List;

import nogbeter.paths.BestProjectTup;
import nogbeter.paths.ConnectedPath;
import nogbeter.paths.Path;
import nogbeter.paths.PathFactory;
import nogbeter.paths.SplittablePath;
import nogbeter.paths.simple.SimplePath;
import nogbeter.paths.simple.nonlinear.Curve;
import nogbeter.util.BBox;
import nogbeter.util.Interval;
import bezier.points.Vec;
import bezier.util.STuple;
import bezier.util.Tuple;
import bezier.util.Util;


public abstract class Line extends SimplePath {

	public Line(Interval tInterval) {
		super(tInterval);
	}
	
	@Override
	public BBox makeBBox(){
		return BBox.fromPoints(getStartPoint(), getEndPoint());
	}
	public ConnectedPath getWithAdjustedStartPoint(Vec newStartPoint) {
		return PathFactory.createLine(newStartPoint, getEndPoint());
	}
	
	abstract double minDistSquaredTo(BBox b);
	

	public <OPathParam> Tuple<List<OPathParam>, List<Double>> intersectionLSplittable(
			SplittablePath<OPathParam> lhs){
		if(overlaps(lhs.getBBox())){
			STuple<Path<OPathParam>> sp = lhs.splitSimpler();
			return Util.appendTupList(
					sp.l.intersection(this),
					sp.r.intersection(this));
		} else {
			return Util.emptyTupleList;
		}
	}
	
	@Override
	public <OPathParam> BestProjectTup<OPathParam, Double> projectLSplittable(
			BestProjectTup<OPathParam, Double> best,
			SplittablePath<OPathParam> lhs) {
		if(best.distSquared > minDistSquaredTo(lhs.getBBox())){
			STuple<Path<OPathParam>> sp = lhs.splitSimpler();
			if(distanceSquared(sp.l.getBBox().getMiddle()) <
					distanceSquared(sp.r.getBBox().getMiddle())){
				best = sp.l.project(best, this);
				return sp.r.project(best, this);
			} else {
				best = sp.r.project(best, this);
				return sp.l.project(best, this);
			}
		} else {
			return best;
		}
	}

	abstract double distanceSquared(Vec v) ;
	
	abstract boolean overlaps(BBox b) ;
}
