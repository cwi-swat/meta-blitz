package nogbeter.paths.simple.lines;

import static bezier.util.Util.square;
import static java.lang.Math.min;
import static nogbeter.points.oned.Interval.emptyInterval;
import static nogbeter.points.oned.Interval.interval01;

import java.awt.geom.PathIterator;

import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.SplittablePath;
import nogbeter.paths.compound.SetIndex;
import nogbeter.paths.compound.ShapeSet;
import nogbeter.paths.compound.SplitIndex;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.intersections.Intersections;
import nogbeter.paths.results.project.BestProject;
import nogbeter.paths.results.project.BestProjectTup;
import nogbeter.paths.results.transformers.IPathIndexTransformer;
import nogbeter.paths.results.transformers.PITransformers;
import nogbeter.paths.simple.SimplePath;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.points.oned.Interval;
import nogbeter.points.twod.BBox;
import nogbeter.points.twod.Vec;
import nogbeter.transform.AffineTransformation;
import bezier.util.STuple;
import bezier.util.Tuple;
import bezier.util.Util;


public class DiagonalLine extends Line {

	public final Vec start, dir, end;

	public DiagonalLine(Vec start, Vec end, Interval tInterval) {
		super(tInterval);
		this.start = start;
		this.dir = end.sub(start);
		this.end = end;
	}

	public Tuple<Double, Double> intersection(DiagonalLine r) {
		Vec cross = start.sub(r.start);
		double solDiv = (r.dir.y * dir.x - r.dir.x * dir.y);
		double sol = (r.dir.x * cross.y - r.dir.y * cross.x) / solDiv;
		if (sol >= 0 && sol <= 1) {
			double sol2 = (dir.x * cross.y - dir.y * cross.x) / solDiv;
			if (sol2 >= 0 && sol2 <= 1) {
				return new Tuple<Double, Double>(sol, sol2);
			}
		}
		return null;
	}

	public double distanceSquared(Vec p) {
		return getAtLocal(closestT(p)).distanceSquared(p);
	}

	public double closestT(Vec p) {
		return Util.clamp(closestTAll(p));
	}

	double closestTAll(Vec p) {
		return -(dir.y * start.y + dir.x * start.x - dir.y * p.y - dir.x * p.x)
				/ (dir.x * dir.x + dir.y * dir.y);
	}

	public double distanceSquared(DiagonalLine r) {
		Tuple<Double, Double> cl = closestTs(r);
		return getAtLocal(cl.l).distanceSquared(r.getAtLocal(cl.r));
	}

	public Tuple<Double, Double> closestTs(DiagonalLine r) {
		Tuple<Double, Double> res = intersection(r);
		if (res == null) {
			double prs = r.closestT(start);
			double pre = r.closestT(end);
			double pls = closestT(r.start);
			double ple = closestT(r.end);
			double drs = start.distanceSquared(r.getAtLocal(prs));
			double dre = end.distanceSquared(r.getAtLocal(pre));
			double dls = r.start.distanceSquared(getAtLocal(pls));
			double dle = r.end.distanceSquared(getAtLocal(ple));

			double min = Util.min4(drs, dre, dls, dle);
			if (min == drs) {
				return new Tuple<Double, Double>(0.0, prs);
			} else if (min == dre) {
				return new Tuple<Double, Double>(1.0, prs);
			} else if (min == dls) {
				return new Tuple<Double, Double>(pls, 0.0);
			} else {
				return new Tuple<Double, Double>(ple, 1.0);
			}
		} else {
			return res;
		}
	}

	public Vec getAtLocal(double t) {
		return start.add(dir.mul(t));
	}
	
	double getXAt(double t){
		return start.x + dir.x * t;
	}
	
	double getYAt(double t){
		return start.y + dir.y * t;
	}

	public Vec getTangentAtLocal(double t) {
		return dir;
	}

	private Double find(double sx, double dx, double xf) {
		if (dx == 0) {
			return null;
		} else {
			double res = findAll(sx, dx, xf);
			if (res >= 0 && res <= 1) {
				return res;
			} else {
				return null;
			}
		}
	}
	
	private double findAll(double sx, double dx, double xf){
		return (xf - sx) / dx;
	}

	public double getTAtY(double y) {
		return (y - start.y) / dir.y;
	}

	public double getTAtX(double x) {
		return (x - start.x) / dir.x;
	}

