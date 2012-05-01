package nogbeter.paths.simple.lines;

import static bezier.util.Util.clamp;
import static bezier.util.Util.square;

import java.util.List;

import nogbeter.paths.BestProject;
import nogbeter.paths.BestProjectTup;
import nogbeter.paths.Path;
import nogbeter.paths.compound.SetIndex;
import nogbeter.paths.compound.ShapeSet;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.paths.simple.nonlinear.Curve;
import nogbeter.util.BBox;
import nogbeter.util.Interval;
import bezier.points.Vec;
import bezier.util.STuple;
import bezier.util.Tuple;
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
	public  <RPP,RLS extends Path,RRS extends Path> Tuple<List<SimplePathIndex>, List<RPP>> intersection(
			Path<RPP,RLS,RRS> other) {
		return other.intersectionLHorLine(this);
	}

	@Override
	public Tuple<List<SimplePathIndex>, List<SimplePathIndex>> intersectionLDiaLine(
			DiagonalLine lhs) {
		return lhs.intersectionLHorLine(this).flip();
	}

	@Override
	public Tuple<List<SimplePathIndex>, List<SimplePathIndex>> intersectionLHorLine(
			HorizontalLine lhs) {
		return Util.emptyTupleList;
	}

	@Override
	public Tuple<List<SimplePathIndex>, List<SimplePathIndex>> intersectionLVerLine(
			VerticalLine lhs) {
		if(xInterval.isInside(lhs.x) && lhs.yInterval.isInside(y)){
			return makeIntersectionResult(lhs,lhs.getTForY(y), getTForX(lhs.x));
		} else {
			return Util.emptyTupleList;
		}
	}
	
	@Override
	public Tuple<List<SetIndex>, List<SimplePathIndex>> intersectionLSet(
			ShapeSet lhs) {
		return lhs.intersectionLHorLine(this).flip();
	}
	
	@Override
	public Tuple<List<SimplePathIndex>, List<SimplePathIndex>> intersectionLCurve(
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
		return Util.emptyTupleList;
	}

	boolean overlaps(BBox b) {
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
	public <RPP,RLS extends Path,RRS extends Path> BestProjectTup<SimplePathIndex, RPP> project(
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
