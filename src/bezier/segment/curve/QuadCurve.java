package bezier.segment.curve;

import java.awt.Shape;
import java.awt.geom.PathIterator;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.List;

import bezier.composite.Path;
import bezier.points.Matrix;
import bezier.points.Vec;
import bezier.segment.Constants;
import bezier.segment.LengthMap;
import bezier.util.BBox;
import bezier.util.Tuple;
import bezier.util.Util;

public final class QuadCurve extends NonLinearCurve implements Curve{
	
	public final Vec p0,p1,p2;

	public QuadCurve(Vec p0, Vec p1, Vec p2) {
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
	}
	
	@Override
	public Vec getAt(double t) {
		double ot = 1.0 -t;
		double t2 = t * t;
		double ot2 = ot * ot;
		return new Vec(p0.x * ot2 + 2 * ot * t * p1.x + t2 * p2.x,
				p0.y * ot2 + 2 * ot * t * p1.y + t2 * p2.y);
	}

	@Override
	public Vec getTangentAt(double t) {
		double ot =2*(1-t);
		double t21 = 2*t;
		return new Vec(ot * (p1.x - p0.x) + t21 * (p2.x - p1.x),
				ot * (p1.y - p0.y) + t21 * (p2.y - p1.y));
	}

	@Override
	public Curve transform(Matrix m) {
		return new QuadCurve( m.mul(p0), m.mul(p1), m.mul(p2));
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
	public Double findX(double x) {
		return find(p0.x, p1.x, p2.x, x);
	}


	@Override
	public Curve reverse() {
		return new QuadCurve(p2, p1, p0);
	}

	@Override
	public  Tuple<Curve,Curve> split(double t) {
		Vec cl = p1.interpolate(t, p0);
		Vec cr = p2.interpolate(t, p1);
		Vec cm = cr.interpolate(t, cl);
		return new  Tuple<Curve,Curve>(new QuadCurve(p0,cl,cm),
										new QuadCurve(cm, cr, p2));
	}

	@Override
	public Vec getStartPoint() {
		return p0;
	}

	@Override
	public Vec getEndPoint() {
		return p2;
	}
	

	@Override
	BBox makeBBox() {
		return new BBox(p0,p2);
	}

	@Override
	Curve getSimplerApproximation() {
		Line l = new Line(p0,p2);
		if(getAt(0.5).distanceSquared(l.getAt(0.5)) <= Constants.HALF_MAX_ERROR_POW2){
			return l;
		} else {
			return this;
		}
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

	@Override
	public List<Curve> projectOn(Path p, LengthMap lm) {
		CubicCurve lifted = lift();
		return lifted.projectOn(p, lm);
	}

	public CubicCurve lift() {
		Vec newControl1 = p1.interpolate(2.0/3.0, p0 );
		Vec newControl2 = p1.interpolate(2.0/3.0, p2 );
		CubicCurve lifted = new CubicCurve(p0, newControl1, newControl2, p2);
		return lifted;
	}

	@Override
	public Curve getWithAdjustedStartPoint(Vec newStartPoint) {
		return new QuadCurve(newStartPoint, p1, p2);
	}

	@Override
	public int currentSegment(float[] coords) {
		coords[0] =(float) p1.x;
		coords[1] = (float)p1.y;
		coords[2] =(float) p2.x;
		coords[3] = (float)p2.y;
		return PathIterator.SEG_QUADTO;
	}

	

}
