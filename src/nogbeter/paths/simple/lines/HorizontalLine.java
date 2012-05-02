package nogbeter.paths.simple.lines;

import static bezier.util.Util.clamp;
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

public abstract class HorizontalLine extends Line {

	public final Interval xInterval;
	public final double y;

	public HorizontalLine(Interval xInterval, double y, Interval tinterval) {
		super(tinterval);
		this.xInterval = xInterval;
		this.y = y;
	}

	abstract double getTForX(double x);
	
	@Override
	public  <RPP extends PathIndex,RLS extends Path,RRS extends Path>
		IIntersections<SimplePathIndex, RPP> intersection(
			Path<RPP,RLS,RRS> other) {
		return other.intersectionLHorLine(this);
	}

	@Override
	public IIntersections<SimplePathIndex, SimplePathIndex> intersectionLDiaLine(
			DiagonalLine lhs) {
		return lhs.intersectionLHorLine(this).flip();
	}

	@Override
	public IIntersections<SimplePathIndex, SimplePathIndex> intersectionLHorLine(
			HorizontalLine lhs) {
		if(y == lhs.y){
			Interval r = xInterval.intersection(lhs.xInterval);
			if(r != null){
				if(r.low == r.high){
					return makeIntersectionResult(lhs, lhs.getTForX(r.low), getTForX(r.low));
				} else {
					return makeIntersectionResult(lhs, lhs.getTForX(r.low), getTForX(r.low))
							.append(
							makeIntersectionResult(lhs, lhs.getTForX(r.high), getTForX(r.high)));
				}
			}
		}
		return Intersections.NoIntersections;
	}

	@Override
	public IIntersections<SimplePathIndex, SimplePathIndex> intersectionLVerLine(
			VerticalLine lhs) {
		if(xInterval.isInside(lhs.x) && lhs.yInterval.isInside(y)){
			return makeIntersectionResult(lhs,lhs.getTForY(y), getTForX(lhs.x));
		} else {
			return Intersections.NoIntersections;
		}
	}
	
	@Override
	public IIntersections<SetIndex, SimplePathIndex> intersectionLSet(
			ShapeSet lhs) {
		return lhs.intersectionLHorLine(this).flip();
	}
	
	@Override
	public IIntersections<SimplePathIndex, SimplePathIndex> intersectionLCurve(
			Curve lhs) {
		if(overlaps(lhs.getBBox())){
			double lt = lhs.findTForY(y);
			if(Interval.interval01.isInside(lt)){
				double rt = getTForX(lhs.getAtLocal(lt).x);
				if(Interval.interval01.isInside(rt)){
					return makeIntersectionResult(lhs,lt,rt);
				}
			}
		} 
		return Intersections.NoIntersections;
	}
	
	@Override
	public <LPP extends PathIndex, LLSimp extends Path, LRSimp extends Path> 
		IIntersections<LPP, SimplePathIndex> intersectionLSplittable(
			SplittablePath<LPP, LLSimp, LRSimp> lhs) {
		return lhs.intersectionLHorLine(this).flip();
	}

	public boolean overlaps(BBox b) {
		return b.yInterval.isInside(y) 
			&& b.xInterval.overlapsWith(xInterval);
	}
	
	
	@Override
	public BestProject<SimplePathIndex> project(double best, Vec p) {
		double x = xInterval.getClosestPoint(p.x);
		double dist = new Vec(x,y).distanceSquared(p);
		return new BestProject<SimplePathIndex>(dist,
				makeGlobalPathIndexFromLocal(getTForX(x)));
	}
	
	@Override
	public <RPP extends PathIndex,RLS extends Path,RRS extends Path> BestProjectTup<SimplePathIndex, RPP> project(
			double best, Path<RPP,RLS,RRS> other) {
		return other.projectLHorLine(best, this);
	}

	@Override
	public BestProjectTup<SimplePathIndex, SimplePathIndex> projectLDiaLine(double best,
			DiagonalLine lhs) {
		return lhs.projectLHorLine(best, this).flip();
	}

	@Override
	public BestProjectTup<SimplePathIndex, SimplePathIndex> projectLHorLine(double best,
			HorizontalLine lhs) {
		STuple<Double> closestX = lhs.xInterval.closestPoints(xInterval);
		double xDist = square(closestX.l - closestX.r);
		double yDist = square(y - lhs.y);
		return
				makeBestProject(xDist + yDist, lhs,
						lhs.getTForX(closestX.l), getTForX(closestX.r));
	}

	@Override
	public BestProjectTup<SimplePathIndex, SimplePathIndex> projectLVerLine(double best,
			VerticalLine lhs) {
		double tl = clamp(lhs.getTForY(y)); double tr = clamp(getTForX(lhs.x));
		Vec l = lhs.getAtLocal(tl); Vec r = getAtLocal(tr);
			return 
					makeBestProject(l.distanceSquared(r),lhs ,tl,tr);
	}



	@Override
	public BestProjectTup<SetIndex, SimplePathIndex> projectLSet(double best,
			ShapeSet lhs) {
		return lhs.projectLHorLine(best, this).flip();
	}
	

	@Override
	public
	double distanceSquared(Vec v) {
		double xDist = square(xInterval.minDistance(v.x));
		double yDist = square(v.y - y);
		return xDist + yDist;
	}
	
	public double minDistSquaredTo(BBox b){
		double xDist = xInterval.minDistance(b.xInterval);
		double yDist = b.yInterval.minDistance(y);
		return xDist*xDist + yDist*yDist;
	}
	
	@Override
	public String toString() {
		return String.format("HorLine %s -> %s", getStartPoint().toString(), getEndPoint().toString());
	}


}