	public Double findX(double x) {
		return find(start.x, dir.x, x);
	}

	public Double findY(double y) {
		return find(start.y, dir.y, y);
	}
	
	public double findXAll(double x) {
		return findAll(start.x, dir.x, x);
	}

	public double findYAll(double y) {
		return findAll(start.y, dir.y, y);
	}

	@Override
	public String toString() {
		return String.format("Line %s -> %s", start.toString(), end.toString());
	}

	public double findTForX(double x) {
		return getTAtX(x);
	}

	@Override
	public  <RPP extends PathIndex> 
			IIntersections<SimplePathIndex, RPP> intersection(Path<RPP> other) {
		return other.intersectionLDiaLine(this);
	}

	@Override
	public IIntersections<SimplePathIndex, SimplePathIndex> intersectionLDiaLine(
			DiagonalLine lhs) {
		Tuple<Double, Double> res = lhs.intersection(this);
		if (res != null) {
			return makeIntersectionResult(lhs,res.l,res.r);
		} else {
			return Intersections.NoIntersections;
		}
	}

	@Override
	public IIntersections<SimplePathIndex, SimplePathIndex> intersectionLHorLine(
			HorizontalLine lhs) {
		double t = getTAtY(lhs.y);
		if(interval01.isInside(t)){
			Vec res = getAtLocal(t);
			if (lhs.xInterval.isInside(res.x)) {
				return makeIntersectionResult(lhs,lhs.getTForX(res.x), t);
			} 
		}
		return Intersections.NoIntersections;
	}

	@Override
	public IIntersections<SimplePathIndex, SimplePathIndex> intersectionLVerLine(
			VerticalLine lhs) {
		double t = getTAtX(lhs.x);
		if(interval01.isInside(t)){
			Vec res = getAtLocal(t);
			if (lhs.yInterval.isInside(res.y)) {
				return makeIntersectionResult(lhs,lhs.getTForY(res.y), t);
			}
		}
		return Intersections.NoIntersections;
	}
	

	@Override
	public IIntersections<SetIndex, SimplePathIndex> intersectionLSet(
			ShapeSet lhs) {
		return lhs.intersectionLDiaLine(this).flip();
	}
	
	@Override
	public <LPP extends PathIndex> 
		IIntersections<LPP, SimplePathIndex> 
		intersectionLSplittable(SplittablePath<LPP> lhs) {
		return lhs.intersectionLDiaLine(this).flip();
	}


	STuple<Interval> getTIntervalsBBox(BBox b){
		return new STuple<Interval>(
				new Interval(	
						findXAll(b.xInterval.low), 
						findXAll(b.xInterval.high)
				).intersection(interval01),
				new Interval(	findYAll(b.yInterval.low), 
								findYAll(b.yInterval.high)
				).intersection(interval01)
		);
	}
	
	public boolean overlaps(BBox b) {
		// 
//		// this requires some drawing to see....
//		// project x end points onto line and y endpoints to
//		// if these t-intervals overlap, then the line
//		// overlaps the bbox
//		STuple<Interval> tIntervals = getTIntervalsBBox(b);
//		return Interval.overlap(tIntervals.l, tIntervals.r);
		// the code above is correct, but too slow....
		return getBBox().overlaps(b);
	}

	@Override
	public BestProject<SimplePathIndex> project(double best, Vec p) {
		double t = closestT(p);
		double dist = getAtLocal(t).distanceSquared(p);
		return new BestProject<SimplePathIndex>(dist, makeGlobalPathIndexFromLocal(t));
	}

	public BestProjectTup<SimplePathIndex, SimplePathIndex> projectLPoint(
			SimplePath lhs, double lt, double x, double y) {
		Vec v = new Vec(x,y);
		double tr = closestT(v);
		double dist = getAtLocal(tr).distanceSquared(v);
		return makeBestProject(dist, lhs, lt, tr);
	}


