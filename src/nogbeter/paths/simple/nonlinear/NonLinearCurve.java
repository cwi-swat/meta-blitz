package nogbeter.paths.simple.nonlinear;

import java.util.Collections;
import java.util.List;

import javax.print.attribute.standard.MediaSize.Other;

import bezier.points.Vec;
import bezier.util.STuple;
import bezier.util.Tuple;
import bezier.util.Util;

import nogbeter.paths.BestProject;
import nogbeter.paths.ConnectedPath;
import nogbeter.paths.Path;
import nogbeter.paths.simple.SimplePath;
import nogbeter.paths.simple.lines.DiagonalLine;
import nogbeter.paths.simple.lines.HorizontalLine;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.util.BBox;
import nogbeter.util.Interval;

import static bezier.util.Util.*;

public abstract class NonLinearCurve extends SimplePath {

	STuple<SimplePath> simpler;


	public NonLinearCurve(Interval tInterval) {
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

	@SuppressWarnings("unchecked")
	public STuple<SimplePath> makeMonotomous() {
		STuple<NonLinearCurve> result = split(xyRoots.get(0));
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
	public STuple<SimplePath> splitSimpler() {
		if (simpler == null) {
			setXYRoots();
			if (!isMonotomous()) {
				return (STuple) makeMonotomous();
			} else {
				STuple<NonLinearCurve> sp = split();
				simpler = new STuple<SimplePath>(
						sp.l.getSimplerApproximation(),
						sp.r.getSimplerApproximation());
			}
		}
		return simpler;
	}

	abstract SimplePath getSimplerApproximation();

	abstract STuple<NonLinearCurve> split(double t);

	STuple<NonLinearCurve> split() {
		return split(0.5);
	}

	@Override
	public <OPathParam> Tuple<List<Double>, List<OPathParam>> intersection(
			Path<OPathParam> other) {
		return other.intersectionLNonLinear(this);
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLDiaLine(
			DiagonalLine lhs) {
		return lhs.intersectionLNonLinear(this).flip();
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLHorLine(
			HorizontalLine lhs) {
		return lhs.intersectionLNonLinear(this).flip();
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLVerLine(
			VerticalLine lhs) {
		return lhs.intersectionLNonLinear(this).flip();
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLNonLinear(
			NonLinearCurve lhs) {
		if (getBBox().diagonalLengthSquared() > lhs.getBBox()
				.diagonalLengthSquared()) {
			STuple<SimplePath> simp = splitSimpler();
			return Util.appendTupList(lhs.intersection(simp.l),
									  lhs.intersection(simp.r));
		} else {
			STuple<SimplePath> simp = lhs.splitSimpler();
			return Util.appendTupList(simp.l.intersection(this),
									  simp.r.intersection(this));
		}
	}
	
	@Override
	public BestProject<Double> project(BestProject best, Vec p) {
		if(getBBox().getNearestPoint(p).distanceSquared(p) > best.distSquared){
			return best;
		}
		STuple<SimplePath> sp = splitSimpler();
		if (sp.l.getBBox().avgDistSquared(p) < 
			sp.r.getBBox().avgDistSquared(p)) {
			return projectSimpler(best,p,sp.l,sp.r);
		} else {
			return projectSimpler(best,p,sp.r,sp.l);
		}
		
	}

	private  BestProject<Double> projectSimpler(BestProject<Double> best, Vec p, SimplePath fst,
			SimplePath snd) {
		best = fst.project(best, p);
		return snd.project(best,p);
	}
	
	@Override
	public <OPathParam> BestProject<Tuple<Double, OPathParam>> project(
			BestProject best, Path<OPathParam> other) {
		return other.projectLNonLinear(best, this);
	}

	@Override
	public BestProject<Tuple<Double, Double>> projectLDiaLine(BestProject best,
			DiagonalLine lhs) {
		return  lhs.projectLNonLinear(best, this).flip();
	}

	@Override
	public BestProject<Tuple<Double, Double>> projectLHorLine(BestProject best,
			HorizontalLine lhs) {
		return  lhs.projectLNonLinear(best, this).flip();
	}

	@Override
	public BestProject<Tuple<Double, Double>> projectLVerLine(BestProject best,
			VerticalLine lhs) {
		return  lhs.projectLNonLinear(best, this).flip();
	}

	@Override
	public BestProject<Tuple<Double, Double>> projectLNonLinear(
			BestProject best, NonLinearCurve lhs) {
		if(best.distSquared > minDistTo(lhs.getBBox())){
			if (getBBox().diagonalLengthSquared() > lhs.getBBox()
					.diagonalLengthSquared()) {
				STuple<SimplePath> sp = splitSimpler();
				if(sp.l.getBBox().avgDistSquared(lhs.getBBox().getMiddle()) <
						sp.l.getBBox().avgDistSquared(lhs.getBBox().getMiddle())){
					return projectSimpler(best, lhs, sp.l, sp.r);
				} else {
					return projectSimpler(best, lhs, sp.r, sp.l);
				}
			} else {
				STuple<SimplePath> sp = lhs.splitSimpler();
				if(sp.l.getBBox().avgDistSquared(getBBox().getMiddle()) <
						sp.l.getBBox().avgDistSquared(getBBox().getMiddle())){
					return projectSimpler(best, this, sp.l, sp.r).flip();
				} else {
					return projectSimpler(best, this, sp.r, sp.l).flip();
				}
			}
		} else {
			return best;
		}
	}
	
	private  BestProject<Tuple<Double, Double>> projectSimpler(
			BestProject<Tuple<Double, Double>> best, Path p, SimplePath fst,
			SimplePath snd) {
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
