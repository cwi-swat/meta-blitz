package paths.paths.paths.simple.curve;

import java.util.Collections;
import java.util.List;

import paths.paths.factory.PathFactory;
import paths.paths.paths.Path;
import paths.paths.paths.compound.Append;
import paths.paths.paths.compound.ShapeSet;
import paths.paths.paths.simple.Line;
import paths.paths.paths.simple.SimplePath;
import paths.paths.results.intersections.IIntersections;
import paths.paths.results.project.BestProjectTup;
import paths.points.oned.Interval;
import paths.points.twod.Vec;
import transform.nonlinear.IDeform;
import transform.nonlinear.ILineTransformer;
import util.Tuple;


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
	
	public Tuple<SimplePath,SimplePath> splitSimp(double t){
		Tuple<Curve, Curve> sp = split(t);
		return new Tuple<SimplePath, SimplePath>(sp.l.getSimplerApprox(), sp.r.getSimplerApprox());
	}

	Tuple<Curve,Curve> split() {
		return split(0.5);
	}

	@Override
	public 
		IIntersections intersection(
			Path other) {
		return other.intersectionLCurve(this);
	}

	@Override
	public IIntersections intersectionLLine(
			Line lhs) {
		return super.intersectionLLine(lhs);
	}
	
	
	@Override
	public IIntersections intersectionLSet(
			ShapeSet lhs) {
		return lhs.intersectionLCurve(this).flip();
	}

	@Override
	public 
		BestProjectTup project(
			double best, Path other) {
		return other.projectLCurve(best, this);
	}

	@Override
	public BestProjectTup projectLSet(double best,
			ShapeSet lhs) {
		return lhs.projectLCurve(best, this).flip();
	}
	
	@Override
	public Tuple<Path, Double> normaliseToLength(
			double prevLength) {
		Tuple<SimplePath,SimplePath> sp = splitSimplerCurve();
		Tuple<Path, Double> l = sp.l.normaliseToLength(prevLength);
		Tuple<Path, Double> r = sp.r.normaliseToLength(l.r);
		return new Tuple<Path, Double>(
			getWithNewSimpleAndInterval(
					(SimplePath)l.l, (SimplePath)r.l, new Interval(prevLength,r.r)),
			r.r);
	}

	abstract Curve getWithNewSimpleAndInterval(
			SimplePath l, SimplePath l2, Interval interval) ;
	
	
	@Override
	public Path deformActual(IDeform p) {
		if(!isMonotomous()){
			Tuple<Path,Path> sp = splitSimpler();
			return new Append(sp.l.deform(p),
					sp.r.deform(p));
		} else {
			return super.deformActual(p);
		}
	
	}
	
	public SimplePath getWithAdjustedStartPointMono(Vec v ) {
		Curve c = (Curve) getWithAdjustedStartPoint(v);
		c.xyRoots = Collections.EMPTY_LIST;
		return c;
	}

	public SimplePath getWithAdjustedEndPointMono(Vec v){
		Curve c = getWithAdjustedEndPoint(v);
		c.xyRoots = Collections.EMPTY_LIST;
		return c;
	}

	public abstract Curve getWithAdjustedEndPoint(Vec newEnd) ;
	
	@Override
	public Path transformApproxLines(ILineTransformer t) {
		Tuple<SimplePath,SimplePath> sp = splitSimplerCurve();
		return PathFactory.createAppends(
				sp.l.transformApproxLines(t),
				sp.r.transformApproxLines(t));
	}
}
