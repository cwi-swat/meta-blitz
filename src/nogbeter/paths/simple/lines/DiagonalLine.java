package nogbeter.paths.simple.lines;

import java.util.Collections;
import java.util.List;

import nogbeter.paths.BestProject;
import nogbeter.paths.BestProjectTup;
import nogbeter.paths.ConnectedPath;
import nogbeter.paths.Path;
import nogbeter.paths.SplittablePath;
import nogbeter.paths.simple.SimplePath;
import nogbeter.paths.simple.nonlinear.Curve;
import nogbeter.paths.simple.nonlinear.QuadCurve;
import nogbeter.util.BBox;
import nogbeter.util.Interval;
import bezier.points.Vec;
import bezier.util.STuple;
import bezier.util.Tuple;
import bezier.util.Util;

import static nogbeter.util.Interval.*;
import static java.lang.Math.*;
import static bezier.util.Util.*;


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
			double res = (xf - sx) / dx;
			if (res >= 0 && res <= 1) {
				return res;
			} else {
				return null;
			}
		}
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

	@Override
	public String toString() {
		return String.format("Line %s -> %s", start.toString(), end.toString());
	}

	public double findTForX(double x) {
		return getTAtX(x);
	}

	@Override
	public <OPathParam> Tuple<List<Double>, List<OPathParam>> intersection(
			Path<OPathParam> other) {
		return other.intersectionLDiaLine(this);
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLDiaLine(
			DiagonalLine lhs) {
		Tuple<Double, Double> res = lhs.intersection(this);
		if (res != null) {
			return makeIntersectionResult(lhs,res.l,res.r);
		} else {
			return Util.emptyTupleList;
		}
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLHorLine(
			HorizontalLine lhs) {
		double t = getTAtY(lhs.y);
		if(interval01.isInside(t)){
			Vec res = getAtLocal(t);
			if (lhs.xInterval.isInside(res.x)) {
				return makeIntersectionResult(lhs,lhs.getTForX(res.x), t);
			} 
		}
		return Util.emptyTupleList;
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLVerLine(
			VerticalLine lhs) {
		double t = getTAtX(lhs.x);
		if(interval01.isInside(t)){
			Vec res = getAtLocal(t);
			if (lhs.yInterval.isInside(res.y)) {
				return makeIntersectionResult(lhs,lhs.getTForY(res.y), t);
			}
		}
		return Util.emptyTupleList;
	}


	STuple<Interval> getTIntervalsBBox(BBox b){
		return new STuple<Interval>(
				new Interval(	findX(b.xInterval.low), 
								findX(b.xInterval.high)
				).intersection(interval01),
				new Interval(	findY(b.yInterval.low), 
								findY(b.yInterval.high)
				).intersection(interval01)
		);
	}
	
	boolean overlaps(BBox b) {
		// this requires some drawing to see....
		// project x end points onto line and y endpoints to
		// if these t-intervals overlap, then the line
		// overlaps the bbox
		STuple<Interval> tIntervals = getTIntervalsBBox(b);
		return Interval.overlap(tIntervals.l, tIntervals.r);
	}

	@Override
	public BestProject<Double> project(BestProject<Double> best, Vec p) {
		double t = closestT(p);
		double dist = getAtLocal(t).distanceSquared(p);
		return best.choose(new BestProject<Double>(dist, tInterval.getAtFactor(t)));
	}

	public BestProjectTup<Double, Double> projectLPoint(
			SimplePath lhs, double lt, double x, double y) {
		Vec v = new Vec(x,y);
		double tr = closestT(v);
		double dist = getAtLocal(tr).distanceSquared(v);
		return makeBestProject(dist, lhs, lt, tr);
	}


	double minDistSquaredTo(BBox b){
		STuple<Interval> tIntervals = getTIntervalsBBox(b);
		if(Interval.overlap(tIntervals.l,tIntervals.r)){
			return 0;
		} else {
			double res = Double.POSITIVE_INFINITY;
			if(tIntervals.l != emptyInterval){
				Interval xyInterval = 
						new Interval(	getYAt(tIntervals.l.low), 
										getYAt(tIntervals.l.high));
				res = min(res, square(xyInterval.minDistance(b.yInterval)));
			}
			if(tIntervals.r != emptyInterval){
				Interval yxInterval = 
						new Interval(	getYAt(tIntervals.r.low), 
										getYAt(tIntervals.r.high));
				res = min(res, square(yxInterval.minDistance(b.xInterval)));
			}
			res = min(res, distanceSquared(b.getLeftUp()));
			res = min(res, distanceSquared(b.getLeftDown()));
			res = min(res, distanceSquared(b.getRightUp()));
			res = min(res, distanceSquared(b.getRightDown()));
			return res;
		}
	}
	



	@Override
	public <OPathParam> BestProjectTup<Double, OPathParam> project(
			BestProjectTup<Double, OPathParam> best, Path<OPathParam> other) {
		return other.projectLDiaLine(best, this);
	}

	@Override
	public BestProjectTup<Double, Double> projectLDiaLine(
			BestProjectTup<Double, Double> best, DiagonalLine lhs) {
		Tuple<Double,Double> tp = lhs.closestTs(this);
		double dist = lhs.getAtLocal(tp.l).distanceSquared(getAtLocal(tp.r));
		if(dist < best.distSquared){
			return makeBestProject(dist,lhs, tp.l, tp.r);
		} else {
			return best;
		}
	}


	@Override
	public BestProjectTup<Double, Double> projectLHorLine(
			BestProjectTup<Double, Double> best, HorizontalLine lhs) {
		Interval txInterval = new Interval(
				getTAtX(lhs.xInterval.low),
				getTAtX(lhs.xInterval.high)).intersection(interval01);
		if(txInterval != null){
			Vec lowv = getAtLocal(txInterval.low); Vec highv = getAtLocal(txInterval.high);
			double lowDist = lowv.y - lhs.y; double highDist = highv.y - lhs.y;
			if(lowDist < highDist){
				best = best.choose(
						makeBestProject(square(lowDist), lhs, lhs.getTForX(lowv.x), txInterval.low)
						);
			} else {
				best = best.choose(
						makeBestProject(square(lowDist), lhs, lhs.getTForX(highv.x), txInterval.high)
					);
			}
		} 
		return best.choose(
				projectLPoint(lhs, lhs.getTForX(lhs.xInterval.low), lhs.xInterval.low, lhs.y))
				.choose(
				projectLPoint(lhs, lhs.getTForX(lhs.xInterval.high),lhs.xInterval.high, lhs.y)
			);
	}

	@Override
	public BestProjectTup<Double, Double> projectLVerLine(
			BestProjectTup<Double, Double> best, VerticalLine lhs) {
		Interval tyInterval = new Interval(
				getTAtX(lhs.yInterval.low),
				getTAtX(lhs.yInterval.high)).intersection(interval01);
		if(tyInterval != null){
			Vec lowv = getAtLocal(tyInterval.low); Vec highv = getAtLocal(tyInterval.high);
			double lowDist = lowv.x - lhs.x; double highDist = highv.x - lhs.x;
			if(lowDist < highDist){
				best = best.choose(
						makeBestProject(square(lowDist), lhs, lhs.getTForY(lowv.x), tyInterval.low)
					);
			} else {
				best = best.choose(
						makeBestProject(square(lowDist), lhs, lhs.getTForY(highv.x), tyInterval.high)
					);
			}
		} 
		return best.choose(
				projectLPoint(lhs, lhs.getTForY(lhs.yInterval.low), lhs.x, lhs.yInterval.low))
				.choose(
				projectLPoint(lhs, lhs.getTForY(lhs.yInterval.high), lhs.x, lhs.yInterval.high)
			);
	}


}
