package nogbeter.paths.simple.nonlinear;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.compound.SetIndex;
import nogbeter.paths.compound.ShapeSet;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.project.BestProjectTup;
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
	public <RPP extends PathIndex,RLS extends Path,RRS extends Path> IIntersections<SimplePathIndex, RPP> intersection(
			Path<RPP,RLS,RRS> other) {
		return other.intersectionLCurve(this);
	}

	@Override
	public IIntersections<SimplePathIndex, SimplePathIndex> intersectionLDiaLine(
			DiagonalLine lhs) {
		return lhs.intersectionLCurve(this).flip();
	}

	@Override
	public IIntersections<SimplePathIndex, SimplePathIndex> intersectionLHorLine(
			HorizontalLine lhs) {
		return lhs.intersectionLCurve(this).flip();
	}

	@Override
	public IIntersections<SimplePathIndex, SimplePathIndex> intersectionLVerLine(
			VerticalLine lhs) {
		return lhs.intersectionLCurve(this).flip();
	}
	
	@Override
	public IIntersections<SetIndex, SimplePathIndex> intersectionLSet(
			ShapeSet lhs) {
		return lhs.intersectionLCurve(this).flip();
	}


	
	@Override
	public <RPP extends PathIndex,RLS extends Path,RRS extends Path>  BestProjectTup<SimplePathIndex, RPP> project(
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
	public BestProjectTup<SetIndex, SimplePathIndex> projectLSet(double best,
			ShapeSet lhs) {
		return lhs.projectLCurve(best, this).flip();
	}

	
	
}
