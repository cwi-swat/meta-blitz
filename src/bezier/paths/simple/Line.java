package bezier.paths.simple;

import java.awt.geom.PathIterator;
import java.util.List;

import bezier.paths.ConnectedPath;
import bezier.paths.Path;
import bezier.paths.factory.PathFactory;
import bezier.paths.util.ITransform;
import bezier.paths.util.PathParameter;
import bezier.paths.util.TPair;
import bezier.points.Vec;
import bezier.util.BBox;
import bezier.util.STuple;
import bezier.util.Util;

public final class Line extends SimplePath{

	public final Vec start, dir, end;
	
	public Line(Vec start,Vec end){
		this(start,end,0,1);
	}
	
	public Line(Vec start,Vec end, double tstart, double tend) {
		super(tstart, tend);
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
	
	public double distanceSquared(Vec p){
		return getAt(closestT(p)).distanceSquared(p);
	}
	
	public double closestT(Vec p){
		return Util.clamp(closestTAll(p));
	}

	public double closestTAll(Vec p) {
		return -(dir.y * start.y + dir.x * start.x - dir.y * p.y - dir.x * p.x)/ (dir.x* dir.x + dir.y * dir.y);
	}
	
	public Double closestTNormal(Vec p){
		double t = closestTAll(p);
		if(t < 0 || t >= 1){
			return null;
		} else {
			return t;
		}
		
	}
	
	public double distanceSquared(Line r){
		TPair cl = closestTs(r);
		return getAt(cl.tl).distanceSquared(r.getAt(cl.tr));
	}
	
	public TPair closestTs(Line r){
		TPair res = intersection(r);
		if(res == null){
			double prs = r.closestT(start);
			double pre = r.closestT(end);
			double pls = closestT(r.start);
			double ple = closestT(r.end);
			double drs = start.distanceSquared(r.getAt(prs));
			double dre = end.distanceSquared(r.getAt(pre));
			double dls = r.start.distanceSquared(getAt(pls));
			double dle = r.end.distanceSquared(getAt(ple));
			
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

	public Vec getTangentAt(double t) {
		return dir;
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

	public int nrBelow(Vec p) {
		if(p.x == end.x){
			return 0;
		}
		Double fx = findX(p.x);
		return fx != null &&  getAt(fx).y < p.y ? 1 : 0 ;
	}

	public ConnectedPath reverse() {
		return new Line(end,start,tEnd,tStart);
	}

	public Vec getStartPoint() {
		return start;
	}

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
	public String toString(){
		return String.format("Line %s -> %s",start.toString(),end.toString());
	}

	@Override
	public BBox makeBBox() {
		return new BBox(start,end);
	}

	public ConnectedPath getWithAdjustedStartPoint(Vec newStartPoint) {
		return new Line(newStartPoint,end,tStart,tEnd);
	}


	public int currentSegmentAWT(float[] coords) {
		
		coords[0] =(float) end.x;
		coords[1] = (float)end.y;
		return PathIterator.SEG_LINETO;
	}

	public double length() {
		return end.distance(start);
	}


	public Vec getNormal() {
		return dir.perpendicularCCW().normalize();
	}

	public double findTForX(double x) {
		return getTAtX(x);
	}


	@Override
	public
	void expand() {
	}

	@Override
	public
	Path getLeftSimpler() {
		throw new Error("Cannot make Line simpler!");
	}

	@Override
	public
	Path getRightSimpler() {
		throw new Error("Cannot make Line simpler!");
	}

	public void intersectionLine(Line line, ReportType type, PathParameter lParent, PathParameter rParent, List<PathParameter> lres,List<PathParameter> rres) {
		addDoubleResult(intersection(line), line,type,lParent,rParent, lres,rres);
	}

	@Override
	public
	Path transform(ITransform m) {
		return new Line(m.transform(start),m.transform(end),tStart,tEnd);
	}

	@Override
	public
	STuple<Path> splitSimpler() {
		throw new Error("Cannot make line simpler!");
	}

	@Override
	public ConnectedPath getSubPath(double start, double end) {
		return PathFactory.createLine(getAt(start),getAt(end));
	}


	
	
	
}
