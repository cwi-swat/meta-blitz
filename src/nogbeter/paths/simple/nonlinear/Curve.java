package nogbeter.paths.simple.nonlinear;

import java.util.Collections;
import java.util.List;

import javax.print.attribute.standard.MediaSize.Other;

import bezier.points.Vec;
import bezier.util.STuple;
import bezier.util.Tuple;
import bezier.util.Util;

import nogbeter.paths.BestProject;
import nogbeter.paths.BestProjectTup;
import nogbeter.paths.ConnectedPath;
import nogbeter.paths.Path;
import nogbeter.paths.SplittablePath;
import nogbeter.paths.simple.SimplePath;
import nogbeter.paths.simple.lines.DiagonalLine;
import nogbeter.paths.simple.lines.HorizontalLine;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.util.BBox;
import nogbeter.util.Interval;

import static bezier.util.Util.*;

public abstract class Curve extends SimplePath implements SplittablePath<Double> {

	STuple<Path<Double>> simpler;


	public Curve(Interval tInterval) {
		super(tInterval);
	}

	protected List<Double> xyRoots;

	abstract List<Double> getXYRoots();

	public void setXYRoots() {
		if (xyRoots == null) {
			xyRoots = getXYRoots();
			Collections.sort(xyRoots);
		}
	}

	public Path<Double> getPath() { return this; }
	
	@SuppressWarnings("unchecked")
	public STuple<SimplePath> makeMonotomous() {
		STuple<Curve> result = split(xyRoots.get(0));
		result.l.xyRoots = Collections.EMPTY_LIST;
		result.r.xyRoots = xyRoots.subList(1, xyRoots.size());
		return (STuple) result;
	}

	abstract Double findX(double x);

	abstract Double findY(double y);

	public double findTForX(double x) {
		Double d = findX(x);
		if (d == null) {
			System.out.printf("Cannot find %f %s\n", x, this);
		}
		return d;
	}

	public double findTForY(double y) {
		Double d = findY(y);
		if (d == null) {
			System.out.printf("Cannot find %f %s\n", y, this);
		}
		return d;
	}

	public boolean isMonotomous() {
		setXYRoots();
		return xyRoots.size() == 0;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public STuple<Path<Double>> splitSimpler() {
		if (simpler == null) {
			setXYRoots();
			if (!isMonotomous()) {
				return (STuple) makeMonotomous();
			} else {
				STuple<Curve> sp = split();
				simpler = new STuple<Path<Double>>(
						sp.l.getSimplerApprox(),
						sp.r.getSimplerApprox());
			}
		}
		return simpler;
	}

	abstract SimplePath getSimplerApprox();

	abstract STuple<Curve> split(double t);

	STuple<Curve> split() {
		return split(0.5);
	}

	@Override
	public <OPathParam> Tuple<List<Double>, List<OPathParam>> intersection(
			Path<OPathParam> other) {
		return other.intersectionLCurve(this);
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLDiaLine(
			DiagonalLine lhs) {
		return lhs.intersectionLCurve(this).flip();
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLHorLine(
			HorizontalLine lhs) {
		return lhs.intersectionLCurve(this).flip();
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLVerLine(
			VerticalLine lhs) {
		return lhs.intersectionLCurve(this).flip();
	}

	@Override
	public  <OPathParam> Tuple<List<OPathParam>, List<Double>> intersectionLSplittable(
			SplittablePath<OPathParam> lhs){
		if (getBBox().diagonalLengthSquared() > lhs.getBBox()
				.diagonalLengthSquared()) {
			STuple<Path<Double>> simp = splitSimpler();
			return Util.appendTupList(lhs.getPath().intersection(simp.l),
									  lhs.getPath().intersection(simp.r));
		} else {
			STuple<Path<OPathParam>> simp = lhs.splitSimpler();
			return Util.appendTupList(simp.l.intersection(this),
									  simp.r.intersection(this));
		}
	}
	
	@Override
	public BestProject<Double> project(BestProject<Double> best, Vec p) {
		if(getBBox().getNearestPoint(p).distanceSquared(p) > best.distSquared){
			return best;
		}
		STuple<Path<Double>> sp = splitSimpler();
		if (sp.l.getBBox().avgDistSquared(p) < 
			sp.r.getBBox().avgDistSquared(p)) {
			return projectSimpler(best,p,sp.l,sp.r);
		} else {
			return projectSimpler(best,p,sp.r,sp.l);
		}
		
	}

	private  BestProject<Double> projectSimpler(BestProject<Double> best, Vec p, Path<Double> fst,
			Path<Double> snd) {
		best = fst.project(best, p);
		return snd.project(best,p);
	}
	
	@Override
	public <OPathParam> BestProjectTup<Double, OPathParam> project(
			BestProjectTup<Double, OPathParam> best, Path<OPathParam> other) {
		return other.projectLCurve(best, this);
	}

	@Override
	public BestProjectTup<Double, Double> projectLDiaLine(BestProjectTup<Double, Double> best,
			DiagonalLine lhs) {
		return  lhs.projectLCurve(best, this).flip();
	}

	@Override
	public BestProjectTup<Double, Double> projectLHorLine(BestProjectTup<Double, Double> best,
			HorizontalLine lhs) {
		return  lhs.projectLCurve(best, this).flip();
	}

	@Override
	public BestProjectTup<Double, Double> projectLVerLine(BestProjectTup<Double, Double> best,
			VerticalLine lhs) {
		return  lhs.projectLCurve(best, this).flip();
	}

	@Override
	public <OPathParam> BestProjectTup<OPathParam, Double> projectLSplittable(
			BestProjectTup<OPathParam, Double> best, SplittablePath<OPathParam> lhs) {
		if(best.distSquared > minDistTo(lhs.getBBox())){
			if (getBBox().diagonalLengthSquared() > lhs.getBBox()
					.diagonalLengthSquared()) {
				STuple<Path<Double>> sp = splitSimpler();
				if(sp.l.getBBox().avgDistSquared(lhs.getBBox().getMiddle()) <
						sp.l.getBBox().avgDistSquared(lhs.getBBox().getMiddle())){
					return projectSimplerTup(best, lhs.getPath(), sp.l, sp.r);
				} else {
					return projectSimplerTup(best, lhs.getPath(), sp.r, sp.l);
				}
			} else {
				STuple<Path<OPathParam>> sp = lhs.splitSimpler();
				if(sp.l.getBBox().avgDistSquared(getBBox().getMiddle()) <
						sp.l.getBBox().avgDistSquared(getBBox().getMiddle())){
					return projectSimplerTup(best.flip(), this, sp.l, sp.r).flip();
				} else {
					return projectSimplerTup(best.flip(), this, sp.r, sp.l).flip();
				}
			}
		} else {
			return best;
		}
	}
	
	private static <LPathParam,RPathParam> BestProjectTup<LPathParam, RPathParam> projectSimplerTup(
			BestProjectTup<LPathParam,RPathParam> best, Path<LPathParam> p, 
			Path<RPathParam> fst,
			Path<RPathParam> snd) {
		best = p.project(best, fst);
		return p.project(best,snd);
	}

	double minDistTo(BBox br){
		BBox bl = getBBox();
		double xDist = square(bl.xInterval.minDistance(br.xInterval));
		double yDist = square(bl.yInterval.minDistance(br.yInterval));
		return xDist + yDist;
	}
	
}
