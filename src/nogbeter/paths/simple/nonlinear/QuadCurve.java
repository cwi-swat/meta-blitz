package nogbeter.paths.simple.nonlinear;

import java.util.ArrayList;
import java.util.List;

import nogbeter.paths.ConnectedPath;
import nogbeter.paths.Path;
import nogbeter.paths.simple.SimplePath;
import nogbeter.paths.simple.SimplePathFactory;
import nogbeter.paths.simple.lines.DiagonalLine;
import nogbeter.paths.simple.lines.HorizontalLine;
import nogbeter.paths.simple.lines.LineApprox;
import nogbeter.paths.simple.lines.VerticalLine;
import bezier.paths.Constants;
import bezier.points.Vec;
import bezier.util.BBox;
import bezier.util.STuple;
import bezier.util.Tuple;
import bezier.util.Util;

public class QuadCurve extends NonLinearCurve{

	
	public final Vec p0,p1,p2;
	
	public QuadCurve(Vec p0,Vec p1, Vec p2){
		this(p0,p1,p2,0,1);
	}
	
	public QuadCurve(Vec p0,Vec p1, Vec p2, double tStart,double tEnd) {
		super(tStart,tEnd);
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
	STuple<NonLinearCurve> split(double t) {
		Vec cl = p0.interpolate(t, p1);
		Vec cr = p1.interpolate(t, p2);
		Vec cm = cl.interpolate(t, cr);
		double midT = tMid(t);
		return new STuple<NonLinearCurve>( new QuadCurve(p0,cl,cm,tStart,midT),
				 new QuadCurve(cm,cr,p2,midT,tEnd));
	}



	@Override
	protected
	SimplePath getSimplerApproximation() {
		SimplePath l = new LineApprox(SimplePathFactory.createLine(p0, p2),tStart,tEnd);
		if(getAt(0.5).distanceSquared(l.getAt(0.5)) <= Constants.HALF_MAX_ERROR_POW2){
			return l;
		} else {
			return this;
		}
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
	public SimplePath reverse() {
		return new QuadCurve(p2,p1, p0);
	}

	@Override
	public SimplePath getWithAdjustedStartPoint(Vec newStartPoint) {
		return SimplePathFactory.createQuad(newStartPoint,p1,p2);
	}

	@Override
	public
	BBox makeBBox() {
		if(isMonotomous()){
			return new BBox(p0,p2);
		} else {
			return BBox.fromPoints(p0,p1,p2);
		}
	}


}
