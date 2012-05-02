package nogbeter.paths.simple.nonlinear;

import static bezier.util.Util.findQuadraticPolynomialRoots;

import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.compound.SetIndex;
import nogbeter.paths.compound.ShapeSet;
import nogbeter.paths.factory.PathFactory;
import nogbeter.paths.results.project.BestProjectTup;
import nogbeter.paths.simple.SimplePath;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.points.oned.Interval;
import nogbeter.points.twod.BBox;
import nogbeter.points.twod.Vec;
import nogbeter.transform.AffineTransformation;
import bezier.paths.Constants;
import bezier.util.STuple;
import bezier.util.Tuple;

public class CubicCurve extends Curve{

	public final Vec p0,p1,p2,p3;

	
	public CubicCurve(Vec p0, Vec p1, Vec p2, Vec p3, Interval tInterval) {
		super(tInterval);
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}

	private static List<Double> getExtremes(double v0, double v1, double v2, double v3){
		double a = (v3 - 3*v2 + 3*v1 - v0)*3;
		double b = (v2 - 2 * v1 +  v0)*6;
		double c = (v1 - v0)*3;
		List<Double> vals = findQuadraticPolynomialRoots(a,b,c);
		List<Double> res = new ArrayList<Double>();
		for(double v : vals){
			if(v > 0 && v < 1.0){
				res.add(v);
			}
		}
		return res;
	}

	@Override
	List<Double> getXYRoots() {
		List<Double> result = getExtremes(p0.x, p1.x, p2.x, p3.x);
		result.addAll(getExtremes(p0.y,p1.y,p2.y,p3.y));
		return result;
	}


	private static double find(double x0, double x1, double x2, double x3, double x) {
		double t = 0.5;
		double cderiv = (x1 - x0)*3;
		double func;
		double derv1 = (x3 - 3*x2 + 3*x1 - x0)*3;
		double derv2 = (x2 - 2 * x1 +  x0)*6;
		// find the root using newton's method for fast convergence
		do{
			double rt = 1.0 - t;
			double rt2 = rt*rt;
			double rt3 = rt2*rt;
			double t2 = t*t;
			double t3 = t*t2;
			double c3rt2t = 3*rt2*t;
			double c3rtt2 = 3*rt*t2;
			func = rt3*x0 + c3rt2t*x1 + c3rtt2*x2 + t3*x3 - x;
			double derivative = derv1* t2 + derv2*t + cderiv ;
			t = t - func / derivative;
		} while(Math.abs(func) > Constants.MAX_ERROR_X_POW_2);
		return t;
	}
	
	@Override
	Double findX(double x) {
		return find(p0.x,p1.x, p2.x, p3.x, x);
	}

	@Override
	Double findY(double y) {
		return find(p0.y,p1.y, p2.y, p3.y, y);
	}

	@Override
	SimplePath getSimplerApprox() {
		Vec extendP1 = p0.interpolate(1.0/3.0, p1);
		Vec extendP2 =  p3.interpolate(1.0/3.0, p2);
		Vec middle = extendP1.interpolate(0.5, extendP2);
		SimplePath simpler = new QuadCurve(p0,middle,p3,tInterval);
		Vec fromCubic = getAtLocal(Constants.T_MAX_DIFF_CUBIC_QUADRATIC);
		Vec fromQuad = simpler.getAtLocal(Constants.T_MAX_DIFF_CUBIC_QUADRATIC);
		if(fromCubic.distanceSquared(fromQuad) <= Constants.HALF_MAX_ERROR_POW2){
			return simpler;
		} else {
			return this;
		}
	}

	@Override
	Tuple<CubicCurve,CubicCurve> split(double t) {
		Vec l0, l1, l2, l3;
		Vec r0, r1, r2, r3;
		l0 = p0;
		r3 = p3;
		l1 = p0.interpolate(t, p1);
		Vec inter = p1.interpolate(t, p2);
		r2 = p2.interpolate(t, p3);
		l2 = l1.interpolate(t, inter);
		r1 = inter.interpolate(t, r2);
		l3 = r0 = l2.interpolate(t, r1);
		STuple<Interval> st = tInterval.split();
		return new  Tuple<CubicCurve,CubicCurve>(
				new CubicCurve(l0,l1,l2,l3,st.l),
				new CubicCurve(r0,r1,r2,r3,st.r));
	}


	@Override
	public Vec getAtLocal(double t) {
		double rt = 1.0 - t;
		double rt2 = rt*rt;
		double rt3 = rt2*rt;
		double t2 = t*t;
		double t3 = t*t2;
		double c3rt2t = 3*rt2*t;
		double c3rtt2 = 3*rt*t2;
		return new Vec(rt3*p0.x + c3rt2t*p1.x + c3rtt2*p2.x + t3*p3.x,
						 rt3*p0.y + c3rt2t*p1.y + c3rtt2*p2.y + t3*p3.y);
	}

	@Override
	public Vec getTangentAtLocal(double t) {
		double ax = (p3.x - 3*p2.x + 3*p1.x - p0.x)*3;
		double bx = (p2.x - 2 * p1.x +  p0.x)*6;
		double cx = (p1.x - p0.x)*3;
		double ay = (p3.y - 3*p2.y + 3*p1.y - p0.y)*3;
		double by = (p2.y - 2 * p1.y +  p0.y)*6;
		double cy = (p1.y - p0.y)*3;
		double t2 = t*t;
		return new Vec(ax*t2 + bx * t + cx, ay*t2 + by * t + cy);
	}


	@Override
	public CubicCurve getWithAdjustedStartPoint(Vec newStart) {
		return PathFactory.createCubic(newStart,p1,p2,p3,tInterval);
	}

	@Override
	public
	BBox makeBBox() {
		return BBox.fromPoints(p0,p1,p2,p3);
	}


	@Override
	public int awtCurSeg(float[] coords) {
		coords[0] = (float)p1.x;
		coords[1] = (float)p1.y;
		coords[2] = (float)p2.x;
		coords[3] = (float)p2.y;
		coords[4] = (float)p3.x;
		coords[5] = (float)p3.y;
		return PathIterator.SEG_QUADTO;
	}

	@Override
	public Path<SimplePathIndex, SimplePath, SimplePath> transform(
			AffineTransformation t) {
		return PathFactory.createCubic(t.to(p0),t.to(p1),t.to(p2),t.to(p3),tInterval);
	}


}
