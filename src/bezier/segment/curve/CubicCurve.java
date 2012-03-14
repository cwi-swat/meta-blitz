package bezier.segment.curve;

import static bezier.util.Util.findQuadraticPolynomialRoots;

import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

import bezier.composite.Path;
import bezier.points.Matrix;
import bezier.points.Vec;
import bezier.segment.Constants;
import bezier.segment.LengthMap;
import bezier.util.BBox;
import bezier.util.STuple;
public final class CubicCurve extends NonLinearCurve {

	public final Vec p0,p1,p2,p3;

	public CubicCurve(Vec p0, Vec p1, Vec p2, Vec p3) {
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}
	

	@Override
	public Vec getStartPoint() {
		return p0;
	}

	@Override
	public Vec getEndPoint() {
		return p3;
	}

	@Override
	public Vec getAt(double t) {
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
	public Vec getTangentAt(double t) {
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
	public Curve transform(Matrix m) {
		return new CubicCurve(m.mul(p0), m.mul(p1), m.mul(p2), m.mul(p3));
	}

	@Override
	public Curve reverse() {
		return new CubicCurve(p3,p2,p1,p0);
	}

	@Override
	BBox makeBBox() {
		return new BBox(p0,p3);
	}

	@Override
	public STuple<Curve> split(double t) {
		Vec l0, l1, l2, l3;
		Vec r0, r1, r2, r3;
		l0 = p0;
		r3 = p3;
		l1 = p1.interpolate(t, p0);
		Vec inter = p2.interpolate(t, p1);
		r2 = p3.interpolate(t, p2);
		l2 = inter.interpolate(t, l1);
		r1 = r2.interpolate(t, inter);
		l3 = r0 = r1.interpolate(t, l2);
		return new  STuple<Curve>(
				new CubicCurve(l0,l1,l2,l3),
				new CubicCurve(r0,r1,r2,r3));
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

	@Override
	Curve getSimplerApproximation() {
		Vec extendP1 = p0.interpolate(2.0/3.0, p1);
		Vec extendP2 =  p3.interpolate(2.0/3.0, p2);
		Vec middle = extendP1.interpolate(0.5, extendP2);
		QuadCurve simpler = new QuadCurve(p0,middle,p3);
		Vec fromCubic = getAt(Constants.T_MAX_DIFF_CUBIC_QUADRATIC);
		Vec fromQuad = simpler.getAt(Constants.T_MAX_DIFF_CUBIC_QUADRATIC);
		if(fromCubic.distanceSquared(fromQuad) <= Constants.HALF_MAX_ERROR_POW2){
			return simpler;
		} else {
			return this;
		}
	}

	@Override
	Double findX(double x) {
		return find(p0.x,p1.x, p2.x, p3.x, x);
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

	public String toString(){
		return String.format("Cubic %s %s %s %s",p0,p1,p2,p3);
	}


	
	
	@Override
	public List<Curve> projectOn(Path p, LengthMap lm) {
		double tStart = lm.findT(p0.x);
		double tEnd = lm.findT(p3.x);
		int from = (int)tStart;
		int till = (int)tEnd;
		if(tEnd == till){
			till --;
		}
		List<Curve> result = new ArrayList<Curve>();
//		if(from != till){
//			int splitAt = from+1;
//			double splitMe = findX(lm.findT(splitAt));
//			if(splitMe != 1.0 && splitMe!= 0.0){
//				Tuple<Curve,Curve> s = split(splitMe);
//	
//				result.addAll(s.l.projectOn(p, lm));
//				result.addAll(s.r.projectOn(p, lm));
//				return result;
//			}
//		} 
			result.add(new CubicCurve(p.projectVec(p0,lm), p.projectVec(p1,lm), 
					p.projectVec(p2,lm), p.projectVec(p3,lm)));
			return result;
	}


	@Override
	public Curve lift() {
		return this;
	}


	@Override
	public Curve getWithAdjustedStartPoint(Vec newStartPoint) {
		return new CubicCurve(newStartPoint, p1, p2, p3);
	}


	@Override
	public int currentSegment(float[] coords) {
		coords[0] = (float)p1.x;
		coords[1] = (float)p1.y;
		coords[2] = (float)p2.x;
		coords[3] = (float)p2.y;
		coords[4] = (float)p3.x;
		coords[5] = (float)p3.y;
		return PathIterator.SEG_CUBICTO;
	}
	


}
