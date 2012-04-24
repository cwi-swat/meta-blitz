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
import nogbeter.util.InclusiveInterval;

public abstract class NonLinearCurve extends SimplePath {

	STuple<SimplePath> simpler;


	public NonLinearCurve(InclusiveInterval tInterval) {
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
	public void project(BestProject<Double> best, Vec p) {
		if(getBBox().getNearestPoint(p).distanceSquared(p) > best.distSquared){
			return;
		}
		STuple<SimplePath> sp = splitSimpler();
		if (sp.l.getBBox().avgDistSquared(p) < 
			sp.r.getBBox().avgDistSquared(p)) {
			projectSimpler(best,p,sp.l,sp.r);
		} else {
			projectSimpler(best,p,sp.r,sp.l);
		}
		
	}

	private void projectSimpler(BestProject<Double> best, Vec p, SimplePath fst,
			SimplePath snd) {
		fst.project(best, p);
		snd.project(best,p);
	}

}
