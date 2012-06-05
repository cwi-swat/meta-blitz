package nogbeter.paths.simple.curve;

import java.util.Collections;
import java.util.List;

import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.SplittablePath;
import nogbeter.paths.compound.Append;
import nogbeter.paths.compound.SetIndex;
import nogbeter.paths.compound.ShapeSet;
import nogbeter.paths.factory.PathFactory;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.intersections.Intersections;
import nogbeter.paths.results.project.BestProjectTup;
import nogbeter.paths.simple.Line;
import nogbeter.paths.simple.SimplePath;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.points.oned.Interval;
import nogbeter.points.twod.Vec;
import nogbeter.transform.nonlinear.IDeform;
import nogbeter.transform.nonlinear.ILineTransformer;
import nogbeter.transform.nonlinear.pathdeform.PathDeform;
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
	
	public Tuple<SimplePath,SimplePath> splitSimp(double t){
		Tuple<Curve, Curve> sp = split(t);
		return new Tuple<SimplePath, SimplePath>(sp.l.getSimplerApprox(), sp.r.getSimplerApprox());
	}

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
	public IIntersections<SimplePathIndex, SimplePathIndex> intersectionLLine(
			Line lhs) {
		return super.intersectionLLine(lhs);
	}
	
	@Override
	public <LPP extends PathIndex> 
		IIntersections<LPP, SimplePathIndex> intersectionLSplittable(
			SplittablePath<LPP> lhs) {
		if(isMonotomous() && lhs == this){
			return (IIntersections<LPP, SimplePathIndex>) selfIntersection();
		} else {
			return super.intersectionLSplittable(lhs);
		}
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
		if(!sr.l.getStartPoint().isEqError(sr.l.getEndPoint())){
			result.add(sr.l);
		}
	}
	
	@Override
	public void getSubPathFrom(SimplePathIndex from, List<Path> result) {
		Tuple<Curve,Curve> sl = split(from.t);
		if(!sl.r.getStartPoint().isEqError(sl.r.getEndPoint())){
			result.add(sl.r);
		}
	}

	@Override
	public void getSubPathTo(SimplePathIndex to, List<Path> result) {
		Tuple<Curve,Curve> sl = split(to.t);
		if(!sl.l.getStartPoint().isEqError(sl.l.getEndPoint())){
			result.add(sl.l);
		}
		
	}
	
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
