package nogbeter.paths.simple.lines;

import static bezier.util.Util.clamp;
import static bezier.util.Util.square;

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

public abstract class VerticalLine extends ActualLine {


	public final Interval yInterval;
	public final double x;

	public VerticalLine(double x, Interval yInterval,  Interval tinterval) {
		super(tinterval);
		this.yInterval = yInterval;
		this.x = x;
	}

	abstract double getTForY(double y);
	
	@Override
	public <OPathParam> Tuple<List<Double>, List<OPathParam>> intersection(
			Path<OPathParam> other) {
		return other.intersectionLVerLine(this);
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLDiaLine(
			DiagonalLine lhs) {
		return lhs.intersectionLVerLine(this).flip();
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLHorLine(
			HorizontalLine lhs) {
		if(yInterval.isInside(lhs.y) && lhs.xInterval.isInside(x)){
			return makeIntersectionResult(lhs,lhs.getTForX(x), getTForY(lhs.y));
		} else {
			return Util.emptyTupleList;
		}
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLVerLine(
			VerticalLine lhs) {
		return Util.emptyTupleList;
	}
	
	@Override
	public Tuple<List<Double>, List<Double>> intersectionLNonLinear(
			NonLinearCurve lhs) {
		if(lhs.getBBox().xInterval.isInside(x) 
			&& lhs.getBBox().yInterval.overlapsWith(yInterval)){
			double lt = lhs.findTForX(x);
			if(Interval.interval01.isInside(lt)){
				double rt = getTForY(lhs.getAt(lt).y);
				if(Interval.interval01.isInside(rt)){
					return makeIntersectionResult(lhs, lt, rt);
				}
			}
		} 
		return Util.emptyTupleList;
	}

	@Override
	public BestProject<Double> project(BestProject best, Vec p) {
		double y = yInterval.getClosestPoint(p.y);
		double dist = new Vec(x,y).distanceSquared(p);
		return best.choose(new BestProject<Double>(dist,
				tInterval.getAtFactor(getTForY(y))));
	}
	
	@Override
	public <OPathParam> BestProject<Tuple<Double, OPathParam>> project(
			BestProject best, Path<OPathParam> other) {
		return other.projectLVerLine(best, this);
	}

	@Override
	public BestProject<Tuple<Double, Double>> projectLDiaLine(BestProject best,
			DiagonalLine lhs) {
		return lhs.projectLVerLine(best, this).flip();
	}

	@Override
	public BestProject<Tuple<Double, Double>> projectLHorLine(BestProject best,
			HorizontalLine lhs) {
		return lhs.projectLVerLine(best, this).flip();
	}

	@Override
	public BestProject<Tuple<Double, Double>> projectLVerLine(BestProject best,
			VerticalLine lhs) {
		STuple<Double> closestY = lhs.yInterval.closestPoints(yInterval);
		double xDist = square(closestY.l - closestY.r);
		double yDist = square(x - lhs.x);
		return best.choose(
				makeBestProject(xDist + yDist, lhs,
						lhs.getTForY(closestY.l), getTForY(closestY.r)));
	}



	@Override
	double distanceSquared(Vec v) {
		double xDist = square(v.x - x);
		double yDist = square(yInterval.minDistance(v.y));
		return xDist + yDist;
	}
	
	double minDistSquaredTo(BBox b){
		double xDist = b.xInterval.minDistance(x);
		double yDist = yInterval.minDistance(b.yInterval);
		return xDist*xDist + yDist*yDist;
	}
}
