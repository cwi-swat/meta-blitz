package nogbeter.paths.simple.nonlinear;

import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.factory.PathFactory;
import nogbeter.paths.simple.SimplePath;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.points.oned.Interval;
import nogbeter.points.twod.BBox;
import nogbeter.points.twod.Vec;
import nogbeter.transform.AffineTransformation;
import bezier.paths.Constants;
import bezier.util.STuple;
import bezier.util.Tuple;
import bezier.util.Util;

public class QuadCurve extends Curve{

	
	public final Vec p0,p1,p2;
	
	public QuadCurve(Vec p0,Vec p1, Vec p2, Interval tInterval){
		this(p0,p1,p2,tInterval,null,null);
	}
	
	public QuadCurve(Vec p0,Vec p1, Vec p2, Interval tInterval,SimplePath lsimp, SimplePath rsimp) {
		super(tInterval,lsimp,rsimp);
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
	}
	
	private double getRoot(double x0, double x1, double x2){
		return (x0 - x1)/(x2 - 2*x1 + x0);
	}
	
	@Override
	List<Double> getXYRoots() {
		double rx = getRoot(p0.x, p1.x, p2.x);
		double ry = getRoot(p0.y, p1.y, p2.y);
		List<Double> result = new ArrayList<Double>();
		if(rx > 0 && rx < 1){
			result.add(rx);
		}
		if(ry > 0 && ry < 1){
			result.add(ry);
		}
		return result;
	}
	
	public String toString(){
		return String.format("Quad %s %s %s",p0,p1,p2);
	}

	Double find(double x0,double x1, double x2, double x){
		double a= x2 -2*x1 + x0;
		double b = 2 * (x1 - x0);
		double c = x0 - x;
		List<Double> roots = Util.findQuadraticPolynomialRoots(a, b, c);
		for(double d : roots){
			if(d >= 0 && d <= 1){
				return d;
			}
		}
		return null;
	}
	
	@Override
	Double findX(double x) {
		return find(p0.x, p1.x, p2.x, x);
	}

	@Override
	Double findY(double y) {
		return find(p0.y, p1.y, p2.y, y);
	}

	@Override
	public
	Tuple<Curve,Curve> split(double t) {
		Vec cl = p0.interpolate(t, p1);
		Vec cr = p1.interpolate(t, p2);
		Vec cm = cl.interpolate(t, cr);
		STuple<Interval> st = tInterval.split();
		return new Tuple<Curve,Curve>( new QuadCurve(p0,cl,cm,st.l),
				 new QuadCurve(cm,cr,p2,st.r));
	}



	@Override
	protected
	SimplePath getSimplerApprox() {
		SimplePath l = PathFactory.createLine(p0, p2,tInterval);
		if(getAtLocal(0.5).distanceSquared(l.getAtLocal(0.5)) <= Constants.HALF_MAX_ERROR_POW2){
			return l;
		} else {
			return this;
		}
	}

	@Override
	public Vec getAtLocal(double t) {
		double ot = 1.0 -t;
		double t2 = t * t;
		double ot2 = ot * ot;
		return new Vec(p0.x * ot2 + 2 * ot * t * p1.x + t2 * p2.x,
				p0.y * ot2 + 2 * ot * t * p1.y + t2 * p2.y);
	}

	@Override
	public Vec getTangentAtLocal(double t) {
		double ot =2*(1-t);
		double t21 = 2*t;
		return new Vec(ot * (p1.x - p0.x) + t21 * (p2.x - p1.x),
				ot * (p1.y - p0.y) + t21 * (p2.y - p1.y));
	}

	@Override
	public QuadCurve getWithAdjustedStartPoint(Vec newStartPoint) {
		return PathFactory.createQuad(newStartPoint,p1,p2,tInterval);
	}

	@Override
	public
	BBox makeBBox() {
		return BBox.fromPoints(p0,p1,p2);
	}

	@Override
	public int awtCurSeg(float[] coords) {
		coords[0] = (float)p1.x;
		coords[1] = (float)p1.y;
		coords[2] = (float)p2.x;
		coords[3] = (float)p2.y;
		return PathIterator.SEG_QUADTO;
	}


	@Override
	public QuadCurve transform(
			AffineTransformation t) {
		return PathFactory.createQuad(t.to(p0),t.to(p1),t.to(p2),tInterval);
	}


	@Override
	Curve getWithNewSimpleAndInterval(SimplePath lsimp,
			SimplePath rsimp, Interval interval) {
		return new QuadCurve(p0, p1, p2,  interval, lsimp, rsimp);
	}

	@Override
	public Path<PathIndex> reverse() {
		return (Path)PathFactory.createQuad(p2, p1, p0);
	}

}
