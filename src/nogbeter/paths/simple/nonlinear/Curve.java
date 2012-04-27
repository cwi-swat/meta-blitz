package nogbeter.paths.simple.nonlinear;

import java.util.Collections;
import java.util.List;

import nogbeter.paths.BestProjectTup;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.simple.SimplePath;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.paths.simple.lines.DiagonalLine;
import nogbeter.paths.simple.lines.HorizontalLine;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.util.Interval;
import bezier.util.Tuple;

public abstract class Curve extends SimplePath {

	Tuple<SimplePath,SimplePath> simpler;


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

	@SuppressWarnings("unchecked")
	public Tuple<Curve,Curve> makeMonotomous() {
		Tuple<Curve,Curve> result = split(xyRoots.get(0));
		result.l.xyRoots = Collections.EMPTY_LIST;
		result.r.xyRoots = xyRoots.subList(1, xyRoots.size());
		return result;
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
	public Tuple<SimplePath,SimplePath> splitSimpler() {
		if (simpler == null) {
			setXYRoots();
			if (!isMonotomous()) {
				return (Tuple) makeMonotomous();
			} else {
				Tuple<Curve,Curve> sp = split();
				simpler = new Tuple<SimplePath,SimplePath>(
						sp.l.getSimplerApprox(),
						sp.r.getSimplerApprox());
			}
		}
		return simpler;
	}

	abstract SimplePath getSimplerApprox();

	abstract <Sub extends Curve> Tuple<Sub,Sub> split(double t);

	<Sub extends Curve> Tuple<Sub,Sub> split() {
		return split(0.5);
	}

	@Override
	public <RPP,RLS extends Path,RRS extends Path> Tuple<List<SimplePathIndex>, List<RPP>> intersection(
			Path<RPP,RLS,RRS> other) {
		return other.intersectionLCurve(this);
	}

	@Override
	public Tuple<List<SimplePathIndex>, List<SimplePathIndex>> intersectionLDiaLine(
			DiagonalLine lhs) {
		return lhs.intersectionLCurve(this).flip();
	}

	@Override
	public Tuple<List<SimplePathIndex>, List<SimplePathIndex>> intersectionLHorLine(
			HorizontalLine lhs) {
		return lhs.intersectionLCurve(this).flip();
	}

	@Override
	public Tuple<List<SimplePathIndex>, List<SimplePathIndex>> intersectionLVerLine(
			VerticalLine lhs) {
		return lhs.intersectionLCurve(this).flip();
	}


	
	@Override
	public <RPP,RLS extends Path,RRS extends Path>  BestProjectTup<SimplePathIndex, RPP> project(
			double best, Path<RPP,RLS,RRS> other) {
		return other.projectLCurve(best, this);
	}

	@Override
	public BestProjectTup<SimplePathIndex, SimplePathIndex> projectLDiaLine(double best,
			DiagonalLine lhs) {
		return  lhs.projectLCurve(best, this).flip();
	}

	@Override
	public BestProjectTup<SimplePathIndex, SimplePathIndex> projectLHorLine(double best,
			HorizontalLine lhs) {
		return  lhs.projectLCurve(best, this).flip();
	}

	@Override
	public BestProjectTup<SimplePathIndex, SimplePathIndex> projectLVerLine(double best,
			VerticalLine lhs) {
		return  lhs.projectLCurve(best, this).flip();
	}

	@Override
	public <LPI> Tuple<List<LPI>, List<SimplePathIndex>> prependRightListRhs(
			Tuple<List<LPI>, List<? extends PathIndex>> intersections) {
		return (Tuple)intersections;
	}

	@Override
	public <LPI> Tuple<List<LPI>, List<SimplePathIndex>> prependLeftListRhs(
			Tuple<List<LPI>, List<? extends PathIndex>> intersections) {
		return (Tuple)intersections;
	}

	@Override
	public <LPI> BestProjectTup<LPI, SimplePathIndex> prependLeftBestTupRhs(
			BestProjectTup<LPI,? extends PathIndex> projectSimplerTup) {
		return (BestProjectTup)projectSimplerTup;
	}

	@Override
	public <LPI> BestProjectTup<LPI, SimplePathIndex> prependRightBestTupRhs(
			BestProjectTup<LPI,? extends PathIndex> projectSimplerTup) {
		return (BestProjectTup)projectSimplerTup;
	}
	
	@Override
	public <LPI> BestProjectTup<SimplePathIndex, LPI> prependLeftBestTupLhs(
			BestProjectTup<? extends PathIndex, LPI> projectSimplerTup) {
		return (BestProjectTup)projectSimplerTup;
	}

	@Override
	public <LPI> BestProjectTup<SimplePathIndex, LPI> prependRightBestTupLhs(
			BestProjectTup<? extends PathIndex, LPI> projectSimplerTup) {
		return (BestProjectTup)projectSimplerTup;
	}
	
	
}
