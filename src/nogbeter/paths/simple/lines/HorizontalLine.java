package nogbeter.paths.simple.lines;

import java.util.List;

import nogbeter.paths.BestProject;
import nogbeter.paths.Path;
import nogbeter.paths.simple.nonlinear.NonLinearCurve;
import nogbeter.util.BBox;
import nogbeter.util.Interval;
import bezier.points.Vec;
import bezier.util.STuple;
import bezier.util.Tuple;
import bezier.util.Util;

import static bezier.util.Util.*;
import static java.lang.Math.min;
import static nogbeter.util.Interval.emptyInterval;

public abstract class HorizontalLine extends ActualLine {

	public final Interval xInterval;
	public final double y;

	public HorizontalLine(Interval xInterval, double y, Interval tinterval) {
		super(tinterval);
		this.xInterval = xInterval;
		this.y = y;
	}

	abstract double getTForX(double x);
	
	@Override
	public <OPathParam> Tuple<List<Double>, List<OPathParam>> intersection(
			Path<OPathParam> other) {
		return other.intersectionLHorLine(this);
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLDiaLine(
			DiagonalLine lhs) {
		return lhs.intersectionLHorLine(this).flip();
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLHorLine(
			HorizontalLine lhs) {
		return Util.emptyTupleList;
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLVerLine(
			VerticalLine lhs) {
		if(xInterval.isInside(lhs.x) && lhs.yInterval.isInside(y)){
			return makeIntersectionResult(lhs,lhs.getTForY(y), getTForX(lhs.x));
		} else {
			return Util.emptyTupleList;
		}
	}
	

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLNonLinear(
			NonLinearCurve lhs) {
		if(lhs.getBBox().yInterval.isInside(y) 
			&& lhs.getBBox().xInterval.overlapsWith(xInterval)){
			double lt = lhs.findTForY(y);
			if(Interval.interval01.isInside(lt)){
				double rt = getTForX(lhs.getAt(lt).x);
				if(Interval.interval01.isInside(rt)){
					return makeIntersectionResult(lhs,lt,rt);
				}
			}
		} 
		return Util.emptyTupleList;
	}
	
	@Override
	public BestProject<Double> project(BestProject best, Vec p) {
		double x = xInterval.getClosestPoint(p.x);
		double dist = new Vec(x,y).distanceSquared(p);
		return best.choose(new BestProject<Double>(dist,
				tInterval.getAtFactor(getTForX(x))));
	}
	
	@Override
	public <OPathParam> BestProject<Tuple<Double, OPathParam>> project(
			BestProject best, Path<OPathParam> other) {
		return other.projectLHorLine(best, this);
	}

	@Override
	public BestProject<Tuple<Double, Double>> projectLDiaLine(BestProject best,
			DiagonalLine lhs) {
		return lhs.projectLHorLine(best, this).flip();
	}

	@Override
	public BestProject<Tuple<Double, Double>> projectLHorLine(BestProject best,
			HorizontalLine lhs) {
		STuple<Double> closestX = lhs.xInterval.closestPoints(xInterval);
		double xDist = square(closestX.l - closestX.r);
		double yDist = square(y - lhs.y);
		return best.choose(
				makeBestProject(xDist + yDist, lhs,
						lhs.getTForX(closestX.l), getTForX(closestX.r)));
	}

	@Override
	public BestProject<Tuple<Double, Double>> projectLVerLine(BestProject best,
			VerticalLine lhs) {
		double tl = clamp(lhs.getTForY(y)); double tr = clamp(getTForX(lhs.x));
		Vec l = lhs.getAt(tl); Vec r = getAt(tr);
			return best.choose(
					makeBestProject(l.distanceSquared(r),lhs ,tl,tr));
	}



	@Override
	double distanceSquared(Vec v) {
		double xDist = square(xInterval.minDistance(v.x));
		double yDist = square(v.y - y);
		return xDist + yDist;
	}
	
	double minDistSquaredTo(BBox b){
		double xDist = xInterval.minDistance(b.xInterval);
		double yDist = b.yInterval.minDistance(y);
		return xDist*xDist + yDist*yDist;
	}


}
