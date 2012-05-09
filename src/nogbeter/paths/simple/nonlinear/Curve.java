package nogbeter.paths.simple.nonlinear;

import java.util.Collections;
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
import nogbeter.points.oned.Interval;
import bezier.util.Tuple;

public abstract class Curve extends SimplePath {

	Tuple<SimplePath,SimplePath> simpler;

	public Curve(Interval tInterval,SimplePath lsimp, SimplePath rsimp) {
		super(tInterval);
		if(lsimp != null){
			this.simpler = new Tuple<SimplePath, SimplePath>(lsimp,rsimp);
		}
	}

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
	
	
	public Tuple<Path,Path> splitSimpler(){
		return (Tuple)splitSimplerCurve();
	}

	public Tuple<SimplePath,SimplePath> splitSimplerCurve() {
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

	abstract Tuple<Curve,Curve> split(double t);

	Tuple<Curve,Curve> split() {
		return split(0.5);
	}

	@Override
	public <RPP extends PathIndex> 
		IIntersections<SimplePathIndex, RPP> intersection(
			Path<RPP> other) {
		return other.intersectionLCurve(this);
	}

	@Override
	public IIntersections<SimplePathIndex, SimplePathIndex> intersectionLDiaLine(
			DiagonalLine lhs) {
		return super.intersectionLDiaLine(lhs);
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
	public <RPP extends PathIndex>  
		BestProjectTup<SimplePathIndex, RPP> project(
			double best, Path<RPP> other) {
		return other.projectLCurve(best, this);
	}

	@Override
	public BestProjectTup<SetIndex, SimplePathIndex> projectLSet(double best,
			ShapeSet lhs) {
		return lhs.projectLCurve(best, this).flip();
	}
	
	@Override
	public Tuple<Path<SimplePathIndex>, Double> normaliseToLength(
			double prevLength) {
		Tuple<SimplePath,SimplePath> sp = splitSimplerCurve();
		Tuple<Path<SimplePathIndex>, Double> l = sp.l.normaliseToLength(prevLength);
		Tuple<Path<SimplePathIndex>, Double> r = sp.r.normaliseToLength(l.r);
		return new Tuple<Path<SimplePathIndex>, Double>(
			(Path<SimplePathIndex>)getWithNewSimpleAndInterval(
					(SimplePath)l.l, (SimplePath)r.l, new Interval(prevLength,r.r)),
			r.r);
	}

	abstract Curve getWithNewSimpleAndInterval(
			SimplePath l, SimplePath l2, Interval interval) ;
	

	@Override
	public void getSubPath(SimplePathIndex from, SimplePathIndex to, List<Path> result) {
		Tuple<Curve,Curve> sl = split(from.t);
		double splitRight = (to.t - from.t) / (1.0 - from.t);
		Tuple<Curve,Curve> sr = sl.r.split(splitRight);
		result.add(sr.l);
	}
	
	@Override
	public void getSubPathFrom(SimplePathIndex from, List<Path> result) {
		Tuple<Curve,Curve> sl = split(from.t);
		result.add(sl.r);
	}

	@Override
	public void getSubPathTo(SimplePathIndex to, List<Path> result) {
		Tuple<Curve,Curve> sl = split(to.t);
		result.add(sl.l);
		
	}
}
