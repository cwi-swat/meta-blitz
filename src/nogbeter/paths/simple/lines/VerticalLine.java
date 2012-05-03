package nogbeter.paths.simple.lines;

import static bezier.util.Util.square;


import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.SplittablePath;
import nogbeter.paths.compound.SetIndex;
import nogbeter.paths.compound.ShapeSet;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.intersections.Intersections;
import nogbeter.paths.results.project.BestProject;
import nogbeter.paths.results.project.BestProjectTup;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.paths.simple.nonlinear.Curve;
import nogbeter.points.oned.Interval;
import nogbeter.points.twod.BBox;
import nogbeter.points.twod.Vec;
import bezier.util.STuple;
import bezier.util.Util;

public abstract class VerticalLine extends Line {


	public final Interval yInterval;
	public final double x;

	public VerticalLine(double x, Interval yInterval,  Interval tinterval) {
		super(tinterval);
		this.yInterval = yInterval;
		this.x = x;
	}

	abstract double getTForY(double y);
	
	@Override
	public <RPP extends PathIndex> 
		IIntersections<SimplePathIndex, RPP> intersection(
			Path<RPP> other) {
		return other.intersectionLVerLine(this);
	}

	@Override
	public IIntersections<SimplePathIndex, SimplePathIndex> intersectionLDiaLine(
			DiagonalLine lhs) {
		return lhs.intersectionLVerLine(this).flip();
	}

	@Override
	public IIntersections<SimplePathIndex, SimplePathIndex> intersectionLHorLine(
			HorizontalLine lhs) {
		return lhs.intersectionLVerLine(this).flip();
	}

	@Override
	public IIntersections<SimplePathIndex, SimplePathIndex> intersectionLVerLine(
			VerticalLine lhs) {
		if(x == lhs.x){
			Interval r = yInterval.intersection(lhs.yInterval);
			if(r != null){
				if(r.low == r.high){
					return makeIntersectionResult(lhs, lhs.getTForY(r.low), getTForY(r.low));
				} else {
					return makeIntersectionResult(lhs, lhs.getTForY(r.low), getTForY(r.low))
							.append(
							makeIntersectionResult(lhs, lhs.getTForY(r.high), getTForY(r.high)));
				}
			}
		}
		return Intersections.NoIntersections;
	}
	
	
	@Override
	public IIntersections<SetIndex, SimplePathIndex> intersectionLSet(
			ShapeSet lhs) {
		return lhs.intersectionLVerLine(this).flip();
	}
	
	@Override
	public IIntersections<SimplePathIndex, SimplePathIndex> intersectionLCurve(
			Curve lhs) {
		if(overlaps(lhs.getBBox())){
			double lt = lhs.findTForX(x);
			if(Interval.interval01.isInside(lt)){
				double rt = getTForY(lhs.getAtLocal(lt).y);
				if(Interval.interval01.isInside(rt)){
					return makeIntersectionResult(lhs, lt, rt);
				}
			}
		} 
		return Intersections.NoIntersections;
	}
	
	@Override
	public <LPP extends PathIndex> 
		IIntersections<LPP, SimplePathIndex> intersectionLSplittable(
			SplittablePath<LPP> lhs) {
		return lhs.intersectionLVerLine(this).flip();
	}


	public boolean overlaps(BBox b) {
		return b.xInterval.isInside(x) 
			&& b.yInterval.overlapsWith(yInterval);
	}

	@Override
	public BestProject<SimplePathIndex> project(double best, Vec p) {
		double y = yInterval.getClosestPoint(p.y);
		double dist = new Vec(x,y).distanceSquared(p);
		return new BestProject<SimplePathIndex>(dist,
				makeGlobalPathIndexFromLocal(getTForY(y)));
	}
	
	@Override
	public <RPP extends PathIndex> 
			BestProjectTup<SimplePathIndex, RPP>  project(
			double best, Path<RPP> other) {
		return other.projectLVerLine(best, this);
	}

	@Override
	public BestProjectTup<SimplePathIndex, SimplePathIndex> projectLDiaLine(double best,
			DiagonalLine lhs) {
		return lhs.projectLVerLine(best, this).flip();
	}

	@Override
	public BestProjectTup<SimplePathIndex, SimplePathIndex> projectLHorLine(double best,
			HorizontalLine lhs) {
		return lhs.projectLVerLine(best, this).flip();
	}

	@Override
	public BestProjectTup<SimplePathIndex, SimplePathIndex> projectLVerLine(double best,
			VerticalLine lhs) {
		STuple<Double> closestY = lhs.yInterval.closestPoints(yInterval);
		double xDist = square(closestY.l - closestY.r);
		double yDist = square(x - lhs.x);
		return makeBestProject(xDist + yDist, lhs,
						lhs.getTForY(closestY.l), getTForY(closestY.r));
	}

	@Override
	public 
	BestProjectTup<SetIndex, SimplePathIndex> 
	projectLSet(double best,ShapeSet lhs) {
		return lhs.projectLVerLine(best, this).flip();
	}
	
	public <LPI extends PathIndex> 
	BestProjectTup<LPI, SimplePathIndex> 
	projectLSplittable(double best, SplittablePath<LPI> lhs) {
		return lhs.projectLVerLine(best, this).flip();
	}


	@Override
	public
	double distanceSquared(Vec v) {
		double xDist = square(v.x - x);
		double yDist = square(yInterval.minDistance(v.y));
		return xDist + yDist;
	}
	
	public double minDistSquaredTo(BBox b){
		double xDist = b.xInterval.minDistance(x);
		double yDist = yInterval.minDistance(b.yInterval);
		return xDist*xDist + yDist*yDist;
	}
	
	@Override
	public String toString() {
		return String.format("VerLine %s -> %s", getStartPoint().toString(), getEndPoint().toString());
	}
}
