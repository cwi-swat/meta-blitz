package bezier.segment.curve;

import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import bezier.composite.Path;
import bezier.points.Matrix;
import bezier.points.Vec;
import bezier.segment.LengthMap;
import bezier.segment.TPair;
import bezier.util.BBox;
import bezier.util.Tuple;
import bezier.util.Util;


public final class Line implements Curve{

	public final Vec start, dir, end;
	
	public Line(Vec start, Vec end) {
		this.start = start;
		this.dir = end.sub(start);
		this.end = end;
	}
	
	public boolean isVertical(){
		return dir.x == 0;
	}
	
	public boolean isHorizontal(){
		return dir.y == 0;
	}
	
	public TPair intersection(Line r){
		Vec cross = start.sub(r.start);
		double solDiv = (r.dir.y * dir.x - r.dir.x * dir.y);
		double sol =
			(r.dir.x * cross.y - r.dir.y * cross.x) /
			solDiv;
		if(sol>= 0 && sol <= 1){
			double sol2 =
					(dir.x * cross.y - dir.y * cross.x) /
					solDiv;
			if(sol2>= 0 && sol2 <= 1){
				return new TPair(sol, sol2);
			}
		} 
		return null;
	}
	
	public double closestT(Vec p){
		double qx = start.x - p.x;
		double qy = start.y - p.y;
		if(dir.x == dir.y && dir.x == 0){
			return 0.5;
		}
		return Util.clamp(-(dir.x * qx + dir.y * qy) / (dir.x * dir.x + dir.y * dir.y));
	}
	
	public TPair closestTs(Line r){
		TPair res = intersection(r);
		if(res == null){
			double prs = r.closestT(start);
			double pre = r.closestT(end);
			double pls = closestT(r.start);
			double ple = closestT(r.end);
			double drs = start.distanceSquared(r.getAt(prs));
			double dre = end.distance(r.getAt(pre));
			double dls = r.start.distance(getAt(pls));
			double dle = r.end.distance(getAt(ple));
			
			double min = Util.min4(drs, dre, dls, dle);
			if(min == drs){
				return new TPair(0,prs);
			} else if(min == dre){
				return new TPair(1,prs);
			} else if(min == dls){
				return new TPair(pls, 0);
			} else {
				return new TPair(ple,1);
			}
		} else {
			return res;
		}
	}
	
	public boolean isAboveLine(Vec p){
		double t = (p.x - start.x) / (dir.x);
		return t >= 0 || t < 1 && start.y + dir.y * t < p.y;
	}


	public Vec getAt(double t){
		return start.add(dir.mul(t));
	}

	
	@Override
	public Vec getTangentAt(double t) {
		return dir;
	}

	@Override
	public Curve transform(Matrix m) {
		return new Line(m.mul(start), m.mul(end));
	}
	
	private Double find(double sx,double dx, double xf){
		if(dx == 0){
			return null;
		} else {
			double res = (xf - sx)/dx;
			if(res >= 0 && res <= 1){
				return res;
			} else {
				return null;
			}
		}
	}

	public double getTAtY(double y){
		return (y - start.y)/dir.y;
	}
	
	public double getTAtX(double x){
		return (x - start.x)/dir.x;
	}
	
	public Double findX(double x) {
		return find(start.x, dir.x, x);
	}

	public Double findY(double y) {
		return find(start.y, dir.y, y);
	}

	@Override
	public int nrBelow(Vec p) {
		if(p.x == end.x){
			return 0;
		}
		Double fx = findX(p.x);
		return fx != null &&  getAt(fx).y < p.y ? 1 :0;
	}

	@Override
	public Curve reverse() {
		return new Line(end,start);
	}

	@Override
	public Shape toAWT() {
		return new Line2D.Double(start.x, start.y, end.x , end.y);
	}

	@Override
	public Vec getStartPoint() {
		return start;
	}

	@Override
	public Vec getEndPoint() {
		return end;
	}

	@Override
	public boolean isLine() {
		return true;
	}

	@Override
	public Line getLine() {
		return this;
	}

	@Override
	public boolean overlapsWith(BBox r) {
		return r.overlaps(this);
	}

	@Override
	public boolean fastIntersectionCheck(Curve other) {
		return other.fastIntersectionCheck(this);
	}

	@Override
	public Tuple<Curve, Curve> splitSimpler() {
		throw new Error("Cannot make line simpler!");
	}
	
	@Override
	public String toString(){
		return String.format("Line %s -> %s",start.toString(),end.toString());
	}

	@Override
	public Tuple<Curve, Curve> split(double t) {
		Vec middle = getAt(t);
		return new Tuple<Curve,Curve>(new Line(start,middle), new Line(middle,end));
	}

	@Override
	public BBox getBBox() {
		return new BBox(start,end);
	}

	@Override
	public List<Curve> makeMonotomous() {
		List<Curve> c = new ArrayList<Curve>(1);
		c.add(this);
		return c;
	}

	@Override
	public void fillLengthMap(LengthMap map, double samplesPerDirect) {
		map.add(1.0, dir.norm());
	}

	@Override
	public List<Curve> projectOn(Path p, LengthMap lm) {
		CubicCurve lifted = lift();
		return lifted.projectOn(p, lm);
	}

	public CubicCurve lift() {
		Vec newControl1 = end.interpolate(2.0/6.0, start);
		Vec newControl2 = end.interpolate(4.0/6.0, start);
		CubicCurve lifted = new CubicCurve(start, newControl1, newControl2, end);
		return lifted;
	}

	@Override
	public Curve getWithAdjustedStartPoint(Vec newStartPoint) {
		return new Line(newStartPoint,end);
	}
	
}