	public double minDistSquaredTo(BBox b){
		return minDistTo(b);
//		STuple<Interval> tIntervals = getTIntervalsBBox(b);
//		if(Interval.overlap(tIntervals.l,tIntervals.r)){
//			return 0;
//		} else {
//			double res = Double.POSITIVE_INFINITY;
//			if(tIntervals.l != emptyInterval){
//				Interval xyInterval = 
//						new Interval(	getYAt(tIntervals.l.low), 
//										getYAt(tIntervals.l.high));
//				res = min(res, square(xyInterval.minDistance(b.yInterval)));
//			}
//			if(tIntervals.r != emptyInterval){
//				Interval yxInterval = 
//						new Interval(	getYAt(tIntervals.r.low), 
//										getYAt(tIntervals.r.high));
//				res = min(res, square(yxInterval.minDistance(b.xInterval)));
//			}
//			res = min(res, distanceSquared(b.getLeftUp()));
//			res = min(res, distanceSquared(b.getLeftDown()));
//			res = min(res, distanceSquared(b.getRightUp()));
//			res = min(res, distanceSquared(b.getRightDown()));
//			return res;
//		}
	}
	



	@Override
	public <RPP extends PathIndex>  
			BestProjectTup<SimplePathIndex, RPP> 
			project(double best, Path<RPP>other) {
		return other.projectLDiaLine(best, this);
	}

	@Override
	public BestProjectTup<SimplePathIndex, SimplePathIndex> projectLDiaLine(
			double best, DiagonalLine lhs) {
		Tuple<Double,Double> tp = lhs.closestTs(this);
		double dist = lhs.getAtLocal(tp.l).distanceSquared(getAtLocal(tp.r));
		if(dist < best){
			return makeBestProject(dist,lhs, tp.l, tp.r);
		} else {
			return BestProjectTup.noBestYet;
		}
	}


	@Override
	public BestProjectTup<SimplePathIndex, SimplePathIndex> projectLHorLine(
			double best, HorizontalLine lhs) {
		Interval txInterval = new Interval(
				getTAtX(lhs.xInterval.low),
				getTAtX(lhs.xInterval.high)).intersection(interval01);
		BestProjectTup<SimplePathIndex, SimplePathIndex> res = BestProjectTup.noBestYet;
		if(txInterval != null){
			Vec lowv = getAtLocal(txInterval.low); Vec highv = getAtLocal(txInterval.high);
			double lowDist = square(lowv.y - lhs.y); double highDist = square(highv.y - lhs.y);
			if(lowDist < highDist){
				res = res.choose(
						makeBestProject(lowDist, lhs, lhs.getTForX(lowv.x), txInterval.low)
						);
			} else {
				res = res.choose(
						makeBestProject(highDist, lhs, lhs.getTForX(highv.x), txInterval.high)
					);
			}
		} 
		return res.choose(
				projectLPoint(lhs, lhs.getTForX(lhs.xInterval.low), lhs.xInterval.low, lhs.y))
				.choose(
				projectLPoint(lhs, lhs.getTForX(lhs.xInterval.high),lhs.xInterval.high, lhs.y)
			);
	}

	@Override
	public BestProjectTup<SimplePathIndex, SimplePathIndex> projectLVerLine(
			double best, VerticalLine lhs) {
		Interval tyInterval = new Interval(
				getTAtY(lhs.yInterval.low),
				getTAtY(lhs.yInterval.high)).intersection(interval01);
		BestProjectTup<SimplePathIndex, SimplePathIndex> res = BestProjectTup.noBestYet;
		if(tyInterval != null){
			Vec lowv = getAtLocal(tyInterval.low); Vec highv = getAtLocal(tyInterval.high);
			double lowDist = square(lowv.x - lhs.x); double highDist = square(highv.x - lhs.x);
			if(lowDist < highDist){
				res = res.choose(
						makeBestProject(lowDist, lhs, lhs.getTForY(lowv.y), tyInterval.low)
					);
			} else {
				res = res.choose(
						makeBestProject(highDist, lhs, lhs.getTForY(highv.y), tyInterval.high)
					);
			}
		} 
		return res.choose(
				projectLPoint(lhs, lhs.getTForY(lhs.yInterval.low), lhs.x, lhs.yInterval.low))
				.choose(
				projectLPoint(lhs, lhs.getTForY(lhs.yInterval.high), lhs.x, lhs.yInterval.high)
			);
	}

	@Override
	public BestProjectTup<SetIndex, SimplePathIndex> projectLSet(double best,
			ShapeSet lhs) {
		return lhs.projectLDiaLine(best, this).flip();
	}
	
	public <LPI extends PathIndex>
			BestProjectTup<LPI, SimplePathIndex> 
			projectLSplittable(double best, SplittablePath<LPI> lhs) {
		return lhs.projectLDiaLine(best, this).flip();
	}

}
