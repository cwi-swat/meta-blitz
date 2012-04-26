package nogbeter.paths.simple.lines;

import java.util.List;

import nogbeter.paths.BestProject;
import nogbeter.paths.ConnectedPath;
import nogbeter.paths.Path;
import nogbeter.paths.simple.SimplePath;
import nogbeter.paths.simple.SimplePathFactory;
import nogbeter.paths.simple.nonlinear.NonLinearCurve;
import nogbeter.util.BBox;
import nogbeter.util.Interval;
import bezier.points.Vec;
import bezier.util.STuple;
import bezier.util.Tuple;
import bezier.util.Util;


public abstract class ActualLine extends SimplePath {

	public ActualLine(Interval tInterval) {
		super(tInterval);
	}
	
	@Override
	public BBox makeBBox(){
		return BBox.fromPoints(getStartPoint(), getEndPoint());
	}
	public ConnectedPath getWithAdjustedStartPoint(Vec newStartPoint) {
		return SimplePathFactory.createLine(newStartPoint, getEndPoint());
	}
	
	public STuple<SimplePath> splitSimpler(){
		Vec middle = getAt(0.5);
		STuple<Interval> tintervals = tInterval.split();
		return new STuple<SimplePath>(SimplePathFactory.createLine(getStartPoint(), middle,tintervals.l),
				SimplePathFactory.createLine(middle, getEndPoint(),tintervals.r));
	}
	
	abstract double minDistSquaredTo(BBox b);
	
	@Override
	public BestProject<Tuple<Double, Double>> projectLNonLinear(
			BestProject best, NonLinearCurve lhs) {
		if(best.distSquared > minDistSquaredTo(lhs.getBBox())){
			STuple<SimplePath> sp = lhs.splitSimpler();
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
	
//	
//	public BestProject<Tuple<Double,Double>> projectLNonLinear(NonLinearCurve other, double bestDist){
//		if(minDistance(other.getBBox()) < bestDist){
//			STuple<SimplePath> s = other.splitSimpler();
//			SimplePath first;
//			SimplePath second;
//			if(minDistance(s.l.getBBox()) < minDistance(s.r.getBBox())){
//				first = s.l; second = s.r;
//			} else {
//				first = s.r; second = s.l;
//			}
//			BestProject<Tuple<Double,Double>> fb = first.project(this,bestDist);
//			BestProject<Tuple<Double,Double>> sb = second.project(this,fb.distSquared);
//			return fb.merge(sb);
//		} else {
//			return BestProject.noResult;
//		}
//	}
	
}
